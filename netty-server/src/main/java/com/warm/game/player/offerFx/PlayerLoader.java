package com.warm.game.player.offerFx;

import com.warm.game.player.Player;

public interface PlayerLoader {

	public boolean exists(long playerId);

	public <T extends Player> T getPlayer(long playerId);
}
