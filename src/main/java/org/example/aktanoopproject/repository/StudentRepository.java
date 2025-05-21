package org.example.aktanoopproject.repository;

import org.example.aktanoopproject.model.InterestType;
import org.example.aktanoopproject.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT s FROM Student s JOIN s.interests i WHERE i IN :interests")
    List<Student> filterByInterests(@Param("interests") Set<InterestType> interests);
}
