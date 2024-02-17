package org.apache.flink.playgrounds.ops.clickcount.model;
import org.apache.flink.playgrounds.ops.clickcount.model.SurveyInput;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.kafka.common.serialization.Serializer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

public class SurveyInputSerializer implements Serializer<SurveyInput> {
  private final ObjectMapper objectMapper;

  public SurveyInputSerializer() {
    this.objectMapper = new ObjectMapper()
            .findAndRegisterModules()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
  }

  @Override
  public void configure(Map<String, ?> configs, boolean isKey) {
    // Implement this method if you need to configure your serializer.
  }

  @Override
  public byte[] serialize(String topic, SurveyInput data) {
    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
      objectMapper.writeValue(outputStream, data);
      return outputStream.toByteArray();
    } catch (IOException e) {
      throw new RuntimeException("Failed to serialize SurveyInput object", e);
    }
  }

  @Override
  public void close() {
    // Implement this method if you need to close resources.
  }
}
