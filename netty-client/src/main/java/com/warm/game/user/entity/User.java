package com.warm.game.user.entity;

public class User {
	protected long playerId;
	protected String name;
	protected int level;

	public User() {

	}

	public User(long playerId) {
		this.playerId = playerId;
	}

	public User(long playerId, String name, int level) {
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
