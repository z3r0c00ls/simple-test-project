package com.revelo.simpletestproject.service;

import com.revelo.simpletestproject.model.Person;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = PersonService.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonServiceTest {


    @InjectMocks
    private PersonService personService;

    Person testPerson = new Person(
            (long) PersonService.personList.size(),
            "TestPerson",
            "FromTestFamily",
            Date.from(
                    LocalDate.of(
                            2022,
                            Month.JANUARY,
                            01).atStartOfDay(ZoneId.of("UTC")
                    ).toInstant()
            )
    );

    @Test
    @Order(1)
    public void create() {
        Long expected = (long) (PersonService.personList.size() + 1);
        Long result = personService.create(testPerson);
        assert expected.equals(result);
    }

    @Test
    @Order(2)
    public void update() {
        testPerson.setFirstName("UniqueTestPerson");
        boolean expected = true;
        boolean result = personService.update(testPerson.getId(), testPerson);
        assert expected == result;
    }

    @Test
    @Order(3)
    public void retrieveById() {
        Person result = personService.retrieveById(testPerson.getId());
        Person expected = PersonService.personList.get(PersonService.personList.size()-1);
        assert expected == result;
    }

    @Test
    @Order(4)
    public void retrieveAll() {
        List<Person> expected = PersonService.personList;
        List<Person> result = personService.retrieveAll();
        assert expected == result;
    }

    @Test
    @Order(5)
    public void delete() {
        boolean expected = true;
        boolean result = personService.delete(testPerson.getId());
        assert expected == result;
    }

}
