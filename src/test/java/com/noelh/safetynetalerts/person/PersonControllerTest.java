package com.noelh.safetynetalerts.person;

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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

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
        Person p = new Person();
        when(personService.getPerson(anyLong())).thenReturn(p);
        when(personService.updatePerson(eq(p), any())).thenReturn(new Person());
        mockMvc.perform(put("/persons/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void updatePersonTest_shouldReturnNotFound() throws Exception {
        when(personService.getPerson(anyLong())).thenThrow(new NoSuchElementException());
        mockMvc.perform(put("/persons/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deletePersonTest_shouldReturnOk() throws Exception {
        Person p = new Person();
        when(personService.getPerson(anyLong())).thenReturn(p);
        when(personService.deletePerson(eq(p))).thenReturn(new Person());
        mockMvc.perform(delete("/persons/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void deletePersonTest_shouldReturnNotFound() throws Exception {
        when(personService.getPerson(anyLong())).thenThrow(new NoSuchElementException());
        mockMvc.perform(delete("/persons/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deletePersonByNameTest_shouldReturnOk() throws Exception {
        Person p = new Person();
        when(personService.getPerson(any(), any())).thenReturn(p);
        when(personService.deletePerson(eq(p))).thenReturn(new Person());
        mockMvc.perform(delete("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void deletePersonByNameTest_shouldReturnNotFound() throws Exception {
        when(personService.getPerson(any(),any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(delete("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNotFound());
    }
}
