package com.warm.game.network;

import com.warm.game.network.transMsg.CustomProtobufDecoder;
import com.warm.game.network.transMsg.CustomProtobufEncoder;
import com.warm.game.network.transMsg.Message;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class NetChannelInitializer extends ChannelInitializer<SocketChannel> {
	private final ChannelHandler handler;

	public NetChannelInitializer(ChannelHandler handler) {
		this.handler = handler;
	}

	@Override
	protected void initChannel(SocketChannel sc) throws Exception {
		ChannelPipeline pipeline = sc.pipeline();
		/*
		 * 使用官方编解码
		 */
		// //用于解决半包和粘包问题
		// pipeline.addLast(new ProtobufVarint32FrameDecoder());
		// //反序列化指定的probuf
		// pipeline.addLast(new
		// ProtobufDecoder(UserMsgProto.UserMsg.getDefaultInstance()));
		// //给序列化的字节数组加一个简单的标识字节长度的包头
		// pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
		// //用于对protobuf类型进行序列化
		// pipeline.addLast(new ProtobufEncoder());
		/*
		 * 使用自定义编解码器，解决单一protobuf
		 */
		pipeline.addLast("decoder", new CustomProtobufDecoder(Message.BUIDLER));

		pipeline.addLast("encoder", new CustomProtobufEncoder());
		// 20170727先添加一个线程处理逻辑
		// pipeline.addLast(new TimeServerHandler());
		// 20170802添加分发器，通过分发器分发到不同的类去处理
		// pipeline.addLast(new NetMessageDispatcher());
		// 20170803分发器的布局完成，不用再动
		pipeline.addLast(this.handler);
	}

}
