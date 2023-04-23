package nl.hva.amsserver;

import nl.hva.amsserver.sockets.NameSocketHandler;
import nl.hva.amsserver.sockets.quiz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.net.Inet4Address;
import java.net.UnknownHostException;

@Configuration
@EnableWebSocket
public class WebSocketServerConfiguration implements WebSocketConfigurer {
    @Autowired
    protected NameSocketHandler nameSocketHandler;
    @Autowired
    protected QuizSocketHandler quizSocketHandler;
    @Autowired
    protected StartQuizSocketHandler startQuizSocket;
    @Autowired
    protected EndSocketHandler endSocketHandler;
    @Autowired
    protected NextQuestionSocketHandler nextQuestionSocketHandler;
    @Autowired
    protected UserAnsweredHandler userAnswered;

    public WebSocketServerConfiguration() {
        this.nameSocketHandler = new NameSocketHandler();
        this.quizSocketHandler = new QuizSocketHandler();
        this.startQuizSocket = new StartQuizSocketHandler();
        this.nextQuestionSocketHandler = new NextQuestionSocketHandler();
        this.endSocketHandler = new EndSocketHandler();
        this.userAnswered = new UserAnsweredHandler();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(this.nameSocketHandler, "/name")
                .addHandler(this.quizSocketHandler, "/lobby")
                .addHandler(this.startQuizSocket, "/start")
                .addHandler(this.nextQuestionSocketHandler, "/next-question")
                .addHandler(this.endSocketHandler, "/end")
                .addHandler(this.userAnswered, "/send-answer")
                .setAllowedOriginPatterns(
                        "http://localhost:*",
                        "https://ams-fe-app-production.up.railway.app*",
                        getHostIPAddressPattern(),
                        "http://*.hva.nl:*"
                );
    }

    private String getHostIPAddressPattern() {
        try {
            return "http://" + Inet4Address.getLocalHost().getHostAddress() + ":*";
        } catch (UnknownHostException ignored) {
        }
        return "http://192.168.*.*:*";
    }
}
