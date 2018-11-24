package com.warm.game.network.session;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.warm.game.message.IMessage;

/*
 * 表明这个类受到spring管理，为了实现这个类的单例，便于分发器的分发
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

	// 控制通道推送信息给client
	public void sendMessage(long sessionId, IMessage message) {
		Session session = sessions.get(sessionId);
		if (null != session) {
			session.sendMessage(message);
		}
	}

	public void sendMessage(IMessage message) {
		for (Session session : new ArrayList<Session>(sessions.values())) {
			session.sendMessage(message);
		}
	}
}
