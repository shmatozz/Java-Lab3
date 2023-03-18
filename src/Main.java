import Exceptions.WrongInputException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String info;

        try {
            System.out.println("Введите ФИО и дату рождения (DD.MM.YYYY):");
            info = scanner.nextLine();
            String[] info_parsed = info.split(" ");

            PersonIdentifier person = new PersonIdentifier(info_parsed);

            person.printInfo();
        } catch (WrongInputException | NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }
}
