var stompClient = null;
var chatroomName = null;

function setConnected(connected) {
	document.getElementById('connect').disabled = connected;
	document.getElementById('chatroom-name').readOnly = connected;
	document.getElementById('disconnect').disabled = !connected;
	document.getElementById('conversation').style.visibility = connected ? 'visible'
			: 'hidden';
	document.getElementById('response').innerHTML = '';
}

function connect() {
	chatroomName = document.getElementById('chatroom-name').value;
	var socket = new SockJS('/ws');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		setConnected(true);
		console.log('Connected to ' + frame);
		stompClient.subscribe('/topic/' + chatroomName + '/chat.message', function(message) {
			showMessage(JSON.parse(message.body).message);
		});
		stompClient.subscribe('/app/' + chatroomName + '/chat.commands', function(commands) {
			commands = JSON.parse(commands.body);
			for (var i = 0; i < commands.length; i++) {
				addCommand(commands[i]);
			}
		})
		stompClient.subscribe('/topic/' + chatroomName + '/chat.commands.add', function(command) {
			console.log('added command' + JSON.parse(command.body));
			addCommand(JSON.parse(command.body));
		})
		stompClient.subscribe('/topic/' + chatroomName + '/chat.commands.delete', function(command) {
			deleteCommand(JSON.parse(command.body));
		}) 
	});
}

function disconnect() {
	if (stompClient != null) {
		stompClient.disconnect();
	}
	
	setConnected(false);
	console.log("Disconnected from endpoint")
}

function sendMessage() {
	var message = document.getElementById('message').value;
	stompClient.send('/app/' + chatroomName + '/chat.message', {}, JSON.stringify({
		'message' : message
	}));
}

function addCommand(command) {
	var phrase = command.phrase;
	var p = document.createElement('p');
	p.style.wordWrap = 'break-word';
	p.appendChild(document.createTextNode('!' + phrase));
	document.getElementById('commands').appendChild(p);
}

function deleteCommand(command) {
	var phrase = command.phrase;
	var commands = document.getElementById('commands');
	for (var i = 0; i < commands.childNodes.length; i++) {
		console.log(commands.childNodes[i].textContent);
		if (commands.childNodes[i].textContent == '!' + command.phrase) {
			commands.removeChild(commands.childNodes[i]);
		}
	}
}

function showMessage(message) {
	var response = document.getElementById('response');
	var p = document.createElement('p');
	p.style.wordWrap = 'break-word';
	p.appendChild(document.createTextNode(message));
	response.appendChild(p);
}