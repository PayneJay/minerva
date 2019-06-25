package com.minerva.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class SearchHistory {
    @Id
    private String key;
    private long timestamp;

    @Generated(hash = 1172447141)
    public SearchHistory(String key, long timestamp) {
        this.key = key;
        this.timestamp = timestamp;
    }

    @Generated(hash = 1905904755)
    public SearchHistory() {
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
