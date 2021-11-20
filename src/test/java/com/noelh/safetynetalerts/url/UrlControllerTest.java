package com.noelh.safetynetalerts.url;

import com.noelh.safetynetalerts.url.dto.FireStationUrlResponse;
import com.noelh.safetynetalerts.url.dto.FireUrlWithStationNumberResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = UrlController.class)
class UrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlService urlService;

    @Test
    public void getPersonsListByStationIdTest_Should_Return_Ok() throws Exception {
        when(urlService.getPersonsListByStationId(1L)).thenReturn(new FireStationUrlResponse(1,1,new ArrayList<>()));
        mockMvc.perform(get("/firestations/?stationId=1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getPersonsListByStationIdTest_Should_Return_notFound() throws Exception {
        when(urlService.getPersonsListByStationId(1L)).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/firestations/?stationId=1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getChildListByAddressTest_Should_Return_Ok() throws Exception {
        when(urlService.getChildListByAddress("748 Townings Dr")).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/childAlert/?address=748 Townings Dr"))
                .andExpect(status().isOk());
    }

    @Test
    public void getChildListByAddressTest_Should_Return_notFound() throws Exception {
        when(urlService.getChildListByAddress("748 Townings Dr")).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/childAlert/?address=748 Townings Dr"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getPhonesByStationIdTest_Should_Return_Ok() throws Exception {
        when(urlService.getPhonesByStationId(1L)).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/phoneAlert/?firestation=1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getPhonesByStationIdTest_Should_Return_notFound() throws Exception {
        when(urlService.getPhonesByStationId(1L)).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/phoneAlert/?firestation=1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getPersonAndStationNumberByAdressTest_Should_Return_Ok() throws Exception {
        when(urlService.getPersonAndStationNumberByAddress("1509 Culver St")).thenReturn(new FireUrlWithStationNumberResponse());
        mockMvc.perform(get("/fire/?address=1509 Culver St"))
                .andExpect(status().isOk());
    }

    @Test
    public void getPersonAndStationNumberByAdressTest_Should_Return_notFound() throws Exception {
        when(urlService.getPersonAndStationNumberByAddress("1509 Culver St")).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/fire/?address=1509 Culver St"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getPersonsGroupedByAddressTest_Should_Return_Ok() throws Exception {
        when(urlService.getPersonsGroupedByAddress(Arrays.asList(1L,2L,4L,3L))).thenReturn(new HashMap<>());
        mockMvc.perform(get("/flood/stations/?stations=1&stations=2&stations=4&stations=3"))
                .andExpect(status().isOk());
    }

    @Test
    public void getPersonsGroupedByAddressTest_Should_Return_notFound() throws Exception {
        when(urlService.getPersonsGroupedByAddress(Arrays.asList(1L,2L,4L,3L))).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/flood/stations/?stations=1&stations=2&stations=4&stations=3"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getPersonInfoByFirstNameAndLastNameTest_Should_Return_Ok() throws Exception {
        when(urlService.getPersonInfoByFirstNameAndLastName("John","Boyd")).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/personInfo/?firstName=John&lastName=Boyd"))
                .andExpect(status().isOk());
    }

    @Test
    public void getPersonInfoByFirstNameAndLastNameTest_Should_Return_notFound() throws Exception {
        when(urlService.getPersonInfoByFirstNameAndLastName("John","Boyd")).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/personInfo/?firstName=John&lastName=Boyd"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getMailsByCityTest_Should_Return_Ok() throws Exception {
        when(urlService.getMailsByCity("Culver")).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/communityEmail/?city=Culver"))
                .andExpect(status().isOk());
    }

    @Test
    public void getMailsByCityTest_Should_Return_notFound() throws Exception {
        when(urlService.getMailsByCity("Culver")).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/communityEmail/?city=Culver"))
                .andExpect(status().isNotFound());
    }
}