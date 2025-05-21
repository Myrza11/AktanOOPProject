package org.example.aktanoopproject.repository;

import org.example.aktanoopproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    @Query("SELECT u FROM User u WHERE LOWER(u.name) LIKE CONCAT('%', LOWER(:name), '%')")
    List<User> findByNameLikeIgnoreCase(@Param("name") String name);



}