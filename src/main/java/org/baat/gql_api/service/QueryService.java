package org.baat.gql_api.service;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.google.common.collect.Lists;
import org.baat.gql_api.transfer.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryService implements GraphQLQueryResolver {

    public List<User> getUsers() {
        return Lists.newArrayList(new User(1L, "ha", "hu", "what"));
    }

    public User getUserForToken(final String userToken) {
        return new User(2L, "1ha", "huasdf", "asdf");
    }

    public List<String> getUserTokens(final String userId) {
        return Lists.newArrayList("1", "2");
    }

}
