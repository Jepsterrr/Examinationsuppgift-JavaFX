package org.openjfx.dao;

import java.sql.SQLException;
import java.util.List;

// Generellt DAO-gränssnitt för CRUD (Create, Read, Update, Delete)
public interface DAO<E, K> {
    void add(E entity) throws SQLException;
    void update(E entity) throws SQLException;
    void delete(K key) throws SQLException;
    E get(K key) throws SQLException;
    List<E> getAll() throws SQLException;
}

// Används t.ex. public class BookDAO implements DAO<Book, Integer>
// add(Book book) INSERT INTO Bibblo.Bok och allt.. 
// update(Book book) UPDATE Bibblo.Bok SET och allt..
// delete(Integer id) DELETE FROM Bibblo.Bok WHERE titelId = ?
// Book get(Integer id) typ SELECT * FROM Bibblo.Bok WHERE titelId = ?
// List<Book> getAll() typ SELECT * FROM Bibblo.Bok

// Varje DAO-klass som t.ex. BookDAO behöver sedan en modellklass som Book
// Book klassen är en representant av en bok i systemet och innehåller alla fält som finns i databasen

