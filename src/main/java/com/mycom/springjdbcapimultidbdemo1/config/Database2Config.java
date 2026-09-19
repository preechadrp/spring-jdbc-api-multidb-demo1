package com.mycom.springjdbcapimultidbdemo1.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class Database2Config {
	//======== datasource2 =========//

	@Bean(name = "dataSourceDb2")
	@ConfigurationProperties(prefix = "custom-config.datasource2")
	HikariDataSource dataSourceDb2() {
		return new HikariDataSource();
	}

	@Bean(name = "jdbcTemplateDb2")
	JdbcTemplate jdbcTemplateDb2(@Qualifier("dataSourceDb2") HikariDataSource ds) {
		log.info("Init jdbcTemplateDb2");
		return new JdbcTemplate(ds);
	}
}
