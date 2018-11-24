package com.warm.game.sysInit;

import java.util.Map;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.warm.game.network.command.Cmd;
import com.warm.game.network.command.CommandContext;

public class ApplicationInitContext {
	/*
	 * 20170803这个类的编排和速度可以好好的测一下
	 */
	public final static ApplicationContext context = new ClassPathXmlApplicationContext(
			"config/ApplicationContext.xml");

	public static void init() {

		/*
		 * 20170803这里初始化单例的CommandContext中的cmds，以供给Dispatcher去read使用，
		 * 这里NetMessageDispatcher和这里对应的同一个单例的CommandContext
		 */
		// 20170803这里的数据结构有问题，需要压测
		Map<String, Object> cmdsMap = context.getBeansWithAnnotation(Cmd.class);
		CommandContext cmdCtx = ApplicationInitContext.context.getBean(CommandContext.class);
		cmdsMap.forEach((k, v) -> {
			try {
				cmdCtx.findCommand(v, v.getClass());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
