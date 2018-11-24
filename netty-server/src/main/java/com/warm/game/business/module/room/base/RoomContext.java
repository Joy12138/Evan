package com.warm.game.business.module.room.base;

import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.beans.factory.annotation.Autowired;

import com.warm.game.business.core.data.GameMode;
import com.warm.game.business.module.room.base.entity.member.MemberStatus;
import com.warm.game.business.module.room.base.entity.member.RoomMember;
import com.warm.game.business.module.room.base.entity.room.RoomStatus;
import com.warm.game.business.module.room.protocol.RoomProtocol;
import com.warm.game.event.EventNotifyer;
import com.warm.game.network.session.SessionContext;
import com.warm.game.network.transMsg.IMessage;
import com.warm.game.network.transMsg.Message;
import com.warm.game.protocol.room.RoomMsgProto.sucessMsg;

/**
 * 20170902单个房间的管理器，保存对房间玩家数据的操作
 */
public class RoomContext {
	/**
	 * 房间管理器的基础数据
	 */
	/**
	 * 房间类型---游戏模式
	 */
	public final GameMode type;
	/**
	 * 房间Id，唯一标示
	 */
	public final int roomId;
	/**
	 * 房间人数
	 */
	public final int memberNum;
	/**
	 * 房间状态
	 */
	protected RoomStatus roomStatus = RoomStatus.CREATED;
	/**
	 * 房间玩家容器
	 */
	protected CopyOnWriteArrayList<RoomMember> playerList = new CopyOnWriteArrayList<RoomMember>();
	/**
	 * 事件触发器---->eventContext模块
	 */
	@Autowired
	protected EventNotifyer eventNotifyer;
	/**
	 * 发送消息模块---->SessionContext模块（因为不是单例的，所以需要传参进来）
	 */
	private SessionContext sessionContext;

	/**
	 * 实例化一个房间
	 */
	public RoomContext(GameMode type, int roomId, int memberNum, SessionContext sessionContext) {
		this.type = type;
		this.roomId = roomId;
		this.memberNum = memberNum;
		this.sessionContext = sessionContext;
	}

	/**
	 * 新进入一个玩家--->在此之前玩家已经准备就绪 这里是解决玩家加入房间的解决方案，房间匹配的解决方案应该是在管理所有房间管理器那里
	 */
	public void enter(long playerId) {
		if (playerId < 0)
			return;
		synchronized (playerList) {
			if (playerList.size() >= this.memberNum) {
				return;
			}
			playerList.add(new RoomMember(playerId, MemberStatus.SUCCESS));
		}
	}

	/**
	 * 玩家离开房间--->玩家自动取消匹配
	 * 20170903这个离开的方法暂时用不到，因为只要被选中进入房间就无法离开，在未被选中之前可以取消等待状态-->这个逻辑在上层的context中实现
	 */
	public void leave(long playerId) {
		playerList.removeIf(member -> member.playerId == playerId);
	}

	/**
	 * 房间进入战斗状态---->如何进入战斗状态，是上层模块的调用
	 */
	public void startGame() {
		if (this.roomStatus != RoomStatus.CREATED) {
			return;
		}
		this.roomStatus = RoomStatus.STARTED;
		for (RoomMember roomMember : playerList) {
			if (MemberStatus.SUCCESS != roomMember.memberStatus) {
				System.out.println("玩家状态出错了");
				return;
			} else {
				roomMember.memberStatus = MemberStatus.BATTLE;
			}
		}
		/**
		 * 通知房间内所有的玩家--->以事件的机制发送给业务层的房间管理器 20170904直接发送消息
		 */
		sucessMsg.Builder builder = sucessMsg.newBuilder();
		builder.setBattleFlag("开始战斗");
		messageBroadcast(Message.build(RoomProtocol.server.START_BATTLE, builder.build()));
	}

	/**
	 * 房间内广播消息
	 */
	public synchronized void messageBroadcast(IMessage message) {
		for (RoomMember roomMember : playerList) {
			sessionContext.getSession(roomMember.playerId).sendMessage(message);
		}
	}
}
