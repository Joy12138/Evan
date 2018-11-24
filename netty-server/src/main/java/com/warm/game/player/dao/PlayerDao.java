package com.warm.game.player.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.warm.game.player.Player;


/*
 * 这里repository注解和resource注解是一对哦
 * */
@Repository
public interface PlayerDao {
	public List<Player> fetchPlayerInfos();
}
