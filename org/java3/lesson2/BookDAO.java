package org.java3.lesson2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Dima on 08.10.2016.
 */
public class BookDAO implements DAO <Book> {
    private Connection connection;
    private PreparedStatement stt;
    private static Logger LOG;

    public BookDAO() {
        connection = DBConnector.getConnection();
        LOG = LoggerFactory.getLogger(BookDAO.class);
        LOG.info("Соединение с базой данных получено");
        create("book");
    }

    @Override
    public void create(String tableName) {
        try {
            stt = connection.prepareStatement("CREATE TABLE " + tableName + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT, author_id INTEGER FOREIN KEY);");
            stt.execute();
            System.out.println("Таблица book создана");
        } catch (SQLException e) {
            int error = e.getErrorCode();
            switch (error) {
                case 1:
                    System.out.println("Таблица book уже существует");
                    break;
                default:
                    e.printStackTrace();
            }
        }
    }

    @Override
    public Collection<Book> select() {
        ArrayList<Book> books = new ArrayList<>();
        try {
            stt = connection.prepareStatement("SELECT * FROM book;");
            ResultSet resultSet = stt.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setName(resultSet.getString("name"));
                book.setAuthor_id(resultSet.getInt("author_id"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return books;
        }
    }

    @Override
    public void insert(Book book) {
        int rowAdded = 0;
        try {
            stt = connection.prepareStatement("INSERT INTO book (name, author_id) VALUES (?, ?);");
            stt.setString(1, book.getName());
            stt.setInt(2, book.getAuthor_id());
            rowAdded = stt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rowAdded == 0) {
                LOG.info("В таблицу book не вставлено ни одной строки");
            } else {
                LOG.info("В таблицу book вставлена новая строка");
            }
        }
    }

    @Override
    public void update(Book book) {
        int rowsUpdated = 0;
        try {
            stt = connection.prepareStatement("UPDATE book SET name = ?, author_id = ? WHERE id = ?;");
            stt.setString(1, book.getName());
            stt.setInt(2, book.getAuthor_id());
            stt.setInt(3, book.getId());
            rowsUpdated = stt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rowsUpdated == 0) {
                LOG.info("В таблице book не обновлено ни одной строки");
            } else {
                LOG.info("В таблице book изменена строка");
            }
        }
    }

    @Override
    public void delete(Book book) {
        int rowsDeleted = 0;
        try {
            stt = connection.prepareStatement("DELETE FROM book WHERE id = ?;");
            stt.setInt(1, book.getId());
            rowsDeleted = stt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rowsDeleted == 0) {
                LOG.info("В таблице book не удалено ни одной строки");
            } else {
                LOG.info("В таблице book удалена строка");
            }
        }
    }

    @Override
    public Book getById(Integer key) {
        Book book = null;
        try {
            stt = connection.prepareStatement("SELECT * FROM book WHERE id = ?");
            stt.setInt(1, key);
            ResultSet rs = stt.executeQuery();
            if (rs.next()) {
                book = new Book();
                book.setId(key);
                book.setName(rs.getString("name"));
                book.setAuthor_id(rs.getInt("author_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return book;
        }
    }
}
