package com.warm.game.network;

import com.warm.game.network.transMsg.IMessage;
import com.warm.game.network.transMsg.Message;
import com.warm.game.protocol.user.UserMsgProto.UserMsg;

import com.warm.game.user.LoginBuilder;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/*
 * 这里是单独写的一个处理逻辑，20170801 next step：这部分的逻辑又分发器转发到每一个处理逻辑去单独处理
 * 20170802这个类已经废弃，可以当作例子看，处理逻辑已经移入command中
 * */
public class TimeServerHandler extends ChannelHandlerAdapter {
	private LoginBuilder loginBuilder;

	public TimeServerHandler() {
		loginBuilder = new LoginBuilder();
	}

	public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
		/*
		 * 使用IMessage封装protobuf的意义在于：分发器和编解码的过程都避免了if／else的判断条件
		 */
		IMessage tmp = (IMessage) msg;
		UserMsg req = UserMsg.parseFrom(tmp.getBody());
		System.out.println("Service accept client subscribe req:[" + req.toString() + "]");
		boolean flag = false;
		if (req.getUserName().equals("zhj")) {
			flag = true;
		}
		/*
		 * protobuf与IMessage的关系在于，protobuf负责数据的存储，
		 * IMessage装载protobuf并且与Bytebuf打交道
		 */
		ctx.writeAndFlush(Message.build((short)10002,loginBuilder.createLoginMsg(flag)));

	}
}
