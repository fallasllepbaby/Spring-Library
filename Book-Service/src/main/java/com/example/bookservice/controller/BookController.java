package com.example.bookservice.controller;

import com.example.bookservice.entity.Book;
import com.example.bookservice.exception.ResourceNotFoundException;
import com.example.bookservice.service.BookService;
import com.example.bookservice.util.FeignUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/books")
public class BookController {

    private final BookService bookService;
    private final FeignUtil feignUtil;

    @Autowired
    public BookController(BookService bookService, FeignUtil feignUtil) {
        this.bookService = bookService;
        this.feignUtil = feignUtil;
    }

    @Operation(summary = "Get all Books", description = "Get all Books", tags = { "book" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation")
    })
    @GetMapping
    public ResponseEntity<List<Book>> getAll() {
        List<Book> books = bookService.findAll();
        return new ResponseEntity<>(books,HttpStatus.OK);
    }

    @Operation(summary = "Get Book by id", description = "Get Book by id", tags = { "book" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "409", description = "Book with such id hasn't found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@Parameter(description = "Book id for getting", required = true) @PathVariable Long id) {
        Book book = bookService.findById(id).get();
        return new ResponseEntity<>(book,HttpStatus.OK);
    }


    @Operation(summary = "Get Book by ISBN", description = "Get Book by ISBN", tags = { "book" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "409", description = "Book with such ISBN hasn't found")
    })
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Book> getByIsbn(@Parameter(description = "Book ISBN for getting", required = true) @PathVariable String isbn) {
        Book book = bookService.findByIsbn(isbn).get();
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @Operation(summary = "Get free Books", description = "Get free Books", tags = { "book" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
    })
    @GetMapping("/free")
    public ResponseEntity<List<Book>> getFreeBooks() {
        List<Long> freeId = feignUtil.getIdFreeBooks();
        List<Book> books = bookService.findAll().stream().filter(book -> freeId.contains(book.getId())).toList();
        return new ResponseEntity<>(books,HttpStatus.OK);
    }

    @Operation(summary = "Add new Book", description = "Add new Book", tags = { "book" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book was successfully created"),
    })
    @PostMapping
    public ResponseEntity<Book> add(@Parameter(description = "Book for adding", required = true) @RequestBody Book book) {
        bookService.store(book);
        feignUtil.add(book.getId());
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a Book", description = "Update a Book", tags = { "book" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "201", description = "Book was successfully created")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@Parameter(description = "Book for updating", required = true) @RequestBody Book book,
                                       @Parameter(description = "Book id for updating", required = true) @PathVariable Long id) {
        return bookService.findById(id).map(updatedBook -> {
            updatedBook.setISBN(book.getISBN());
            updatedBook.setName(book.getName());
            updatedBook.setGenre(book.getGenre());
            updatedBook.setDescription(book.getDescription());
            updatedBook.setAuthor(book.getAuthor());
            bookService.store(updatedBook);
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        }).orElseGet(() -> {
            Book newBook = bookService.store(book);
            return new ResponseEntity<>(newBook, HttpStatus.CREATED);
        });
    }

    @Operation(summary = "Delete a Book", description = "Delete a Book", tags = { "book" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "409", description = "Book with such id hasn't found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Book> delete(@Parameter(description = "Book id for deleting", required = true) @PathVariable Long id) {
        Optional<Book> bookToDelete = bookService.findById(id);
        feignUtil.delete(id);
        bookService.delete(id);
        return new ResponseEntity<>(bookToDelete.get(), HttpStatus.OK);
    }


}