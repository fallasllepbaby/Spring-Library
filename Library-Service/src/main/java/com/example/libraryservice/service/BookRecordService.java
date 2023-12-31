package com.example.libraryservice.service;


import com.example.libraryservice.entity.BookRecord;
import com.example.libraryservice.exception.ResourceNotFoundException;
import com.example.libraryservice.repository.BookRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookRecordService {

    private final BookRecordRepository bookRecordRepository;

    @Autowired
    public BookRecordService(BookRecordRepository bookRecordRepository) {
        this.bookRecordRepository = bookRecordRepository;
    }

    public BookRecord store(Long id) {
        BookRecord bookRecord = new BookRecord(id);
        return bookRecordRepository.save(bookRecord);
    }

    @Transactional
    public BookRecord store(BookRecord bookRecord) {
        BookRecord saveBookRecord = bookRecordRepository.save(bookRecord);
        return saveBookRecord;
    }

    public List<Long> getBookIdsWithNullDateTaken() {
        List<BookRecord> bookRecords = bookRecordRepository.findByDateTheBookWasTakenIsNull();
        List<Long> freeId = bookRecords.stream().map(BookRecord::getBookId).toList();
        return freeId;
    }

    public Optional<BookRecord> findByBookId(Long bookId) {
        if (!bookRecordRepository.findByBookId(bookId).isPresent())
            throw new ResourceNotFoundException("There isn't book record with book id : " + bookId);
        return bookRecordRepository.findByBookId(bookId);
    }

    @Transactional
    public void delete(Long id) {
        bookRecordRepository.deleteBookRecordByBookId(id);
    }

}
