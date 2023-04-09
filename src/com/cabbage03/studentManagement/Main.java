package com.cabbage03.studentManagement;

import com.cabbage03.studentManagement.entities.Student;
import com.cabbage03.studentManagement.enums.Gender;
import com.cabbage03.studentManagement.tools.CommandLineUtils;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Student> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            if(i%2 == 0){
                Student wuqiongStudent = new Student("吴琼" + i, Gender.WOMAN, i%4+1);
                list.add(wuqiongStudent);
            }else{
                Student wuqiongStudent = new Student("小张同学" + i, Gender.MAN, i%4+1);
                list.add(wuqiongStudent);
            }

        }

        CommandLineUtils.tableData(list);
    }
}