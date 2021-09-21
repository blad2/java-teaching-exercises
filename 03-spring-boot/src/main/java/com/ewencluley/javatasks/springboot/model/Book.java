package com.ewencluley.javatasks.springboot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Book {
    @JsonProperty
    String title;
    @JsonProperty
    String author;
    @JsonProperty
    int pages;

    @JsonCreator
    public Book(@JsonProperty("title") String title, @JsonProperty("author") String author,@JsonProperty("pages") int pages) {
        this.title = title;
        this.author = author;
        this.pages = pages;
    }

    public String getTitle() {
        return title;
    }
}
