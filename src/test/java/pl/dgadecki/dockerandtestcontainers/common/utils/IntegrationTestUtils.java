package pl.dgadecki.dockerandtestcontainers.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IntegrationTestUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * Method that can be used to serialize any Java Object as JSON output.
     *
     * @param object the object for which the JSON is to be generated
     * @return generated JSON for given object
     */
    public static String asJsonString(final Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (Exception exception) {
            throw new IllegalArgumentException("Unexpected exception occurred while trying to convert the object to the JSON-String");
        }
    }

    /**
     * Method to deserialize response from {@link MvcResult} with JSON content to object of given class.
     *
     * @param mvcResult {@link MvcResult} which contains response object with JSON content
     * @param clazz class to which deserialization should occur
     * @return deserialized object of given class or null when JSON content in response was blank
     */
    public static <T> T fromMvcResult(final MvcResult mvcResult, Class<T> clazz) {
        try {
            String json = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
            return fromJsonString(json, clazz);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Unexpected exception occurred while trying to convert the response with JSON content to the object");
        }
    }

    /**
     * Method to deserialize JSON content from given JSON content String.
     *
     * @param json JSON which should be deserialized
     * @param clazz class to which deserialization should occur
     * @return deserialized object of given class or null when given JSON was blank
     */
    public static <T> T fromJsonString(final String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.findAndRegisterModules().readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Unexpected exception occurred while trying to convert the JSON-String to the Object");
        }
    }

    /**
     * Method to deserialize object content from given array of bytes.
     *
     * @param byteArray array of bytes which should be deserialized
     * @param clazz class to which deserialization should occur
     * @return deserialized object of given class or null
     */
    public static <T> T fromByteArray(final byte[] byteArray, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.findAndRegisterModules().readValue(byteArray, clazz);
        } catch (IOException exception) {
            throw new IllegalArgumentException("Unexpected exception occurred while trying to convert the array of bytes to the Object");
        }
    }
}
