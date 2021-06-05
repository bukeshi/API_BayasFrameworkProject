package com.devxschool.framework.api.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
@JsonIgnoreProperties({"meta"})
public class ProductResponseList {

    @JsonProperty("data")
    private List<Product> productList;
    @JsonProperty("code")
    private int bodyStatCode;

}
