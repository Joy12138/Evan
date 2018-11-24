package com.warm.game.business.module.room.base.entity.room;

/**
 * 房间状态类型
 */
public enum RoomStatus {
	/**
	 * 房间初始化状态
	 */
	CREATED(false),
	/**
	 * 房间进入战斗状态
	 */
	STARTED(true),
	/**
	 * 战斗结束，房间即将进入关闭状态
	 */
	END(true),
	/**
	 * 房间关闭状态
	 */
	CLOSE(true);

	private boolean started;

	public boolean isStarted() {
		return started;
	}
	/**
	 * 判断是否开始战斗，状态变更在别处
	 * @param started
	 */
	private RoomStatus(boolean started) {
		this.started = started;
	}

}
