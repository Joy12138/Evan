package com.warm.game.event;
/**
 * 这个接口是为了方便在系统初始化的时候进行集中填充EventContext上下文的
 * */
public interface ListenerRegistrator {
	public void regist(EventListener listener);
}
