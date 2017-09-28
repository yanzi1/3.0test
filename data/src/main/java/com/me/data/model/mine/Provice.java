package com.me.data.model.mine;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xunice on 23/05/2017.
 */

public class Provice implements Serializable{

    private String id;
    private String name;

    private List<City> citys;

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

    public List<City> getCitys() {
        return citys;
    }

    public void setCitys(List<City> citys) {
        this.citys = citys;
    }
}
