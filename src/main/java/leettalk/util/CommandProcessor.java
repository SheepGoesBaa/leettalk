package leettalk.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
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
	
	@Autowired
	private SphereEngineApi sphereEngineApi;
	
	@Value("${command-add-dest}")
	private String commandAddDest;
	
	@Value("${command-delete-dest}")
	private String commandDeleteDest;
	
	private final Collection<Command> builtinCommands = Arrays.asList(new Command[]{new Command("addCommand"),
			new Command("deleteCommand"),
			new Command("languages"),
			new Command("source")
			});
	
	public CommandProcessor() {
		super();
	}
	
	public String process(String command, Chatroom chatroom) {
		command = command.substring(1); //trims the beginning '!'
		String[] splitCommand = command.split(" "); 
		
		switch (splitCommand[0]) {
			case "addCommand":
				splitCommand = command.split(" ", 4);
				return addCommand(chatroom, splitCommand[1], splitCommand[2], splitCommand[3]);
			case "deleteCommand":
				return deleteCommand(splitCommand[1], chatroom);
			case "languages":
				return listLanguages();
			case "source" :
				return source(chatroom, splitCommand[1]);
			default:
				//TODO
		}
		
		
		return "Error processing command";
	}
	
	public boolean isCommand(String command) { 
		return command.charAt(0) == '!';
	}
	
	public String addCommand(Chatroom chatroom, String phrase, String language, String sourceCode) {
		if (commandRepository.findByPhraseAndChatroom(phrase, chatroom).isPresent()) {
			return "Unable to add command (already exists)";
		}
		Command command = null;
		try {
			command = new Command(chatroom, phrase, sourceCode, Integer.valueOf(language));
		} catch (Exception e) {
			return "Failed to add command";
		}
		
		commandRepository.save(command);
		
		//send broadcast
		messagingTemplate.convertAndSend(String.format(commandAddDest, chatroom.getName()), command);
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
			return "Command not found";
		}
	}
	
	public String listLanguages() {
		Map<Integer, String> languages = sphereEngineApi.listLanguages();
		
		StringBuilder sb = new StringBuilder();
		for (Integer i : languages.keySet()) {
			sb.append(i);
			sb.append(" : ");
			sb.append(languages.get(i));
			sb.append('\n');
		}
		
		return sb.toString();
	}
	
	public String source(Chatroom chatroom, String phrase) {
		Optional<Command> commandOpt = commandRepository.findByPhraseAndChatroom(phrase, chatroom);
		if (commandOpt.isPresent()) {
			return "Language: " + commandOpt.get().getLanguage() + "\n\n" + commandOpt.get().getSourceCode();
		}
		
		return "Command not found";
	}
	
	public Collection<Command> getBuiltinCommands() {
		return this.builtinCommands;
	}
}
