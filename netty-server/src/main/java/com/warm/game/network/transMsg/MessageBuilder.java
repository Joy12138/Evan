package com.warm.game.network.transMsg;

public abstract class MessageBuilder {
	public IMessage build() {
		return build((short) 0);
	}

	public abstract IMessage build(short paramShort);
}
