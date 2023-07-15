package com.github.ivan.kopylov.telegram.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KeyboardButton extends BaseApiType
{
    @JsonProperty("text")
    private String text;

    public String getText()
    {
        return text;
    }

    public void setText(final String text)
    {
        this.text = text;
    }
}
