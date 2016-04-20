package leettalk.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import leettalk.model.Chatroom;
import leettalk.model.ChatroomRepository;
import leettalk.model.Command;
import leettalk.model.CommandRepository;

@RestController
public class TestController {
	
	@Autowired
	private ChatroomRepository chatroomRepository;
	
	@Autowired 
	private CommandRepository commandRepository;
	
	@Autowired
	private ChatController chatController;
	
	@RequestMapping("/create/{roomName}")
	public Chatroom createRoom(@PathVariable String roomName) {
		chatroomRepository.save(new Chatroom(roomName));
		return chatroomRepository.findByName(roomName).get();
	}
	
	@RequestMapping("/{roomName}/create/{commandName}")
	public Collection<Command> createCommand(@PathVariable String roomName, @PathVariable String commandName) {
		Chatroom chatroom = chatroomRepository.findByName(roomName).get();
		commandRepository.save(new Command(chatroom, commandName, commandName, 1));
		Collection<Command> commands = commandRepository.findByChatroomName(roomName);
		return commands;
	}
	
	@RequestMapping("/test")
	public Chatroom test() {
		Chatroom chatroom = chatroomRepository.save(new Chatroom("testroom"));
		for (int i = 0; i < 5; i++) {
			commandRepository.save(new Command(chatroom, ""+i, "source: " + i, i));
		}
		return chatroomRepository.findByName("testroom").get();
	}
	
}
