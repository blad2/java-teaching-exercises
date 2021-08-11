package com.ewencluley.javatasks.springboot.controllers;

import com.ewencluley.javatasks.springboot.model.Book;
import com.ewencluley.javatasks.springboot.model.BookCreationReponse;
import com.ewencluley.javatasks.springboot.model.BookNotFoundException;
import com.ewencluley.javatasks.springboot.model.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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
    public Book getBook(@PathVariable("bookTitle") String bookName) throws BookNotFoundException {
        Book book = bookTitle.get(bookName);
        if (book == null) {
            //return new ResponseEntity<String>("{\"error\": \"Not found\"}", HttpStatus.NOT_FOUND);
            throw new BookNotFoundException("Not found");
        }
        return book;
    }
}
