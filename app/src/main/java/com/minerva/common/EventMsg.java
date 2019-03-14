package com.minerva.common;

public class EventMsg {
    private String msg;
    private String content;
    private Object data;

    public EventMsg(String msg) {
        this.msg = msg;
    }

    public EventMsg(String msg, String content) {
        this.msg = msg;
        this.content = content;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
