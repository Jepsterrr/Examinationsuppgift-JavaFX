package org.openjfx.dao;

import org.openjfx.table.Book; // Importer behövs antagligen för andra metoder
import org.openjfx.table.MediaItem;
import org.openjfx.util.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList; // Antagligen behövs för getAll
import java.util.List;

public class MediaItemDAO implements DAO<MediaItem, Integer> {
    @Override
    public void add(MediaItem item) {
        // Implementera denna metod
    }

    @Override
    public void update(MediaItem item) {
        // Implementera denna metod
    }

    @Override
    public void delete(Integer id) {
        // Implementera denna metod
    }

    @Override
    public MediaItem get(Integer id) {
        // Implementera denna metod
        return null;
    }

    @Override
    public List<MediaItem> getAll() {
        // Implementera denna metod
        return null;
    }

    public static String getTitleById(int id) {
        String sql = "SELECT titel FROM Bibblo.titel WHERE titelId = ?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("titel");
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            // Här kan du logga felet istället för att bara skriva ut det
            e.printStackTrace();
            return null; // Returnera null eller kasta ett eget undantag vid fel
        }
    }

    public static int getIdByTitle(String title) {
        String sql = "SELECT titelId FROM Bibblo.titel WHERE LOWER(titel) LIKE ?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, "%" + title.toLowerCase() + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("titelId");
                } else {
                    return -1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
} 