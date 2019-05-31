package com.example.courseinstructorstudent;

import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Long> {
    Iterable<Student> findAllByCourse (Course course);
}
