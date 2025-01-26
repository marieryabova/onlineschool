package com.example.onlineschool.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Student {
    private Integer ID;
    private String FirstName;
    private String LastName;
    private String Surname;
    private String Email;
    private String Phone;
    private Integer CourseNumber;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getID() {
        return ID;
    }
}
