package com.hangman.adapter.rest.random.word;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class RandomWordRestRepository implements RandomWordRepository {

    private final RandomWordRyanrkApiClient randomWordRyanrkApiClient;

    @Override
    public String randomWord() {
        return randomWordRyanrkApiClient.getRandomWord().get(0);
    }
}
