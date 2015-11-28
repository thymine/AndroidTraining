package me.zhang.lab.realm;

import io.realm.RealmObject;

/**
 * Created by Zhang on 11/28/2015 5:32 下午.
 */
public class Country extends RealmObject {

    private String name;
    private int population;
//    @PrimaryKey
    private String code;

    public Country() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

}
