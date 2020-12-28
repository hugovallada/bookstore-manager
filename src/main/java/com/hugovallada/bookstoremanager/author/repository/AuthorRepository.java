package com.hugovallada.bookstoremanager.author.repository;

import com.hugovallada.bookstoremanager.author.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
