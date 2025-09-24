package com.increff.pos.pojo;

import lombok.Data;

import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "orders")
public class OrderPojo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,updatable = false)
    private ZonedDateTime createdTime;

    @Column(nullable = false)
    private ZonedDateTime updatedTime;

}