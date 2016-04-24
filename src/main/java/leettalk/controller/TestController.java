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
import leettalk.model.SubmissionResult;
import leettalk.util.SphereEngineApi;

@RestController
public class TestController {

	@Autowired
	private ChatroomRepository chatroomRepository;

	@Autowired
	private CommandRepository commandRepository;

	@Autowired
	private ChatController chatController;

	@Autowired
	private SphereEngineApi SphereEngineApi;

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
			commandRepository.save(new Command(chatroom, "" + i, "source: " + i, i));
		}
		return chatroomRepository.findByName("testroom").get();
	}

	@RequestMapping("/testapi")
	public SubmissionResult testapi() {
		int id = SphereEngineApi.createSubmission("#include <stdio.h>\nint main() {\nprintf(\"Hello World!\");}\n", 11, "");
		SubmissionResult result;
		while (!(result = SphereEngineApi.getSubmissionResult(id)).isDone()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
