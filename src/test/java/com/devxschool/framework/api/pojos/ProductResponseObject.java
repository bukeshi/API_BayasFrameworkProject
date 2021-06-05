package com.devxschool.framework.api.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties({"meta"})
public class ProductResponseObject {
    @JsonProperty("data")
    private Product product;
    @JsonProperty("code")
    private int bodyStatCode;
}
