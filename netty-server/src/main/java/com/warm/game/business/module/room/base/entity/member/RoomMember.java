package com.warm.game.business.module.room.base.entity.member;

/**
 * player转换成RoomMember对象
 */
public class RoomMember {

	public final long playerId;

	public MemberStatus memberStatus;

	public RoomMember(long playerId, MemberStatus memberStatus) {
		this.playerId = playerId;
		this.memberStatus = memberStatus;
	}

	/**
	 * 玩家的状态变更
	 */
	public void changeStatus(MemberStatus memberStatus) {
		if (null != memberStatus) {
			this.memberStatus = memberStatus;
		}
	}
}
