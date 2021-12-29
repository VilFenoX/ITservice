package com.itservice.model;

import lombok.Data;
import lombok.ToString;
import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class StringForm implements Serializable {
    public String value;
    public String valueTwo;
    public transient int sum=0;
    public transient List<String> result;
    public String type;


}
