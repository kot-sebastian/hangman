package com.hangman.domain.game;

import java.util.Collection;

public interface GameService {

    GameState createGame(String userId);

    GameState getGameState(Long gameId, String userId);

    GameState addUserToGame(Long gameId, String userId);

    void removeUserFromGame(Long gameId, String userId);

    GameState markUserReady(Long gameId, String userId);

    GameState addLetter(Long gameId, String userId, Character letter);

    Collection<GameState> findGamesForUser(String userId);
}
