package com.hangman.adapter.db.game;

import com.hangman.domain.game.GameState;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class GameRepositoryImpl implements GameRepository {

    private final GameJpaRepository gameJpaRepository;
    private final GameEntityMapper gameEntityMapper;

    @Override
    public Optional<GameState> findById(Long id) {
        return gameJpaRepository
                .findById(id)
                .map(gameEntityMapper::toDomain);
    }

    @Override
    public GameState saveOrUpdate(GameState gameState) {
        return gameEntityMapper.toDomain(gameJpaRepository.save(gameEntityMapper.toEntity(gameState)));
    }

    @Override
    public void deleteById(Long id) {
        gameJpaRepository.deleteById(id);
    }

    @Override
    public Collection<GameState> findByUserId(String userId) {
        throw new NotImplementedException("not implemented");
    }
}
