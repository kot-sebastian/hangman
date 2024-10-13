package com.hangman.adapter.db.game

import com.hangman.domain.game.GameState
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static com.hangman.domain.game.GameStatus.LOBBY
import static java.util.Collections.emptySet

class GameEntityMapperTest extends Specification {

    @Subject
    GameEntityMapper mapper = new GameEntityMapper()

    @Unroll
    def "should map to domain"() {
        given:
        def object =
                GameEntity
                        .builder()
                        .id(id)
                        .gameStatus(gameStatus)
                        .word(word)
                        .usedLetters(usedLetters)
                        .badCounter(badCounter)
                        .expectedMoveUserId(expectedMoveUserId)
                        .userIds(userIds)
                        .readyUserIds(readyUserIds)
                        .lostUserId(lostUserId)
                        .winnerUserId(winnerUserId)
                        .build()

        when:
        def result = mapper.toDomain(object)

        then:
        with(result as GameState) {
            it.id == id
            it.gameStatus == gameStatus
            it.word == word
            it.usedLetters == usedLetters
            it.badCounter == badCounter
            it.expectedMoveUserId == expectedMoveUserId
            it.userIds == ((userIds == null) ? emptySet() : Set.of(userIds.split(',')))
            it.readyUserIds == ((readyUserIds == null) ? emptySet() : Set.of(readyUserIds.split(',')))
            it.lostUserId == lostUserId
            it.winnerUserId == winnerUserId
        }

        where:
        id   | gameStatus | word  | usedLetters | badCounter | expectedMoveUserId | userIds | readyUserIds | lostUserId | winnerUserId || _
        null | null       | null  | null        | null       | null               | null    | null         | null       | null         || _
        1L   | null       | null  | null        | null       | null               | null    | null         | null       | null         || _
        null | LOBBY      | null  | null        | null       | null               | null    | null         | null       | null         || _
        null | null       | "KNP" | null        | null       | null               | null    | null         | null       | null         || _
        null | null       | null  | "asd"       | null       | null               | null    | null         | null       | null         || _
        null | null       | null  | null        | 3          | null               | null    | null         | null       | null         || _
        null | null       | null  | null        | null       | "dsa"              | null    | null         | null       | null         || _
        null | null       | null  | null        | null       | null               | "a,b"   | null         | null       | null         || _
        null | null       | null  | null        | null       | null               | null    | "a,b"        | null       | null         || _
        null | null       | null  | null        | null       | null               | null    | null         | "da"       | null         || _
        null | null       | null  | null        | null       | null               | null    | null         | null       | "wp"         || _
    }

    @Unroll
    def "should map to entity"() {
        given:
        def object =
                GameState
                        .builder()
                        .id(id)
                        .gameStatus(gameStatus)
                        .word(word)
                        .usedLetters(usedLetters)
                        .badCounter(badCounter)
                        .expectedMoveUserId(expectedMoveUserId)
                        .userIds(userIds)
                        .readyUserIds(readyUserIds)
                        .lostUserId(lostUserId)
                        .winnerUserId(winnerUserId)
                        .build()

        when:
        def result = mapper.toEntity(object)

        then:
        with(result as GameEntity) {
            it.id == id
            it.gameStatus == gameStatus
            it.word == word
            it.usedLetters == usedLetters
            it.badCounter == badCounter
            it.expectedMoveUserId == expectedMoveUserId
            it.userIds == ((userIds.isEmpty()) ? null : userIds.join(','))
            it.readyUserIds == ((readyUserIds.isEmpty()) ? null : readyUserIds.join(','))
            it.lostUserId == lostUserId
            it.winnerUserId == winnerUserId
        }

        where:
        id   | gameStatus | word  | usedLetters | badCounter | expectedMoveUserId | userIds          | readyUserIds     | lostUserId | winnerUserId || _
        null | null       | null  | null        | null       | null               | emptySet()       | emptySet()       | null       | null         || _
        1L   | null       | null  | null        | null       | null               | emptySet()       | emptySet()       | null       | null         || _
        null | LOBBY      | null  | null        | null       | null               | emptySet()       | emptySet()       | null       | null         || _
        null | null       | "KNP" | null        | null       | null               | emptySet()       | emptySet()       | null       | null         || _
        null | null       | null  | "asd"       | null       | null               | emptySet()       | emptySet()       | null       | null         || _
        null | null       | null  | null        | 3          | null               | emptySet()       | emptySet()       | null       | null         || _
        null | null       | null  | null        | null       | "dsa"              | emptySet()       | emptySet()       | null       | null         || _
        null | null       | null  | null        | null       | null               | Set.of("a", "b") | emptySet()       | null       | null         || _
        null | null       | null  | null        | null       | null               | emptySet()       | Set.of("a", "b") | null       | null         || _
        null | null       | null  | null        | null       | null               | emptySet()       | emptySet()       | "da"       | null         || _
        null | null       | null  | null        | null       | null               | emptySet()       | emptySet()       | null       | "wp"         || _
    }
}
