package ru.vyukov.jabba.agent.receiver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsOperations;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ru.vyukov.jabba.pojo.BackupTask;

@Component
public class TaskReceiver {
	
	
	@Autowired
	private JmsOperations jmsOperations;
	


	@JmsListener(destination = "test")
	public void processMessage(@Payload BackupTask content) {
		System.err.println(content);
	}
}
