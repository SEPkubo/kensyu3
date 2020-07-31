package com.example.demo.dto;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import lombok.Data;

@Embeddable
@Data
public class StatusName implements Serializable {

    @Embedded
    private int customer_id;

    private int status_id;
}
