package com.ewencluley.javatasks.bookwebsite.controllers;

import com.ewencluley.javatasks.springboot.model.Book;
import com.ewencluley.javatasks.springboot.model.BookCreationReponse;
import com.ewencluley.javatasks.springboot.model.BookNotFoundException;
import com.ewencluley.javatasks.springboot.model.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class StatusController {

    @JsonProperty
    Map<String, Book> bookTitle = new HashMap<>();

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public Status status() {
        return new Status("OK");
    }

    @RequestMapping(value = "/book", method = RequestMethod.POST)
    public BookCreationReponse book(@RequestBody Book bookRequest) {
        bookTitle.put(bookRequest.getTitle().toLowerCase(), bookRequest);
        return new BookCreationReponse(bookRequest);
    }

    @RequestMapping(value = "/book/{bookTitle}", produces = {"application/json"}, method = RequestMethod.GET)
    @ResponseBody
    public Book getBook(@PathVariable("bookTitle") String bookTitle) throws BookNotFoundException {
        Book book = this.bookTitle.get(bookTitle);
        if (book == null) {
            throw new BookNotFoundException("Not found");
        }
        return book;
    }
}
