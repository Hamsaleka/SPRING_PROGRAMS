package com.example.kgfsl1.God3;

import javax.persistence.*;

@Entity
class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    private String name;
    private String status;
    private String priority;
    private Boolean isCompleted;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getPriority() {
        return priority;
    }

    public void setBoolean(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Boolean getBoolean() {
        return isCompleted;
    }

}