package com.github.lazyf1sh.telegram.api.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lazyf1sh.telegram.api.domain.*;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.github.lazyf1sh.telegram.api.util.Util.dumpObject;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public final class TelegramClient
{
    private static final Logger                      LOGGER                = LoggerFactory.getLogger(TelegramClient.class);
    private static final ObjectMapper                OBJECT_MAPPER         = new ObjectMapper();
    private static final jakarta.ws.rs.client.Client WEB_CLIENT            = ClientBuilder.newClient();
    private static final String                      TELEGRAM_API_BASE_URL = "https://api.telegram.org/";
    private static final String                      BOT_PREFIX            = "bot";

    private final String apiKey;

    private String buildTelegramUrl(final String method)
    {
        return TELEGRAM_API_BASE_URL + BOT_PREFIX + apiKey + "/" + method;
    }

    private TelegramClient(String apiKey)
    {
        this.apiKey = apiKey;
    }

    public static TelegramClient telegramClient(String apiKey)
    {
        return new TelegramClient(apiKey);
    }

    public GetMe getMe()
    {
        return WEB_CLIENT.target(buildTelegramUrl("getMe"))
                         .request()
                         .get(GetMe.class);
    }

    public void answerCallbackQuery(final String updateId, final String buttonText)
    {
        final MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
        params.add("callback_query_id", String.valueOf(updateId));
        params.add("text", buttonText);
        params.add("show_alert", String.valueOf(true));

        final String url = buildTelegramUrl("answerCallbackQuery");

        performRequest(url, params);
    }

    public void sendSingleButton(final long chatId, final String messageText, final String buttonText, final String callbackName)
    {
        final InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(buttonText);
        inlineKeyboardButton.setCallbackData(callbackName);

        final InlineKeyboardButton[][] keyboard = {{inlineKeyboardButton}};
        final InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setInlineKeyboard(keyboard);

        String json = null;
        try
        {
            json = OBJECT_MAPPER.writeValueAsString(keyboardMarkup);
        }
        catch (final JsonProcessingException e)
        {
            LOGGER.error("JsonProcessingException", e);
        }

        final MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
        params.add("chat_id", String.valueOf(chatId));
        params.add("text", messageText);
        params.add("disable_notification", String.valueOf(true));
        params.add("reply_markup", json);


        final String url = buildTelegramUrl("sendMessage");

        performRequest(url, params);
    }

    public void sendReplyButton(final long chatId, final String buttonText)
    {
        final KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText("button test");
        final KeyboardButton[][] keyboardButtons = {{keyboardButton}};

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboardButtons);

        String json = null;
        try
        {
            json = OBJECT_MAPPER.writeValueAsString(replyKeyboardMarkup);
        }
        catch (final JsonProcessingException e)
        {
            LOGGER.error("JsonProcessingException", e);
        }

        final MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
        params.add("chat_id", String.valueOf(chatId));
        params.add("text", "hardcoded placeholder2");
        params.add("disable_notification", String.valueOf(true));
        params.add("reply_markup", json);

        final String url = buildTelegramUrl("sendMessage");

        performRequest(url, params);
    }

    public GetUpdate getUpdate(final int startFrom)
    {
        final String url = buildTelegramUrl("getUpdates");

        return WEB_CLIENT.target(url)
                         .queryParam("offset", String.valueOf(startFrom))
                         .request(APPLICATION_JSON)
                         .get(GetUpdate.class);
    }

    public GetChatMember getChatMember(final long chatId, final long userId)
    {
        final String url = buildTelegramUrl("getChatMember");

        return WEB_CLIENT.target(url)
                         .queryParam("chat_id", String.valueOf(chatId))
                         .queryParam("user_id", String.valueOf(userId))
                         .request(APPLICATION_JSON)
                         .get(GetChatMember.class);
    }

    public void sendSingleMessage(final String text, final long chatId)
    {
        final String url = buildTelegramUrl("sendMessage");

        final MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
        params.add("chat_id", String.valueOf(chatId));
        params.add("disable_notification", String.valueOf(true));
        params.add("text", text);

        performRequest(url, params);
    }

    private void performRequest(final String url, final MultivaluedMap<String, String> params)
    {
        final WebTarget webTarget = WEB_CLIENT.target(url);

        final Response response = webTarget.request(APPLICATION_JSON)
                                           .post(Entity.form(params));

        if (response.getStatus() != 200)
        {
            dumpObject("not 200", response.getStatusInfo());
        }
    }
}
