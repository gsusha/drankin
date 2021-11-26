package ru.sfedu.Test.model;

import java.util.List;

public class Result<T> {
    private List<T> data;
    private ResultState resultState;
    private String message;

    public Result(List<T> data, ResultState resultState, String message) {
        this.resultState = resultState;
        this.message = message;
        this.data = data;
    }

    public void setResultState(ResultState resultState) {
        this.resultState = resultState;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public ResultState getResultState() {
        return resultState;
    }

    public String getMessage() {
        return message;
    }

    public List<T> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "resultState=" + resultState +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}