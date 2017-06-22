package com.baranov.translator.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Baranov on 21.06.2017 to Translator.
 */

@Entity
public class Language {

    @Id
    private Long id;

    @Unique
    @NotNull
    private String code;
    private String title;

    public Language() {

    }

    public Language(String code, String title) {
        this.code = code;
        this.title = title;
    }

    @Generated(hash = 1992972198)
    public Language(Long id, @NotNull String code, String title) {
        this.id = id;
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}