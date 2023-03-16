import Exceptions.WrongDateException;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class PersonIdentifier {
    private static final Integer[] days_in_months = new Integer[] {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    String surname, name, patronymic, birth_date_string;
    Calendar birth_date;

    private String sex;
    private int age;

    PersonIdentifier(String surname, String name, String patronymic, String birth_date_string) throws WrongDateException {
        isExistDate(birth_date_string);
        this.surname = surname; this.name = name; this.patronymic = patronymic; this.birth_date_string = birth_date_string;
    }

    private void isExistDate(String birth_date_string) throws WrongDateException { // проверка даты на правильность
        try {
            String[] split_birth_date = birth_date_string.split("\\.");
            if (split_birth_date.length != 3)
                throw new WrongDateException("Wrong date length!"); // если в дате не 3 числа

            int day = Integer.parseInt(split_birth_date[0]), // достаём день
                    month = Integer.parseInt(split_birth_date[1]), // месяц
                    year = Integer.parseInt(split_birth_date[2]); // год

            if (month > 12 || month < 1)    // если месяц за пределами существующих
                throw new WrongDateException("Wrong month number!");

            if (day < 1 || (day > days_in_months[month - 1] && month != 2) ||  // если неправильный день
                    (!isLeap(year) && month == 2 && day > 28) ||
                    (isLeap(year) && month == 2 && day > 29)) throw new WrongDateException("Wrong day number!");

            if (LocalDate.of(year, month, day).isAfter(LocalDate.now()))
                throw new WrongDateException("Birth date cannot be in the future!");

            if (year < 0) throw new WrongDateException("Year cannot be negative!");

        } catch (WrongDateException e) {
            throw new WrongDateException(e.getMessage());
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Strings cannot be a part of date!");
        }
    }

    private boolean isLeap(int year) {
        return new GregorianCalendar().isLeapYear(year);
    }
}
