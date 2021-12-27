package com.itservice.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class StringForm implements Serializable {
    public String value;
    //public int[][] value2;
    public int sum=0;
    public List<String> result;
    //public int[][] res2;

}
