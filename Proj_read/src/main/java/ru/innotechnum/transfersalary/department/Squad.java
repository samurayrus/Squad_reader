package ru.innotechnum.transfersalary.department;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Squad {
    static final int CHARS_AFTER_POINT = 6;  //Кол-во знаков после запятой для расчетов. 6 написал от балды
    private List<Employee> listEmpl = new ArrayList<Employee>(); //Данные отдела (ФИО/ЗП)
    private String name = null; //Название отдела
    private BigDecimal avarageSalary; //Часто повторяется вызов функции с одним и тем же рузультатом. Вынес в переменную

    public Squad() {
    }

    public Squad(String name) {
        setName(name);
    }

    public BigDecimal sumSalary() { //Подсчитывает суммарную зп одного отдела в BigDecimal. Экономия места, дублирующийся код.
        BigDecimal salary = new BigDecimal(0);
        for (int i = 0; i < listEmpl.size(); i++) {
            salary = salary.add(listEmpl.get(i).getSalary());
        }
        return salary;
    }

    public List<Employee> getListEmpl() {
        return listEmpl;
    }

    public void addEmpl(Employee empl) {
        getListEmpl().add(empl);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getAvarageSalary() {
        if (avarageSalary == null) {
            return avarageSalary();
        } else {
            return avarageSalary;
        }
    }

    public void setAverageSalary(BigDecimal avarageSalary) {
        this.avarageSalary = avarageSalary;
    }

    //Выводит в консоль данные об отделе, сотрудниках и ср. зарплате
    public void display() {
        StringBuilder answer = new StringBuilder("\nDisplay ->" + name + ": \n");

        for (int i = 0; i < listEmpl.size(); i++) {
            answer.append("\nTD " + i + ":   " + listEmpl.get(i).getName() + " " + listEmpl.get(i).getSalary());
        }

        answer.append("\nSum salary: " + sumSalary() + " ar size " + listEmpl.size() + "\nAverage salary: ");
        System.out.println(answer);
        System.out.printf("%.2f", avarageSalary());    //Среднний доход
    }

    //Подсчет средней зп по отделу
    public BigDecimal avarageSalary() {
        BigDecimal salary = sumSalary(); //Подсчет суммарной зп всех работников в отделе
        salary = salary.divide(new BigDecimal(listEmpl.size()), CHARS_AFTER_POINT, RoundingMode.HALF_UP);
        setAverageSalary(salary);
        return salary;
    }

    //Подсчет средней зп по отделу после изменений (перевода сотрудников).
    // num - Количество переведенных сотрудников
    public BigDecimal avarageSalaryWithTransfer(BigDecimal sal, int num) {
        BigDecimal salary = sumSalary(); //Подсчет суммарной зп всех работников в отделе
        salary = salary.subtract(sal); //Вычитаем из суммарной зп зп переводящегося сотрудника (или складываем. Может придти отрицательное число для рассчетов)

        if (sal.compareTo(BigDecimal.ZERO) > 0) {//В зависимости от знака sal - открепляем или прикрепляем сотрудника к отделу.
            salary = salary.divide(new BigDecimal(listEmpl.size() - num), CHARS_AFTER_POINT, RoundingMode.HALF_UP);
        }

        if (sal.compareTo(BigDecimal.ZERO) < 0) {
            salary = salary.divide(new BigDecimal(listEmpl.size() + num), CHARS_AFTER_POINT, RoundingMode.HALF_UP);
        }

        return salary;
    }
}
