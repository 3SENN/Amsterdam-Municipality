package nl.hva.amsserver.sockets.quiz;

import com.google.gson.Gson;
import nl.hva.amsserver.models.dto.Message;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class StartQuizSocketHandler extends TextWebSocketHandler {
    final static Map<Long, WebSocketSession> sessions = new HashMap<>();
    private static Map<Long, WebSocketSession> sessionsStart = new HashMap<>();

    public StartQuizSocketHandler() {

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("[StartQuizSocketHandler] Session established... " + session);
        long userId = Long.parseLong(Objects.requireNonNull(session.getUri()).getQuery().split("=")[1]);
        sessions.put(userId, session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {
        Map value = new Gson().fromJson(message.getPayload(), Map.class);
        // [Message{userId=10, room='8', session='3d8bf94b-774d-b73b-2c97-49dddccf63ee'}]

        // The room messages
        Map<Long, Message> messages = QuizSocketHandler.getMessagesForRoom((String) value.get("room"));
        // search session value by session id
        for (Map.Entry<Long, Message> message1 : messages.entrySet()) {
            Message recievedMessage = message1.getValue();
            // Loop through all the messages, retrieve user id and create a map of connected users with their session
            sessions.entrySet()
                    .stream()
                    .filter(item -> item.getKey().equals(recievedMessage.getUserId()))
                    .findAny().ifPresent(connectedSession -> sessionsStart.put(recievedMessage.getUserId(), connectedSession.getValue()));
        }

        for (WebSocketSession webSocketSession : sessionsStart.values()) {
            webSocketSession.sendMessage(new TextMessage(message.getPayload()));
        }


        // [Message{userId=10, room='8', session='defadb74-3125-faeb-6ad9-fb7f13e45df4'}, Message{userId=1, room='8', session='a1d5a8f0-4e24-ae4a-1b05-a82338ef0c16'}]
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("[StartQuizSocketHandler] Session closed... " + session);
        long userId = Long.parseLong(Objects.requireNonNull(session.getUri()).getQuery().split("=")[1]);
        sessions.remove(userId, session);
        if (sessionsStart.containsValue(session)) sessionsStart.remove(userId, session);
    }

    public static Map<Long, WebSocketSession> getSessionsStart() {
        return sessionsStart;
    }
}
