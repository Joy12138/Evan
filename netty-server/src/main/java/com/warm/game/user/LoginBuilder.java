package com.warm.game.user;

import com.warm.game.protocol.user.UserMsgProto.chkLoginMsg;

public class LoginBuilder {
	public chkLoginMsg createLoginMsg(boolean flag) {
		chkLoginMsg.Builder build = chkLoginMsg.newBuilder();
		build.setIsLogin(flag);
		return build.build();
	}
}
