package com.zduo.dotsandboxes.view;

import com.zduo.dotsandboxes.model.Player;

import java.util.Map;

public interface PlayersStateView {
    void setPlayerNow(Player player);

    void setPlayerOccupyingBoxesCount(Map<Player, Integer> player_occupyingBoxesCount_map);

    void playerTouched();

    void setWinner(Player[] winner);
}
