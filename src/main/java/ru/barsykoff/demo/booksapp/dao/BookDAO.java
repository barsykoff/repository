package ru.barsykoff.demo.booksapp.dao;

import ru.barsykoff.demo.booksapp.model.Book;

import java.util.List;
import java.util.Map;

public interface BookDAO {

    public List<Book> findAllOrderByTitleDesc();

    public int add(Book book);

    public Map<String, List<Book>> findAllGroupByAuthor();

    public List<Map<String, Integer>> findBySymbol(Character querySymbol);
}
