package com.devxschool.framework.api.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    private String name;
    private String description;
    private String image;
    private double price;
    private String status;

    private int id;
    @JsonProperty("discount_amount")
    private double discountAmount;
    private List<Categories> categories;


}
