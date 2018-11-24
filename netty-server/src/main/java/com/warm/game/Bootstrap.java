package com.warm.game;

import com.warm.game.network.NetServer;
import com.warm.game.network.command.CommandContext;
import com.warm.game.sysInit.ApplicationInitContext;

public class Bootstrap {
	private static NetServer netServer;

	public static void main(String[] args) throws Exception {
		try {
			/*
			 * 初始化后台CommandContext的过程，以便分发器的分发
			 * */
			ApplicationInitContext.init();
			netServer = new NetServer();
			netServer.start(8080,ApplicationInitContext.context.getBean(CommandContext.class));
			System.out.println("监听成功");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
