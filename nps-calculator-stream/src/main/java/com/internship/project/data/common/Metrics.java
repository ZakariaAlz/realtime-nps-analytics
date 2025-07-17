package com.internship.project.data.common;

public class Metrics {
  public int promotersCount;
  public int detractorsCount;
  public int passivesCount;
  public int validResponsesCount;
  public int invalidResponsesCount;

  public Metrics(int promotersCount, int detractorsCount, int passivesCount, int validResponsesCount, int invalidResponsesCount) {
    this.promotersCount = promotersCount;
    this.detractorsCount = detractorsCount;
    this.passivesCount = passivesCount;
    this.validResponsesCount = validResponsesCount;
    this.invalidResponsesCount = invalidResponsesCount;
  }
}
