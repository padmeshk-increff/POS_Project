package com.increff.pos.flow;

import com.increff.pos.api.ClientApi;
import com.increff.pos.api.InventoryApi;
import com.increff.pos.api.ProductApi;
import com.increff.pos.commons.exception.ApiException;
import com.increff.pos.entity.Client;
import com.increff.pos.entity.Inventory;
import com.increff.pos.entity.Product;
import com.increff.pos.model.result.ConversionResult;
import com.increff.pos.model.data.ProductData;
import com.increff.pos.utils.ProductUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Transactional(rollbackFor = ApiException.class)
public class ProductFlow {

    @Autowired
    private ProductApi productApi;

    @Autowired
    private ClientApi clientApi;

    @Autowired
    private InventoryApi inventoryApi;

    public Product insert(Product product) throws ApiException {
        clientApi.getCheckById(product.getClientId());
        Product insertedProduct = productApi.insert(product);

        Inventory inventory = new Inventory();
        inventory.setProductId(product.getId());
        inventory.setQuantity(0); //create an inventory for the product by default

        inventoryApi.insert(inventory);
        return insertedProduct;
    }

    public ProductData convert(Product product) throws ApiException{
        ProductData productData = ProductUtil.convert(product);
        Inventory inventory = inventoryApi.getCheckByProductId(product.getId());
        Client client = clientApi.getById(product.getClientId());
        productData.setQuantity(inventory.getQuantity());
        productData.setClientName(client.getClientName());
        return productData;
    }

    public List<ProductData> convert(List<Product> products) throws ApiException{
        List<ProductData> productsData = new ArrayList<>();
        for(Product product:products){
            productsData.add(convert(product));
        }
        return productsData;
    }

    public void deleteById(Integer id) throws ApiException{
        productApi.deleteById(id);
        inventoryApi.deleteById(id);
    }

    public ConversionResult<Product> convert(MultipartFile file) throws ApiException {
        if (file.isEmpty()) {
            throw new ApiException("File is empty. Please upload a valid TSV file.");
        }

        List<Product> validProductList = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        int rowNumber = 1; // To track the row number for clear error messages

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line = reader.readLine(); // Skip header
            if (line == null) {
                throw new ApiException("File is empty or has no header.");
            }

            while ((line = reader.readLine()) != null) {
                rowNumber++;

                if(line.trim().isEmpty()){
                    continue;
                }

                String[] tokens = line.split("\t");

                try {
                    // Delegate all logic for one row to the helper method.
                    Product product = processProductRow(tokens, productApi, clientApi);
                    validProductList.add(product);
                } catch (ApiException e) {
                    errors.add("Error in row #" + rowNumber + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new ApiException("Failed to read the uploaded file. Ensure it's a valid TSV.");
        }

        // Prepare the final conversion result
        ConversionResult<Product> conversionResult = new ConversionResult<>();
        conversionResult.setValidRows(validProductList);
        conversionResult.setErrors(errors);
        return conversionResult;
    }

    /**
     * A dedicated helper to process a single row from the product TSV file.
     * It handles parsing, validation (including DB checks), and entity creation.
     */

    public void upload(List<Product> productList, List<String> errors) throws ApiException {
        // Use a Set to efficiently track barcodes from the file to detect duplicates.
        Set<String> barcodesInFile = new HashSet<>();

        // --- FATAL ERROR CHECK ---
        // First, loop through the list to check for any structural/fatal errors
        // that should invalidate the entire batch, like duplicate barcodes in the file.
        for (Product product : productList) {
            if (barcodesInFile.contains(product.getBarcode())) {
                // This is a fatal error. The file is ambiguous and should be rejected completely.
                // Throwing the exception here will be caught by @Transactional, causing a full rollback.

                throw new ApiException("Duplicate barcode '" + product.getBarcode() + "' found in the file.");
            }
            barcodesInFile.add(product.getBarcode());
        }

        // --- ROW-LEVEL PROCESSING ---
        // If we've passed the fatal error checks, proceed to insert each product individually.
        for (Product product : productList) {
            try {
                // --- ROW-LEVEL VALIDATION ---
                // This logic is inside a try-catch block. If an exception is thrown here,
                // it will be caught and recorded. It will NOT cause a full rollback.
                if (product.getMrp() < 0) {
                    throw new ApiException("MRP cannot be negative for barcode '" + product.getBarcode() + "'");
                }
                // You can add other row-level business logic checks here.
                // --- DATABASE OPERATION ---
                // If all checks for this specific product pass, insert it into the database.
                insert(product); // Assuming 'insert' is the method to save a single product.

            } catch (ApiException e) {
                // Catch the row-level error, add it to the list, and continue the loop.
                errors.add(e.getMessage());
            }
        }
    }

    private Product processProductRow(String[] tokens, ProductApi productApi, ClientApi clientApi) throws ApiException {
        // Step 1: Structural Validation
        if (tokens.length != 5) {
            throw new ApiException("Invalid number of columns. Expected 5, found " + tokens.length);
        }

        // Step 2: Parsing and Data Type Validation
        String barcode = tokens[0].trim().toLowerCase();
        String name = tokens[1].trim();
        String clientName = tokens[3].trim().toLowerCase();
        String category = tokens[4].trim();
        Double mrp;

        try {
            mrp = Double.parseDouble(tokens[2]);
        } catch (NumberFormatException e) {
            throw new ApiException("Invalid number format for MRP: '" + tokens[2] + "'");
        }

        // Step 3: Business Logic Validation
        if (mrp < 0) {
            throw new ApiException("MRP cannot be negative.");
        }
        if (productApi.getByBarcode(barcode) != null) {
            throw new ApiException("Barcode '" + barcode + "' already exists.");
        }
        Client client = clientApi.getByName(clientName);
        if (client == null) {
            throw new ApiException("Client with name '" + clientName + "' does not exist.");
        }

        // Step 4: Entity Creation
        Product p = new Product();
        p.setBarcode(barcode);
        p.setName(name);
        p.setMrp(mrp);
        p.setClientId(client.getId());
        p.setCategory(category);
        return p;
    }

}
