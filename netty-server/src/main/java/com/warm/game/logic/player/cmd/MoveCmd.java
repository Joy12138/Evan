package com.warm.game.logic.player.cmd;

import org.springframework.beans.factory.annotation.Autowired;

import com.warm.game.business.module.room.base.GlobelRoomManager;
import com.warm.game.business.module.room.base.RoomContext;
import com.warm.game.logic.player.protocol.PlayerProtocol;
import com.warm.game.network.command.Cmd;
import com.warm.game.network.command.Command;
import com.warm.game.network.session.Session;
import com.warm.game.network.transMsg.IMessage;
import com.warm.game.network.transMsg.Message;
import com.warm.game.protocol.user.UserMsgProto.moveMsg;

@Cmd(PlayerProtocol.Client.MOVE)
public class MoveCmd implements Command {

	@Autowired
	private GlobelRoomManager globelRoomManager;

	@Override
	public void messageHandler(Session session, IMessage req) throws Exception {
		moveMsg.Builder builder = moveMsg.newBuilder();
		builder.setFlag(1);
		RoomContext roomContext = globelRoomManager.getRoom(123);
		roomContext.messageBroadcast(Message.build(PlayerProtocol.Server.MOVE, builder.build()));
	}

}
