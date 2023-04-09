package com.cabbage03.studentManagement.tools;

import com.cabbage03.studentManagement.annotations.TableColumnWidth;
import com.cabbage03.studentManagement.annotations.TableDataField;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class CommandLineUtils {


    public static boolean deleteConfirm(String deleteTip) {

        System.out.println(deleteTip + " (y/n)");
        Scanner scanner = new Scanner(System.in);

        //获取用户的输入 , 并且将用户的输入转成小写的形式.
        String input = scanner.nextLine().toLowerCase();

        if (input.equals("y")) {
            return true;
        } else if (input.equals("n")) {
            return false;
        } else {
            return false;
        }
    }

    public static boolean deleteConfirm() {
        return deleteConfirm("确定要删除吗");
    }

    private static final int DEFAULT_COLUMN_WIDTH = 10;

    /**
     * 只显示实体类中 , 被 TableDataField 装饰的类.
     *
     * @param list
     * @param cls
     */
    public static void tableData(List<?> list, Class<?> cls) {

        Field[] fields = cls.getDeclaredFields();
        List<Field> fieldsShowInTable = new ArrayList<>();
        System.out.print("#" + "\t");

        for (Field field : fields) {
            TableDataField tableDataField = field.getAnnotation(TableDataField.class);
            TableColumnWidth tableColumnWidth = field.getAnnotation(TableColumnWidth.class);
            if (tableDataField != null) {
                System.out.print(StringUtils.padString(tableDataField.value(), tableColumnWidth == null ? DEFAULT_COLUMN_WIDTH : tableColumnWidth.value()) + "\t\t");
                fieldsShowInTable.add(field);
            }
        }


        System.out.println();
        if (list.isEmpty()) {
            System.out.println();
            System.out.println("\t\t\tno data");
        }

        AtomicInteger index = new AtomicInteger();
        list.forEach((Object obj) -> {
            System.out.print(index.incrementAndGet() + "\t");
            fieldsShowInTable.forEach((Field field) -> {
                //判断该属性有没有get方法 , 如果有 则invoke 对应的get方法
                try {
                    TableColumnWidth tableColumnWidth = field.getAnnotation(TableColumnWidth.class);
                    System.out.printf(
                            StringUtils.padString(
                                    cls.getMethod("get" + StringUtils.capitalize(field.getName())).invoke(obj).toString(),
                                    tableColumnWidth == null ? DEFAULT_COLUMN_WIDTH : tableColumnWidth.value())
                                    + "\t\t");
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    System.out.print("no data" + "\t\t");
                }
            });
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
