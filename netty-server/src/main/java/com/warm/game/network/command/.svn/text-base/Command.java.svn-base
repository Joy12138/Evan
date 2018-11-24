package com.warm.game.network.command;

import com.warm.game.network.session.Session;
import com.warm.game.network.transMsg.IMessage;
/*
 * 所有后台的XXXcmd实现这个结构，目的是便于CommandContext的管理（父类的作用，便于抽象）
 * */
public interface Command {
	public void messageHandler(Session session,IMessage req) throws Exception;
}
