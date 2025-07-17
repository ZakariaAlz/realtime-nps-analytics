package com.internship.project.data.common;

import java.util.Date;

public class Answer {
  public String id;
  public String text;
  public String timestamp;

  public Answer(String id, String text, String timestamp) {
    this.id = id;
    this.text = text;
    this.timestamp = timestamp;
  }
}