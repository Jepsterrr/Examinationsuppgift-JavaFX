package org.openjfx.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.openjfx.table.Book;
import org.openjfx.util.DBConnection;

public class BookDAO implements DAO<Book, Integer> {

    @Override
    public void add(Book book) throws SQLException {
        String sql = "INSERT INTO Bibblo.bok(titelId, ISBN, antalSidor, bokutgivareId) VALUES (?,?,?,?)";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, book.getTitleId());
            ps.setString(2, book.getIsbn());
            ps.setInt(3, book.getNumberOfPages());
            ps.setInt(4, book.getPublisherId());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Book book) throws SQLException {
        String sql = "UPDATE Bibblo.bok SET titelId = ?, ISBN = ?, antalSidor = ?, bokutgivareId = ? WHERE titelId = ?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, book.getTitleId());
            ps.setString(2, book.getIsbn());
            ps.setInt(3, book.getNumberOfPages());
            ps.setInt(4, book.getPublisherId());
            ps.setInt(5, book.getTitleId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM Bibblo.bok WHERE titelId = ?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Book get(Integer id) throws SQLException {
        String sql = "SELECT * FROM Bibblo.bok WHERE titelId = ?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            var rs = ps.executeQuery();
            if (rs.next()) {
                return new Book(
                    rs.getInt("titelId"),
                    rs.getString("titel"),
                    rs.getString("ISBN"),
                    rs.getInt("antalSidor"),
                    rs.getInt("bokutgivareId"),
                    rs.getBoolean("onLoan")
                );
            }
        }
        return null;
    }

    @Override
    public List<Book> getAll() throws SQLException {
        String sql = "SELECT * FROM bibblo.Bok";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            var rs = ps.executeQuery();
            List<Book> books = new ArrayList<>();
            while (rs.next()) {
                books.add(new Book(
                    rs.getInt("titelId"),
                    rs.getString("titel"),
                    rs.getString("ISBN"),
                    rs.getInt("antalSidor"),
                    rs.getInt("bokutgivareId"),
                    rs.getBoolean("onLoan")
                ));
            }
            return books;
        }
    }
}
