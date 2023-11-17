package com.example.libraryservice.controller;

import com.example.libraryservice.entity.BookRecord;
import com.example.libraryservice.exception.ResourceNotFoundException;
import com.example.libraryservice.service.BookRecordService;
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
@RequestMapping("/v1/bookrecords")
public class BookRecordController {

    private final BookRecordService bookRecordService;

    @Autowired
    public BookRecordController(BookRecordService bookRecordService) {
        this.bookRecordService = bookRecordService;
    }

    @Operation(summary = "Get all free book records", description = "Get all free book records", tags = { "book record" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation")
    })
    @GetMapping("/free")
    public ResponseEntity<List<Long>> getFreeId() {
        List<Long> freeId = bookRecordService.getBookIdsWithNullDateTaken();
        return new ResponseEntity<>(freeId,HttpStatus.OK);
    }

    @Operation(summary = "Add new book record", description = "Add new book record", tags = { "book record" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created")
    })
    @PostMapping("/{id}")
    public ResponseEntity<BookRecord> add(@Parameter(description = "Book id for adding book record", required = true) @PathVariable Long id) {
        BookRecord bookRecord = bookRecordService.store(id);
        return new ResponseEntity<>(bookRecord, HttpStatus.CREATED);
    }

    @Operation(summary = "Update book record", description = "Update book record", tags = { "book record" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "201", description = "Successfully created")
    })
    @PutMapping("/{id}")
    public ResponseEntity<BookRecord> update(@Parameter(description = "Book record for updating", required = true) @RequestBody BookRecord bookRecord,
                                             @Parameter(description = "Book id for updating book record", required = true) @PathVariable Long id) {
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

    @Operation(summary = "Delete book record", description = "Delete book record", tags = { "book record" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "409", description = "Book record with such bookId hasn't found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<BookRecord> delete(@Parameter(description = "Book id for deleting book record", required = true) @PathVariable("id") Long id) {
        Optional<BookRecord> bookRecordToDelete = bookRecordService.findByBookId(id);
        bookRecordService.delete(id);
        return new ResponseEntity<>(bookRecordToDelete.get(), HttpStatus.OK);
    }
}
