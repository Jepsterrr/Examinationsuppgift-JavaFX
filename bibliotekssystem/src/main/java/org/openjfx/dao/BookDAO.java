package org.openjfx.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.openjfx.table.Book;
import org.openjfx.util.DBConnection;

public class BookDAO implements MediaItemDAO<Book> {

    @Override
    public void add(Book b) throws SQLException {
        String sql = "INSERT INTO Bibblo.Titel(titelId, lanetypId, titel) VALUES (?, ?, ?);\n"
                   + "INSERT INTO Bibblo.Bok(titelId, antalSidor, ISBN, bokutgivareId) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, b.getTitleId());
            ps.setInt(2, b.getLoanTypeId());
            ps.setString(3, b.getTitle());
            ps.setInt(4, b.getTitleId());
            ps.setInt(5, b.getNumberOfPages());
            ps.setString(6, b.getIsbn());
            ps.setInt(7, b.getPublisherId());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Book b) throws SQLException {
        String sql = "UPDATE Bibblo.Titel SET titel = ? WHERE titelId = ?;\n"
                   + "UPDATE Bibblo.Bok SET antalSidor = ?, ISBN = ?, bokutgivareId = ? WHERE titelId = ?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, b.getTitle());
            ps.setInt(2, b.getTitleId());
            ps.setInt(3, b.getNumberOfPages());
            ps.setString(4, b.getIsbn());
            ps.setInt(5, b.getPublisherId());
            ps.setInt(6, b.getTitleId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Bibblo.Titel WHERE titelId = ?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Book get(int id) throws SQLException {
        String sql = "SELECT t.titelId, t.titel, t.lanetypId, t.antalExemplar,\n"
                   + "       b.antalSidor, b.ISBN, b.bokutgivareId\n"
                   + "FROM Bibblo.Titel t\n"
                   + "JOIN Bibblo.Bok b ON b.titelId = t.titelId\n"
                   + "WHERE t.titelId = ?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Book(
                        rs.getInt("titelId"),
                        rs.getString("titel"),
                        rs.getInt("lanetypId"),
                        rs.getInt("antalExemplar"),
                        rs.getString("ISBN"),
                        rs.getInt("antalSidor"),
                        rs.getInt("bokutgivareId")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Book> getAll() throws SQLException {
        String sql = "SELECT t.titelId, t.titel, t.lanetypId, t.antalExemplar, b.ISBN, b.antalSidor, b.bokutgivareId\n"
                   + "FROM Bibblo.Titel t\n"
                   + "JOIN Bibblo.Bok b ON b.titelId = t.titelId";
        List<Book> list = new ArrayList<>();
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Book(
                        rs.getInt("titelId"),
                        rs.getString("titel"),
                        rs.getInt("lanetypId"),
                        rs.getInt("antalExemplar"),
                        rs.getString("ISBN"),
                        rs.getInt("antalSidor"),
                        rs.getInt("bokutgivareId")
                    ));
                }
            }
        }
        return list;
    }

    @Override
    public List<Book> searchByTitle(String title) throws SQLException {
        String sql = "SELECT t.titelId, t.titel, t.lanetypId, t.antalExemplar,\n"
                   + "       b.antalSidor, b.ISBN, b.bokutgivareId\n"
                   + "FROM Bibblo.Titel t\n"
                   + "JOIN Bibblo.Bok b ON b.titelId = t.titelId\n"
                   + "WHERE t.titel ILIKE ?";
        List<Book> list = new ArrayList<>();
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, "%" + title.toLowerCase() + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Book(
                        rs.getInt("titelId"),
                        rs.getString("titel"),
                        rs.getInt("lanetypId"),
                        rs.getInt("antalExemplar"),
                        rs.getString("ISBN"),
                        rs.getInt("antalSidor"),
                        rs.getInt("bokutgivareId")
                    ));
                }
            }
        }
        return list;
    }
}