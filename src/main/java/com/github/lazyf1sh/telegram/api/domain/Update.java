package com.github.lazyf1sh.telegram.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * https://core.telegram.org/bots/api#update
 *
 * @author Ivan Kopylov
 */
public class Update extends BaseApiType
{
    @JsonProperty("update_id")
    private int updateId;

    /**
     * null when callback received.
     */
    @JsonProperty("message")
    private Message message;

    /**
     * Optional. New incoming callback query.
     * <p>
     * null when usual message is received.
     */
    @JsonProperty("callback_query")
    private CallbackQuery callbackQuery;

    public CallbackQuery getCallbackQuery()
    {
        return callbackQuery;
    }

    public void setCallbackQuery(final CallbackQuery callbackQuery)
    {
        this.callbackQuery = callbackQuery;
    }

    public int getUpdateId()
    {
        return updateId;
    }

    public void setUpdateId(final int updateId)
    {
        this.updateId = updateId;
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
