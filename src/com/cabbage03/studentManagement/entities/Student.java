package com.cabbage03.studentManagement.entities;


import com.cabbage03.studentManagement.annotations.TableDataField;
import com.cabbage03.studentManagement.enums.Gender;
import com.cabbage03.studentManagement.enums.StringAlignment;

public class Student {

    public Student(String name, Gender gender, int grade) {
        this.name = name;
        this.grade = grade;
        this.gender = gender;
    }


    public void describe() {
        System.out.println("my name is " + name);
    }


    @TableDataField(name = "姓名", width = 12)
    private String name;

    @TableDataField(name = "年级", width = 8, direction = StringAlignment.MIDDLE)
    private int grade;

    @TableDataField(name = "性别", width = 6)
    private Gender gender; // 0 女  , 1 男


    static boolean isHuman = true;


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

}
