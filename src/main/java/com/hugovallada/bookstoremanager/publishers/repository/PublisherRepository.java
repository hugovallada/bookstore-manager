package com.hugovallada.bookstoremanager.publishers.repository;

import com.hugovallada.bookstoremanager.publishers.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}
