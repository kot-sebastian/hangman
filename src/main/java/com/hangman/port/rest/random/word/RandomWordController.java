package com.hangman.port.rest.random.word;

import com.hangman.domain.random.word.RandomWordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/random/word")
@RequiredArgsConstructor
class RandomWordController {

    private final RandomWordService service;

    @GetMapping
    String randomWord() {
        return service.generateRandomUpperCasedWord();
    }
}
