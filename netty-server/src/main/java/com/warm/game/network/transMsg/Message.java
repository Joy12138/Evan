package com.warm.game.network.transMsg;

import com.google.protobuf.MessageLite;

import io.netty.buffer.ByteBuf;

/* 
 * 封装protobuf的实体类
 * */
public class Message implements IMessage {

	public static final MessageBuilder BUIDLER = new MessageBuilder() {
		public IMessage build(short code) {
			return Message.build(code);
		}
	};

	private long id;
	private short header;
	private short code;
	private short paramsLen;
	private short bodyLen;
	private byte[] body;

	public Message() {
		this.header = 21346;
		this.body = new byte[0];
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public short getHeader() {
		return header;
	}

	public void setHeader(short header) {
		this.header = header;
	}

	public short getCode() {
		return code;
	}

	public void setCode(short code) {
		this.code = code;
	}

	public int getParamsLen() {
		return paramsLen;
	}

	public void setParamsLen(short paramsLen) {
		this.paramsLen = paramsLen;
	}

	public int getBodyLen() {
		return bodyLen;
	}

	public void setBodyLen(short bodyLen) {
		this.bodyLen = bodyLen;
	}

	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}

	public void readHeader(ByteBuf buff) {
		this.header = buff.readShort();
		this.code = buff.readShort();
		this.id = buff.readLong();
		this.paramsLen = buff.readShort();
		this.bodyLen = buff.readShort();
	}

	public void readBody(ByteBuf buff) {
		int len = getBodyLen();
		if (len > 0) {
			byte[] bytes = new byte[len];
			buff.readBytes(bytes);
			this.body = bytes;
		}
	}

	public void writeHeader(ByteBuf buff) {
		writeHeader0(buff, this.header, this.code, this.id);
	}

	private void writeHeader0(ByteBuf buff, short header, short code, long id) {
        buff.writeShort(header);
        buff.writeShort(code);
        buff.writeLong(id);       
        buff.writeShort(this.paramsLen);
        buff.writeShort(this.bodyLen);
    }	
	public void writeBody(ByteBuf buff) {
		if (getBodyLen() > 0) {
			buff.writeBytes(this.body);
		}
	}

	public static Message build() {
		return new Message();
	}

	public static Message build(short code) {
		Message message = build();
		message.setCode(code);
		return message;
	}

	public static Message build(short code, long id) {
		Message message = build(code);
		message.setId(id);
		return message;
	}

	public static Message build(short code, MessageLite lite) {
		byte[] bytes = lite == null ? null : lite.toByteArray();
		return build(code, bytes);
	}

	public static Message build(Short code, byte[] bytes) {
		Message message = build(code);
		if (bytes != null) {
			if (bytes.length > 65535) {
				throw new IllegalArgumentException("Message body is too long 4 encoding!!!");
			}
			message.setBodyLen((short) bytes.length);
			message.setBody(bytes);
		}
		return message;
	}

	public IMessage copy(long id) {
		return new CopiedMessage(this, id);
	}
	
	private static class CopiedMessage implements IMessage {
        private final Message message;
        private final long id;
        private short header;
        private short code;
        
        public CopiedMessage(Message message, long id) {
            this.message = message;
            this.id = id;
            this.header = message.header;
            this.code = message.code;
        }
        @Override
        public int getBodyLen() {
            return message.getBodyLen();
        }
        @Override
        public int getParamsLen() {
            return message.getParamsLen();
        }
        @Override
        public long getId() {
            return this.id;
        }
        @Override
        public byte[] getBody() {
            return message.body;
        }
        @Override
        public void readHeader(ByteBuf buff) {
            message.readHeader(buff);
        }
        @Override
        public void readBody(ByteBuf buff) {
            message.readBody(buff);
        }
        @Override
        public void writeHeader(ByteBuf buff) {
            message.writeHeader0(buff, this.header, this.code, this.id);
        }
        @Override
        public void writeBody(ByteBuf buff) {
            message.writeBody(buff);
        }

        @Override
        public short getHeader() {
            return this.header;
        }

        @Override
        public short getCode() {
            return this.code;
        }
        @Override
        public void setHeader(short header) {
            this.header = header;
        }
        @Override
        public void setCode(short code) {
            this.code = code;
        }
    }
}
