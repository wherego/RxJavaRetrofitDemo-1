package com.example.dagaozi.rxjavaretrofitdemo.model;

import java.util.List;

/**
 * Created by dagaozi .（dagaozi@163.com）
 * 创建时间：2016/4/3 11:33
 * 类描述：学生有姓名和所选的课程 ，课程有课程名称，和老师，成绩
 */
public class Student extends Person{

    private int id;
    private List<Course> courseList;

    public Student(int id,String name,int gender,List<Course> courseList) {
        super(name,gender);
        this.id=id;
        this.courseList = courseList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }
 public  int getGender() {
     return gender;
 }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }



}
