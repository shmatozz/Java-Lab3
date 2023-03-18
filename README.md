## Java-Lab3
This lab includes working with strings, personal full name, sex and age.

---

To create an object of person identifying class, you need to pass to the **PersonIdentifier** class constructor an array of arguments.

> Array should consist of [_surname_, _name_, _patronymic_, _birth date_ _(DD.MM.YYYY)_] in **russian language**

```java
info = scanner.nextLine();
String[] info_parsed = info.split(" ");

PersonIdentifier person = new PersonIdentifier(info_parsed);
```

Method **.printInfo()** output information about person in format: _surname_ _initials_ _sex_ _age_.

```java
person.printInfo();
```
Information will be output in the console.
