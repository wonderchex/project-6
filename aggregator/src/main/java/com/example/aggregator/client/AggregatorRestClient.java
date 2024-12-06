package com.example.aggregator.client;

import com.example.aggregator.model.Entry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AggregatorRestClient {

    private RestTemplate restTemplate;

    public AggregatorRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = "getDefinitionForCB", fallbackMethod = "fallbackGetDefinitionFor")
    public Entry getDefinitionFor(String word) {

        String uri = "http://localhost:9091/getWord/" + word;

        Entry result = restTemplate.getForObject(uri, Entry.class);

        return result;
    }

    public List<Entry> getWordsStartingWith(String chars) {

        String uri = "http://localhost:9091/getWordsStartingWith/" + chars;

        ResponseEntity<Entry[]> responseEntity = restTemplate.getForEntity(uri, Entry[].class);
        Entry[] entryArray = responseEntity.getBody();

        return Arrays.stream(entryArray)
                     .collect(Collectors.toList());
    }

    public List<Entry> getWordsThatContain(String chars) {

        String uri = "http://localhost:9091/getWordsThatContain/" + chars;

        ResponseEntity<Entry[]> responseEntity = restTemplate.getForEntity(uri, Entry[].class);
        Entry[] entryArray = responseEntity.getBody();

        return Arrays.stream(entryArray)
                     .collect(Collectors.toList());
    }

    public List<Entry> getWordsThatContainConsecutiveLetters() {

        String uri = "http://localhost:9091/getWordsThatContainConsecutiveLetters";

        ResponseEntity<Entry[]> responseEntity = restTemplate.getForEntity(uri, Entry[].class);
        Entry[] entryArray = responseEntity.getBody();

        return Arrays.stream(entryArray)
                     .collect(Collectors.toList());
    }

    // Fallback methods
    public Entry fallbackGetDefinitionFor(String word, Throwable t) {
        return new Entry("Doh!","Definition not available");
    }

}
