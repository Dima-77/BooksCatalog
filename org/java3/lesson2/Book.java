package org.java3.lesson2;

/**
 * Created by Dima on 07.10.2016.
 */
public class Book {
    private Integer id;
    private String name;
    private Integer author_id;

    public Book() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAuthor_id() {
        return author_id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor_id(Integer author_id) {
        this.author_id = author_id;
    }

    @Override
    public String toString() {
        return String.format("id = %s; Название: %s; id автора: %s", id, name, author_id);
    }
}
