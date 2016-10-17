package org.java3.lesson2;

import java.util.Collection;
import java.util.Scanner;

/**
 * Created by Dima on 08.10.2016.
 */
public class Catalog {
    Scanner scanner;
    Collection<Author> authors;
    Collection<Book> books;

    public Catalog() {
        init();
    }

    void init() {
        System.out.println("Привет, это книжная лавка!\n(Наберите exit в любом месте для выхода)");
        while (true) {
            System.out.println("Выберете опцию (наберите цифру):\n" +
                    "1)добавить автора\n" +
                    "2)обновить автора\n" +
                    "3)удалить автора\n" +
                    "4)посмотреть всех авторов\n" +
                    "5)добавить книгу\n" +
                    "6)обновить книгу\n" +
                    "7)удалить книгу\n" +
                    "8)посмотреть все книги\n" +
                    "9)просмотреть все книги и соответствующих авторов");
            scanner = new Scanner(System.in);
            if (scanner.hasNextLine()) {
                String option = scanner.nextLine();
                switch (option) {
                    case "1":
                        addAuthor();
                        break;
                    case "2":
                        updateAuthor();
                        break;
                    case "3":
                        deleteAuthor();
                        break;
                    case "4":
                        viewAllAuthors();
                        break;
                    case "5":
                        addBook();
                        break;
                    case "6":
                        updateBook();
                        break;
                    case "7":
                        deleteBook();
                        break;
                    case "8":
                        viewAllBooks();
                        break;
                    case "9":
                        viewAllBooksAndThatAuthors();
                        break;
                    case "exit":
                        exit();
                        break;
                    default:
                        System.out.println("Неправильная команда\n");
                }
                System.out.println("Для возврата к списку действий нажмите Enter");
                scanLine(true);
            }
        }
    }

    void exit() {
        scanner.close();
        System.exit(0);
    }

    void addAuthor() {
        authors = Facade.getAuthorDAO().select();
        int maxId = getMaxAuthorId(authors);
        Author author = new Author();
        System.out.println("Введите имя:");
        author.setFirstName(scanLine(false));
        System.out.println("Введите фамилию:");
        author.setSecondName(scanLine(false));
        System.out.println("Введите отчество (необязательно):");
        author.setMiddleName(scanLine(true));
        Facade.getAuthorDAO().insert(author);
        author = Facade.getAuthorDAO().getById(maxId + 1);
        if (author != null) {
            System.out.println("Новый автор:");
            System.out.println(author.toString());
            System.out.println();
        }
    }

    private void updateAuthor() {
        viewAllAuthors();
        System.out.println("Введите id обновляемого автора из списка");
        Author author = Facade.getAuthorDAO().getById(scanLineForInt());
        if (author != null) {
            System.out.println("Введите имя (необязательно):");
            String string = scanLine(true);
            if (!string.isEmpty()) author.setFirstName(string);
            System.out.println("Введите фамилию (необязательно):");
            string = scanLine(true);
            if (!string.isEmpty()) author.setSecondName(string);
            System.out.println("Введите отчество (необязательно):");
            string = scanLine(true);
            if (!string.isEmpty()) author.setMiddleName(string);
            System.out.println("Вы уверены что хотите обновить автора? Введите Yes/No");
            if (scanLineForAccept()) {
                Facade.getAuthorDAO().update(author);
                System.out.println("Автор обновлён:");
                System.out.println(Facade.getAuthorDAO().getById(author.getId()).toString());
            } else {
                System.out.println("Операция обновления автора отменена");
            }
            System.out.println();
        } else {
            System.out.println("Автора с таким id не существует\n");
        }
    }

    private void deleteAuthor() {
        viewAllAuthors();
        System.out.println("Введите id удаляемого автора из списка");
        Author author = Facade.getAuthorDAO().getById(scanLineForInt());
        if (author != null) {
            System.out.println("Вы уверены что хотите удалить автора? Введите Yes/No");
            if (scanLineForAccept()) {
                Facade.getAuthorDAO().delete(author);
                System.out.println("Автор удалён:");
                System.out.println(author.toString());
            } else {
                System.out.println("Операция удаления автора отменена");
            }
            System.out.println();
        } else {
            System.out.println("Автора с таким id не существует\n");
        }
    }

    private void viewAllAuthors() {
        authors = Facade.getAuthorDAO().select();
        System.out.println("Список всех авторов");
        for (Author a : authors) {
            System.out.println(a.toString());
        }
        System.out.println();
    }

    void addBook() {
        books = Facade.getBookDAO().select();
        int maxId = getMaxBookId(books);
        Book book = new Book();
        System.out.println("Введите название книги:");
        book.setName(scanLine(false));
        System.out.println("Введите id автора:");
        book.setAuthor_id(scanLineForInt());
        Facade.getBookDAO().insert(book);
        book = Facade.getBookDAO().getById(maxId + 1);
        if (book != null) {
            System.out.println("Новая книга:");
            System.out.println(book.toString());
            System.out.println();
        }
    }

    private void updateBook() {
        viewAllBooks();
        System.out.println("Введите id обновляемой книги из списка");
        Book book = Facade.getBookDAO().getById(scanLineForInt());
        if (book != null) {
            System.out.println("Введите название книги (необязательно):");
            String string = scanLine(true);
            if (!string.isEmpty()) book.setName(string);
            System.out.println("Введите id автора (>0, иначе - без изменений):");
            int authorId = scanLineForInt();
            if (authorId > 0) book.setAuthor_id(authorId);
            System.out.println("Вы уверены что хотите обновить книгу? Введите Yes/No");
            if (scanLineForAccept()) {
                Facade.getBookDAO().update(book);
                System.out.println("Книга обновлена:");
                System.out.println(Facade.getBookDAO().getById(book.getId()).toString());
            } else {
                System.out.println("Операция обновления книги отменена");
            }
            System.out.println();
        } else {
            System.out.println("Книги с таким id не существует\n");
        }
    }

    private void deleteBook() {
        viewAllBooks();
        System.out.println("Введите id удаляемой книги из списка");
        Book book = Facade.getBookDAO().getById(scanLineForInt());
        if (book != null) {
            System.out.println("Вы уверены что хотите удалить книгу? Введите Yes/No");
            if (scanLineForAccept()) {
                Facade.getBookDAO().delete(book);
                System.out.println("Книга удалена:");
                System.out.println(book.toString());
            } else {
                System.out.println("Операция удаления книги отменена");
            }
            System.out.println();
        } else {
            System.out.println("Книги с таким id не существует\n");
        }
    }

    private void viewAllBooks() {
        books = Facade.getBookDAO().select();
        System.out.println("Список всех книг");
        for (Book b : books) {
            System.out.println(b.toString());
        }
        System.out.println();
    }

    private void viewAllBooksAndThatAuthors() {
        books = Facade.getBookDAO().select();
        System.out.println("Список всех книг и их авторов");
        StringBuilder booksAndAuthors = new StringBuilder();
        Author author;
        for (Book b : books) {
            booksAndAuthors.append(b.getName());
            author = Facade.getAuthorDAO().getById(b.getAuthor_id());
            if (author == null) {
                booksAndAuthors.append(".\tНеизвестный автор");
            } else {
                booksAndAuthors.append(".\t");
                booksAndAuthors.append(author.getFirstName());
                booksAndAuthors.append(" ");
                booksAndAuthors.append(author.getMiddleName());
                booksAndAuthors.append(" ");
                booksAndAuthors.append(author.getSecondName());
            }
            booksAndAuthors.append("\n");
        }
        System.out.println(booksAndAuthors);
    }

    private String scanLine(boolean enabledBlank) {
        String line;
        while (true) {
            if (scanner.hasNextLine()) {
                line = scanner.nextLine().trim();
                if (line.equalsIgnoreCase("EXIT")) {
                    exit();
                } else if (!line.isEmpty() || enabledBlank) {
                    break;
                }
            }
        }
        return line;
    }

    private Boolean scanLineForAccept() {
        String line;
        Boolean accept;
        while (true) {
            if (scanner.hasNextLine()) {
                line = scanner.nextLine().trim();
                if (line.equalsIgnoreCase("EXIT")) {
                    exit();
                } else if (line.equalsIgnoreCase("YES")) {
                    accept = true;
                    break;
                } else if (line.equalsIgnoreCase("NO")) {
                    accept = false;
                    break;
                }
            }
        }
        return accept;
    }

    private Integer scanLineForInt() {
        String line;
        Integer key;
        while (true) {
            if (scanner.hasNextLine()) {
                line = scanner.nextLine().trim();
                if (line.equalsIgnoreCase("EXIT")) {
                    exit();
                } else if (!line.isEmpty()) {
                    try {
                        key = Integer.parseInt(line);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Нужно ввести число");
                    }
                }
            }
        }
        return key;
    }

    private int getMaxAuthorId(Collection<Author> entity) {
        int maxId = 0;
        for (Author a : entity) {
            if (maxId < a.getId()) maxId = a.getId();
        }
        return maxId;
    }

    private int getMaxBookId(Collection<Book> entity) {
        int maxId = 0;
        for (Book a : entity) {
            if (maxId < a.getId()) maxId = a.getId();
        }
        return maxId;
    }
}
