package com.github.ivan_kopylov.telegram.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReplyKeyboardMarkup extends BaseApiType
{
    @JsonProperty("keyboard")
    private KeyboardButton[][] keyboard;

    public KeyboardButton[][] getKeyboard()
    {
        return keyboard;
    }

    public void setKeyboard(final KeyboardButton[][] keyboard)
    {
        this.keyboard = keyboard;
    }
}
