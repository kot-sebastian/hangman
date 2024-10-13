package com.hangman.adapter.db.game;

import com.hangman.domain.game.GameStatus;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.EnumType.STRING;

@Entity(name = "game")
@Table(name = "game")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
class GameEntity {

    @Id
    @GeneratedValue(generator = "game_seq")
    @SequenceGenerator(name = "game_seq", sequenceName = "game_seq", allocationSize = 1)
    private Long id;
    @Enumerated(STRING)
    private GameStatus gameStatus;
    private String word;
    private String usedLetters;
    private Integer badCounter;
    private String expectedMoveUserId;
    private String userIds;
    private String readyUserIds;
    private String lostUserId;
    private String winnerUserId;
}
