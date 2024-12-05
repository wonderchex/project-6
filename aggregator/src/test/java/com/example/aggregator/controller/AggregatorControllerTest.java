package com.example.aggregator.controller;

import com.example.aggregator.model.Entry;
import com.example.aggregator.service.AggregatorService;
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

@WebMvcTest(AggregatorController.class)
public class AggregatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AggregatorService aggregatorService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testHelloWorld() throws Exception {
        Entry entry1 = new Entry("hello", "definition1");
        Entry entry2 = new Entry("world", "definition2");
        Mockito.when(aggregatorService.getDefinitionFor("hello")).thenReturn(entry1);
        Mockito.when(aggregatorService.getDefinitionFor("world")).thenReturn(entry2);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"word\":\"hello\",\"definition\":\"definition1\"},{\"word\":\"world\",\"definition\":\"definition2\"}]"));
    }

    @Test
    public void testGetDefinitionFor() throws Exception {
        Entry entry = new Entry("test", "definition");
        Mockito.when(aggregatorService.getDefinitionFor(anyString())).thenReturn(entry);

        mockMvc.perform(get("/getDefinitionFor/test"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"word\":\"test\",\"definition\":\"definition\"}"));
    }

    @Test
    public void testGetWordsStartingWith() throws Exception {
        List<Entry> entries = Arrays.asList(new Entry("test", "definition"));
        Mockito.when(aggregatorService.getWordsStartingWith(anyString())).thenReturn(entries);

        mockMvc.perform(get("/getWordsStartingWith/te"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"word\":\"test\",\"definition\":\"definition\"}]"));
    }

    @Test
    public void testGetWordsThatContain() throws Exception {
        List<Entry> entries = Arrays.asList(new Entry("test", "definition"));
        Mockito.when(aggregatorService.getWordsThatContain(anyString())).thenReturn(entries);

        mockMvc.perform(get("/getWordsThatContain/es"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"word\":\"test\",\"definition\":\"definition\"}]"));
    }

    @Test
    public void testGetWordsThatContainSuccessiveLettersAndStartsWith() throws Exception {
        List<Entry> entries = Arrays.asList(new Entry("letter", "definition"));
        Mockito.when(aggregatorService.getWordsThatContainSuccessiveLettersAndStartsWith(anyString())).thenReturn(entries);

        mockMvc.perform(get("/getWordsThatContainSuccessiveLettersAndStartsWith/le"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"word\":\"letter\",\"definition\":\"definition\"}]"));
    }

    @Test
    public void testGetWordsThatContainSpecificConsecutiveLetters() throws Exception {
        List<Entry> entries = Arrays.asList(new Entry("letter", "definition"));
        Mockito.when(aggregatorService.getWordsThatContainSpecificConsecutiveLetters(anyString())).thenReturn(entries);

        mockMvc.perform(get("/getWordsThatContainSpecificConsecutiveLetters/tt"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"word\":\"letter\",\"definition\":\"definition\"}]"));
    }
}