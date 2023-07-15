package com.github.ivan.kopylove.telegram.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetMe extends ResponseBase
{
    @JsonProperty("result")
    private User user;

    public User getUser()
    {
        return user;
    }

    public void setUser(final User user)
    {
        this.user = user;
    }
}