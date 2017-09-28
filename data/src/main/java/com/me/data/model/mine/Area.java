package com.me.data.model.mine;

import java.io.Serializable;

/**
 * Created by xunice on 23/05/2017.
 */

public class Area implements Serializable{

    private String id;
    private String name;

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
}
