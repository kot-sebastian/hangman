package com.hangman.port.rest.game;

import com.hangman.domain.game.GameService;
import com.hangman.domain.game.GameState;
import com.hangman.domain.random.word.RandomWordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
class GameController {

    private final GameService service;
    private final GameStateMapper gameStateMapper;

    @GetMapping("/state")
    GameStateDto getGameState(
            @RequestParam(value = "gameId") Long gameId,
            @RequestParam("userId") String userId) {
        final var gameState = service.getGameState(gameId, userId);
        return gameStateMapper.toDto(gameState);
    }

    @PostMapping("/{gameId}/join")
    GameStateDto joinGame(@PathVariable Long gameId, @RequestParam("userId") String userId) {
        return gameStateMapper.toDto(service.addUserToGame(gameId, userId));
    }

    @PostMapping("/{gameId}/leave")
    void leaveGame(@PathVariable Long gameId, @RequestParam("userId") String userId) {
        service.removeUserFromGame(gameId, userId);
    }

    @PostMapping
    GameStateDto createGame(@RequestParam("userId") String userId) {
        return gameStateMapper.toDto(service.createGame(userId));
    }

    @PostMapping("/{gameId}/mark-user-ready")
    GameStateDto markUserReady(@PathVariable Long gameId, @RequestParam("userId") String userId) {
        return gameStateMapper.toDto(service.markUserReady(gameId, userId));
    }

    @GetMapping
    Collection<GameStateDto> getUserGames(@RequestParam("userId") String userId) {
        return service.findGamesForUser(userId)
                .stream()
                .map(gameStateMapper::toDto)
                .toList();
    }

    @PostMapping("/{gameId}/continue")
    GameStateDto makeMoveInGame(@PathVariable Long gameId, @RequestParam("userId") String userId, @RequestParam("letter") Character letter) {
        return gameStateMapper.toDto(service.addLetter(gameId, userId, letter));
    }
}
