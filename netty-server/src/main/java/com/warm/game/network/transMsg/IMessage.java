package com.warm.game.network.transMsg;

import io.netty.buffer.ByteBuf;

/*
 * 这里定义的消息格式为 messageHeader(playerId,protobufCode),messageBody
 * */
public interface IMessage {
	public int getBodyLen();

	public int getParamsLen();

	/*
	 * 玩家的唯一标识符playerId
	 * 
	 */
	public long getId();

	public byte[] getBody();

	public short getCode();

	public short getHeader();

	public void setHeader(short paramShort);

	public void setCode(short paramShort);

	public void readHeader(ByteBuf paramByteBuf);

	public void readBody(ByteBuf paramByteBuf);

	public void writeHeader(ByteBuf paramByteBuf);

	public void writeBody(ByteBuf paramByteBuf);
}
