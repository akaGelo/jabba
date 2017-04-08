package ru.vyukov.jaba.place;

import javax.jms.ConnectionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsOperations;
import org.springframework.scheduling.annotation.Scheduled;

import ru.vyukov.jabba.pojo.BackupTask;

import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;


@SpringBootApplication
public class JabbaPlaceApplication {

	@Autowired
	private JmsOperations jmsOperations;

	@Bean
	public JmsListenerContainerFactory<?> topicListenerFactory(
			ConnectionFactory connectionFactory,
			DefaultJmsListenerContainerFactoryConfigurer configurer) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		// the configurer will use PubSubDomain from application.properties if defined or
		// false if not
		// so setting it on the factory level need to be set after this
		configurer.configure(factory, connectionFactory);
		factory.setPubSubDomain(true);
		return factory;
	}

	@Bean // Serialize message content to json using TextMessage
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}

	public static void main(String[] args) {
		SpringApplication.run(JabbaPlaceApplication.class, args);
	}

	@Scheduled(fixedDelay = 5_000)
	public void shedule() {
		BackupTask backupTask = new BackupTask();
		backupTask.setTaskId("test");
		jmsOperations.convertAndSend("test", backupTask);
	}
}
