package com.example.dictionary.service;

import com.example.dictionary.exception.WordNotFoundException;
import com.example.dictionary.model.Entry;
import com.example.dictionary.reference.DictionaryReference;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DictionaryService {

    public Entry getWord(String word) throws WordNotFoundException {

        String definition = DictionaryReference.getDictionary().get(word);
        Entry entry = new Entry(word, definition);

        // validation
        if (entry.getDefinition() == null) {
            throw new WordNotFoundException("Word [" + word + "] not found.");
        }

        return entry;
    }

    public List<Entry> getWordsStartingWith(String value) {

        return DictionaryReference.getDictionary()
                                  .entrySet()
                                  .stream()
                                  .filter(entry -> entry.getKey()
                                                        .startsWith(value))
                                  .sorted(Map.Entry.comparingByKey(Comparator.naturalOrder()))
                                  .map(entry -> new Entry(entry.getKey(), entry.getValue()))
                                  .collect(Collectors.toList());
    }

    public List<Entry> getWordsThatContain(String value) {

        return DictionaryReference.getDictionary()
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey()
                        .contains(value))
                .sorted(Map.Entry.comparingByKey(Comparator.naturalOrder()))
                .map(entry -> new Entry(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public List<Entry> getWordsThatContainConsecutiveDoubleLetters() {

        return DictionaryReference.getDictionary()
                .entrySet()
                .stream()
                .filter(entry -> {

                    String word = entry.getKey();
                    for (int i = 0; i < word.length() - 1; i++) {
                        if (word.charAt(i) == word.charAt(i + 1)) {
                            return true;
                        }
                    }
                    return false;
                })
                .sorted(Map.Entry.comparingByKey(Comparator.naturalOrder()))
                .map(entry -> new Entry(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public List<Entry> getWordsEndingWith(String value) {

        return DictionaryReference.getDictionary()
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey()
                        .endsWith(value))
                .sorted(Map.Entry.comparingByKey(Comparator.naturalOrder()))
                .map(entry -> new Entry(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
