package com.devxschool.framework.api.pojos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


@Data
@JsonIgnoreProperties({"code","meta"})
public class UserResponseList {
    @JsonProperty("data")
    private List<User> userList;

}
