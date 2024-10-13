package com.hangman.adapter.db.game;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface GameJpaRepository extends JpaRepository<GameEntity, Long> {
}
