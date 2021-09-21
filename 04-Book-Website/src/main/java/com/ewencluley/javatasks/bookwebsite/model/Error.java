package com.ewencluley.javatasks.bookwebsite.model;

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
