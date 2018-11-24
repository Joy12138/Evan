package com.warm.game.network.session;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

import com.warm.game.network.transMsg.IMessage;

/*
 * 表明这个类受到spring管理，为了实现这个类的单例  Component <---> Autowired
 * */
@Component
public class SessionContext {
	/*
	 * 部分锁机制的hashmap(hashcode()--->segment--->lock())
	 */
	private ConcurrentHashMap<Long, Session> sessions;

	public SessionContext() {
		sessions = new ConcurrentHashMap<Long, Session>();
	}

	/*
	 * 管理Session
	 */
	public Session getSession(long sessionId) {
		return sessions.get(sessionId);
	}

	public Session registSession(long id, Session session) {
		return sessions.put(id, session.bind(id));
	}

	public void closeSession(long id) {
		closeSession(sessions.remove(id));
	}

	private void closeSession(Session session) {
		if (null != session) {
			session.close();
		}
	}

	public int sessionCount() {
		return this.sessions.size();
	}

	/*
	 * 控制通道推送信息给client Sessions的key为:playerId Sessions的value为:SessionServer对象
	 * 这个方法对接各个需要发送信息给client的模块
	 */
	public void sendMessage(long palyerId, IMessage message) {
		Session session = sessions.get(palyerId);
		if (null != session) {
			session.sendMessage(message);
		}
	}

	/*
	 * 广播的方式例如发送位置等方式
	 */
	public void broadcast(IMessage message) {
//		Iterator<Entry<Long, Session>> iter = sessions.entrySet().iterator();
//		Entry<Long, Session> entry = null;
//		while (iter.hasNext()) {
//			entry = iter.next();
//			entry.getValue().sendMessage(message);
//		}
		for (Session session : new ArrayList<>(sessions.values())) {
    		session.sendMessage(message);
    	}
	}
}
