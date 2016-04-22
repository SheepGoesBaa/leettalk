package leettalk.util;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import leettalk.model.Chatroom;
import leettalk.model.ChatroomRepository;
import leettalk.model.Command;
import leettalk.model.CommandRepository;

@Component
public class CommandProcessor {
	
	private Logger log = LoggerFactory.getLogger(CommandProcessor.class);
	
	@Autowired
	private CommandRepository commandRepository;
	
	@Autowired
	private ChatroomRepository chatroomRepository;
	
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	@Value("${command-add-dest}")
	private String commandAddDest;
	
	@Value("${command-delete-dest}")
	private String commandDeleteDest;
	
	public CommandProcessor() {
		super();
	}
	
	public String process(String command, Chatroom chatroom) {
		command = command.substring(1); //trims the beginning '!'
		String[] splitCommand = command.split(" "); 
		
		switch (splitCommand[0]) {
			case "addCommand":
				splitCommand = command.split(" ", 4);
				addCommand(chatroom, splitCommand[1], splitCommand[2], splitCommand[3]);
				break;
			case "deleteCommand":
				deleteCommand(splitCommand[1], chatroom);
				break;
			default:
				//TODO
		}
		
		
		return "something test something " + command;
	}
	
	public boolean isCommand(String command) { 
		return command.charAt(0) == '!';
	}
	
	public String addCommand(Chatroom chatroom, String phrase, String language, String sourceCode) {
		Command command = new Command(chatroom, phrase, sourceCode, Integer.valueOf(language));
		commandRepository.save(command);
		
		//send broadcast
		messagingTemplate.convertAndSend(String.format(commandAddDest, chatroom.getName()), command);
		log.info("did it boys");
		log.info(String.format(commandAddDest, chatroom.getName()));
		return "Added command";
	}
	
	public String deleteCommand(String phrase, Chatroom chatroom) {
		Optional<Command> commandOpt = commandRepository.findByPhraseAndChatroom(phrase, chatroom);
		if (commandOpt.isPresent()) {
			commandRepository.delete(commandOpt.get());
			
			//send broadcast
			messagingTemplate.convertAndSend(String.format(commandDeleteDest, chatroom.getName()), commandOpt.get());
			return "Deleted command";
		} else {
			//idk
			return "Command not found";
		}
	}
}
