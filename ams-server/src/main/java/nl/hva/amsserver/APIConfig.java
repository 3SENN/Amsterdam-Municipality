package nl.hva.amsserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Set;

@Configuration
public class APIConfig implements WebMvcConfigurer {

    //JWT configuration that can be adjusted from application.properties
    public static final String IP_FORWARDED_FOR = "X-Forwarded-For";

    public Set<String> SECURED_PATHS =
            Set.of("/test");

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.passphrase}")
    private String passphrase;

    @Value("${jwt.duration-of-validity}")
    private int tokenDurationOfValidity;


    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getPassphrase() {
        return passphrase;
    }

    public void setPassphrase(String passphrase) {
        this.passphrase = passphrase;
    }

    public int getTokenDurationOfValidity() {
        return tokenDurationOfValidity;
    }

    public void setTokenDurationOfValidity(int tokenDurationOfValidity) {
        this.tokenDurationOfValidity = tokenDurationOfValidity;
    }

    public Set<String> getSECURED_PATHS() {
        return SECURED_PATHS;
    }

    public void setSECURED_PATHS(Set<String> SECURED_PATHS) {
        this.SECURED_PATHS = SECURED_PATHS;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:*",
                        "https://ams-fe-app-production.up.railway.app*",
                        getHostIPAddressPattern(), "http://*.hva.nl:*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE, IP_FORWARDED_FOR)
                .exposedHeaders(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE, IP_FORWARDED_FOR)
                .allowCredentials(true);
        //.allowedOrigins("*")
    }

    private String getHostIPAddressPattern() {
        try {
            return "http://" + Inet4Address.getLocalHost().getHostAddress() + ":*";
        } catch (UnknownHostException ignored) {
        }
        return "http://192.168.*.*:*";
    }

}