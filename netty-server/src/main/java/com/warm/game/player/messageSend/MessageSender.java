package com.warm.game.player.messageSend;

import com.warm.game.network.transMsg.Message;
/*
 *  提供message发送的接口，只要实现这个方法，即可发送自定义的信息
 * */
public interface MessageSender {
	void sendMessage(Message message);
}
