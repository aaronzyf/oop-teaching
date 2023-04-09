package com.cabbage03.studentManagement.tools;

import com.cabbage03.studentManagement.annotations.TableDataField;
import com.cabbage03.studentManagement.enums.AnsiColor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class CommandLineUtils {



    public static void setPrintColor(AnsiColor color){
        System.out.print(color.getCode());
    }
    public static void resetColor(){
        System.out.print(AnsiColor.RESET.getCode());
    }


    public static void clear(){

    }

    public static boolean deleteConfirm(String deleteTip) {
        setPrintColor(AnsiColor.RED);
        System.out.println(deleteTip + " (y/n)");
        resetColor();
        Scanner scanner = new Scanner(System.in);

        //获取用户的输入 , 并且将用户的输入转成小写的形式.
        String input = scanner.nextLine().toLowerCase();

        if (input.equals("y")) {
            return true;
        } else if (input.equals("n")) {
            return false;
        } else {
            clear();
            setPrintColor(AnsiColor.RED);
            System.out.println("输入不正确 , 请输入y确认删除 ,或输入n取消删除");
            resetColor();
            deleteConfirm(deleteTip);

            return false;
        }
    }

    public static boolean deleteConfirm() {
        return deleteConfirm("确定要删除吗");
    }


    /**
     * 只显示实体类中 , 被 TableDataField 装饰的类.
     *
     * @param list
     * @param cls
     */
    public static void tableData(List<?> list, Class<?> cls) {

        Field[] fields = cls.getDeclaredFields();
        List<Field> fieldsShowInTable = new ArrayList<>();

        setPrintColor(AnsiColor.BRIGHT_MAGENTA);
        System.out.print("#" + "\t");

        for (Field field : fields) {
            TableDataField tableDataField = field.getAnnotation(TableDataField.class);
            if (tableDataField != null) {
                System.out.print(StringUtils.padString(tableDataField.name(), tableDataField.width()) + "\t");
                fieldsShowInTable.add(field);
            }
        }
        resetColor();


        System.out.println();
        if (list.isEmpty()) {
            System.out.println();
            System.out.println("\tno data");
        }

        AtomicInteger index = new AtomicInteger();
        list.forEach((Object obj) -> {
            setPrintColor(index.get()%2 ==0 ? AnsiColor.GREEN:AnsiColor.BRIGHT_GREEN );
            System.out.print(index.incrementAndGet() + "\t");
            fieldsShowInTable.forEach((Field field) -> {
                //判断该属性有没有get方法 , 如果有 则invoke 对应的get方法
                try {
                    TableDataField tableDataField = field.getAnnotation(TableDataField.class);
                    System.out.printf(
                            StringUtils.padString(
                                    cls.getMethod("get" + StringUtils.capitalize(field.getName())).invoke(obj).toString(),
                                    tableDataField.width(), tableDataField.direction())
                                    + "\t");
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    System.out.print("no data" + "\t");
                }
            });
            resetColor();
            System.out.println();
        });
    }

    public static void tableData(List<?> list) {
        if (list.isEmpty()) {
            System.out.println("No data available.");
            return;
        }
        Class<?> cls = list.get(0).getClass();
        tableData(list, cls);
    }
}
