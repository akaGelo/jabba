package ru.vyukov.jabba.agent;

import javax.jms.ConnectionFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.slf4j.Slf4j;
import ru.vyukov.jabba.agent.cli.RunTypeConfig;
import ru.vyukov.pojocli.CliParser;
import ru.vyukov.pojocli.ParseException;

@EnableScheduling
@Slf4j
@SpringBootApplication
public class JabbaAgentApplication implements CommandLineRunner {

	
	
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
	
	public static void main(String[] args) throws ParseException {
		CliParser cliParser = new CliParser(args);

		RunTypeConfig runTypeConfig = cliParser.parse(RunTypeConfig.class);
		if (runTypeConfig.isDaemon()) {
			log.info("Runing as backup agent daemon");
			SpringApplication.run(JabbaAgentApplication.class, args);
		}
		else {
			log.info("Runing in cli mode");
			// TODO запуск в консольном режиме
		}
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
