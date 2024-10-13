package com.hangman.domain.game;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class GameState {

    private Long id;
    private GameStatus gameStatus;
    private String word;
    private String usedLetters;
    private Integer badCounter;
    private String expectedMoveUserId;
    private Set<String> userIds;
    private Set<String> readyUserIds;
    private String lostUserId;
    private String winnerUserId;
}
