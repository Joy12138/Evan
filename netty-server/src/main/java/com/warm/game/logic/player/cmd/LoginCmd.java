package com.warm.game.logic.player.cmd;

import com.warm.game.logic.player.GamePlayer;
import com.warm.game.logic.player.protocol.PlayerProtocol;
import com.warm.game.network.command.Cmd;
import com.warm.game.network.command.Command;
import com.warm.game.network.session.Session;
import com.warm.game.network.transMsg.IMessage;
import com.warm.game.player.command.PlayerCommand;
import com.warm.game.protocol.user.UserMsgProto.UserMsg;

@Cmd(PlayerProtocol.Client.LOGIN)
public class LoginCmd implements Command {

//	@Override
//	public void messageHandler(GamePlayer player, IMessage tmp) throws Exception {
//		UserMsg req = UserMsg.parseFrom(tmp.getBody());
//		System.out.println("Service accept client subscribe req:[" + req.toString() + "]");
//		player.sendPlayerInfoMsg();
//	}

	@Override
	public void messageHandler(Session session, IMessage req) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
