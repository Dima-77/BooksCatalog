package org.java3.lesson2;

/**
 * Created by Dima on 08.10.2016.
 */
public class Facade {
    private static AuthorDAO authorDAO = null;
    private static BookDAO bookDAO = null;

    public  static AuthorDAO getAuthorDAO (){
        if (authorDAO == null){
            authorDAO = new AuthorDAO();
        }
        return authorDAO;
    }

    public  static BookDAO getBookDAO (){
        if (bookDAO == null){
            bookDAO = new BookDAO();
        }
        return bookDAO;
    }
}
