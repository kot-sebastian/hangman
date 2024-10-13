package com.hangman.domain.random.word;

import com.hangman.adapter.rest.random.word.RandomWordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.StringUtils.upperCase;

@Service
@RequiredArgsConstructor
class RandomWordServiceImpl implements RandomWordService {
    private final RandomWordRepository repository;

    @Override
    public String generateRandomUpperCasedWord() {
        return upperCase(repository.randomWord());
    }
}
