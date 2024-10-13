package com.hangman.domain.game;

import com.hangman.adapter.db.game.GameRepository;
import com.hangman.domain.random.word.RandomWordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static com.hangman.domain.game.GameStatus.*;
import static java.lang.Character.toUpperCase;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.collections4.SetUtils.difference;
import static org.apache.commons.collections4.SetUtils.emptySet;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@Service
@RequiredArgsConstructor
class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final GameValidationService validationService;
    private final RandomWordService randomWordService;
    private final TurnChoser turnChoser;

    @Override
    public GameState createGame(String userId) {
        return gameRepository
                .saveOrUpdate(
                        GameState
                                .builder()
                                .gameStatus(LOBBY)
                                .word(randomWordService.generateRandomUpperCasedWord())
                                .usedLetters(EMPTY)
                                .badCounter(0)
                                .userIds(Set.of(userId))
                                .readyUserIds(emptySet())
                                .build()
                );
    }

    @Override
    public GameState getGameState(Long gameId, String userId) {
        final var maybeGame = gameRepository.findById(gameId);
        if (maybeGame.isEmpty()) {
            throw new RuntimeException("Game with id " + gameId + " does not exist");
        }
        final var game = maybeGame.get();
        validationService.userIsAssignedToGame(game, userId);
        return game;
    }

    @Override
    public GameState addUserToGame(Long gameId, String userId) {
        final var maybeGame = gameRepository.findById(gameId);
        if (maybeGame.isEmpty()) {
            throw new RuntimeException("Game with id " + gameId + " does not exist");
        }
        final var game = maybeGame.get();
        validationService.gameIsInLobby(game);
        final var userIds = new HashSet<>(game.getUserIds());
        userIds.add(userId);
        game.setUserIds(userIds);
        return gameRepository.saveOrUpdate(game);
    }

    @Override
    public void removeUserFromGame(Long gameId, String userId) {
        final var maybeGame = gameRepository.findById(gameId);
        if (maybeGame.isEmpty()) {
            throw new RuntimeException("Game with id " + gameId + " does not exist");
        }
        final var game = maybeGame.get();
        validationService.gameIsInLobby(game);
        final var userIds = new HashSet<>(game.getUserIds());
        userIds.remove(userId);
        game.setUserIds(userIds);
        if (game.getReadyUserIds().size() == game.getUserIds().size()) {
            game.setGameStatus(IN_PROGRESS);
            game.setExpectedMoveUserId(turnChoser.nextUserTurn(game.getUserIds(), userId));
        }
        gameRepository.saveOrUpdate(game);
    }

    @Override
    public GameState markUserReady(Long gameId, String userId) {
        final var maybeGame = gameRepository.findById(gameId);
        if (maybeGame.isEmpty()) {
            throw new RuntimeException("Game with id " + gameId + " does not exist");
        }
        final var game = maybeGame.get();
        validationService.gameIsInLobby(game);
        final var readyUserIds = new HashSet<>(game.getReadyUserIds());
        readyUserIds.add(userId);
        game.setReadyUserIds(readyUserIds);
        if (game.getReadyUserIds().size() == game.getUserIds().size()) {
            game.setGameStatus(IN_PROGRESS);
            game.setExpectedMoveUserId(turnChoser.nextUserTurn(game.getUserIds(), userId));
        }
        return gameRepository.saveOrUpdate(game);
    }

    @Override
    public GameState addLetter(Long gameId, String userId, Character letter) {
        final var capitalizedLetter = toUpperCase(letter);
        final var maybeGame = gameRepository.findById(gameId);
        if (maybeGame.isEmpty()) {
            throw new RuntimeException("Game with id " + gameId + " does not exist");
        }
        final var game = maybeGame.get();
        validationService.validateMove(game, userId);
        game.setExpectedMoveUserId(turnChoser.nextUserTurn(game.getUserIds(), userId));
        if (wordContainsNewCharacter(capitalizedLetter, game)) {
            game.setUsedLetters(game.getUsedLetters() + capitalizedLetter);
            if (wordIsFound(game)) {
                game.setGameStatus(FINISHED);
                game.setWinnerUserId(userId);
            }
        } else {
            game.setUsedLetters(game.getUsedLetters() + capitalizedLetter);
            game.setBadCounter(game.getBadCounter() + 1);
            if (game.getBadCounter() >= 6) {
                game.setGameStatus(FINISHED);
                game.setLostUserId(userId);
            }
        }
        return gameRepository.saveOrUpdate(game);
    }

    @Override
    public Collection<GameState> findGamesForUser(String userId) {
        return gameRepository.findByUserId(userId);
    }

    private boolean wordContainsNewCharacter(Character letter, GameState game) {
        return game.getWord().indexOf(letter) != -1;
    }

    private boolean wordIsFound(GameState game) {
        final var wordLetters = game.getWord().chars().boxed().collect(toSet());
        final var usedLetters = game.getUsedLetters().chars().boxed().collect(toSet());
        return difference(wordLetters, usedLetters).isEmpty();
    }
}
