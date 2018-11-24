package com.warm.game.logic.player.cmd;

import org.springframework.beans.factory.annotation.Autowired;

import com.warm.game.logic.player.protocol.PlayerProtocol;
import com.warm.game.network.command.Cmd;
import com.warm.game.network.command.Command;
import com.warm.game.network.session.Session;
import com.warm.game.network.session.SessionContext;
import com.warm.game.network.transMsg.IMessage;
import com.warm.game.network.transMsg.Message;
import com.warm.game.user.LoginBuilder;
@Cmd(PlayerProtocol.Client.REGISTER)
public class RegisterCmd implements Command{

	@Autowired
	private SessionContext sessionCtx;
	
	public void messageHandler(Session session, IMessage req) throws Exception {
		long playerId = req.getId();
		sessionCtx.registSession(playerId, session);
		System.out.println(playerId);
//		LoginBuilder builder = new LoginBuilder();
//		session.sendMessage(Message.build(PlayerProtocol.Server.REGISTER,builder.createLoginMsg(true)));
	}

}
