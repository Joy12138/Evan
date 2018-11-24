package com.warm.game.event;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.warm.util.collection.IntHashMap;

/**
 * event的context管理类
 */
@Component
public class EventNotifyer implements ListenerRegistrator {
	private IntHashMap<List<EventListener>> listeners;

	public EventNotifyer() {
		listeners = new IntHashMap<List<EventListener>>();
	}

	public void register(EventListener listener) {
		int eventType = listener.eventType();
		List<EventListener> list = listeners.get(eventType);
		if (null == list) {
			list = new LinkedList<EventListener>();
			listeners.put(eventType, list);
		}
		list.add(listener);
	}

	public void notify(Event event) {
		List<EventListener> list = listeners.get(event.eventType());
		if (list.isEmpty()) {
			return;
		}
		for (EventListener eventListener : list) {
			eventListener.onEvent(event);
		}
	}

	@Override
	public void regist(EventListener listener) {
		register(listener);
	}
}
