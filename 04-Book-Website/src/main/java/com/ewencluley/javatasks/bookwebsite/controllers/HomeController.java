package com.ewencluley.javatasks.bookwebsite.controllers;

import com.ewencluley.javatasks.bookwebsite.model.Book;
import com.ewencluley.javatasks.bookwebsite.model.sorting.BookAuthorComparator;
import com.ewencluley.javatasks.bookwebsite.model.sorting.BookPagesComparator;
import com.ewencluley.javatasks.bookwebsite.model.sorting.BookTitleComparator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class HomeController {



    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String status(Model model) {
        model.addAttribute("user", UUID.randomUUID().toString());
        return "user";
    }

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView books(@RequestParam(required = false) Optional<String> sortOrder, @RequestParam(required = false, name = "bookTitle") List<String> bookFilter, @RequestParam Optional<String> sortField) {

        List<Book> books = new ArrayList<>((List.of(new Book("abook", "blad", 20),
                new Book("abookaldkfasd", "alad", 300),
                new Book("cbook 13", "john", 10),
                new Book("dbook 341", "Steve", 2330))));
        ModelAndView view = new ModelAndView(); // Webpage's object

        List<Book> bookResult;
        bookResult = books.stream()
                .filter(b -> bookFilter == null
                        || bookFilter.contains(b.getTitle())
                        || bookFilter.contains(b.getAuthor()))
                .sorted(getComparator(sortOrder, sortField))
                .collect(Collectors.toList());

        view.setViewName("books");
        view.addObject("the_books", bookResult);
        view.addObject("page_title", "Books 2021");
        return view;
    }

    public Comparator<Book> getComparator(Optional<String> sortOrder, Optional<String> sortField) {
        // to avoid getting multiple values from these fields, I separate the value by comas (,) and get the first index
        String orderValue = sortOrder.isPresent() ? sortOrder.get().split(",")[0] : "asc";
        String fieldValue = sortField.isPresent() ? sortField.get().split(",")[0] : "title";
        return orderValue.equals("des") ? getComparator(fieldValue).reversed() : getComparator(fieldValue);
    }

    public Comparator<Book> getComparator(String fieldValue) {
        return switch (fieldValue) {
            case "title" -> new BookTitleComparator();
            case "author" -> new BookAuthorComparator();
            case "pages" -> new BookPagesComparator();
            default -> new BookTitleComparator(); // I need to pass the value of the parameter to print it on the screen saying it is not valid.
        };
    }
}
