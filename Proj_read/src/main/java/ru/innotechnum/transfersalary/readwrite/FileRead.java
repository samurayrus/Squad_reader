package ru.innotechnum.transfersalary.readwrite;

import ru.innotechnum.transfersalary.department.Employee;
import ru.innotechnum.transfersalary.department.Squad;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class FileRead {
    private String path;

    public FileRead(String filePath) {
        path = filePath;
    }

    private Boolean parsingString(String[] mas) { //Проверки на входные данные
        if (mas.length != 3) {
            System.out.println("Строка состоит не из трех частей, а из " + mas.length);
            return false;
        }
        if (mas[0].length() < 2 || !mas[0].matches("[a-zA-Z ]+") || mas[2].split("\\s").length == 0) {
            System.out.println("Некорректное имя. Разрешены только буквы и пробелы");
            return false;
        }
        try {
            if (mas[1].split("\\.").length > 1) {
                int numberAfterPoint = mas[1].split("\\.")[1].length();  //считаем знаки после запятой
                if (numberAfterPoint != 2)
                    throw new NumberFormatException("Должно быть два знака после запятой. [X.xx], а в строке " + numberAfterPoint);
            }
            BigDecimal sal = new BigDecimal(mas[1]);
        } catch (NumberFormatException numEx) {
            System.out.println("Некорректное число.\n" + numEx.getMessage());
            return false;
        }
        return true;
    }

    public Map<String, Squad> reading() {  //Сама функция чтения
        String line; //Считываемая строка
        Map<String, Squad> hashMapSquads = new HashMap<String, Squad>(); //Возвращаемый

        try (FileReader fileReader = new FileReader(path);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) { //try с ресурсами. Не нужно следить за закрытием подключения
            line = bufferedReader.readLine();
            int numberLine = 0;     //Счетчик прочитанных строк
            while (line != null) {     //Пока строка есть - читаем
                numberLine++;     //Считаем строки для обозначения ошибочной строки.
                String[] mas;
                mas = line.split("/");    // имя/доход/отдел

                if (!parsingString(mas)) { // Проверка входных параметров. Если не прошел, пропускаем цикл
                    System.out.println("Некорректная запись в строке " + numberLine + "\n" + line);
                    line = bufferedReader.readLine(); //След строка
                    continue;
                } else {
                    hashMapSquads.putIfAbsent(mas[2], new Squad(mas[2])); //Если такого нет, то создаем
                    hashMapSquads.get(mas[2]).addEmpl(new Employee(mas[0], mas[1])); //Кладем в него сотрудника
                }

                System.out.println(line);    //вывод прочитанной строки
                line = bufferedReader.readLine(); //След строка
            }
        } catch (IOException ex) {
            System.out.println("Path to file not found");
            return new HashMap<>(); //Поставил возврат map вместо null. Хотя по факту тоже самое
        }
        return hashMapSquads;
    }
}