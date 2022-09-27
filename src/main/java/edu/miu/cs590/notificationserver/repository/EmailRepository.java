package edu.miu.cs590.notificationserver.repository;

import edu.miu.cs590.notificationserver.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
}
