package org.java3.lesson2;

/**
 * Created by Dima on 07.10.2016.
 */
public class Author {
    private Integer id;
    private String firstName;
    private String middleName;

    private String secondName;

    public Author() {

    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getMiddleName() {
        return middleName;
    }

    @Override
    public String toString() {
        return String.format("id = %s; Имя: %s; Отчество: %s; Фамилия: %s", id, firstName, middleName, secondName);
    }
}
