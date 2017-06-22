package com.baranov.translator.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Baranov on 22.06.2017 to Translator.
 */

@Entity
public class Favorite {

    @Id
    private Long id;

    @Unique
    @NotNull
    private String original;
    private String translate;

    public Favorite(String original, String translate) {
        this.original = original;
        this.translate = translate;
    }

    @Generated(hash = 1766599907)
    public Favorite(Long id, @NotNull String original, String translate) {
        this.id = id;
        this.original = original;
        this.translate = translate;
    }

    @Generated(hash = 459811785)
    public Favorite() {
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
