package com.ewencluley.javatasks.springboot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Error {
    @JsonProperty
    String error;

    @JsonCreator
    public Error(@JsonProperty("error") String error) {
        this.error = error;
    }
}
