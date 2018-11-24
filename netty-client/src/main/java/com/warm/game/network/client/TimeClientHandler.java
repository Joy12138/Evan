package com.warm.game.network.client;

import java.util.LinkedList;
import java.util.Queue;

import com.warm.game.message.IMessage;
import com.warm.game.message.Message;
import com.warm.game.network.session.Session;
import com.warm.game.network.session.SessionServer;
import com.warm.game.protocol.room.RoomMsgProto.sucessMsg;
import com.warm.game.protocol.user.UserMsgProto.chkLoginMsg;
import com.warm.game.protocol.user.UserMsgProto.moveMsg;
import com.warm.game.user.UserMessageBuilder;
import com.warm.game.user.entity.User;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeClientHandler extends ChannelHandlerAdapter {
	private UserMessageBuilder userMessageBuilder;

	public TimeClientHandler() {
		userMessageBuilder = new UserMessageBuilder();
	}

	/*
	 * 20170804这里需要关闭ChannelHandlerContext－－还未关闭
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception { // (1)
		User user = new User();
		user.setPlayerId(12136);
		user.setName("zhj");
		user.setLevel(0);
		Queue<Message> queue = new LinkedList<Message>();
		queue.add(Message.build((short) 10001, (long) 12139)); //注册
		queue.add(Message.build((short) 10101, (long) 12139)); //进入房间
		while (!queue.isEmpty()) {
			ctx.write(queue.poll());
		}
		ctx.flush();
	}

	/**
	 * 20170906进度---实现了加入房间->开始战斗->房间内通信的过程
	 * next step : 开始写后台的核心业务模块--->移动
	 * */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		IMessage tmp = (IMessage) msg;
		sucessMsg req = sucessMsg.parseFrom(tmp.getBody());
		System.out.println("Receive server response:[" + req.getBattleFlag() + "]");
		ctx.writeAndFlush(Message.build((short) 10003, (long) 12136));
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Client Read complete");
		ctx.flush();
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		Session session = new SessionServer(ctx.channel());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
