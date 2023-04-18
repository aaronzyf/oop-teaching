package theater.tools;

import theater.annotations.TableDataField;
import theater.bo.Menu;
import theater.enums.AnsiColor;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

public class CommandLineUtils {

    public static int getIntegerInput() {
        Scanner scanner = new Scanner(System.in);
        int result = scanner.nextInt();
        return result;
    }

    public static String getStringInput() {
        Scanner scanner = new Scanner(System.in);
        String result = scanner.nextLine();
        return result;
    }

    public static float getFloatInput() {
        Scanner scanner = new Scanner(System.in);
        float result = scanner.nextFloat();
        return result;
    }

    public static void setPrintColor(AnsiColor color) {
        System.out.print(color.getCode());
    }

    public static void resetColor() {
        System.out.print(AnsiColor.RESET.getCode());
    }

    public static void successMessage(String message) {
        setPrintColor(AnsiColor.BRIGHT_GREEN);
        System.out.println(message);
        resetColor();
    }

    public static void warningMessage(String message) {
        setPrintColor(AnsiColor.BRIGHT_YELLOW);
        System.out.println(message);
        resetColor();
    }

    public static void errorMessage(String message) {
        setPrintColor(AnsiColor.BRIGHT_RED);
        System.out.println(message);
        resetColor();
    }


    public static void clear() {

    }

    public static boolean deleteConfirm(String deleteTip) {
        return deleteConfirm(deleteTip,null);
    }
    public static boolean deleteConfirm() {
        return deleteConfirm("确定要删除吗",null);
    }
    public static boolean deleteConfirm(Menu.Operation operation) {
        return deleteConfirm("确定要删除吗",operation);
    }

    public static boolean deleteConfirm(String deleteTip, Menu.Operation operation) {
        setPrintColor(AnsiColor.RED);
        System.out.println(deleteTip + " (y/n)");
        resetColor();
        Scanner scanner = new Scanner(System.in);

        //获取用户的输入 , 并且将用户的输入转成小写的形式.
        String input = getStringInput().toLowerCase();

        if (input.equals("y")) {
            if(!isNull(operation)) operation.execute();
            return true;
        } else if (input.equals("n")) {
            return false;
        } else {
            clear();
            setPrintColor(AnsiColor.RED);
            System.out.println("输入不正确 , 请输入y确认删除 ,或输入n取消删除");
            resetColor();
            deleteConfirm(deleteTip,operation);

            return false;
        }
    }


    public static Object createObject(Class<? extends Object> cls) {
        try {
            Object object = cls.newInstance();
            List<Field> fields = Arrays.asList(cls.getDeclaredFields());
            fields.forEach(field ->{
                TableDataField tableDataField = field.getAnnotation(TableDataField.class);
                if(tableDataField != null){
                    Class<?>  fieldClass =  field.getType();
                    if(!(fieldClass == int.class || fieldClass == String.class || fieldClass == float.class || fieldClass == Date.class )){
                        return;
                    }
                    System.out.println("Enter the " + tableDataField.name());

                    if(fieldClass == int.class){
                        int input = getIntegerInput();
                        //调用该字段的set方法
                        try {
                            Method fieldSetMethod  =  cls.getMethod("set"+StringUtils.capitalize(field.getName()),int.class);
                            fieldSetMethod.invoke(object,input);
                        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }

                    }else if(fieldClass == String.class){
                        String input = getStringInput();
                        try {
                            Method fieldSetMethod = cls.getMethod("set"+ StringUtils.capitalize(field.getName()),String.class);
                            fieldSetMethod.invoke(object, input);

                        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }else if(fieldClass == float.class){
                        float input = getFloatInput();
                        try {
                            Method fieldSetMethod = cls.getMethod("set"+ StringUtils.capitalize(field.getName()),float.class);
                            fieldSetMethod.invoke(object, input);

                        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }else if(fieldClass == Date.class){
                        warningMessage("your input format should match `yyyy/mm/dd hh:ii`");
                        warningMessage("if the format that you input is wrong  , we will use current time `");
                        String input = getStringInput();
                        Date showTime = new Date();
                        String regex = "(\\d{4})/(\\d{2})/(\\d{2}) (\\d{2}):(\\d{2})";
                        Pattern pattern = Pattern.compile(regex);
                        Matcher matcher = pattern.matcher(input);
                        if (matcher.find()) {
                            int year = Integer.parseInt(matcher.group(1));
                            int month = Integer.parseInt(matcher.group(2));
                            int day = Integer.parseInt(matcher.group(3));
                            int hour = Integer.parseInt(matcher.group(4));
                            int minute = Integer.parseInt(matcher.group(5));

                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, month - 1, day, hour, minute);
                            showTime = calendar.getTime();
                        }

                        try {
                            Method fieldSetMethod = cls.getMethod("set"+ StringUtils.capitalize(field.getName()),Date.class);
                            fieldSetMethod.invoke(object, showTime);

                        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });

            return object;
        } catch (InstantiationException | IllegalAccessException e) {
            setPrintColor(AnsiColor.RED);
            System.out.println(e.getMessage());
            resetColor();
        }
        return null;
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
            setPrintColor(index.get() % 2 == 0 ? AnsiColor.GREEN : AnsiColor.BRIGHT_GREEN);
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
                } catch (Exception e) {
                    System.out.println(StringUtils.padString("error",20));
                    e.printStackTrace();
                    errorMessage(e.getMessage());

                }
            });
            resetColor();
            System.out.println();
        });
    }

    public static void pressAnyKeyToContinue() {
        setPrintColor(AnsiColor.BRIGHT_BLUE);
        System.out.println("Press any key to continue...");
        resetColor();
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void tableData(List<?> list) {
        if (list.isEmpty()) {
            System.out.println("No data available.");
            return;
        }
        Class<?> cls = list.get(0).getClass();
        tableData(list, cls);
    }


    public static void menu(List<Menu> menuItems) {
        menu(menuItems, "");
    }

    public static void menu(List<Menu> menuItems, String operationInstruction) {
        if (operationInstruction != null && !operationInstruction.isEmpty()) {
            System.out.println(operationInstruction);
        }
        AtomicInteger index = new AtomicInteger();
        for (Menu menuItem : menuItems) {
            System.out.println(index.incrementAndGet() + ".\t" + menuItem.getActionName());
        }


        boolean selectedMenuFlag = false;

        while (!selectedMenuFlag) {
            setPrintColor(AnsiColor.BRIGHT_GREEN);
            System.out.print("Enter your choice: ");
            resetColor();
            try {
                int inputIndex = getIntegerInput();
                if (inputIndex < 0 || inputIndex > index.get()) {
                    throw new Exception("input error");
                } else {
                    selectedMenuFlag = true;
                    Menu.Operation operation = menuItems.get(inputIndex - 1).getOperation();
                    try{
                        operation.execute();
                    }catch (Exception e){
                        setPrintColor(AnsiColor.RED);
                        System.out.println("Execute menu callback Error: " + e.getMessage());
                        e.printStackTrace();
                        resetColor();
                    }
                }
            } catch (Exception e) {
                setPrintColor(AnsiColor.YELLOW);
                System.out.println(e.getMessage());
                System.out.println("Incorrect input, please re-enter the menu number");
                resetColor();
            }

        }
    }
}
