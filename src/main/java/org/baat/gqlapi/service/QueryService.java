package org.baat.gqlapi.service;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.apache.commons.lang3.BooleanUtils;
import org.baat.gqlapi.transfer.Channel;
import org.baat.gqlapi.transfer.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class QueryService implements GraphQLQueryResolver {

    @Value("${user_service_uri}")
    private String userServiceURI;

    @Value("${channel_service_uri}")
    private String channelServiceURI;

    public List<User> getUsers(final String userToken) {
        validateUserToken(userToken);

        return new RestTemplate().exchange(
                URI.create(userServiceURI + "/users/"), HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
                }).getBody();
    }

    public User getUserForToken(final String userToken) {
        validateUserToken(userToken);

        return new RestTemplate().getForObject(
                URI.create(userServiceURI + "/userForToken/" + userToken), User.class);
    }


    public List<Channel> getChannels(final String userToken) {
        validateUserToken(userToken);

        return new RestTemplate().exchange(
                URI.create(channelServiceURI + "/channels/"), HttpMethod.GET, null, new ParameterizedTypeReference<List<Channel>>() {
                }).getBody();
    }


    public List<Channel> getChannelsForUser(final String userToken) {
        final User user = getUserForToken(userToken);

        return new RestTemplate().exchange(
                URI.create(channelServiceURI + "/users/" + user.getId() + "/channels"), HttpMethod.GET, null, new ParameterizedTypeReference<List<Channel>>() {
                }).getBody();
    }

    public List<User> getDirectsForUser(final String userToken) {
        final User currentUser = getUserForToken(userToken);
        final List<User> allUsers = getUsers(userToken);

        // TODO improve this logic by passing user ids to service
        // TODO even better cache Users.
        final List<Long> directUserIds = new RestTemplate().exchange(
                URI.create(channelServiceURI + "/directs/" + currentUser.getId()), HttpMethod.GET, null, new ParameterizedTypeReference<List<Long>>() {
                }).getBody();

        return allUsers.stream().filter(user -> Objects.requireNonNull(directUserIds).contains(user.getId())).collect(Collectors.toList());
    }


    private void validateUserToken(final String userToken) {
        if (!BooleanUtils.isTrue(new RestTemplate().getForObject(
                URI.create(userServiceURI + "/validateUserToken/" + userToken), Boolean.class))) {
            throw new IllegalStateException("Unauthorised user access");
        }
    }
}
