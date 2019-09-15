package org.baat.gql_api.service;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.google.common.collect.Lists;
import org.baat.gql_api.transfer.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Set;

@Service
public class QueryService implements GraphQLQueryResolver {

    @Value("${user_service_uri}")
    private String userServiceURI;

    @SuppressWarnings("unchecked")
    public List<User> getUsers() {
        return new RestTemplate().getForObject(
                URI.create(userServiceURI + "/users/"), List.class);
    }

    public User getUserForToken(final String userToken) {
        return new RestTemplate().getForObject(
                URI.create(userServiceURI + "/userForToken/" + userToken), User.class);
    }

    @SuppressWarnings("unchecked")
    public Set<String> getUserTokens(final Long userId) {
        return new RestTemplate().getForObject(
                URI.create(userServiceURI + "/userTokens/" + userId), Set.class);
    }

}
