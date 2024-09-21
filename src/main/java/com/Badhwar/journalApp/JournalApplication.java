package com.Badhwar.journalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication // Annotates the Starting point of the application
@EnableTransactionManagement // Only applies to the Main Class and let us uses the Transaction annotation inside the application.
@EnableScheduling
public class JournalApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(JournalApplication.class, args);
		ConfigurableEnvironment environment = context.getEnvironment();
		System.out.println(environment.getActiveProfiles()[0]);
	}

	//To Handle @Transaction, we need TransactionManager, unlike SQL in NoSQl Dbs we need to manually create a Bean of,
	// TransactionManager.
	@Bean
	public PlatformTransactionManager add(MongoDatabaseFactory dbFactory)
	{
		return new MongoTransactionManager(dbFactory);
	}

	@Bean
	public RestTemplate restTemplate()
	{
		return new RestTemplate();
	}
}

//PlatformTransactionManager
//MongoTransactionManager