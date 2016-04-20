package leettalk.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import leettalk.model.ChatMessage;
import leettalk.model.Chatroom;
import leettalk.model.ChatroomRepository;
import leettalk.model.Command;
import leettalk.model.CommandRepository;
import leettalk.util.CommandProcessor;

@Controller
public class ChatController {
	
	Logger log = LoggerFactory.getLogger(ChatController.class);
	
	@Autowired
	private ChatroomRepository chatroomRepository;
	
	@Autowired
	private CommandRepository commandRepository;
	
	@Autowired
	private CommandProcessor commandProcessor;
	
	@MessageMapping("/{chatroomName}/chat.message")
	public ChatMessage message(@Payload ChatMessage message, @DestinationVariable String chatroomName) {
		Chatroom chatroom = chatroomRepository.findByName(chatroomName).get(); 
		if (commandProcessor.isCommand(message.getMessage())) {
			log.info("Processing " + message.getMessage() + " as a command");
			String commandReturn = commandProcessor.process(message.getMessage(), chatroom);
			return new ChatMessage(commandReturn);
		}
		
		log.info("Message received: " + message.getMessage());
		return message;
	}
	
	@SubscribeMapping("/{chatroomName}/chat.commands")
	public Collection<Command> commands(@DestinationVariable String chatroomName) {
		log.info("Commands requested");
		return commandRepository.findByChatroomName(chatroomName);
	}
	
	
}
