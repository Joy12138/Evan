package com.warm.game.player.offerFx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.warm.game.player.Player;
import com.warm.game.player.PlayerContext;

/*
 * 封装playerContext对外的接口
 * */
@Component
public class DefaultPlayerLoader implements PlayerLoader {

	@Autowired
	private PlayerContext playerContext;

	@Override
	public boolean exists(long playerId) {
		return playerContext.isExist(playerId);
	}

	// 注册加载玩家池
	@Override
	public <T extends Player> T getPlayer(long playerId) {
		return playerContext.getPlayerWithLoad(playerId);
	}

}
