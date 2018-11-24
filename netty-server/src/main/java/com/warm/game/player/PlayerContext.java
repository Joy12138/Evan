package com.warm.game.player;

import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.warm.game.loadable.Loadable;
import com.warm.game.player.dao.PlayerDao;

/*
 * 拥有管理所有玩家的线程池
 * */
@SuppressWarnings("unchecked")
@Component
public class PlayerContext implements Loadable {

	@Resource
	private PlayerDao playerDao;

	private final PlayerCollection players;

	public PlayerContext() {
		this.players = new PlayerCollection();
	}

	@Override
	public void load() {
		playerDao.fetchPlayerInfos().forEach(Player -> {
			players.put(Player.getPlayerId(), Player);
		});
	}

	// 获取当前登录玩家信息
	public <T extends Player> T getPlayer(long playerId) {
		if (playerId < 1)
			return null;
		return (T) players.get(playerId);
	}

	public boolean isExist(long playerId) {
		return players.isExist(playerId);
	}

	// 创建角色---加入玩家池缓存
	public <T extends Player> T getPlayerWithLoad(long playerId) {
		if (playerId < 1)
			return null;
		Player tmp = players.get(playerId);
		if (null != tmp)
			return (T) tmp;

		// 创建实例并且返回
		// 20170828这里需要修改成工厂模式（待定）
		Player player = new Player(playerId, "玩家", 0);
		players.put(playerId, player);
		return (T) player;
	}

	private static class PlayerCollection {
		ConcurrentHashMap<Long, PlayerData> context = new ConcurrentHashMap<>();

		public Player get(long playerId) {
			PlayerData playData = (PlayerData) context.get(playerId);
			if (null == playData)
				return null;
			playData.setActiveTime(System.currentTimeMillis());
			return playData.getData();
		}

		public boolean isExist(long playerId) {
			return context.containsKey(playerId);
		}

		public void put(long playerId, Player player) {
			if (!context.containsKey(playerId)) {
				context.put(playerId, new PlayerData(player));
			}
		}

		public void remove(long playerId) {
			context.remove(playerId);
		}

		public void clear() {
			context.clear();
		}

		public int size() {
			return context.size();
		}
	}

	public static class PlayerData {
		private Player data;
		private long createTime;// 创建对像的时间
		private long activeTime;// 最后的活跃时间

		public PlayerData(Player player) {
			this.data = player;
			this.createTime = this.activeTime = System.currentTimeMillis();
		}

		public Player getData() {
			return data;
		}

		public long getCreateTime() {
			return createTime;
		}

		public void setActiveTime(long activeTime) {
			this.activeTime = activeTime;
		}
	}
}
