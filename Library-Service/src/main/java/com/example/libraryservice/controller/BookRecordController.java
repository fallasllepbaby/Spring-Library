package com.example.libraryservice.controller;

import com.example.libraryservice.entity.BookRecord;
import com.example.libraryservice.exception.ResourceNotFoundException;
import com.example.libraryservice.service.BookRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/bookrecords")
public class BookRecordController {

    private final BookRecordService bookRecordService;

    @Autowired
    public BookRecordController(BookRecordService bookRecordService) {
        this.bookRecordService = bookRecordService;
    }

    @GetMapping("/free")
    public ResponseEntity<List<Long>> getFreeId() {
        List<Long> freeId = bookRecordService.getBookIdsWithNullDateTaken();
        return new ResponseEntity<>(freeId,HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<BookRecord> add(@PathVariable Long id) {
        BookRecord bookRecord = bookRecordService.store(id);
        return new ResponseEntity<>(bookRecord, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookRecord> update(@RequestBody BookRecord bookRecord,
                                       @PathVariable Long id) {
        return bookRecordService.findByBookId(id).map(updatedBookRecord -> {
            updatedBookRecord.setDateTheBookWasTaken(bookRecord.getDateTheBookWasTaken());
            updatedBookRecord.setBookReturnDate(bookRecord.getBookReturnDate());
            bookRecordService.store(updatedBookRecord);
            return new ResponseEntity<>(updatedBookRecord, HttpStatus.OK);
        }).orElseGet(() -> {
            BookRecord newBookRecord = bookRecordService.store(bookRecord);
            return new ResponseEntity<>(newBookRecord, HttpStatus.CREATED);
        });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookRecord> delete(@PathVariable("id") Long id) {
        Optional<BookRecord> bookRecordToDelete = bookRecordService.findByBookId(id);
        if (!bookRecordToDelete.isPresent())
            throw new ResourceNotFoundException("There isn't book record with book id : " + id);
        bookRecordService.delete(id);
        return new ResponseEntity<>(bookRecordToDelete.get(), HttpStatus.NO_CONTENT);
    }
}
