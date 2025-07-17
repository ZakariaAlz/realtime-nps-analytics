package com.internship.project.data;

import com.google.gson.Gson;
import com.internship.project.data.common.Answer;
import com.internship.project.data.common.Participant;
import com.internship.project.data.common.Question;

public class SurveyInput {
  public String surveyId;
  public String surveyRequestId;
  public String surveyCode;
  public String surveySubject;
  public String surveyTimestamp;
  public Question question;
  public Participant participant;
  public Answer answer;

  public SurveyInput(String surveyId, String surveyRequestId, String surveyCode, String surveySubject, String surveyTimestamp, Question question, Participant participant, Answer answer) {
    this.surveyId = surveyId;
    this.surveyRequestId = surveyRequestId;
    this.surveyCode = surveyCode;
    this.surveySubject = surveySubject;
    this.surveyTimestamp = surveyTimestamp;
    this.question = question;
    this.participant = participant;
    this.answer = answer;
  }

  @Override
  public String toString() {
    return new Gson().toJson(this);
  }
}
