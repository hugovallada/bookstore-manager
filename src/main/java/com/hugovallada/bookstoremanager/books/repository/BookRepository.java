package com.hugovallada.bookstoremanager.books.repository;

import com.hugovallada.bookstoremanager.books.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
