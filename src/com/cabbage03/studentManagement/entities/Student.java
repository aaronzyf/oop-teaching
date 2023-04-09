package  com.cabbage03.studentManagement.entities;


import com.cabbage03.studentManagement.annotations.TableDataField;
import com.cabbage03.studentManagement.enums.Gender;

public class Student {

    public Student(String name , Gender gender , int grade){
        this.name = name;
        this.grade =  grade;
        this.gender = gender;
    }



    public void describe(){
        System.out.println("my name is "+ name);
    }


    @TableDataField("姓名")
    private String name;

    @TableDataField("年级")
    private int grade ;

    @TableDataField("性别")
    private Gender gender ; // 0 女  , 1 男



    static  boolean isHuman = true;



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
