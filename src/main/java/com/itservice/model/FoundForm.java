package com.itservice.model;


import lombok.ToString;

//@ToString
public class FoundForm {
   public Long id;
   public String type;
   public String data;

    public FoundForm() {
    }

    public Long getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    public String getData() {
        return this.data;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setData(String data) {
        this.data = data;
    }
}
