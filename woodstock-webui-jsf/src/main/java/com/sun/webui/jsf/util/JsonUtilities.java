/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sun.webui.jsf.util;

import java.io.StringReader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParserFactory;

/**
 *
 * @author rgrecour
 */
public final class JsonUtilities {

    /**
     * Cannot be instanciated.
     */
    private JsonUtilities() {
    }

    /**
     * JSON builder factory.
     */
    public static final JsonBuilderFactory JSON_BUILDER_FACTORY
            = Json.createBuilderFactory(null);

    /**
     * JSON generator factory.
     */
    public static final JsonWriterFactory JSON_WRITER_FACTORY
            = createJsonWriterFactory();

    /**
     * JSON parser factory.
     */
    public static final JsonParserFactory JSON_PARSER_FACTORY =
            Json.createParserFactory(null);

    /**
     * Write a given JSON object.
     * @param json the object to write
     * @param writer the writer to use
     */
    public static void writeJsonObject(JsonObject json, Writer writer){
        JsonWriter jsonWriter = JSON_WRITER_FACTORY.createWriter(writer);
        jsonWriter.writeObject(json);
    }

    /**
     * Parse the given string as a JSON object.
     * @param input the input string to parse
     * @return JsonObject
     */
    public static JsonObject parseJsonObject(String input){
        return JSON_PARSER_FACTORY.createParser(new StringReader(input))
                .getObject();
    }

    /**
     * Introspect the type fo the given value object and create a
     * {@code JsonValue}.
     * @param value value to convert
     * @return JsonValue
     */
    public static JsonValue jsonValueOf(Object value){
        if (value == null) {
            return JsonValue.NULL;
        }
        JsonValue jsonValue;
        if (value instanceof String) {
            jsonValue = Json.createValue((String) value);
        } else if (value instanceof Integer) {
            jsonValue = Json.createValue((Integer) value);
        } else if (value instanceof Double) {
            jsonValue = Json.createValue((Double) value);
        } else if (value instanceof Long) {
            jsonValue = Json.createValue((Long) value);
        } else {
            throw new IllegalStateException("Unsupported type: "
                    + value.getClass());
        }
        return jsonValue;
    }

    /**
     * Create a JSON writer factory with pretty printing enabled.
     *
     * @return JsonWriterFactory
     */
    private static JsonWriterFactory createJsonWriterFactory() {
        Map<String, Object> config = new HashMap<String, Object>();
        config.put(JsonGenerator.PRETTY_PRINTING, true);
        return Json.createWriterFactory(config);
    }
}
