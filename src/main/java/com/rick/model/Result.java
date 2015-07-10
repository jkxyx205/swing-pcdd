package com.rick.model;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created by Administrator on 2015/7/5.
 */
public class Result {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getSortId() {
        return sortId;
    }

    public void setSortId(int sortId) {
        this.sortId = sortId;
    }

    public int getSmallOrBig() {
        return smallOrBig;
    }

    public void setSmallOrBig(int smallOrBig) {
        this.smallOrBig = smallOrBig;
    }

    public int getSingleOrDouble() {
        return singleOrDouble;
    }

    public void setSingleOrDouble(int singleOrDouble) {
        this.singleOrDouble = singleOrDouble;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String id;

    private String date;

    private String question;

    private int result;

    private int sortId;

    //

    private int smallOrBig;

    private int singleOrDouble;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
