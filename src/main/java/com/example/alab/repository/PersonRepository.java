package com.example.alab.repository;

import com.example.alab.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person,Integer> {

    List<Person> findByDocuments_DocNumContains(String docNum);

}
