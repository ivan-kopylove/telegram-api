package com.github.lazyf1sh.telegram.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User extends BaseApiType
{
    @JsonProperty("id")
    private long     id;
    @JsonProperty("is_bot")
    private boolean isBot;
    @JsonProperty("first_name")
    private String  firstName;
    @JsonProperty("last_name")
    private String  lastName;
    @JsonProperty("username")
    private String  username;
    @JsonProperty("language_code")
    private String  languageCode;

    public long getId()
    {
        return id;
    }

    public void setId(final long id)
    {
        this.id = id;
    }

    public boolean isBot()
    {
        return isBot;
    }

    public void setBot(final boolean bot)
    {
        isBot = bot;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(final String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(final String lastName)
    {
        this.lastName = lastName;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(final String username)
    {
        this.username = username;
    }

    public String getLanguageCode()
    {
        return languageCode;
    }

    public void setLanguageCode(final String languageCode)
    {
        this.languageCode = languageCode;
    }
}