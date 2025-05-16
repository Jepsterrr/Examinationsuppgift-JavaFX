package org.openjfx.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.openjfx.table.Article;
import org.openjfx.util.DBConnection;

public class ArticleDAO implements MediaItemDAO<Article> {

    @Override
    public void add(Article a) throws SQLException {
        String sql = "INSERT INTO Bibblo.Titel(titelId, lanetypId, titel) VALUES (?, ?, ?);\n"
                   + "INSERT INTO Bibblo.Artikel(titelId, artikelSidor, tidsskrift) VALUES (?, ?, ?)";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, a.getTitleId());
            ps.setInt(2, a.getLoanTypeId());
            ps.setString(3, a.getTitle());
            ps.setInt(4, a.getTitleId());
            ps.setInt(5, a.getPages());
            ps.setString(6, a.getJournal());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Article a) throws SQLException {
        String sql = "UPDATE Bibblo.Titel SET titel = ? WHERE titelId = ?;\n"
                   + "UPDATE Bibblo.Artikel SET artikelSidor = ?, tidsskrift = ? WHERE titelId = ?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, a.getTitle());
            ps.setInt(2, a.getTitleId());
            ps.setInt(3, a.getPages());
            ps.setString(4, a.getJournal());
            ps.setInt(5, a.getTitleId());
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
    public Article get(int id) throws SQLException {
        String sql = "SELECT t.titelId, t.titel, t.lanetypId, t.antalExemplar, a.artikelSidor, a.tidsskrift\n"
                   + "FROM Bibblo.Titel t\n"
                   + "JOIN Bibblo.Artikel a ON a.titelId = t.titelId\n"
                   + "WHERE t.titelId = ?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Article(
                        rs.getInt("titelId"),
                        rs.getString("titel"),
                        rs.getInt("lanetypId"),
                        rs.getInt("antalExemplar"),
                        rs.getInt("artikelSidor"),
                        rs.getString("tidsskrift")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Article> getAll() throws SQLException {
        String sql = "SELECT t.titelId, t.titel, t.lanetypId, t.antalExemplar, a.artikelSidor, a.tidsskrift, COALESCE(string_agg(k.fNamn || ' ' || k.eNamn, ', ' ORDER BY k.eNamn), '') AS kreatorer\n"
                   + "FROM Bibblo.Titel t\n"
                   + "JOIN Bibblo.Artikel a ON a.titelId = t.titelId\n"
                   + "LEFT JOIN Bibblo.Kreatörskap ks ON ks.titelId = t.titelId\n"
                   + "LEFT JOIN Bibblo.Kreatör k ON k.personID = ks.personID\n"
                   + "GROUP BY t.titelId, t.titel, t.lanetypId, t.antalExemplar, a.artikelSidor, a.tidsskrift";
        List<Article> list = new ArrayList<>();
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Article article = new Article(
                        rs.getInt("titelId"),
                        rs.getString("titel"),
                        rs.getInt("lanetypId"),
                        rs.getInt("antalExemplar"),
                        rs.getInt("artikelSidor"),
                        rs.getString("tidsskrift")
                    );
                    article.setCreatorNames(rs.getString("kreatorer"));
                    list.add(article);
                }
            }
        }
        return list;
    }

    @Override
    public List<Article> searchByTerm(String term) throws SQLException {
        String sql = "SELECT t.titelId, t.titel, t.lanetypId, t.antalExemplar, a.artikelSidor, a.tidsskrift COALESCE(string_agg(k.fNamn || ' ' || k.eNamn, ', ' ORDER BY k.eNamn), '') AS kreatorer\n"
                    + "FROM Bibblo.Titel t\n"
                    + "JOIN Bibblo.Artikel a ON a.titelId = t.titelId\n"
                    + "LEFT JOIN Bibblo.Kreatörskap ks ON ks.titelId = t.titelId\n"
                    + "LEFT JOIN Bibblo.Kreatör k ON k.personID = ks.personID\n"
                    + "LEFT JOIN Bibblo.TitelNyckelord tk ON tk.titelId = t.titelId\n"
                    + "LEFT JOIN Bibblo.Nyckelord nk ON nk.nyckelordId = tk.nyckelordId\n"
                    + "WHERE t.titel ILIKE ?\n"
                    + "OR a.tidsskrift ILIKE ?\n"
                    + "OR k.fNamn ILIKE ?\n"
                    + "OR k.eNamn ILIKE ?\n"
                    + "OR nk.nyckelord ILIKE ?\n"
                    + "GROUP BY t.titelId, t.titel, t.lanetypId, t.antalExemplar, a.artikelSidor, a.tidsskrift";
        List<Article> list = new ArrayList<>();
        String pattern = "%" + term + "%";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            ps.setString(3, pattern);
            ps.setString(4, pattern);
            ps.setString(5, pattern);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Article article = new Article(
                        rs.getInt("titelId"),
                        rs.getString("titel"),
                        rs.getInt("lanetypId"),
                        rs.getInt("antalExemplar"),
                        rs.getInt("artikelSidor"),
                        rs.getString("tidsskrift")
                    );
                    article.setCreatorNames(rs.getString("kreatorer"));
                    list.add(article);
                }
            }
        }
        return list;
    }
}