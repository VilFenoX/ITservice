package com.itservice.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class StringForm {
    public String value;
    //public int[][] value2;
    public int sum=0;
    public List<String> result;
    //public int[][] res2;

}
