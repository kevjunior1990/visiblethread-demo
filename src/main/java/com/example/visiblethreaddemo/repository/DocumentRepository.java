package com.example.visiblethreaddemo.repository;

import com.example.visiblethreaddemo.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    Optional<Document> findByName(String name);

    List<Document> findByUserIdAndCreatedDateBetween(Long userId, Timestamp startDate, Timestamp endDate);

    Boolean existsByUserIdAndCreatedDateBetween(Long userId, Timestamp startDate, Timestamp endDate);
}
