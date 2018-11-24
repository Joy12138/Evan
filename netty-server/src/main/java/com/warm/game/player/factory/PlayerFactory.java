package com.warm.game.player.factory;

import java.lang.reflect.Constructor;
import com.warm.game.player.Player;
//20170829此工厂类尚未被使用
public class PlayerFactory {
	public static Constructor<? extends Player> constructor;

	public PlayerFactory(Class<? extends Player> assemble) {
		try {
			constructor = assemble.getConstructor(long.class, String.class, int.class);
		} catch (Throwable e) {
			throw new IllegalArgumentException(e);
		}
	}
}
