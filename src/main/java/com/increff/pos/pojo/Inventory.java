package com.increff.pos.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Inventory extends BaseEntity{

    @Id
    private Integer productId;

    @Column(nullable = false)
    private Integer quantity;

}