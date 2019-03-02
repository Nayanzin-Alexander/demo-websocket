'use strict';

let stompClient = null;
let connectButton;
let disconnectButton;
let sendButton;
let idInput;
let nameInput;
let conversationTable;
let greetingsTBody;
let greetings10TBody;
let greetings20TBody;


function setConnected(connected) {

    // Toggle connect/disconnect buttons.
    connectButton.prop("disabled", connected);
    disconnectButton.prop("disabled", !connected);

    // Toggle conversation table visibility.
    connected === true ? conversationTable.show() : conversationTable.hide();

    // Clear greetings TBody element
    greetingsTBody.html("");
}

function connect() {
    let socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({},
        function (frame) {
            setConnected(true);
            console.log('Connected: ' + frame);

            stompClient.subscribe('/topic/hello/**',
                // Receive message handler
                function (topicMessage) {
                    showGreeting(JSON.parse(topicMessage.body).content);
            });

            stompClient.subscribe('/topic/hello/10',
                function (topicMessage) {showGreeting10(JSON.parse(topicMessage.body).content);});

            stompClient.subscribe('/topic/hello/20',
                function (topicMessage) {showGreeting20(JSON.parse(topicMessage.body).content);});
        });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    let destination = "/app/hello/" + idInput.val();
    stompClient.send(destination, {}, JSON.stringify(
        {
            'name' : nameInput.val()
        }
    ));
}

function showGreeting(message) {
    greetingsTBody.append("<tr><td>" + message +"</td></tr>tr>");
}

function showGreeting10(message) {
    greetings10TBody.append("<tr><td>" + message +"</td></tr>tr>");
}

function showGreeting20(message) {
    greetings20TBody.append("<tr><td>" + message +"</td></tr>tr>");
}

// Kick start
$(function() {
    $("form").on('submit', function(e) {
        e.preventDefault();
    });

    connectButton = $("#connect");
    disconnectButton = $("#disconnect");
    disconnectButton.prop("disabled", true);
    sendButton = $("#send");
    idInput = $("#id");
    nameInput = $("#name");
    conversationTable = $("#conversation");
    greetingsTBody = $("#greetings");
    greetings10TBody = $("#greetings10");
    greetings20TBody = $("#greetings20");

    connectButton.click(function() { connect();});
    disconnectButton.click(function () {disconnect();});
    sendButton.click(function () {sendName();});
});