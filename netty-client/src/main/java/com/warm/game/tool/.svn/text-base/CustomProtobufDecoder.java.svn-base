package com.warm.game.tool;

import java.util.List;

import com.warm.game.message.IMessage;
import com.warm.game.message.MessageBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/*
 * 用于解决netty官方编解码器需要指定唯一protobuf类型的问题
 * */
public class CustomProtobufDecoder extends ByteToMessageDecoder {

	private final MessageBuilder builder;

	public CustomProtobufDecoder(MessageBuilder builder) {
		this.builder = builder;
	}

	/*
	 * 解码的过程ByteBuf -->IMessage
	 */
	protected void decode(ChannelHandlerContext ctx, ByteBuf buff, List<Object> out) throws Exception {
		/*
		 * 这里粘包数据需要进行测试确认
		 */
		if (buff.readableBytes() < 4) {
			return;
		}
		buff.markReaderIndex();
		IMessage message = this.builder.build();
		message.readHeader(buff);
		int len = message.getBodyLen() + message.getParamsLen();
		if (len > buff.readableBytes()) {
			buff.resetReaderIndex();
			return;
		}
		message.readBody(buff);
		out.add(message);
	}

	/*
	 * 使用新的方式编解码IMessage,IMessage <--> byteBuf互换，作用是避免在这一层的过多的if／else
	 */
	// @Override
	// protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object>
	// out) throws Exception {
	// //设置缓冲区长度
	// while(in.readableBytes() > 4){
	// in.markReaderIndex();
	//
	// //获取包头中的body长度
	// byte low = in.readByte();
	// byte high = in.readByte();
	// short s0 = (short)(low & 0xff);
	// short s1 = (short)(high & 0xff);
	// s1 <<= 8;
	// short length = (short)(s0 | s1);
	//
	// //获取包头中的protobuf类型
	// in.readByte();
	// byte dataType = in.readByte();
	//
	// //如果可读长度小于body长度,恢复读指针，退出
	// if(in.readableBytes() < length){
	// in.resetReaderIndex();
	// return;
	// }
	//
	// //读取body
	// ByteBuf bodyByteBuf = in.readBytes(length);
	//
	// byte[] array;
	// int offset;
	//
	// int readableLen = bodyByteBuf.readableBytes();
	// if(bodyByteBuf.hasArray()){
	// array = bodyByteBuf.array();
	// offset = bodyByteBuf.arrayOffset() + bodyByteBuf.readerIndex();
	// }else{
	// array = new byte[readableLen];
	// bodyByteBuf.getBytes(bodyByteBuf.readerIndex(),array,0,readableLen);
	// offset = 0;
	// }
	//
	// //反序列化
	// MessageLite result = decodeBody(dataType,array,offset,readableLen);
	// out.add(result);
	// }
	// }
	//
	// /*
	// * 例程，这里的设计估计需要修改20170730
	// * */
	// private MessageLite decodeBody(byte dataType, byte[] array, int offset,
	// int readableLen) throws Exception {
	// if( 0x00 == dataType){
	// return
	// UserMsgProto.UserMsg.getDefaultInstance().getParserForType().parseFrom(array,offset,readableLen);
	// }else if(0x01 == dataType){
	// return
	// UserMsgProto.chkLoginMsg.getDefaultInstance().getParserForType().parseFrom(array,offset,readableLen);
	// }
	// return null;
	// }

}
