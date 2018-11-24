package com.warm.game.network.transMsg;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/*
 * 编码的过程IMessage --> ByteBuf
 * */
public class CustomProtobufEncoder extends MessageToByteEncoder<IMessage> {

	protected void encode(ChannelHandlerContext ctx, IMessage message, ByteBuf out) throws Exception {
		message.writeHeader(out);
		message.writeBody(out);
	}

	// @Override
	// protected void encode(ChannelHandlerContext ctx, MessageLite msg, ByteBuf
	// out) throws Exception {
	// byte[] body = msg.toByteArray();
	// byte[] header = encodeHeader(msg, (short) body.length);
	// out.writeBytes(header);
	// out.writeBytes(body);
	// return;
	// }
	//
	// private byte[] encodeHeader(MessageLite msg, short bodyLength) {
	// byte messageType = 0x0f;
	// if(msg instanceof UserMsgProto.UserMsg)
	// messageType = 0x00;
	// else if(msg instanceof UserMsgProto.chkLoginMsg)
	// messageType = 0x01;
	// //创建一个4字节的头
	// byte[] header = new byte[4];
	// //body长度low
	// header[0] = (byte)(bodyLength & 0xff);
	// //body长度high
	// header[1] = (byte)((bodyLength >> 8) & 0xff);
	// header[2] = 0;
	// //协议字段标示哪个message
	// header[3] = messageType;
	//
	// return header;
	// }
}
