package com.lomi.utils;

import cn.hutool.core.lang.generator.SnowflakeGenerator;

public class IdUtil {

	public static Long getPrimaryKey() {
		SnowflakeGenerator sg = new SnowflakeGenerator();
		return sg.next();
	}

}
