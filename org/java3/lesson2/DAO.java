package org.java3.lesson2;

import java.util.Collection;

/**
 * Created by Dima on 07.10.2016.
 */
public interface DAO <E> {
    void create(String tableName);
    Collection <E> select();
    void insert(E obj);
    void update(E obj);
    void delete(E obj);
    E getById (Integer key);
}
