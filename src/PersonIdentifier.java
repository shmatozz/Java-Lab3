import Exceptions.WrongInputException;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;


public class PersonIdentifier {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final Integer[] days_in_months = new Integer[] {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    String surname, name, patronymic;
    LocalDate birth_date;

    private final char sex;
    int age;

    PersonIdentifier(String[] info) throws WrongInputException {
        try {
            checkDate(info[3]); // проверка даты
            checkName(info[0], info[1], info[2]);   // проверка ФИО
            this.surname = info[0].substring(0, 1).toUpperCase() + info[0].substring(1);
            this.name = info[1];
            this.patronymic = info[2];
            this.birth_date = LocalDate.parse(info[3], formatter);
            this.age = Period.between(birth_date, LocalDate.now()).getYears();
            this.sex = getSex(info[2]);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Дата рождения не может содержать строки");
        } catch (WrongInputException e) {
            throw new WrongInputException(e.getMessage());
        }
    }

    void printInfo() {
        System.out.print(surname + ' ' +
                name.substring(0, 1).toUpperCase() + '.' +
                patronymic.substring(0, 1).toUpperCase() + ". " +
                sex + ' ' + age + ' ');
        if (age % 10 == 0 || age % 10 >= 5 || (10 <= age && age <= 20)) {
            System.out.println("лет");
        } else if (age % 10 == 1) {
            System.out.println("год");
        } else {
            System.out.println("года");
        }
    }

    private void checkDate(String birth_date_string) throws WrongInputException { // проверка даты на правильность
        String[] split_birth_date = birth_date_string.split("\\.");
        if (split_birth_date.length != 3) {
            throw new WrongInputException("Неверно введена дата"); // если в дате не 3 числа
        }

        int day = Integer.parseInt(split_birth_date[0]), // достаём день
                month = Integer.parseInt(split_birth_date[1]), // месяц
                year = Integer.parseInt(split_birth_date[2]); // год

        if (month > 12 || month < 1) {  // если месяц за пределами существующих
            throw new WrongInputException("Неверно введён номер месяца");
        }

        if (day < 1 || (day > days_in_months[month - 1] && month != 2) ||  // если неправильный день
                (!isLeap(year) && month == 2 && day > 28) ||
                (isLeap(year) && month == 2 && day > 29)) {
            throw new WrongInputException("Неверно введён ень");
        }

        if (LocalDate.of(year, month, day).isAfter(LocalDate.now())) { // если дата рождения в будущем
            throw new WrongInputException("Дата рождения не может быть в будущем");
        }

        if (year < 0) {
            throw new WrongInputException("Год не может быть отрицательным");  // если год отрицательный
        }
    }

    private void checkName(String surname, String name, String patronymic) throws WrongInputException {
        if (surname.matches("^[a-zA-Z]*$") || name.matches("^[a-zA-Z]*$") || patronymic.matches("^[a-zA-Z]*$")) {
            throw new WrongInputException("ФИО не должно содержать символы английского алфавита");
        }
    }

    private char getSex(String patronymic) {
        if (patronymic.endsWith("ич") || patronymic.endsWith("лы")) return 'M';
        else return 'Ж';
    }

    private boolean isLeap(int year) {
        return new GregorianCalendar().isLeapYear(year);
    }
}
