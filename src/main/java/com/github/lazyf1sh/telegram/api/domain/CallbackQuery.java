package com.github.lazyf1sh.telegram.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * https://core.telegram.org/bots/api#callbackquery
 */
public class CallbackQuery extends BaseApiType
{
    @JsonProperty("id")
    private String id;

    @JsonProperty("inline_message_id")
    private String inlineMessageId;

    @JsonProperty("chat_instance")
    private String chatInstance;

    /**
     * Optional (nullable).
     * <p>
     * Data associated with the callback button. Be aware that a bad client can send arbitrary data in this field.
     */
    @JsonProperty("data")
    private String data;

    @JsonProperty("game_short_name")
    private String gameShortName;

    @JsonProperty("from")
    private User from;

    @JsonProperty("message")
    private Message message;

    public String getId()
    {
        return id;
    }

    public void setId(final String id)
    {
        this.id = id;
    }

    public String getInlineMessageId()
    {
        return inlineMessageId;
    }

    public void setInlineMessageId(final String inlineMessageId)
    {
        this.inlineMessageId = inlineMessageId;
    }

    public String getChatInstance()
    {
        return chatInstance;
    }

    public void setChatInstance(final String chatInstance)
    {
        this.chatInstance = chatInstance;
    }

    public String getData()
    {
        return data;
    }

    public void setData(final String data)
    {
        this.data = data;
    }

    public String getGameShortName()
    {
        return gameShortName;
    }

    public void setGameShortName(final String gameShortName)
    {
        this.gameShortName = gameShortName;
    }

    public User getFrom()
    {
        return from;
    }

    public void setFrom(final User from)
    {
        this.from = from;
    }

    public Message getMessage()
    {
        return message;
    }

    public void setMessage(final Message message)
    {
        this.message = message;
    }
}
