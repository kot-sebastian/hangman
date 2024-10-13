package com.hangman.adapter.rest.random.word;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(
        name = "randomWordRyanrkApi",
        url = "${hangman.rest.client.random-world.ryanrk.url}"
)
interface RandomWordRyanrkApiClient {

    @GetMapping("/api/en/word/random")
    List<String> getRandomWord();
}
