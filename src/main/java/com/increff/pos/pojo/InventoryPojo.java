package com.increff.pos.pojo;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "inventory")
public class InventoryPojo {

    @Id
    private Integer productId;

    @Column(nullable = false)
    private Integer quantity;

}