package ir.darkdeveloper.moviecatalogservice;

import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.ThreadPoolBulkheadConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4jBulkheadConfigurationBuilder;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4jBulkheadProvider;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@SpringBootApplication
@Configuration
@EnableEurekaClient
public class MovieCatalogServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieCatalogServiceApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public WebClient.Builder webClient() {
        return WebClient.builder();
    }

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> globalCustomConfiguration() {

        var circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .minimumNumberOfCalls(2)
                .waitDurationInOpenState(Duration.ofMillis(1000))
                .slidingWindowSize(2)
                .build();
        var timeLimiterConfig = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(1))
                .build();
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .timeLimiterConfig(timeLimiterConfig)
                .circuitBreakerConfig(circuitBreakerConfig)
                .build());
    }

    @Bean
    public Customizer<Resilience4jBulkheadProvider> defaultBulkheadCustomizer() {
        return provider -> provider.configureDefault(id -> new Resilience4jBulkheadConfigurationBuilder()
                .bulkheadConfig(BulkheadConfig.custom().maxConcurrentCalls(4).build())
                .threadPoolBulkheadConfig(ThreadPoolBulkheadConfig.custom().coreThreadPoolSize(1).maxThreadPoolSize(1).build())
                .build()
        );
    }

}

