package com.increff.pos.model.data;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public abstract class BaseData {

    protected ZonedDateTime createdAt;
    protected ZonedDateTime updatedAt;
    protected Integer version;

}
