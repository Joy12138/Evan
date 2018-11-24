package com.warm.game.business.module.room.protocol;

import com.warm.util.type.ProtocolType;

public class RoomProtocol {
	@ProtocolType({ 10100, 10199 })
	public interface client {
		// 开始匹配
		short MATCH_BATTLE = 10101;
	}

	@ProtocolType({ 20100, 20199 })
	public interface server {
		// 发送开始战斗的消息
		short START_BATTLE = 20101;
	}
}
