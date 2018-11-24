package com.warm.game.user;

import com.warm.game.user.entity.User;
import com.warm.game.protocol.user.UserMsgProto.UserMsg;

public class UserMessageBuilder {
	public UserMsg createUserMsg(User user) {
		UserMsg.Builder userBuilder = UserMsg.newBuilder();
		userBuilder.setPlayerId(user.getPlayerId());
		userBuilder.setName(user.getName());
		userBuilder.setLevel(user.getLevel());
		return userBuilder.build();
	}
}
