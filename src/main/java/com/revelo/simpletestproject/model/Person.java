package com.revelo.simpletestproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class Person {

    Long id;
    String firstName;
    String lastName;
    Date birthDate;
}
