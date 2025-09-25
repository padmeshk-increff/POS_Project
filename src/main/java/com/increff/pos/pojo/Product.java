package com.increff.pos.pojo;

import com.increff.pos.model.enums.ProductStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"barcode"}))
public class Product extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String barcode;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private Double mrp;

    @Column(nullable = false)
    private Double costPrice;

    private String imageUrl;

    @Column( nullable = false)
    private Integer clientId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status;
}