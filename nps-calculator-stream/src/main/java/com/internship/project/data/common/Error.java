package com.internship.project.data.common;

public class Error {
  public String code;
  public String message;
  public String errorType;

  public Error(String code, String message, String errorType) {
    this.code = code;
    this.message = message;
    this.errorType = errorType;
  }
}
