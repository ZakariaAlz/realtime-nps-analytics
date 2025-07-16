package com.internship.project.data.common;

public class Question {
  public String id;
  public String name;
  public String questionCode;
  public String type;
  public int detractorScaleMinIncluded = 0;
  public int detractorScaleMaxIncluded = 6;
  public int detractorScaleStep = 1;
  public int passiveScaleMinIncluded = 7;
  public int passiveScaleMaxIncluded = 8;
  public int passiveScaleStep = 1;
  public int promoterScaleMinIncluded = 9;
  public int promoterScaleMaxIncluded = 10;
  public int promoterScaleStep = 1;

  public Question(String id, String name, String questionCode, String type) {
    this.id = id;
    this.name = name;
    this.questionCode = questionCode;
    this.type = type;
  }
}
