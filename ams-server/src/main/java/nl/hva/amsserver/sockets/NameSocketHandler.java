package nl.hva.amsserver.sockets;

import com.google.gson.Gson;
import nl.hva.amsserver.AmsServerApplication;
import nl.hva.amsserver.sockets.quiz.QuizSocketHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class NameSocketHandler extends TextWebSocketHandler {

    final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException {

        for (WebSocketSession webSocketSession : sessions) {
            try {
                Map value = new Gson().fromJson(message.getPayload(), Map.class);
                webSocketSession.sendMessage(new TextMessage("Hello " + value.get("name") + " !"));
            } catch (Exception error){
                // solves the problem that connection closes when a certain client refreshes the page or closes the page
                synchronized( this.sessions ) {
                    sessions.remove( webSocketSession );
                }
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println(session);
        //the messages will be broadcasted to all users.
        sessions.add(session);
    }
}
