package com.github.lazyf1sh.telegram.api.domain;

public class SendMessage extends ResponseBase
{
    private Message result;

    public Message getResult()
    {
        return result;
    }

    public void setResult(final Message result)
    {
        this.result = result;
    }
}
