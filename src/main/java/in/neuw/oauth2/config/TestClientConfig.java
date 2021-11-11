package in.neuw.oauth2.config;


import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import in.neuw.oauth2.client.TestClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebSecurity
public class TestClientConfig extends WebSecurityConfigurerAdapter {

    @Value("${test.client.base.url}")
    private String testClientBaseUrl;

    private Logger testWebClientLogger = LoggerFactory.getLogger("TEST_WEB_CLIENT");

    @Bean
    public TestClientService webClient(
        OAuth2AuthorizedClientManager authorizedClientManager) {

        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth =
            new ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        oauth.setDefaultClientRegistrationId("composite");

        WebClient client = WebClient.builder()
            .baseUrl(testClientBaseUrl)
            .filter(logRequest())
            .apply(oauth.oauth2Configuration())
            .build();

        return new TestClientService(client);
    }

    @Bean
    public OAuth2AuthorizedClientManager authorizedClientManager(
        ClientRegistrationRepository clientRegistrationRepository,
        OAuth2AuthorizedClientService clientService) {

        OAuth2AuthorizedClientProvider authorizedClientProvider =
            OAuth2AuthorizedClientProviderBuilder.builder()
                .clientCredentials()
                .build();

        AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager =
            new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                clientRegistrationRepository, clientService);
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

        return authorizedClientManager;
    }


    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(c -> {
            testWebClientLogger.info("Request: {} {}", c.method(), c.url());
            c.headers().forEach((n, v) -> {
                if (!n.equalsIgnoreCase(AUTHORIZATION)) {
                    testWebClientLogger.info("request header {}={}", n, v);
                } else {
                    testWebClientLogger.debug("request header {}={}", n, v);
                }
            });
            return Mono.just(c);
        });
    }

    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(c -> {
            testWebClientLogger.info("Response: {}", c.statusCode());
            // if want to show the response headers in the log by any chance?
            c.headers().asHttpHeaders().forEach((n, v) -> {
                testWebClientLogger.info("response header {}={}", n, v);
            });
            return Mono.just(c);
        });
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
            .disable()
            .authorizeRequests()
            .anyRequest()
            .permitAll();
    }
}

