package com.baranov.translator.model;

import java.util.List;

public class ResponseFromYandex {

    private int code;
    private List<String> text;
    private String lang;

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setText(List<String> text) {
        this.text = text;
    }

    public List<String> getText() {
        return text;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLang() {
        return lang;
    }

    @Override
    public String toString() {
        return
                "ResponseFromYandex{" +
                        "code = '" + code + '\'' +
                        ",text = '" + text + '\'' +
                        ",lang = '" + lang + '\'' +
                        "}";
    }
}