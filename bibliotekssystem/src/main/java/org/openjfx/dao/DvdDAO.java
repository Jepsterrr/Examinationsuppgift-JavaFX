package org.openjfx.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.openjfx.table.DVD;
import org.openjfx.util.DBConnection;

public class DvdDAO implements MediaItemDAO<DVD> {

    @Override
    public void add(DVD d) throws SQLException {
        String sql = "INSERT INTO Bibblo.Titel(titelId, lanetypId, titel) VALUES (?, ?, ?);\n"
                   + "INSERT INTO Bibblo.DVD(titelId, antalMin) VALUES (?, ?)";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, d.getTitleId());
            ps.setInt(2, d.getLoanTypeId());
            ps.setString(3, d.getTitle());
            ps.setInt(4, d.getTitleId());
            ps.setInt(5, d.getDurationMinutes());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(DVD d) throws SQLException {
        String sql = "UPDATE Bibblo.Titel SET titel = ? WHERE titelId = ?;\n"
                   + "UPDATE Bibblo.DVD SET antalMin = ? WHERE titelId = ?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, d.getTitle());
            ps.setInt(2, d.getTitleId());
            ps.setInt(3, d.getDurationMinutes());
            ps.setInt(4, d.getTitleId());
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
    public DVD get(int id) throws SQLException {
        String sql = "SELECT t.titelId, t.titel, t.lanetypId, t.antalExemplar, d.antalMin, COALESCE(string_agg(k.fNamn || ' ' || k.eNamn, ', ' ORDER BY k.eNamn), '') AS kreatorer\n"
                   + "FROM Bibblo.Titel t\n"
                   + "JOIN Bibblo.DVD d ON d.titelId = t.titelId\n"
                   + "WHERE t.titelId = ?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new DVD(
                        rs.getInt("titelId"),
                        rs.getString("titel"),
                        rs.getInt("lanetypId"),
                        rs.getInt("antalExemplar"),
                        rs.getString("kreatorer"),
                        rs.getInt("antalMin")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<DVD> getAll() throws SQLException {
        String sql = "SELECT t.titelId, t.titel, t.lanetypId, t.antalExemplar, d.antalMin, COALESCE(string_agg(k.fNamn || ' ' || k.eNamn, ', ' ORDER BY k.eNamn), '') AS kreatorer\n"
                   + "FROM Bibblo.Titel t\n"
                   + "JOIN Bibblo.DVD d ON d.titelId = t.titelId\n"
                   + "LEFT JOIN Bibblo.Kreatörskap ks ON ks.titelId = t.titelId\n"
                   + "LEFT JOIN Bibblo.Kreatör k ON k.personID = ks.personID\n"
                   + "GROUP BY t.titelId, t.titel, t.lanetypId, t.antalExemplar, d.antalMin";
        List<DVD> list = new ArrayList<>();
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DVD DVD = new DVD(
                        rs.getInt("titelId"),
                        rs.getString("titel"),
                        rs.getInt("lanetypId"),
                        rs.getInt("antalExemplar"),
                        rs.getString("kreatorer"),
                        rs.getInt("antalMin")
                    );
                    DVD.setCreatorNames(rs.getString("kreatorer"));
                    list.add(DVD);
                }
            }
        }
        return list;
    }

    @Override
    public List<DVD> searchByTerm(String term) throws SQLException {
        String sql = "SELECT t.titelId, t.titel, t.lanetypId, t.antalExemplar, d.antalMin, COALESCE(string_agg(k.fNamn || ' ' || k.eNamn, ', ' ORDER BY k.eNamn), '') AS kreatorer\n"
                    + "FROM Bibblo.Titel t\n"
                    + "JOIN Bibblo.DVD d ON d.titelId = t.titelId\n"
                    + "LEFT JOIN Bibblo.Kreatörskap ks ON ks.titelId = t.titelId\n"
                    + "LEFT JOIN Bibblo.Kreatör k ON k.personID = ks.personID\n"
                    + "LEFT JOIN Bibblo.TitelNyckelord tk ON tk.titelId = t.titelId\n"
                    + "LEFT JOIN Bibblo.Nyckelord nk ON nk.nyckelordId = tk.nyckelordId\n"
                    + "WHERE t.titel ILIKE ?\n"
                    + "OR k.fNamn ILIKE ?\n"
                    + "OR k.eNamn ILIKE ?\n"
                    + "OR nk.nyckelord ILIKE ?\n"
                    + "GROUP BY t.titelId, t.titel, t.lanetypId, t.antalExemplar, d.antalMin";
        List<DVD> list = new ArrayList<>();
        String pattern = "%" + term + "%";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            ps.setString(3, pattern);
            ps.setString(4, pattern);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DVD DVD = new DVD(
                        rs.getInt("titelId"),
                        rs.getString("titel"),
                        rs.getInt("lanetypId"),
                        rs.getInt("antalExemplar"),
                        rs.getString("kreatorer"),
                        rs.getInt("antalMin")
                    );
                    DVD.setCreatorNames(rs.getString("kreatorer"));
                    list.add(DVD);
                }
            }
        }
        return list;
    }
}