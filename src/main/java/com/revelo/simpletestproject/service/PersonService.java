package com.revelo.simpletestproject.service;

import com.revelo.simpletestproject.model.Person;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class PersonService {

    private static final List<Person> personList = new ArrayList<>();

    LocalDate today = LocalDate.of(1965, Month.JANUARY,1);

    static {
        //Initialize Data
        Person personOne = new Person(
                1L,
                "FirstPerson",
                "NumberOne",
                Date.from(
                        LocalDate.of(
                                1965,
                                Month.JANUARY,
                                29).atStartOfDay(ZoneId.of("UTC")
                        ).toInstant()
                )
        );
        Person personTwo = new Person(
                2L,
                "SecondPerson",
                "NumberTwo",
                Date.from(
                        LocalDate.of(
                                1986,
                                Month.AUGUST,
                                15).atStartOfDay(ZoneId.of("UTC")
                        ).toInstant()
                )
        );
        Person personThree = new Person(
                3L,
                "ThirdPerson",
                "NumberThree",
                Date.from(
                        LocalDate.of(
                                1986,
                                Month.NOVEMBER,
                                17).atStartOfDay(ZoneId.of("UTC")
                        ).toInstant()
                )
        );
        Person personFour = new Person(
                4L,
                "FortyPerson",
                "NumberFour",
                Date.from(
                        LocalDate.of(
                                1988,
                                Month.JUNE,
                                15).atStartOfDay(ZoneId.of("UTC")
                        ).toInstant()
                )
        );

        personList.add(personOne);
        personList.add(personTwo);
        personList.add(personThree);
        personList.add(personFour);
    }

    public Long create(Person person) {
        Long personId = (long) personList.size() + 1;
        person.setId(personId);
        personList.add(person);
        return personId;
    }

    public boolean update(Long personId, Person person) {
        AtomicBoolean updated = new AtomicBoolean(false);
        personList.stream().filter(personToUpdate -> personToUpdate.getId().equals(personId)).forEach(personToUpdate -> {
            personToUpdate.setFirstName(person.getFirstName());
            personToUpdate.setLastName(person.getLastName());
            personToUpdate.setBirthDate(person.getBirthDate());
            updated.set(true);

        });
        return updated.get();
    }

    public Person retrieveById(Long personId) {
        return personList.stream().filter(person -> person.getId().equals(personId)).findFirst().orElse(null);
    }

    public List<Person> retrieveAll() {
        return personList;
    }

    public boolean delete(Long personId) {
        return personList.removeIf(person -> person.getId().equals(personId));
    }


}
