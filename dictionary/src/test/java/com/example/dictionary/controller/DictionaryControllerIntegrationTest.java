package com.example.dictionary.controller;

import com.example.dictionary.model.Entry;
import com.example.dictionary.service.DictionaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DictionaryController.class)
public class DictionaryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DictionaryService dictionaryService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetWord() throws Exception {
        Entry entry = new Entry("test", "definition");
        Mockito.when(dictionaryService.getWord(anyString())).thenReturn(entry);

        mockMvc.perform(get("/getWord/test"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"word\":\"test\",\"definition\":\"definition\"}"));
    }

    @Test
    public void testGetWordsStartingWith() throws Exception {
        List<Entry> entries = Arrays.asList(new Entry("test", "definition"));
        Mockito.when(dictionaryService.getWordsStartingWith(anyString())).thenReturn(entries);

        mockMvc.perform(get("/getWordsStartingWith/te"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"word\":\"test\",\"definition\":\"definition\"}]"));
    }

    @Test
    public void testGetWordsThatContain() throws Exception {
        List<Entry> entries = Arrays.asList(new Entry("test", "definition"));
        Mockito.when(dictionaryService.getWordsThatContain(anyString())).thenReturn(entries);

        mockMvc.perform(get("/getWordsThatContain/es"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"word\":\"test\",\"definition\":\"definition\"}]"));
    }

    @Test
    public void testGetWordsThatContainConsecutiveLetters() throws Exception {
        List<Entry> entries = Arrays.asList(new Entry("letter", "definition"));
        Mockito.when(dictionaryService.getWordsThatContainConsecutiveDoubleLetters()).thenReturn(entries);

        mockMvc.perform(get("/getWordsThatContainConsecutiveLetters"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"word\":\"letter\",\"definition\":\"definition\"}]"));
    }
}