package com.internship.project.survey;


import com.internship.project.data.SurveyInput;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;

public class SurveyInputTimeAssigner extends BoundedOutOfOrdernessTimestampExtractor<SurveyInput> {
  public SurveyInputTimeAssigner(Time maxOutOfOrderness) {
    super(maxOutOfOrderness);
  }

  /**
   * Extracts the timestamp from the given element.
   *
   * @param element The element that the timestamp is extracted from.
   * @return The new timestamp.
   */
  @Override
  public long extractTimestamp(SurveyInput surveyInput) {
    return System.currentTimeMillis();
  }

}
