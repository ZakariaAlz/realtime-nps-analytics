package org.apache.flink.datagen.model;

import java.time.LocalDateTime;

public class SurveyOutput {
    private String surveyId;
    private String surveyRequestId;
    private String surveyCode;
    private String surveySubject;
    private LocalDateTime surveyTimeStamp;

    private SurveyInput.Question question; // Reusing Question from our SurveyInput
    private SurveyInput.Participant participant; // Reusing Participant from our SurveyInput
    private SurveyInput.Answer answer; // Reusing Answer from our SurveyInput

    private State state;
    private Error error;
    private Metrics metrics;

    // Getters and setters for our SurveyOutput fields

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public String getSurveyRequestId() {
        return surveyRequestId;
    }
    public void setSurveyRequestId(String surveyRequestId) {
        this.surveyRequestId = surveyRequestId;
    }
    public String getSurveyCode() {
        return surveyCode;
    }

    public void setSurveyCode(String surveyCode) {
        this.surveyCode = surveyCode;
    }

    public String getSurveySubject() {
        return surveySubject;
    }
    public void setSurveySubject(String surveySubject) {
        this.surveySubject = surveySubject;
    }

    public void getSurveyTimeStamp(LocalDateTime surveyTimeStamp) {
        this.surveyTimeStamp = surveyTimeStamp;
    }

    public void setSurveyTimeStamp(LocalDateTime surveyTimeStamp) {
        this.surveyTimeStamp = surveyTimeStamp;
    }

    // Inner classes for State, Error, and Metrics
    public static class State {
        private Boolean isDetractor;
        private Boolean isPromoter;
        private Boolean isPassive;
        private Boolean isNps;
        private Boolean isValidAnswer;
        private Boolean isOutOfScope;
        private Boolean isUnclear;
        private Boolean isGibberish;
        private Boolean isOffensive;


        // Constructors, getters, and setters for State

        public Boolean getDetractor() {
            return isDetractor;
        }

        public void setDetractor(Boolean detractor) {
            isDetractor = detractor;
        }

        public Boolean getPromoter() {
            return isPromoter;
        }

        public void setPromoter(Boolean promoter) {
            isPromoter = promoter;
        }

        public Boolean getPassive() {
            return isPassive;
        }

        public void setPassive(Boolean passive) {
            isPassive = passive;
        }

        public Boolean getNps() {
            return isNps;
        }

        public void setNps(Boolean nps) {
            isNps = nps;
        }

        public Boolean getValidAnswer() {
            return isValidAnswer;
        }

        public void setValidAnswer(Boolean validAnswer) {
            isValidAnswer = validAnswer;
        }

        public Boolean getOutOfScope() {
            return isOutOfScope;
        }

        @Override
        public String toString() {
            return "State{" +
                    "isDetractor=" + isDetractor +
                    ", isPromoter=" + isPromoter +
                    ", isPassive=" + isPassive +
                    ", isNps=" + isNps +
                    ", isValidAnswer=" + isValidAnswer +
                    ", isOutOfScope=" + isOutOfScope +
                    ", isUnclear=" + isUnclear +
                    ", isGibberish=" + isGibberish +
                    ", isOffensive=" + isOffensive +
                    '}';
        }

        public void setOutOfScope(Boolean outOfScope) {
            isOutOfScope = outOfScope;
        }

        public Boolean getUnclear() {
            return isUnclear;
        }

        public void setUnclear(Boolean unclear) {
            isUnclear = unclear;
        }

        public Boolean getGibberish() {
            return isGibberish;
        }

        public void setGibberish(Boolean gibberish) {
            isGibberish = gibberish;
        }

        public Boolean getOffensive() {
            return isOffensive;
        }

        public void setOffensive(Boolean offensive) {
            isOffensive = offensive;
        }
    }

    public static class Error {
        private String code;
        private String message;

        private String errorType;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getErrorType() {
            return errorType;
        }

        public void setErrorType(String errorType) {
            this.errorType = errorType;
        }
    }

    public static class Metrics {
        private int promotersCount;
        private int detractorsCount;
        private int passivesCount;
        private int validResponsesCount;
        private int invalidResponsesCount;

        // Constructors, getters, and setters for Metrics
        public int getPromotersCount() {
            return promotersCount;
        }

        public void setPromotersCount(int promotersCount) {
            this.promotersCount = promotersCount;
        }

        public int getDetractorsCount() {
            return detractorsCount;
        }

        public void setDetractorsCount(int detractorsCount) {
            this.detractorsCount = detractorsCount;
        }

        public int getPassivesCount() {
            return passivesCount;
        }

        public void setPassivesCount(int passivesCount) {
            this.passivesCount = passivesCount;
        }

        public int getValidResponsesCount() {
            return validResponsesCount;
        }

        public void setValidResponsesCount(int validResponsesCount) {
            this.validResponsesCount = validResponsesCount;
        }

        public int getInvalidResponsesCount() {
            return invalidResponsesCount;
        }

        public void setInvalidResponsesCount(int invalidResponsesCount) {
            this.invalidResponsesCount = invalidResponsesCount;
        }
    }

    // Getters and setters for the State, Error, and Metrics
    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public Metrics getMetrics() {
        return metrics;
    }

    public void setMetrics(Metrics metrics) {
        this.metrics = metrics;
    }
}
