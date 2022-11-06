package ru.barsykoff.demo.booksapp;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.barsykoff.demo.booksapp.dao.BookDAO;
import ru.barsykoff.demo.booksapp.model.Book;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Slf4j
public class BooksController {

    private final BookDAO bookDAOImpl;

    @GetMapping("/books")
    public List<Book> findAllOrderByTitleDesc() {
        return bookDAOImpl.findAllOrderByTitleDesc();
    }

    @PostMapping("/books")
    public ResponseEntity<String> add(@Valid @RequestBody Book book) {
        bookDAOImpl.add(book);
        return ResponseEntity.ok("Book is added");
    }

    @GetMapping("/authors/books")
    public Map<String, List<Book>> findByAuthor() {
        return bookDAOImpl.findAllGroupByAuthor();
    }

    @GetMapping("/authors/books/title/{querySymbol}")
    public List<Map<String, Integer>> findBooksBySymbol(@PathVariable("querySymbol") Character querySymbol) {
        return bookDAOImpl.findBySymbol(querySymbol);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handle(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        return "Book's attributes are not valid";
    }
}
