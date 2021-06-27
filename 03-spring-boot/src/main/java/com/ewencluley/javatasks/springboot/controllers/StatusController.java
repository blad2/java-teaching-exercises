package com.ewencluley.javatasks.springboot.controllers;

import com.ewencluley.javatasks.springboot.model.Book;
import com.ewencluley.javatasks.springboot.model.BookCreationReponse;
import com.ewencluley.javatasks.springboot.model.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class StatusController {

    @JsonProperty
    Book book;

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public Status status() {
        return new Status("OK");
    }

    @RequestMapping(value = "/book", method = RequestMethod.POST)
    public BookCreationReponse book(@RequestBody Book bookRequest) {
        book = bookRequest;
        return new BookCreationReponse(bookRequest);
    }

    @RequestMapping(value = "/book/{title}", method = RequestMethod.GET)
    public Object getBook(@PathVariable(name = "title") String title) {
        if (book.getTitle().equals(title)) {
            return book;
        }
        // Still haven't figured out how to get the response 404 {"error": "Not found"}
        return HttpStatus.NOT_FOUND;
    }
}
