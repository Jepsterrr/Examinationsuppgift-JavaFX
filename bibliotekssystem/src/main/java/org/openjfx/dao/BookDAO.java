package org.openjfx.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.openjfx.table.Book;
import org.openjfx.util.DBConnection;

public class BookDAO implements DAO<Book, Integer> {
    @Override
    public void add(Book book) throws SQLException {
        // Implementera logik för att lägga till en bok i databasen
        String sql = "INSERT INTO Bibblo.Bok(titelId, ISBN, antalSidor, bokutgivareId) VALUES (?,?,?,?)";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, book.getTitleId());
            ps.setString(2, book.getIsbn());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Book book) {
        // Implementera logik för att uppdatera en bok i databasen
    }

    @Override
    public void delete(Integer id) {
        // Implementera logik för att ta bort en bok från databasen
    }

    @Override
    public Book get(Integer id) {
        // Implementera logik för att hämta en bok från databasen
        return null;
    }

    @Override
    public List<Book> getAll() {
        // Implementera logik för att hämta alla böcker från databasen
        return null;
    }
}
