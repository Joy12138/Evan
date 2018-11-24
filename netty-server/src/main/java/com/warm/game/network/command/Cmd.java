package com.warm.game.network.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/*
 * 20170802生成的一个注解，解决分发器分发的controller的注册和分发问题
 * */
@Target(ElementType.TYPE)
// 运行时动态获取注解信息(编译时注解？)
@Retention(RetentionPolicy.RUNTIME)
//这里这个注解必须加，目的是为了使spring标记管理这个注解
@Component
public @interface Cmd {
	short value();
}
