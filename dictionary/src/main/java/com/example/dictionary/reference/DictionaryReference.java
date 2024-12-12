package com.example.dictionary.reference;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;



@Component
public class DictionaryReference {

    private static final Logger logger = LoggerFactory.getLogger(DictionaryReference.class);

    private static Map<String, String> dictionary;

    static {

        try {
            readDictionaryFile();
        } catch (JsonProcessingException e) {
            logger.error("Error reading dictionary file", e);
        }
    }

    private static void readDictionaryFile() throws JsonProcessingException {

        StopWatch sw = new StopWatch();
        sw.start();
        InputStream inputStream = DictionaryReference.class.getClassLoader()
                                                           .getResourceAsStream("dictionary.json");
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String json = bufferedReader.lines()
                                    .collect(Collectors.joining("\n"));

        ObjectMapper mapper = new ObjectMapper();
        dictionary = mapper.readValue(json, Map.class);

        sw.stop();

        long millieseonds = sw.getLastTaskTimeMillis();

        String message = new StringBuilder().append("Dictionary created with ")
                .append(dictionary.size())
                .append(" entries in ")
                .append(millieseonds)
                .append("ms")
                .toString();
        logger.info(message);

    }
    public static Map<String, String> getDictionary() {
        return DictionaryReference.dictionary;
    }


}
