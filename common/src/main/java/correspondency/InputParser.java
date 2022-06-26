package correspondency;

import storage.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputParser {

    static public boolean isBlank(String name) {
        return name == null || name.isEmpty();
    }

    public static Worker getWorkerFromInput(Scanner source, String user) throws NoSuchElementException {
        String name;
        Coordinates coords;
        Long salary;
        LocalDate date;
        int pweight;
        Color eyeC;
        Color hairC;
        Country pcountry;
        Person person;
        Position pos;
        Status st;

        Scanner in = source;

        System.out.println("Введите имя:");

        name = in.nextLine();
        while (isBlank(name.trim())) {
            System.out.println("Неверное имя. Повторите ввод");
            name = in.nextLine();
        }

        System.out.println("Введите координаты x");
        String[] temp = in.nextLine().split(" ");
        float x;
        while (true) {
            try {
                x = Float.parseFloat(temp[0]);
                if (!Coordinates.validateX(x)) {
                    System.out.println("Out of boundaries");
                    temp = in.nextLine().split(" ");
                    continue;
                }
                break;
            }
            catch (NumberFormatException e) {
                System.out.println("Неверный ввод. Повторите");
                temp = in.nextLine().split(" ");
            }
        }

        System.out.println("Введите координаты y");
        temp = in.nextLine().split(" ");
        while (true) {
            try {
                int y = Integer.parseInt(temp[0]);
                coords = new Coordinates(x, y);
                if (!coords.isValid())
                    throw new NumberFormatException();
                break;
            }
            catch (NumberFormatException e) {
                System.out.println("Неверный ввод. Повторите");
                temp = in.nextLine().split(" ");
            }
        }

        System.out.println("Введите зарплату:");
        while (true) {
            try {
                salary = Long.parseLong(in.nextLine());
                break;
            }
            catch (NumberFormatException e) {
                System.out.println("Неверный ввод. Повторите");
            }
        }

        System.out.println("Введите дату yyyy-mm-dd");
        while (true) {
            try {
                date = LocalDate.parse(in.nextLine());
                break;
            }
            catch (DateTimeParseException e) {
                System.out.println("Неверный ввод. Повторите");
            }
        }

        System.out.println("Введите позицию");
        Arrays.stream(
                Position.values())
                .forEach(val -> System.out.print(val + " ")
                );
        System.out.println();
        while (true) {
            try {
                String temp1 = in.nextLine();
                if (isBlank(temp1))
                    pos = null;
                else
                    pos = Position.valueOf(temp1);
                break;
            }
            catch (IllegalArgumentException e) {
                System.out.println("Неверный ввод. Повторите");
            }
        }

        System.out.println("Введите статус");
        Arrays.stream(
                Status.values())
                .forEach(val -> System.out.print(val + " ")
                );
        System.out.println();
        while (true) {
            try {
                String temp1 = in.nextLine();
                if (isBlank(temp1))
                    st = null;
                else
                    st = Status.valueOf(temp1);
                break;
            }
            catch (IllegalArgumentException e) {
                System.out.println("Неверный ввод. Повторите");
            }
        }

        System.out.println("Введите вес человека:");
        while (true) {
            try {
                pweight = Integer.parseInt(in.nextLine());
                if (pweight <= 0)
                    throw new NumberFormatException();
                break;
            }
            catch (NumberFormatException e) {
                System.out.println("Неверный ввод. Повторите");
            }
        }

        System.out.println("Введите цвет глаз человека");
        Arrays.stream(
                Color.values())
                .forEach(val -> System.out.print(val + " ")
                );
        System.out.println();
        while (true) {
            try {
                String temp1 = in.nextLine();
                if (isBlank(temp1))
                    eyeC = null;
                else
                    eyeC = Color.valueOf(temp1);
                break;
            }
            catch (IllegalArgumentException e) {
                System.out.println("Неверный ввод. Повторите");
            }
        }

        System.out.println("Введите цвет волос человека");
        Arrays.stream(
                Color.values())
                .forEach(val -> System.out.print(val + " ")
                );
        System.out.println();
        while (true) {
            try {
                String temp1 = in.nextLine();
                if (isBlank(temp1))
                    hairC = null;
                else
                    hairC = Color.valueOf(temp1);
                break;
            }
            catch (IllegalArgumentException e) {
                System.out.println("Неверный ввод. Повторите");
            }
        }

        System.out.println("Введите национальность человека");
        Arrays.stream(
                Country.values())
                .forEach(val -> System.out.print(val + " ")
                );
        while (true) {
            try {
                String temp1 = in.nextLine();
                if (isBlank(temp1))
                    pcountry = null;
                else
                    pcountry = Country.valueOf(temp1);
                break;
            }
            catch (IllegalArgumentException e) {
                System.out.println("Неверный ввод. Повторите");
            }
        }
        person = new Person(pweight, eyeC, hairC, pcountry);
        Worker w = new Worker(name, coords,
                salary, date, pos, st, person, user);
        return w;
    }

}
