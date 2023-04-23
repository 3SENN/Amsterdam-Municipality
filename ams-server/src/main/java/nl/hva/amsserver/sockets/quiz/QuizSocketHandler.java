package nl.hva.amsserver.sockets.quiz;

import com.google.gson.Gson;
import nl.hva.amsserver.models.dto.Message;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

@Component
public class QuizSocketHandler extends TextWebSocketHandler {

    private static final Map<Long, WebSocketSession> sessions = new HashMap<>();
    static Map<Long, Message> messages = new HashMap<>();
    Set<Message> roomMessages = new CopyOnWriteArraySet<>();

    private static Map<String, WebSocketSession> sessionsById = new HashMap<>();

    public QuizSocketHandler() {
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {

        sessionsById.put(session.getId(), session);

        for (Map.Entry<Long, WebSocketSession> longWebSocketSessionEntry : sessions.entrySet()) {
            WebSocketSession webSocketSession = longWebSocketSessionEntry.getValue();
            try {
                if (message.getPayloadLength() > 0) {
                    Map value0 = new Gson().fromJson(message.getPayload(), Map.class);
                    // Instantiating new message object
                    Message incomingMessage = new Message(
                            Integer.parseInt((String) value0.get("id")),
                            (String) value0.get("room"),
                            session.getId()
                    );
                    long userId = Long.parseLong(Objects.requireNonNull(session.getUri()).getQuery().split("=")[1]);

                    // Adding the incoming message to the list if it is empty
                    if (messages.isEmpty()) messages.put(userId, incomingMessage);

                    // Find matching object in set
                    Optional<Message> matchingMessage = messages.values().stream()
                            .filter(m -> m.equals(incomingMessage))
                            .findFirst();

                    // add message to list when there was no matching message found
                    if (matchingMessage.isEmpty()) messages.put(userId, incomingMessage);

                    // If message is present replace by new incoming message to update the sessions id
                    matchingMessage.ifPresent(value -> messages.remove(value));
                    messages.put(userId, incomingMessage);

                    // adds all messages if the room equals to the room of the incoming message
                    roomMessages = messages.values().stream()
                            .filter(m -> m.roomEquals(incomingMessage))
                            .collect(Collectors.toSet());

                    // Sending the messages to all the users in the same room
                    for (Message roomMessage : roomMessages) {
                        if (roomMessage.getSession().equals(webSocketSession.getId())) {
                            webSocketSession.sendMessage(new TextMessage(new Gson().toJson(roomMessages)));
                        }
                    }
                }
            } catch (Exception error) {
                error.printStackTrace();
                // solves the problem that connection closes when a certain client refreshes the page or closes the page
                synchronized (sessions) {
                    sessions.remove(longWebSocketSessionEntry.getKey(), session);
                    this.refreshSessions(webSocketSession);
                }
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("[QuizSocketHandler] Session established... " + Objects.requireNonNull(session.getUri()).getQuery());
        long userId = Long.parseLong(session.getUri().getQuery().split("=")[1]);
        sessions.put(userId, session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("[QuizSocketHandler] Session closed... " + session);
        long userId = Long.parseLong(Objects.requireNonNull(session.getUri()).getQuery().split("=")[1]);
        sessions.remove(userId, session);
        this.refreshSessions(session);
    }

    public void refreshSessions(WebSocketSession session) throws Exception {
        Optional<Map.Entry<String, WebSocketSession>> sessionById = sessionsById
                .entrySet()
                .stream()
                .filter(s -> s.getValue() == session)
                .findFirst();

        System.out.println(roomMessages);
        sessionById.ifPresent(stringWebSocketSessionEntry ->
                roomMessages.removeIf(r -> r.getSession().equals(stringWebSocketSessionEntry.getKey())));

        System.out.println(roomMessages);
        for (WebSocketSession webSocketSession : sessions.values()) {
            for (Message roomMessage : roomMessages) {
                if (roomMessage.getSession().equals(webSocketSession.getId())){
                    System.out.println(webSocketSession);
                    webSocketSession.sendMessage(new TextMessage(new Gson().toJson(roomMessages)));
                }
            }
        }
    }

    public static Map<Long, Message> getMessagesForRoom(String room) {
        return messages.values().stream()
                .filter(m -> m.getRoom().equals(room))
                .collect(Collectors.toMap(Message::getUserId, x -> x));
    }

    public static Map<String, WebSocketSession> getSessionsById() {
        return sessionsById;
    }

    public static Map<Long, WebSocketSession> getSessions() {
        return sessions;
    }
}
