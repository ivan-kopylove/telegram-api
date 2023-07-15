package com.github.ivan.kopylov.telegram.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InlineKeyboardMarkup extends BaseApiType
{
    @JsonProperty("inline_keyboard")
    private InlineKeyboardButton[][] inlineKeyboard;

    public InlineKeyboardButton[][] getInlineKeyboard()
    {
        return inlineKeyboard;
    }

    public void setInlineKeyboard(final InlineKeyboardButton[][] inlineKeyboard)
    {
        this.inlineKeyboard = inlineKeyboard;
    }
}
