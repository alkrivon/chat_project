'use strict'

var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#text');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');
var roomNumber = document.location.pathname.substring(document.location.pathname.lastIndexOf("message") + 8);

var stompClient = null;
var username = null;

function onConnected() {
    stompClient.subscribe('/topic/public', onMessageReceived);
    connectingElement.classList.add('hidden');
}

function onError(error) {
    connectingElement.textContent = 'Could not coonect to WebSocket server. Please refresh this page to try again!'
}

function send(event) {
    if (!stompClient) {
        username = document.querySelector('#name').textContent.trim();

        if (username) {
            var socket = new SockJS('/chat-websocket');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, onConnected(), onError());
        }
        event.preventDefault();
    } else {
        var messageContent = messageInput.value.trim();

        if (messageContent && stompClient) {
            var chatMessage = {
                author: username,
                content: messageInput.value,
                room: roomNumber
            };

            stompClient.send("app/chat.send", {}, JSON.stringify(chatMessage));
            messageInput.value = '';
        }
        event.preventDefault();
    }
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    if ((message.room === roomNumber && message.room !== 'bot') || (message.author === username && message.room === roomNumber)) {
        var messageElement = document.createElement('li');
        messageElement.classList.add('chat-message');

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.author);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);

        var textElement = document.createElement('p');
        textElement.innerHTML = message.content;

        messageElement.appendChild(textElement);

        messageArea.appendChild(messageElement);
    }
}

messageForm.addEventListener('submit', send, true);
document.querySelector('#send').click();
