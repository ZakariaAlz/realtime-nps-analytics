package com.internship.project.data;

import com.internship.project.data.common.*;
import com.internship.project.data.common.Error;

public class SurveyOutput {
  public String surveyId;
  public String surveyRequestId;
  public String surveyCode;
  public String surveySubject;
  public String surveyTimestamp;
  public Question question;
  public Participant participant;
  public Answer answer;
  public Error error;
  public Metrics metrics;

  public SurveyOutput(String surveyId, String surveyRequestId, String surveyCode, String surveySubject, String surveyTimestamp, Question question, Participant participant, Answer answer, Error error, Metrics metrics) {
    this.surveyId = surveyId;
    this.surveyRequestId = surveyRequestId;
    this.surveyCode = surveyCode;
    this.surveySubject = surveySubject;
    this.surveyTimestamp = surveyTimestamp;
    this.question = question;
    this.participant = participant;
    this.answer = answer;
    this.error = error;
    this.metrics = metrics;
  }
}
