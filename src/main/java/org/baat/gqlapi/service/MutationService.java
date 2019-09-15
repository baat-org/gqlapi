package org.baat.gqlapi.service;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.baat.core.transfer.chat.ChatMessage;
import org.baat.core.transfer.user.SignupRequest;
import org.baat.core.transfer.user.UserCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.springframework.http.HttpMethod.PUT;

@Service
public class MutationService implements GraphQLMutationResolver {
    @Value("${user_service_uri}")
    private String userServiceURI;

    @Value("${chat_service_uri}")
    private String chatServiceURI;

    public String authenticate(final String userName, final String password) {
        return new RestTemplate().exchange(
                URI.create(userServiceURI + "/authenticate/"), PUT, new HttpEntity<>(new UserCredentials(userName, password)),
                String.class).getBody();
    }

    public String signup(final String email, final String name, final String password, final String avatarUrl) {
        return new RestTemplate().exchange(
                URI.create(userServiceURI + "/signup/"), PUT, new HttpEntity<>(new SignupRequest(email, name, avatarUrl, password)),
                String.class).getBody();
    }

    public Boolean validateUserToken(final String userToken) {
        return new RestTemplate().getForObject(
                URI.create(userServiceURI + "/validateUserToken/" + userToken), Boolean.class);
    }

    public Boolean chat(final String senderUserToken, final Long recipientChannelId, final Long recipientUserId, final String textMessage) {
        new RestTemplate().put(URI.create(chatServiceURI + "/"), new ChatMessage(senderUserToken, recipientChannelId, recipientUserId, textMessage));
        return true;
    }

}
