package com.github.ivan.kopylove.telegram.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChatMember extends BaseApiType
{
    @JsonProperty("user")
    private User user;

    @JsonProperty("status")
    private String status;

    public User getUser()
    {
        return user;
    }

    public void setUser(final User user)
    {
        this.user = user;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(final String status)
    {
        this.status = status;
    }
}
