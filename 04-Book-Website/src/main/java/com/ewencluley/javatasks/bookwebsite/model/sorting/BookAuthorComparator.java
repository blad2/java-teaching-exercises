package com.ewencluley.javatasks.bookwebsite.model.sorting;

import com.ewencluley.javatasks.bookwebsite.model.Book;

import java.util.Comparator;

public class BookAuthorComparator implements Comparator<Book> {

    @Override
    public int compare(Book o1, Book o2) {
        return o1.getAuthor().compareToIgnoreCase(o2.getAuthor());
    }
}
