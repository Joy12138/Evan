package com.warm.game.business.module.room.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.warm.game.business.core.data.GameMode;
import com.warm.game.network.session.SessionContext;
import com.warm.game.player.Player;
import com.warm.util.collection.IntHashMap;

/**
 * 管理所有房间和等待匹配的管理器
 */
@Component
public class GlobelRoomManager {
	/**
	 * 房间容器
	 */
	protected IntHashMap<RoomContext> roomMap = new IntHashMap<RoomContext>();
	// /**
	// * 将所有房间按照主题分类
	// */
	// protected Map<R, Set<Integer>> subjectMap = new HashMap<R,
	// Set<Integer>>();
	/**
	 * 等待匹配池
	 */
	public ConcurrentHashMap<Integer, Player> waitHashMap = new ConcurrentHashMap<Integer, Player>();
	public static final List<Long> waitList = new LinkedList<Long>();
	/**
	 * 单个房间系统管理器
	 */
	private RoomContext roomContext;
	/**
	 * SessionContext与RoomContext属于同一层，不可以直接调用
	 */
	@Autowired
	private SessionContext sessionContext;

	/**
	 * 系统挑选玩家组成游戏
	 */
	public void SysSelect() {
		// 20170904这里应该开一个定时任务模块，这里是测试用例
		if (waitList.size() < 4)
			return;
		roomContext = new RoomContext(GameMode.PVP, 123, 4, sessionContext);
		for (long playerId : waitList) {
			roomContext.enter(playerId);
		}
		if (null != roomContext) {
			roomContext.startGame();
		} else {
			System.out.println("roomContext为null");
		}
		roomMap.put(123, roomContext);
	}

	/**
	 * 查找某一个房间--->广播的时候需要直到是在哪一个房间通知
	 */
	public RoomContext getRoom(int roomId) {
		return roomMap.get(roomId);
	}
}
