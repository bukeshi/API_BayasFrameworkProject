package com.devxschool.framework.api.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RebrandlyLink {
    private String id;
    private String title;
    private String destination;
    private int clicks;
    private boolean isPublic;
    private String domainId;
    private String status;
    private Domain domain;
    private Creator creator;

}
