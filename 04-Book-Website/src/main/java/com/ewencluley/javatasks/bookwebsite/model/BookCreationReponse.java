package com.ewencluley.javatasks.bookwebsite.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@JsonPropertyOrder({"status", "book", "receivedAt"})
// Added the annotation above because, it was returning a different order (book, status time).
// and this fixed it. I know the order would not matter, but wanted to add it as it shows in the example.
public class BookCreationReponse {
    @JsonProperty
    String status;
    @JsonProperty
    Book book;
    @JsonFormat(pattern = "")
    String receivedAt;

    @JsonCreator
    public BookCreationReponse(@JsonProperty("book") Book book) {
        this.status = "ACCEPTED";
        this.book = book;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        this.receivedAt = df.format(new Date());
    }
}