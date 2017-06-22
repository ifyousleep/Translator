package com.baranov.translator.model;

import java.util.List;
import java.util.Map;

/**
 * Created by Baranov on 21.06.2017 to Translator.
 */

public class LangsFromYandex {

    private List<String> dirs;
    private Map<String, String> langs;

    public List<String> getDirs()
    {
        return dirs;
    }

    public void setDirs(List<String> dirs)
    {
        this.dirs = dirs;
    }

    public Map<String, String> getLangs()
    {
        return langs;
    }

    public void setLangs(Map<String, String> langs)
    {
        this.langs = langs;
    }
}
