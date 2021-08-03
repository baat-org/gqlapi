package org.baat.gqlapi.service;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.apache.commons.lang3.BooleanUtils;
import org.baat.core.transfer.channel.Channel;
import org.baat.core.transfer.channel.Direct;
import org.baat.core.transfer.chat.ChatMessage;
import org.baat.core.transfer.user.SignupRequest;
import org.baat.core.transfer.user.UserCredentials;
import org.baat.core.transfer.user.UserInfo;
import org.baat.gqlapi.transfer.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.HttpMethod.*;

@Service
public class MutationService implements GraphQLMutationResolver {
    @Value("${user_service_uri}")
    private String userServiceURI;

    @Value("${chat_service_uri}")
    private String chatServiceURI;

    @Value("${channel_service_uri}")
    private String channelServiceURI;

    public String authenticate(final String userName, final String password) {
        return new RestTemplate().exchange(
                URI.create(userServiceURI + "/authenticate/"), PUT, new HttpEntity<>(new UserCredentials(userName, password)),
                String.class).getBody();
    }

    public Boolean authorize(final String userToken) {
        return new RestTemplate().getForObject(
                URI.create(userServiceURI + "/validateUserToken/" + userToken), Boolean.class);
    }

    public String signup(final String email, final String name, final String password, final String avatarUrl) {
        return new RestTemplate().exchange(
                URI.create(userServiceURI + "/signup/"), PUT, new HttpEntity<>(new SignupRequest(email, name, avatarUrl, password)),
                String.class).getBody();
    }

    public Boolean chat(final String senderUserToken, final Long recipientChannelId, final Long recipientUserId, final String textMessage) {
        validateUserToken(senderUserToken);

        final UserInfo userInfo = findUserForToken(senderUserToken);
        if (userInfo != null) {
            new RestTemplate().put(URI.create(chatServiceURI + "/"), new ChatMessage(null, userInfo.getId(), recipientChannelId, recipientUserId, textMessage, null));
            return true;
        }

        return false;
    }

    public Long createChannel(final String userToken, final String name, final String description) {
        validateUserToken(userToken);

        return new RestTemplate().exchange(
                URI.create(channelServiceURI + "/channels"), POST, new HttpEntity<>(new Channel(null, name, description, false)),
                Channel.class).getBody().getId();
    }

    public Boolean updateChannel(final String userToken, final Long channelId, final String name, final String description) {
        validateUserToken(userToken);

        return new RestTemplate().exchange(
                URI.create(channelServiceURI + "/channels"), PUT, new HttpEntity<>(new Channel(channelId, name, description, false)),
                Boolean.class).getBody();
    }

    public Boolean archiveChannel(final String userToken, final Long channelId) {
        validateUserToken(userToken);

        return new RestTemplate().exchange(
                URI.create(channelServiceURI + "/channels/" + channelId + "/archive"), PUT, null,
                Boolean.class).getBody();
    }

    public Boolean addUsersToChannel(final String userToken, final Long channelId, final List<String> emails) {
        validateUserToken(userToken);

        // TODO improve this logic by making a call to user controller passing emails & even cache users
        final List<User> allUsers = getUsers();
        final Set<Long> userIdsToAdd = allUsers.stream().filter(user -> emails.contains(user.getEmail())).map(User::getId).collect(Collectors.toSet());

        return new RestTemplate().exchange(
                URI.create(channelServiceURI + "/channels/" + channelId + "/users"), PUT, new HttpEntity<>(userIdsToAdd),
                Boolean.class).getBody();
    }

    public Boolean removeUserFromChannel(final String userToken, final Long channelId, final String email) {
        validateUserToken(userToken);

        // TODO improve this logic by making a call to user controller passing emails & even cache users
        final List<User> allUsers = getUsers();
        final Optional<User> foundUser = allUsers.stream().filter(user -> email.equals(user.getEmail())).findAny();
        if (foundUser.isPresent()) {
            return new RestTemplate().exchange(
                    URI.create(channelServiceURI + "/channels/" + channelId + "/users/" + foundUser.get().getId()), DELETE, null,
                    Boolean.class).getBody();
        } else {
            throw new IllegalStateException("Invalid User");
        }
    }

    public Boolean createDirect(final String userToken, final Long secondUserId) {
        validateUserToken(userToken);

        final UserInfo userInfo = findUserForToken(userToken);

        //TODO validate secondUserId is a valid user
        return new RestTemplate().exchange(
                URI.create(channelServiceURI + "/directs"), POST, new HttpEntity<>(new Direct(userInfo.getId(), secondUserId)),
                Boolean.class).getBody();
    }

    public Boolean removeDirect(final String userToken, final Long secondUserId) {
        validateUserToken(userToken);

        final UserInfo userInfo = findUserForToken(userToken);

        //TODO validate secondUserId is a valid user
        return new RestTemplate().exchange(
                URI.create(channelServiceURI + "/directs"), DELETE, new HttpEntity<>(new Direct(userInfo.getId(), secondUserId)),
                Boolean.class).getBody();
    }

    // TODO turn this into an aspect?
    private void validateUserToken(final String userToken) {
        if (!BooleanUtils.isTrue(new RestTemplate().getForObject(
                URI.create(userServiceURI + "/validateUserToken/" + userToken), Boolean.class))) {
            throw new IllegalStateException("Unauthorised user access");
        }
    }

    private UserInfo findUserForToken(final String userToken) {
        return new RestTemplate().getForObject(
                URI.create(userServiceURI + "/userForToken/" + userToken), UserInfo.class);
    }

    private List<User> getUsers() {
        return new RestTemplate().exchange(
                URI.create(userServiceURI + "/users/"), HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
                }).getBody();
    }

}
