package com.warm.game.user.cmd;

import org.springframework.beans.factory.annotation.Autowired;

import com.warm.game.network.command.Cmd;
import com.warm.game.network.command.Command;
import com.warm.game.network.session.Session;
import com.warm.game.network.transMsg.IMessage;
import com.warm.game.protocol.user.UserMsgProto.UserMsg;
import com.warm.game.user.entity.User;
import com.warm.game.user.service.UsrChkServiceImp;

/*
 * 20170802达到成就写到controller类，next step：实现注解分发
 * 20170803完成spring的使用和分发器
 * 20170804打成spring的注解注入到service
 * */
@Cmd(value = 12138)
public class UserCheckCmd implements Command {

	//spring的IOC自动注解注入
	@Autowired
	private UsrChkServiceImp usrChkService;
	
	public void messageHandler(Session session,IMessage tmp) throws Exception {
		UserMsg req = UserMsg.parseFrom(tmp.getBody());
		User user = new User(req.getUserName(), req.getPassword());
		System.out.println("Service accept client subscribe req:[" + req.toString() + "]");
		System.out.println(usrChkService.check(user));
	}
}
