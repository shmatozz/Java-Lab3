import Exceptions.WrongInputException;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;

public class PersonIdentifier {
    // формат для парсинга даты рождения
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    // список количества дней в каждом месяце по индексу
    private static final Integer[] days_in_months = new Integer[] {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    String surname, name, patronymic;   // ФИО
    LocalDate birth_date;   // дата рождения
    char sex;   // пол
    int age;    // возраст

    /**
     * Конструктор
     * @param info массив аргументов
     *             info[0] - Фамилия
     *             info[1] - Имя
     *             info[2] - Отчество
     *             info[3] - Дата рождения
     * @throws WrongInputException в случае неверного формата введёных данных
     */
    PersonIdentifier(String[] info) throws WrongInputException {
        try {
            if (info.length != 4) throw new WrongInputException("Неверные входные данные");

            checkDate(info[3]); // проверка даты
            checkName(info[0], info[1], info[2]);   // проверка ФИО

            // Делаем первую букву фамилии заглавной на случай, если пользователь ввёл со строчной
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

    // вывод информации о человеке, Фамилия Инициалы Пол Возраст
    void printInfo() {
        System.out.print(surname + ' ' +
                name.substring(0, 1).toUpperCase() + '.' +
                (!patronymic.equals("-") ? patronymic.substring(0, 1).toUpperCase() + ". " : ' ') +
                sex + ' ' +
                age + ' ');
        if (age % 10 == 0 || age % 10 >= 5 || (10 <= age && age <= 20)) {
            System.out.println("лет");
        } else if (age % 10 == 1) {
            System.out.println("год");
        } else {
            System.out.println("года");
        }
    }

    // проверка даты
    private void checkDate(String birth_date_string) throws WrongInputException {
        String[] split_birth_date = birth_date_string.split("\\.");
        if (split_birth_date.length != 3) {
            throw new WrongInputException("Неверный формат даты"); // если в дате не 3 числа, разделённые точкой
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

    // проверка ФИО
    private void checkName(String surname, String name, String patronymic) throws WrongInputException {
        if (!surname.matches("^[а-яА-Я]*$") || !name.matches("^[а-яА-Я]*$") || !patronymic.matches("^[а-яА-Я-]*$")) {
            throw new WrongInputException("ФИО должно содержать только символы русского алфавита");
        }
    }

    // определение пола по отчеству или фамилии
    private char getSex(String patronymic) {
        if (patronymic.endsWith("ич") || patronymic.endsWith("лы")) return 'M';
        else if (patronymic.endsWith("на") || patronymic.endsWith("зы")) return 'Ж';

        if (surname.endsWith("в")) return 'M';
        else if (surname.endsWith("ва")) return 'Ж';

        return '-';
    }

    // проверка года на високосность
    private boolean isLeap(int year) {
        return new GregorianCalendar().isLeapYear(year);
    }
}
