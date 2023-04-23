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
public class UserAnsweredHandler extends TextWebSocketHandler {
    Map<Long, WebSocketSession> sessions = new HashMap<>();
    Map<Long, WebSocketSession> sessionsStart = StartQuizSocketHandler.getSessionsStart();

    public UserAnsweredHandler() {
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("[NextQuestionSocketHandler] Session established... " + session);
        long userId = Long.parseLong(Objects.requireNonNull(session.getUri()).getQuery().split("=")[1]);
        sessions.put(userId, session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println(message.getPayload());
        Map value = new Gson().fromJson(message.getPayload(), Map.class);
        Map<Long, Message> messages = QuizSocketHandler.getMessagesForRoom((String) value.get("room"));

        for (Map.Entry<Long, Message> message1 : messages.entrySet()) {
            Message recievedMessage = message1.getValue();

            sessions.entrySet()
                    .stream()
                    .filter(item -> item.getKey().equals(recievedMessage.getUserId()))
                    .findAny()
                    .ifPresent(connectedSession -> sessionsStart.put(recievedMessage.getUserId(), connectedSession.getValue()));
        }

        for (WebSocketSession webSocketSession : sessionsStart.values()) {
            webSocketSession.sendMessage(new TextMessage(message.getPayload()));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("[NextQuestionSocketHandler] Session closed... " + session);
        long userId = Long.parseLong(Objects.requireNonNull(session.getUri()).getQuery().split("=")[1]);
        sessions.remove(userId, session);
        if (sessionsStart.containsValue(session)) sessionsStart.remove(userId, session);
    }
}
