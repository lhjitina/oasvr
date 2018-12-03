package com.ks4pl.oasvr;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Department {
    private Integer Id;
    private String Name;


    public Department() {
    }

    public Department(Integer id, String name) {
        Id = id;
        Name = name;
    }

    @JsonProperty("Id")
    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    @JsonProperty("Name")
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return "Department{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                '}';
    }
}
