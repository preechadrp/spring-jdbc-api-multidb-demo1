package com.mycom.springjdbcapimultidbdemo1.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class Database1Config {
	//======== datasource1 =========//

	@Primary
	@Bean(name = "dataSourceDb1")
	@ConfigurationProperties(prefix = "custom-config.datasource1")
	HikariDataSource dataSourceDb1() {
		return new HikariDataSource();
	}

	@Primary
	@Bean(name = "jdbcTemplateDb1")
	JdbcTemplate jdbcTemplateDb1(@Qualifier("dataSourceDb1") HikariDataSource ds) {
		log.info("Init jdbcTemplateDb1");
		return new JdbcTemplate(ds);
	}

	// ======== transaction manager db1 =========
	@Bean(name = "transactionManagerDb1")
	PlatformTransactionManager transactionManagerDb1(
			@Qualifier("dataSourceDb1") HikariDataSource ds) {

		return new DataSourceTransactionManager(ds);
	}
}