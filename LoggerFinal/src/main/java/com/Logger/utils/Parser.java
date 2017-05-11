package com.Logger.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Parser
{
   static final Long EMPTY_LONG=0L;
    static final String EMPTY_STRING="";
    static final int EMPTY_INT=0;

    public static Long getLong(String paramName, ObjectNode node) {

        return node.get(paramName) != null ? node.get(paramName).longValue() : EMPTY_LONG;
    }

    public static String getString(String paramName, ObjectNode node) {

        return node.get(paramName) != null ? node.get(paramName).textValue() : EMPTY_STRING;
    }

    public static int getInt(String paramName, ObjectNode node) {

        return node.get(paramName) != null ? node.get(paramName).intValue() : EMPTY_INT;
    }

    public static String getStatus(String paramName, ObjectNode node) {

        return node.get(paramName) != null ? node.get(paramName).textValue() : "N/A";
    }

    public static TypeValue getTypeValue(String reservationId ,String uniqueId, ObjectNode node) {

        return node.get(reservationId) != null && node.get(uniqueId)!=null? TypeValue.RESERVATION : TypeValue.PROPERTY;
    }

    public Date getDate(String paramName, JsonNode jsonNode) {
        return DateUtils.getDate(jsonNode.get(paramName).textValue(), DateUtils.DB_FORMAT_DATETIME);

    }

    public Date getCurrentDate() {
        return new Date();
    }
}
