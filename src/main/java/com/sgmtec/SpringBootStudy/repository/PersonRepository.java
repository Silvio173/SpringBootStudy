package com.sgmtec.SpringBootStudy.repository;

import com.sgmtec.SpringBootStudy.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
