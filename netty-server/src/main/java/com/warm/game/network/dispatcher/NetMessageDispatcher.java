package com.warm.game.network.dispatcher;

import com.warm.game.network.command.Command;
import com.warm.game.network.command.CommandContext;
import com.warm.game.network.session.Session;
import com.warm.game.network.session.SessionServer;
import com.warm.game.network.transMsg.IMessage;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

//netty用于每个channelHandler可以安全的共享这个单例
@Sharable
public class NetMessageDispatcher extends ChannelHandlerAdapter {

	private CommandContext cmds;

	public NetMessageDispatcher(CommandContext cmds) {
		this.cmds = cmds;
	}

	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		/*
		 * 封装protobuf的作用在这里体现了
		 */
		IMessage req = (IMessage) msg;
		Session session = Session.get(ctx);
		/*
		 * 20170802暂时直接使用，而不使用code分发next step:使用注解实现直接实例化并且分发
		 */
		// UserCheckCmd userCheckCmd = new UserCheckCmd();
		// userCheckCmd.messageHandler(req);

		short code = req.getCode();
		Command cmd = this.cmds.getCmd(code);
		if (null == cmd) {
			System.out.println("cmd编号出错");
			return;
		}
		cmd.messageHandler(session, req);
	}

	// 这个registered方法是在每次已连接socket中触发，也就是每次登录都会建立新的channel
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		Session session = new SessionServer(ctx.channel());
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
