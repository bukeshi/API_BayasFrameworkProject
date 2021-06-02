package com.devxschool.framework.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gherkin.lexer.Ca;

import java.util.Arrays;
import java.util.List;

public class ObjectConverter {
    private static ObjectMapper objectMapper = new ObjectMapper();


    /***
     * Converting json object string into the java object
     *
     * @param jsonObject
     * @param clazz java class that we want to desirazlize it into
     * @param <T> Generic Type
     * @return Java Object
     * @throws JsonProcessingException
     *
     *  to create this kind of  Java doc comment type /*** and hit Enter
     */
    public static <T> T convertJsonObjectToJavaObject(String jsonObject, Class clazz) throws JsonProcessingException {
        return (T) objectMapper.readValue(jsonObject,clazz);
    }

    /***
     * Converting json array into list of Java objects
     *
     * @param jsonArray
     * @param clazz java class that we want to desirializa it into
     * @param <T>
     * @return arraylist of java objects
     * @throws JsonProcessingException
     */
    public static <T> List<T> convertJsonArrayToListOfObjects(String jsonArray, Class<T[]> clazz) throws JsonProcessingException {
        return Arrays.asList(objectMapper.readValue(jsonArray,clazz));
    }
}
