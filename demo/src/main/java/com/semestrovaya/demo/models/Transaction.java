package com.semestrovaya.demo.models;

import javax.persistence.*;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long Count_id;

    private int sum;

    private int status;

    public Transaction(int sum, Long Count_id) {
        this.sum = sum;
        this.Count_id = Count_id;
        this.status = 1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCount_id() {
        return Count_id;
    }

    public void setCount_id(Long count_id) {
        Count_id = count_id;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Transaction() {
    }
}
