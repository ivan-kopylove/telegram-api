package com.github.ivan_kopylov.telegram.api.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Util
{
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Logger       LOGGER        = LoggerFactory.getLogger(Util.class);

    public static void dumpObject(final String message, final Object object)
    {
        String objectDump;
        try
        {
            objectDump = OBJECT_MAPPER.writeValueAsString(object);
        }
        catch (final JsonProcessingException e)
        {
            LOGGER.error("JsonProcessingException", e);
            objectDump = e.getMessage();
            e.printStackTrace();
        }

        LOGGER.info(message + " " + objectDump);
    }
}
