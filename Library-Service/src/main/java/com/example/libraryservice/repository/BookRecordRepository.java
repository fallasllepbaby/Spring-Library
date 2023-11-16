package com.example.libraryservice.repository;

import com.example.libraryservice.entity.BookRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRecordRepository extends JpaRepository<BookRecord, Long> {
    List<BookRecord> findByDateTheBookWasTakenIsNull();

    Optional<BookRecord> findByBookId(Long bookId);

    void deleteBookRecordByBookId(Long bookId);
}
