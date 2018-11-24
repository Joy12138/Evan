package com.warm.game.network.session;

import com.warm.game.message.IMessage;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;

/*
 * 通过这个类进行session的实例化（在分发器的chanelRigister中）
 * */
public class SessionServer extends Session {

	protected long id;
	protected Channel channel;

	public SessionServer(Channel channel) {
		this.bindChannel(channel);
	}

	private void bindChannel(Channel channel) {
		this.channel = channel;
		this.channel.attr(SESSION).set(this);
	}

	@Override
	public long id() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public Channel channel() {
		return this.channel;
	}

	@Override
	public Session bind(long id) {
		this.id = id;
		return this;
	}

	@Override
	public void sendMessage(IMessage message) {
		channel.writeAndFlush(message);
	}

	@Override
	public void sendMessageAndClose(IMessage message) {
		channel.write(message);
		channel.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
	}

	@Override
	public boolean isActive() {
		return null != this.channel && this.channel.isActive();
	}

	@Override
	public boolean reconnect() {
		return false;
	}

	@Override
	public void close() {
		if (null != channel && channel.isOpen()) {
			channel.close();
		}
	}

}
