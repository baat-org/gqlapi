package org.baat.gqlapi.service;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.apache.commons.lang3.BooleanUtils;
import org.baat.gqlapi.transfer.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Service
public class QueryService implements GraphQLQueryResolver {

    @Value("${user_service_uri}")
    private String userServiceURI;

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

    private void validateUserToken(final String userToken) {
        if (!BooleanUtils.isTrue(new RestTemplate().getForObject(
                URI.create(userServiceURI + "/validateUserToken/" + userToken), Boolean.class))) {
            throw new IllegalStateException("Unauthorised user access");
        }
    }
}
