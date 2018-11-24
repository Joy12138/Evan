package com.warm.game.business.module.room.cmd;

import org.springframework.beans.factory.annotation.Autowired;

import com.warm.game.business.module.room.base.GlobelRoomManager;
import com.warm.game.business.module.room.protocol.RoomProtocol;
import com.warm.game.network.command.Cmd;
import com.warm.game.network.command.Command;
import com.warm.game.network.session.Session;
import com.warm.game.network.transMsg.IMessage;

@Cmd(RoomProtocol.client.MATCH_BATTLE)
public class StartGameCmd implements Command {
	@Autowired
	public GlobelRoomManager globelRoomManager;
	
	@Override
	public void messageHandler(Session session, IMessage req) throws Exception {
		long playerId = req.getId();
		GlobelRoomManager.waitList.add(playerId);
		globelRoomManager.SysSelect();
	}

}
