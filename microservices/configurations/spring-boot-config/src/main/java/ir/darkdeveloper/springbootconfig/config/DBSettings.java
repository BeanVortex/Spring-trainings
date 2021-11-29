package ir.darkdeveloper.springbootconfig.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties("db")
@Component
public class DBSettings {
    String connection;
    String host;
    Integer port;
}
