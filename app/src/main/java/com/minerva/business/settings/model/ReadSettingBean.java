package com.minerva.business.settings.model;

import com.minerva.base.BaseBean;

import java.util.ArrayList;
import java.util.List;

public class ReadSettingBean extends BaseBean {

    private List<RatiosBean> ratios = new ArrayList<>();
    private List<List<String>> options = new ArrayList<>();

    public List<RatiosBean> getRatios() {
        return ratios;
    }

    public void setRatios(List<RatiosBean> ratios) {
        this.ratios = ratios;
    }

    public List<List<String>> getOptions() {
        return options;
    }

    public void setOptions(List<List<String>> options) {
        this.options = options;
    }

    public static class RatiosBean {
        /**
         * id : 1
         * name : 科技类
         * value : 1.0
         */

        private String id;
        private String name;
        private String value;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value == null ? "" : value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
