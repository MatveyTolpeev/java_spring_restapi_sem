package com.semestrovaya.demo.models;

import javax.persistence.*;

@Entity
public class Count {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String comment;
    private int status;

    public Count() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public int getStatus() {
        return status;
    }

    public Count(String name, String comment) {
        this.name = name;
        this.comment = comment;
        this.status = 1;
    }


}
