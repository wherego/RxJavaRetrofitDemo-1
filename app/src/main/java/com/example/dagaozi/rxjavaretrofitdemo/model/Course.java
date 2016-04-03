package com.example.dagaozi.rxjavaretrofitdemo.model;

/**
 * Created by dagaozi .（dagaozi@163.com）
 * 创建时间：2016/4/3 11:52
 * 类描述：
 */
public class Course {
    private String name;
    private String teacher;
    private int score;

    public Course(String name, String teacher, int score) {
        this.name = name;
        this.teacher = teacher;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}