package com.warm.game.network.session;

import com.warm.game.network.transMsg.IMessage;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

public abstract class Session {
	protected static final AttributeKey<Session> SESSION = AttributeKey.valueOf("SESSION");
	//这里通过ctx获取对应的channel
	public static Session get(ChannelHandlerContext channel) {
		return get(channel.channel());
	}
	//这里通过Attribute容器获取与当前channel绑定的Session
	private static Session get(Channel channel) {
		return channel.attr(SESSION).get();
	}

	public abstract long id();
	
	public abstract Channel channel();
	
	public abstract Session bind(long id);
	
	public abstract void sendMessage(IMessage message);
	
	public abstract void sendMessageAndClose(IMessage message);
	
	public abstract boolean isActive();
	
	public abstract boolean reconnect();
	
	public abstract void close();
}
