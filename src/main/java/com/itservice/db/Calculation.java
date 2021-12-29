package com.itservice.db;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "calculation")
public class Calculation {
    @javax.persistence.Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long Id;

    @Column(name = "string")
    @ElementCollection
    private List<String> strings;

    @Column(name = "string")
    @ElementCollection
    private List<String> stringsTwo;

    @Column(name = "type")
    private String type;

    @Column(name = "data")
    private String date;

    public Calculation() {
    }
}
