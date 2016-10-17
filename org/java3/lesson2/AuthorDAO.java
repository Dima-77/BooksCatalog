package org.java3.lesson2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Dima on 07.10.2016.
 */
public class AuthorDAO implements DAO<Author> {
    private Connection connection;
    private PreparedStatement stt;
    private static Logger LOG;

    public AuthorDAO() {
        connection = DBConnector.getConnection();
        LOG = LoggerFactory.getLogger(AuthorDAO.class);
        LOG.info("Соединение с базой данных получено");
        create("author");
    }

    @Override
    public void create(String tableName) {
        try {
            stt = connection.prepareStatement("CREATE TABLE " + tableName + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "firstName TEXT, middleName TEXT, secondName TEXT);");
            stt.execute();
            System.out.println("Таблица author создана");
        } catch (SQLException e) {
            int error = e.getErrorCode();
            switch (error) {
                case 1:
                    System.out.println("Таблица author уже существует");
                break;
                default:
                    e.printStackTrace();
            }
        }
    }

    @Override
    public Collection<Author> select() {
        ArrayList<Author> authors = new ArrayList<>();
        try {
            stt = connection.prepareStatement("SELECT * FROM author;");
            ResultSet resultSet = stt.executeQuery();
            while (resultSet.next()) {
                Author author = new Author();
                author.setId(resultSet.getInt("id"));
                author.setFirstName(resultSet.getString("firstName"));
                author.setSecondName(resultSet.getString("secondName"));
                author.setMiddleName(resultSet.getString("middleName"));
                authors.add(author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return authors;
        }
    }

    @Override
    public void insert(Author author) {
        int rowAdded = 0;
        try {
            stt = connection.prepareStatement("INSERT INTO author (firstName, secondName, middleName) VALUES (?, ?, ?);");
            stt.setString(1, author.getFirstName());
            stt.setString(2, author.getSecondName());
            stt.setString(3, author.getMiddleName());
            rowAdded = stt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rowAdded == 0) {
                LOG.info("В таблицу author не вставлено ни одной строки");
            } else {
                LOG.info("В таблицу author вставлена новая строка");
            }
        }
    }

    @Override
    public void update(Author author) {
        int rowsUpdated = 0;
        try {
            stt = connection.prepareStatement("UPDATE author SET firstName = ?, secondName = ?, middleName = ? " +
                                              "WHERE id = ?;");
            stt.setString(1, author.getFirstName());
            stt.setString(2, author.getSecondName());
            stt.setString(3, author.getMiddleName());
            stt.setInt(4, author.getId());
            rowsUpdated = stt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rowsUpdated == 0) {
                LOG.info("В таблице author не обновлено ни одной строки");
            } else {
                LOG.info("В таблице author изменена строка");
            }
        }
    }

    @Override
    public void delete(Author author) {
        int rowsDeleted = 0;
        try {
            stt = connection.prepareStatement("DELETE FROM author WHERE id = ?;");
            stt.setInt(1, author.getId());
            rowsDeleted = stt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rowsDeleted == 0) {
                LOG.info("В таблице author не удалено ни одной строки");
            } else {
                LOG.info("В таблице author удалена строка");
            }
        }
    }

    @Override
    public Author getById(Integer key) {
        Author author = null;
        try {
            stt = connection.prepareStatement("SELECT * FROM author WHERE id = ?");
            stt.setInt(1, key);
            ResultSet rs = stt.executeQuery();
            if (rs.next()) {
                author = new Author();
                author.setId(key);
                author.setFirstName(rs.getString("firstName"));
                author.setSecondName(rs.getString("secondName"));
                author.setMiddleName(rs.getString("middleName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return author;
        }
    }
}
