package com.example.dagaozi.rxjavaretrofitdemo.Utils;

import com.example.dagaozi.rxjavaretrofitdemo.model.Course;
import com.example.dagaozi.rxjavaretrofitdemo.model.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dagaozi .（dagaozi@163.com）
 * 创建时间：2016/4/3 11:50
 * 类描述：
 */
public class DataTestManager {
    public static List<Student> getStudents() {
        List<Student> students=new ArrayList<>() ;
        for(int i=0;i<10;i++){

            List<Course> courses=new ArrayList<>();
            for(int j=0;j<3;j++){
                Course course=new Course("courseNmae"+i,"tearcherName"+i,i);
                courses.add(course);
            }
            Student student=new Student(i,"name"+i,i%2,courses);
            students.add(student);
        }
        return  students;
    }
}
