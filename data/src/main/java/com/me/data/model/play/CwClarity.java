package com.me.data.model.play;

import java.io.Serializable;

/**
 * Created by dell on 2017/5/25.
 */

public class CwClarity implements Serializable {

    private String name;
    private String path;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof CwClarity) {
            CwClarity cwClarity = (CwClarity) o;
            return this.name.equals(cwClarity.getName());
        }
        return super.equals(o);
    }
}
