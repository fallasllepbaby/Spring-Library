package com.example.bookservice.service;

import com.example.bookservice.entity.Book;
import com.example.bookservice.exception.AlreadyExistException;
import com.example.bookservice.exception.ResourceNotFoundException;
import com.example.bookservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;

    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> findById(Long id) {
        if (!bookRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException("There isn't book with id : " + id);
        }
        return bookRepository.findById(id);
    }

    public Optional<Book> findByIsbn(String isbn) {
        if (!bookRepository.findByISBN(isbn).isPresent()) {
            throw new ResourceNotFoundException("There isn't book with ISBN: " + isbn);
        }
        return bookRepository.findByISBN(isbn);
    }

    public Book store(Book book) {

        if (bookRepository.findByISBN(book.getISBN()).isPresent()) {
            throw new AlreadyExistException("Book with ISBN: " + book.getISBN() + " already exists");
        }

        Book saveBook = bookRepository.save(book);

        return saveBook;
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
}

