const ws = new WebSocket("ws://localhost:8080/notification");

ws.onopen = function() {
    console.log("Successfully connected to the WebSocket server.");
};

ws.onclose = function() {
    console.log("Disconnected from WebSocket server.");
};

ws.onerror = function(error) {
    console.error("WebSocket Error: " + error);
};

export function sendMessageToServer(myMessage) {
    if (ws.readyState === WebSocket.OPEN) ws.send(myMessage);
    else console.log("Can't send, WebSocket is not open.");
}

export default ws;
