package com.hangman.port.rest.game;

import com.hangman.domain.game.GameStatus;
import lombok.Builder;

import java.util.Set;

@Builder
record GameStateDto(
        Long id,
        String word,
        String maskedWord,
        String usedLetters,
        Integer badCounter,
        String expectedMoveUserId,
        Set<String> userIds,
        Set<String> readyUserIds,
        String lostUserId,
        GameStatus status,
        String winnerUserId
) {
}
