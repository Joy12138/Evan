package com.warm.game.player;

/*
 * 玩家的实体类和对应的玩家操作
 * */
public class Player {
	protected long playerId;
	protected String name;
	protected int level;

	public Player() {

	}

	public Player(long playerId) {
		this.playerId = playerId;
	}

	public Player(long playerId, String name, int level) {
		this.playerId = playerId;
		this.name = name;
		this.level = level;
	}

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
