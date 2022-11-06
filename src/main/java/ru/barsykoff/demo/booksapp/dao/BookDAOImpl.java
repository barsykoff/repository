package ru.barsykoff.demo.booksapp.dao;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.barsykoff.demo.booksapp.model.Book;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class BookDAOImpl implements BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Book> findAllOrderByTitleDesc() {
        return jdbcTemplate.query("SELECT * FROM books ORDER BY title DESC",
                new BeanPropertyRowMapper<>(Book.class));
    }

    @Override
    public int add(Book book) {
        return jdbcTemplate.update("INSERT INTO books (title, author, description) VALUES (?, ?, ?)",
                book.getTitle(),
                book.getAuthor(),
                book.getDescription());
    }

    @Override
    public Map<String, List<Book>> findAllGroupByAuthor() {
        return jdbcTemplate.query("SELECT * FROM books ORDER BY author",
                new BeanPropertyRowMapper<>(Book.class))
                .stream()
                .collect(Collectors.groupingBy(Book::getAuthor));
    }

    @Override
    public List<Map<String, Integer>> findBySymbol(Character querySymbol) {
        List<Map<String, Integer>> list = new ArrayList<>();

        SqlRowSet rs = jdbcTemplate.queryForRowSet("" +
                "SELECT * FROM (\n" +
                "    SELECT author,\n" +
                "           sum((SELECT length(title) - length(replace(upper(title), upper(?), '')))) AS count\n" +
                "    FROM books\n" +
                "    GROUP BY author\n" +
                "    ) t\n" +
                "WHERE t.count > 0\n" +
                "ORDER BY t.count desc\n" +
                "LIMIT 10", querySymbol);

        while (rs.next()) {
            Map<String, Integer> map = new HashMap<>();
            map.put(rs.getString("author"), rs.getInt("count"));
            list.add(map);
        }

        return list;
    }
}
