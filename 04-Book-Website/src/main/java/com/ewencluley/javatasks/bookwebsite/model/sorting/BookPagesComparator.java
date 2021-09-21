package com.ewencluley.javatasks.bookwebsite.model.sorting;

import com.ewencluley.javatasks.bookwebsite.model.Book;

import java.util.Comparator;

public class BookPagesComparator implements Comparator<Book> {

    @Override
    public int compare(Book o1, Book o2) {
        return Integer.compare(o1.getPages(), o2.getPages());
    }
}
