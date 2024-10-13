package com.hangman.domain.game;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
class TurnChoser {

    String nextUserTurn(Set<String> userIds, String lastUserId) {
        final var sortedUserIds = userIds.stream().sorted().toList();
        final var lastUserIndex = sortedUserIds.indexOf(lastUserId);
        final var nextUserIndex = (lastUserIndex + 1) % sortedUserIds.size();
        return sortedUserIds.get(nextUserIndex);
    }
}
