package com.warm.game.network.command;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
/*
 * 表明这个类受到spring管理，为了实现这个类的单例，便于分发器的分发
 * */
@Component
public class CommandContext {
	private Map<Short, Command> cmds;

	public CommandContext() {
		this.cmds = new HashMap<Short, Command>();
	}

	/*
	 * commandContext通过regist／get管理command
	 */
	public void registCmd(short code, Command command) {
		this.cmds.put(code, command);
	}

	public Command getCmd(short code) {
		Command cmd = (Command) this.cmds.get(code);
		return cmd;
	}

	/*
	 * command实例化的生成是通过Cmd注解找到XXXCmd的class生成对应的Command，为了绑定后台的对象与code做转换的一个类
	 */
	public void findCommand(Object obj, Class<?> clazz) throws Exception {
		Cmd ann = clazz.getAnnotation(Cmd.class);
		/*
		 * 判断XXXCmd确实是复写messageHandler的类
		 */
		if ((null != ann) && (!Modifier.isAbstract(clazz.getModifiers()))
				&& (!Modifier.isInterface(clazz.getModifiers()))) {
			registCmd(ann.value(), (Command) obj);
		}
	}
}
