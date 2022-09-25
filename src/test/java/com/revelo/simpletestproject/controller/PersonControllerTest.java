package com.revelo.simpletestproject.controller;

import com.revelo.simpletestproject.model.Person;
import com.revelo.simpletestproject.service.PersonService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = PersonController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    Person mockPerson = new Person(
            1L,
            "Person",
            "Mock",
            Date.from(
                    LocalDate.of(
                            2000,
                            Month.JANUARY,
                            1).atStartOfDay(ZoneId.of("UTC")
                    ).toInstant()
            )
    );
    String personMockOtherJson =
        "{\n" +
            "\"id\": 99,\n" +
            "\"firstName\": \"Person\",\n" +
            "\"lastName\": \"MockOther\",\n" +
            "\"birthDate\": \"2000-01-31T00:00:00.000+00:00\"\n" +
            "" +
        "}";


    @Test
    @Order(1)
    public void createPerson() throws Exception {
        Person anotherMockPerson = new Person(
                99L,
                "Person",
                "MockOther",
                Date.from(
                        LocalDate.of(
                                2000,
                                Month.JANUARY,
                                31).atStartOfDay(ZoneId.of("UTC")
                        ).toInstant()
                )
        );

        Mockito.when(personService.create(Mockito.any(Person.class))).thenReturn(anotherMockPerson.getId());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/person")
                .accept(MediaType.APPLICATION_JSON).content(personMockOtherJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

    @Test
    @Order(2)
    public void retrievePersonById() throws Exception {

        Mockito.when(
                personService.retrieveById(Mockito.anyLong())).thenReturn(mockPerson);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/person/1").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected =
                "{\n" +
                    "\"id\": 1,\n" +
                    "\"firstName\": \"Person\",\n" +
                    "\"lastName\": \"Mock\",\n" +
                    "\"birthDate\": \"2000-01-01T00:00:00.000+00:00\"\n" +
                "}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    @Order(3)
    public void retrievePersonList() throws Exception {

        Mockito.when(
                personService.retrieveAll()).thenReturn(Lists.newArrayList(mockPerson));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/person").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected =
                "[{\n" +
                        "\"id\": 1,\n" +
                        "\"firstName\": \"Person\",\n" +
                        "\"lastName\": \"Mock\",\n" +
                        "\"birthDate\": \"2000-01-01T00:00:00.000+00:00\"\n" +
                        "}]";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    @Order(4)
    public void updatePerson() throws Exception {

        Mockito.when(
                personService.update(Mockito.anyLong(), Mockito.any(Person.class))).thenReturn(true);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/person/1")
                .accept(MediaType.APPLICATION_JSON).content(personMockOtherJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        Long expected = 1L;

        JSONAssert.assertEquals(String.valueOf(expected), result.getResponse().getContentAsString(), false);
    }

    @Test
    @Order(5)
    public void notPerformUpdatePerson() throws Exception {
        String personMockOtherOtherJson =
                "{\n" +
                        "\"id\": -1,\n" +
                        "\"firstName\": \"Person\",\n" +
                        "\"lastName\": \"personMockOtherOtherJson\",\n" +
                        "\"birthDate\": \"2000-01-01T00:00:00.000+00:00\"\n" +
                        "}";

        Mockito.when(
                personService.update(Mockito.anyLong(), Mockito.any(Person.class))).thenReturn(false);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/person/666")
                .accept(MediaType.APPLICATION_JSON).content(personMockOtherOtherJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "";

        assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    @Order(5)
    public void deletePerson() throws Exception {
        Mockito.when(
                personService.delete(Mockito.anyLong())).thenReturn(true);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/person/1")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        boolean expected = true;

        assertEquals(String.valueOf(expected), result.getResponse().getContentAsString());
    }
}
