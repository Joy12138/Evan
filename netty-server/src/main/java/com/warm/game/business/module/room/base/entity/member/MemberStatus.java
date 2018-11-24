package com.warm.game.business.module.room.base.entity.member;

/**
 * 20170902房间内玩家的状态
 */
public enum MemberStatus {
	/**
	 * 玩家处于主界面状态
	 */
	INIT,
	/**
	 * 玩家等待匹配状态
	 */
	WAIT,
	/**
	 * 玩家匹配完毕状态
	 */
	SUCCESS,
	/**
	 * 玩家进入战斗状态
	 */
	BATTLE;
}
