package org.openjfx.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Generiskt DAO för alla MediaItem-subklasser.
 */
public interface MediaItemDAO<T> {
    void add(T item) throws SQLException;
    void update(T item) throws SQLException;
    void delete(int id) throws SQLException;
    T get(int id) throws SQLException;
    List<T> getAll() throws SQLException;
    List<T> searchByTitle(String title) throws SQLException;
}
