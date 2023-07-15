package com.github.ivan.kopylove.telegram.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetChatMember
{
    @JsonProperty("ok")
    private boolean    ok;
    @JsonProperty("result")
    private ChatMember result;

    public boolean isOk()
    {
        return ok;
    }

    public ChatMember getResult()
    {
        return result;
    }
}
