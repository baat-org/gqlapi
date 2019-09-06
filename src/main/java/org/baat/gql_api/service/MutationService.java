package org.baat.gql_api.service;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Service;

@Service
public class MutationService implements GraphQLMutationResolver {
    public String authenticate(final String userName, final String password) {
        return userName;
    }

    public String signup(final String email, final String name, final String password, final String avatarUrl) {
        return email;
    }

    public Boolean validateUserToken(final String userToken) {
        return true;
    }

    public Boolean chat(final String senderUserToken, final String recipientChannelId, final String recipientUserId, final String textMessag) {
        return true;
    }

}
