package com.hugovallada.bookstoremanager.users.repository;

import com.hugovallada.bookstoremanager.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
