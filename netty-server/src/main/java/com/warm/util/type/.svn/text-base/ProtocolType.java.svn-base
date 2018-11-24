package com.warm.util.type;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/*
 * 单纯为了定义协议号的区间，避免重复，没有加逻辑判断
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ProtocolType {
	short[] value() default { 0, Short.MAX_VALUE };
}
