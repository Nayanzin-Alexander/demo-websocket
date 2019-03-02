'use strict';

var stompClient = null;

var connectButton;
var disconnectButton;
var sendButton;
var nameInput;
var conversationTable;
var greetingsTBody;


function setConnected(connected) {

    // Toggle connect/disconnect buttons.
    connectButton.prop("disabled", !connected);
    disconnectButton.prop("disabled", !connected);

    // Toggle conversation table visibility.
    connected == true ? conversationTable.show() : conversationTable.hide();

    // Clear greetings TBody element
    greetingsTBody.html("");
}

function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({},
        function (frame) {
            setConnected(true);
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/greetings', function (topicMessage) {
                showGreeting(JSON.parse(topicMessage.body).content);
            });
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
    stompClient.send("/app/hello", {}, JSON.stringify(
        {
            'name' : nameInput.val()
        }
    ));
}

function showGreeting(message) {
    greetingsTBody.append("<tr><td>" + message +"</td></tr>tr>");
}

// Kick start
$(function() {
    $("form").on('submit', function(e) {
        e.preventDefault();
    });

    connectButton = $("#connect");
    disconnectButton = $("#disconnect");
    sendButton = $("#send");
    nameInput = $("#name");
    conversationTable = $("#conversation");
    greetingsTBody = $("#greetings");

    connectButton.click(function() { connect();});
    disconnectButton.click(function () {disconnect();});
    sendButton.click(function () {sendName();});
});