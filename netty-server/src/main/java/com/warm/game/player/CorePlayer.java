package com.warm.game.player;

import org.springframework.beans.factory.annotation.Autowired;

import com.warm.game.network.session.SessionContext;
import com.warm.game.network.transMsg.Message;
import com.warm.game.player.messageSend.MessageSender;

public class CorePlayer implements MessageSender {

	protected long playerId;
	/*
	 * 关联SessionContext
	 */
	@Autowired
	private SessionContext sessionCtx;

	public CorePlayer(long playerId) {
		this.playerId = playerId;
	}

	public boolean load() {
		return true;
	}

	/*
	 * 这里只能获取message的body部分，这里需要组装基本的id信息到需要发送的信息中
	 */
	@Override
	public void sendMessage(Message message) {
		sessionCtx.sendMessage(playerId, message.copy(this.playerId));
	}
}
