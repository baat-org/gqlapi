package org.baat.gqlapi.service;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.baat.gqlapi.transfer.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Set;

@Service
public class QueryService implements GraphQLQueryResolver {

    @Value("${user_service_uri}")
    private String userServiceURI;

    public List<User> getUsers() {
        return new RestTemplate().exchange(
                URI.create(userServiceURI + "/users/"), HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
                }).getBody();
    }

    public User getUserForToken(final String userToken) {
        return new RestTemplate().getForObject(
                URI.create(userServiceURI + "/userForToken/" + userToken), User.class);
    }

    public Set<String> getUserTokens(final Long userId) {
        return new RestTemplate().exchange(
                URI.create(userServiceURI + "/userTokens/" + userId), HttpMethod.GET, null, new ParameterizedTypeReference<Set<String>>() {
                }).getBody();
    }

}
