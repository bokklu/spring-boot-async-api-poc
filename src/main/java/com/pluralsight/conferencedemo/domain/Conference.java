package com.pluralsight.conferencedemo.domain;

public class Conference {
    private String name;

    private Conference(String name) {
        this.name = name;
    }

    public static Conference create(String name) {
        return new Conference(name);
    }

    public String getName() {
        return name;
    }

    //region [Add domain mutator methods here]
    //endregion
}