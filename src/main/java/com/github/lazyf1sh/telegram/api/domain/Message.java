package com.github.lazyf1sh.telegram.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Message extends BaseApiType
{
    @JsonProperty("message_id")
    private int message_id;

    @JsonProperty("from")
    private User user;

    @JsonProperty("chat")
    private Chat chat;

    @JsonProperty("date")
    private int date;

    @JsonProperty("text")
    private String text;

    @JsonProperty("edit_date")
    private Integer edit_date;

    /**
     * Message from.
     *
     * @return
     */
    public User getUser()
    {
        return user;
    }

    public void setUser(final User user)
    {
        this.user = user;
    }

    public Integer getEdit_date()
    {
        return edit_date;
    }

    public void setEdit_date(final Integer edit_date)
    {
        this.edit_date = edit_date;
    }

    public int getMessage_id()
    {
        return message_id;
    }

    public void setMessage_id(final int message_id)
    {
        this.message_id = message_id;
    }

    public Chat getChat()
    {
        return chat;
    }

    public void setChat(final Chat chat)
    {
        this.chat = chat;
    }

    public int getDate()
    {
        return date;
    }

    public void setDate(final int date)
    {
        this.date = date;
    }

    public String getText()
    {
        return text;
    }

    public void setText(final String text)
    {
        this.text = text;
    }
}
