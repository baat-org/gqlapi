package org.baat.gqlapi.error;

import graphql.ErrorType;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;

public class GraphQLErrorAdaptor implements GraphQLError {

    private GraphQLError error;

    public GraphQLErrorAdaptor(GraphQLError error) {
        this.error = error;
    }

    @Override
    public Map<String, Object> getExtensions() {
        return error.getExtensions();
    }

    @Override
    public List<SourceLocation> getLocations() {
        return error.getLocations();
    }

    @Override
    public ErrorType getErrorType() {
        return error.getErrorType();
    }

    @Override
    public List<Object> getPath() {
        return error.getPath();
    }

    @Override
    public Map<String, Object> toSpecification() {
        return error.toSpecification();
    }

    @Override
    public String getMessage() {
        if (error instanceof ExceptionWhileDataFetching) {
            if (((ExceptionWhileDataFetching) error).getException() instanceof HttpClientErrorException) {
                return ((HttpClientErrorException) ((ExceptionWhileDataFetching) error).getException()).getResponseBodyAsString();
            } else {
                return ((ExceptionWhileDataFetching) error).getException().getMessage();
            }
        } else {
            return error.getMessage();
        }
    }
}