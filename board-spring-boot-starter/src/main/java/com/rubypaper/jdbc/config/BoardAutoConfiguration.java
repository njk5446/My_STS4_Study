package com.rubypaper.jdbc.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rubypaper.jdbc.util.JDBCConnectionManager;

@Configuration
public class BoardAutoConfiguration {
	
	@Bean
	@ConditionalOnMissingBean 
	//같은 클래스 타입의 데이터가 존재하는지 찾고 없으면(비어있으면) 해당 빈을 실행하고, 있으면 해당 빈을 실행하지 않는다..
	JDBCConnectionManager getJDBCConnectionManager() {
		JDBCConnectionManager manager = new JDBCConnectionManager();
		manager.setUrl("jdbc:mysql//localhost3306/boot");
		manager.setUsername("scott");
		manager.setPassword("tiger");
		return manager;
	}
}
