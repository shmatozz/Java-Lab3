import Exceptions.WrongDateException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws WrongDateException {
        Scanner scanner = new Scanner(System.in);
        String surname, name, patronymic, birth_date_string;

        System.out.print("Введите ФИО и дату рождения (day.month.year): ");
        surname = scanner.next();
        name = scanner.next();
        patronymic = scanner.next();
        birth_date_string = scanner.next();

        PersonIdentifier person = new PersonIdentifier(surname, name, patronymic, birth_date_string);

        System.out.println(surname + ' ' + name + ' ' + patronymic + ' ' + birth_date_string);
    }
}
