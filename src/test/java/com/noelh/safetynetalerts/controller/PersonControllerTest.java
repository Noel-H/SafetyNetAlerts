package com.noelh.safetynetalerts.controller;

import com.noelh.safetynetalerts.json.JsonInitializer;
import com.noelh.safetynetalerts.json.jsonparser.Person;
import com.noelh.safetynetalerts.service.PersonService;
import com.noelh.safetynetalerts.web.controller.PersonController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JsonInitializer jsonInitializer;

    @MockBean
    private PersonService personService;

    @BeforeEach
    public void setup() throws Exception {
        doNothing().when(jsonInitializer).run();
    }

    @Test
    public void getPersonsTest_shouldReturnOk() throws Exception {
        when(personService.getPersons()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/persons"))
                .andExpect(status().isOk());
    }

    @Test
    public void getPersonTest_shouldReturnOk() throws Exception {
        when(personService.getPerson(anyLong())).thenReturn(new Person());
        mockMvc.perform(get("/persons/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getPersonTest_shouldReturnNotFound() throws Exception {
        when(personService.getPerson(anyLong())).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/persons/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addPersonTest_shouldReturnOk() throws Exception {
        when(personService.getPerson(anyString(), anyString())).thenReturn(new Person());
        mockMvc.perform(post("/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")).andExpect(status().isOk());
    }

    @Test
    public void updatePersonTest_shouldReturnOK() throws Exception {
        //Given
        Person p = new Person();
        when(personService.getPerson(anyLong())).thenReturn(p);
        when(personService.updatePerson(eq(p), any())).thenReturn(new Person());
        //WHEN
        mockMvc.perform(put("/persons/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                //THEN
                .andExpect(status().isOk());
    }

    @Test
    public void updatePersonTest_shouldReturnNotFound() throws Exception {
        //Given étant que je veux mettre a jour une personne qui n'existe pas
        when(personService.getPerson(anyLong())).thenThrow(new NoSuchElementException());
        //When je fait un put sur l'id en question
        mockMvc.perform(put("/persons/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
        //Then je reçois une 404
                .andExpect(status().isNotFound());
    }

    @Test
    public void deletePersonTest_shouldReturnOk() throws Exception {
        //Given étant donné que j'éssaie de supprimer une person dont l'id existe
        Person p = new Person();
        when(personService.getPerson(anyLong())).thenReturn(p);
        when(personService.deletePerson(eq(p))).thenReturn(new Person());
        //When je fait un delete sur l'id en question
        mockMvc.perform(delete("/persons/1"))
        //Then je reçois 200
                .andExpect(status().isOk());
    }

    @Test
    public void deletePersonTest_shouldReturnNotFound() throws Exception {
//Given étant donné que j'éssaie de supprimer une person dont l'id n'existe pas
        when(personService.getPerson(anyLong())).thenThrow(new NoSuchElementException());
        //When  je fait un delete sur l'id en question
        mockMvc.perform(delete("/persons/1"))
                //Then je reçois 404
                .andExpect(status().isNotFound());
    }

    @Test
    public void deletePersonByNameTest_shouldReturnOk() throws Exception {
        //Given que j'éssai de supprimer une person dont le nom et prénom existe
        Person p = new Person();
        when(personService.getPerson(any(), any())).thenReturn(p);
        when(personService.deletePerson(eq(p))).thenReturn(new Person());
        //When je fais un delete avec le nom et prénom de cette person
        mockMvc.perform(delete("/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
        //Then je reçois 200
                .andExpect(status().isOk());
    }

    @Test
    public void deletePersonByNameTest_shouldReturnNotFound() throws Exception {
        //Given que j'éssai de supprimer une person qui n'existe pas avec le nom et prénom
        when(personService.getPerson(any(),any())).thenThrow(new NoSuchElementException());
        //When je fais un delete avec le nom et prénom de cette person
        mockMvc.perform(delete("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
        //Then je reçois un 404
                .andExpect(status().isNotFound());
    }
}
