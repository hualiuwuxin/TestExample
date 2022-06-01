package com.lomi.serialize;

import java.math.BigInteger;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * 全局Long 输出成 String
 * @author ZHANGYUKUN
 * @Date2022年5月31日
 *
 */
@Configuration
public class Jackson2ObjectMapper {
	
	@Bean("jackson2ObjectMapperBuilderCustomizer")
	public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
	    Jackson2ObjectMapperBuilderCustomizer customizer = new Jackson2ObjectMapperBuilderCustomizer() {
	        @Override
	        public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
	            jacksonObjectMapperBuilder
	            .serializerByType(Long.class, ToStringSerializer.instance)
	            .serializerByType(Long.TYPE, ToStringSerializer.instance)
	            .serializerByType(BigInteger.class, ToStringSerializer.instance);
	        }
	    };
	    return customizer;
	}
	
}
