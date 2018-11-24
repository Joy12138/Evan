package com.warm.game.logic.player;

import com.warm.game.logic.player.protocol.PlayerProtocol;
import com.warm.game.network.transMsg.Message;
import com.warm.game.player.CorePlayer;
import com.warm.game.protocol.user.UserMsgProto.playerInfoMsg;

public class GamePlayer extends CorePlayer {

	public GamePlayer(long playerId) {
		super(playerId);
	}

	public void sendPlayerInfoMsg() {
		this.sendMessage(Message.build(PlayerProtocol.Server.PLAYER_INFO, buildPlayerInfoMsg()));
	}

	protected playerInfoMsg buildPlayerInfoMsg() {
		playerInfoMsg.Builder builder = playerInfoMsg.newBuilder();
		builder.setPlayerId(123);
		builder.setName("evan");
		builder.setLevel(0);
		return builder.build();
	}

}
