package com.pressTheButton.Auth;

import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.client.ClientBuilder;
import com.stormpath.sdk.client.Clients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Tyler on 2016-08-12.
 */
@Configuration
public class StormpathClient {

    private final Client client;

    public StormpathClient() {
        ClientBuilder builder = Clients.builder();
        client = builder.build();
    }

    @Bean
    public StormpathClient stormpathClient(){
        return new StormpathClient();
    }

    public Client getClient(){
        return client;
    }
}
