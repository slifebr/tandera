package com.tandera.core.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(value = "com.tandera.core.dao*")
@EnableJpaRepositories(basePackages = "com.tandera.core.dao.springjpa")
public class PersistenceConfigEstatico {
	private static final String PERSISTENCE_UNIT = "PU_CFIP";
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(createDataSource());
		emf.setJpaVendorAdapter(jpaVendorApapter());
		emf.setJpaProperties(getProperties());
		emf.setPersistenceUnitName(PERSISTENCE_UNIT);
		emf.setPackagesToScan("com.tandera.core.model");
		return emf;
	}

	
	public DataSource createDataSource2() {
		DriverManagerDataSource dataSource = null;
		dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
		dataSource.setUrl("jdbc:hsqldb:file:/porgamador/cfip/implementacao/db/cfipdb");
		dataSource.setUsername("sa");
		dataSource.setPassword("");

		return dataSource;
	}
	@Bean
	public DataSource createDataSource() {
		DriverManagerDataSource dataSource = null;
		dataSource = new DriverManagerDataSource();
		
		/*
		dataSource.setDriverClassName(getValue(Ambiente.DB_DRIVER));
		dataSource.setUrl(getValue(Ambiente.DB_URL));
		dataSource.setUsername(getValue(Ambiente.DB_USER));
		dataSource.setPassword(getValue(Ambiente.DB_PASS));
		*/
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/cfip");
		dataSource.setUsername("postgres");
		dataSource.setPassword("slife");
		
		return dataSource;
	}
	private Properties getProperties() {
		
		Properties properties = new Properties();
		/*
		properties.put(AvailableSettings.HBM2DDL_AUTO, "update");
		properties.put(AvailableSettings.DIALECT, "org.hibernate.dialect.HSQLDialect");
		properties.put(AvailableSettings.SHOW_SQL, "true");
        */
		
		properties.put(AvailableSettings.HBM2DDL_AUTO, "update");
		properties.put(AvailableSettings.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
		properties.put(AvailableSettings.SHOW_SQL, "true");
		properties.put(AvailableSettings.FORMAT_SQL, "true");		
		return properties;
	}
	

	
	@Bean
	public JpaVendorAdapter jpaVendorApapter() {
		return new HibernateJpaVendorAdapter();
	}

	@Bean
	@Autowired
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManager) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManager);
		return transactionManager;
	}

}
