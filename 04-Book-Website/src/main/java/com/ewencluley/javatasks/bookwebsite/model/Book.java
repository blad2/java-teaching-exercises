package com.ewencluley.javatasks.bookwebsite.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.el.stream.Optional;

public class Book{
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
    public String getAuthor() {
        return author;
    }
    public int getPages() {
        return pages;
    }
}
