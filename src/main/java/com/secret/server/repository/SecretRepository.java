package com.secret.server.repository;

import com.secret.server.model.Secret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecretRepository extends JpaRepository<Secret, String> {
}
