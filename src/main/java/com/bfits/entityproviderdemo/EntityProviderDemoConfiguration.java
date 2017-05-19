package com.bfits.entityproviderdemo;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.vaadin.spring.annotation.EnableVaadin;

@SpringBootConfiguration
@EnableVaadin
@EnableJpaRepositories
@EnableTransactionManagement
@ComponentScan
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
public class EntityProviderDemoConfiguration {

}
