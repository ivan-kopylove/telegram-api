package com.github.ivan.kopylov.telegram.api.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ivan.kopylov.telegram.api.domain.*;
import com.github.ivan_kopylov.telegram.api.domain.*;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static java.net.http.HttpClient.newHttpClient;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Comparator.comparingInt;

public final class TelegramClient
{
    private static final Logger        LOGGER                = LoggerFactory.getLogger(TelegramClient.class);
    private static final ObjectMapper  OBJECT_MAPPER         = new ObjectMapper();
    private static final HttpClient    HTTP_CLIENT           = newHttpClient();
    private static final String        TELEGRAM_API_BASE_URL = "https://api.telegram.org/";
    private static final String        BOT_PREFIX            = "bot";
    private              Instant       clientInstantiated    = Instant.now();
    private              AtomicInteger lastProcessedId       = new AtomicInteger(0);

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

    public GetMe getMe() throws URISyntaxException, IOException, InterruptedException
    {
        final HttpRequest request = HttpRequest.newBuilder()
                                               .uri(URI.create(buildTelegramUrl("getMe")))
                                               .GET()
                                               .build();

        final HttpResponse<String> send = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        return OBJECT_MAPPER.readValue(send.body(), GetMe.class);
    }

    public void answerCallbackQuery(final String updateId, final String buttonText) throws IOException, InterruptedException
    {
        final MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
        params.add("callback_query_id", String.valueOf(updateId));
        params.add("text", buttonText);
        params.add("show_alert", String.valueOf(true));

        final String url = buildTelegramUrl("answerCallbackQuery");

        //        performRequest(url, params);
    }

    public void sendSingleButton(final long chatId, final String messageText, final String buttonText, final String callbackName) throws IOException, InterruptedException
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

        final Map<String, String> params = new HashMap<>();
        params.put("chat_id", String.valueOf(chatId));
        params.put("text", messageText);
        params.put("disable_notification", String.valueOf(true));
        params.put("reply_markup", json);


        final String url = buildTelegramUrl("sendMessage");
        doPost(url, params);
    }

    public void sendReplyButton(final long chatId, final String buttonText) throws IOException, InterruptedException
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

        final Map<String, String> params = new HashMap<>();
        params.put("chat_id", String.valueOf(chatId));
        params.put("text", "hardcoded placeholder2");
        params.put("disable_notification", String.valueOf(true));
        params.put("reply_markup", json);

        final String url = buildTelegramUrl("sendMessage");

        doPost(url, params);
    }

    public GetUpdate getUpdate() throws IOException, InterruptedException
    {
        String url = buildTelegramUrl("getUpdates");
        url += "?offset=" + lastProcessedId.get();

        final HttpRequest request = HttpRequest.newBuilder()
                                               .uri(URI.create(url))
                                               .GET()
                                               .header("Content-Type", APPLICATION_JSON)
                                               .build();

        final HttpResponse<String> send = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        GetUpdate getUpdate = OBJECT_MAPPER.readValue(send.body(), GetUpdate.class);


        Integer nextUpdateId = getUpdate.getResult()
                                        .stream()
                                        .max(comparingInt(Update::getUpdateId))
                                        .map(update -> update.getUpdateId() + 1)
                                        .orElse(lastProcessedId.get());


        if (lastProcessedId.get() == 0)
        {
            lastProcessedId.compareAndSet(lastProcessedId.get(), nextUpdateId);

            GetUpdate emptyUpdate = new GetUpdate();
            emptyUpdate.setResult(List.of());
            return emptyUpdate;
        }

        lastProcessedId.compareAndSet(lastProcessedId.get(), nextUpdateId);

        return getUpdate;
    }

    public GetChatMember getChatMember(final long chatId, final long userId)
    {
        final String url = buildTelegramUrl("getChatMember");

        //        return WEB_CLIENT.target(url)
        //                         .queryParam("chat_id", String.valueOf(chatId))
        //                         .queryParam("user_id", String.valueOf(userId))
        //                         .request(APPLICATION_JSON)
        //                         .get(GetChatMember.class);

        return null;
    }

    public void sendSingleMessage(final String text, final long chatId) throws IOException, InterruptedException
    {
        final Map<String, String> params = new HashMap<>();
        params.put("chat_id", String.valueOf(chatId));
        params.put("disable_notification", String.valueOf(true));
        params.put("text", text);

        String url = buildTelegramUrl("sendMessage");


        doPost(url, params);
    }

    private void doPost(String url, final Map<String, String> params) throws IOException, InterruptedException
    {
        url += "?";
        url += urlEncode(params);

        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(URI.create(url))
                                         .POST(HttpRequest.BodyPublishers.noBody())
                                         .build();

        HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private static String urlEncode(Map<?, ?> map)
    {
        return map.entrySet()
                  .stream()
                  .map(entry -> entry.getValue() == null ? urlEncode(entry.getKey()) : urlEncode(entry.getKey()) + "=" + urlEncode(
                          entry.getValue()))
                  .collect(Collectors.joining("&"));
    }

    private static String urlEncode(Object obj)
    {
        return URLEncoder.encode(obj.toString(), UTF_8);
    }
}
