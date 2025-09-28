package com.increff.pos.model.data;

import com.increff.pos.model.enums.ProductStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductData extends BaseData {

    private Integer id;
    private String barcode;
    private String name;
    private String category;
    private Double mrp;
    private Double costPrice;
    private String imageUrl;
    private Integer clientId;
    private ProductStatus status;

}
