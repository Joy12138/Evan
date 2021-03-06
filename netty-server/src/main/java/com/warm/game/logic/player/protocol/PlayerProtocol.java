package com.warm.game.logic.player.protocol;

import com.warm.util.type.ProtocolType;

public class PlayerProtocol {
	@ProtocolType({ 10000, 10099 })
	public interface Client {
		// 注册
		short REGISTER = 10001;
		// 登录
		short LOGIN = 10002;
		//信息同步---玩家移动
		short MOVE = 10003;
	}

	@ProtocolType({ 20000, 20099 })
	public interface Server {
		// 注册结果
		short REGISTER = 20001;
		//不需要登录的原因在于数据来源于第三方平台
		//用户个人数据
		short PLAYER_INFO = 20002;
		//信息同步---玩家移动
		short MOVE = 20003;
	}
}
