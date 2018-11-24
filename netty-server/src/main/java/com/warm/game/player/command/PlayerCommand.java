package com.warm.game.player.command;

import org.springframework.beans.factory.annotation.Autowired;

import com.warm.game.network.command.Command;
import com.warm.game.network.session.Session;
import com.warm.game.network.transMsg.IMessage;
import com.warm.game.player.Player;
import com.warm.game.player.offerFx.DefaultPlayerLoader;

public abstract class PlayerCommand<T extends Player> implements Command {
	@Autowired
	private DefaultPlayerLoader playerLoader;

	@Override
	public void messageHandler(Session session, IMessage req) throws Exception {
		long playerId = req.getId();
		T player = playerLoader.getPlayer(playerId);
		if (null != player) {
			messageHandler((T) player, req);
		}
	}

	// for outter exec
	protected abstract void messageHandler(T player, IMessage req) throws Exception;
}
