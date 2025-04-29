package org.openjfx.dao;

import org.openjfx.table.Copy;
import org.openjfx.util.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CopyDAO implements DAO<Copy, String> {

    @Override
    public void add(Copy e) throws SQLException {
        String sql = "INSERT INTO Bibblo.Copy(Streckkod, platsId, titelId, utlanad, Referenslitteratur) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, e.getStreckkod());
            ps.setInt(2, e.getPlatsId());
            ps.setInt(3, e.getTitelId());
            ps.setBoolean(4, e.isUtlanad());
            ps.setBoolean(5, e.isReferenslitteratur());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Copy e) throws SQLException {
        String sql = "UPDATE Bibblo.Copy SET platsId=?, titelId=?, utlanad=?, Referenslitteratur=? WHERE Streckkod=?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, e.getPlatsId());
            ps.setInt(2, e.getTitelId());
            ps.setBoolean(3, e.isUtlanad());
            ps.setBoolean(4, e.isReferenslitteratur());
            ps.setString(5, e.getStreckkod());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(String barcode) throws SQLException {
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(
             "DELETE FROM Bibblo.Copy WHERE Streckkod=?")) {
            ps.setString(1, barcode);
            ps.executeUpdate();
        }
    }

    @Override
    public Copy get(String barcode) throws SQLException {
        String sql = "SELECT * FROM Bibblo.Copy WHERE Streckkod=?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, barcode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Copy(
                      rs.getString("Streckkod"),
                      rs.getInt("platsId"),
                      rs.getInt("titelId"),
                      rs.getBoolean("utlanad"),
                      rs.getBoolean("Referenslitteratur")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Copy> getAll() throws SQLException {
        List<Copy> list = new ArrayList<>();
        try (ResultSet rs = DBConnection.getConnection()
                 .createStatement()
                 .executeQuery("SELECT * FROM Bibblo.Copy")) {
            while (rs.next()) {
                list.add(new Copy(
                  rs.getString("Streckkod"),
                  rs.getInt("platsId"),
                  rs.getInt("titelId"),
                  rs.getBoolean("utlanad"),
                  rs.getBoolean("Referenslitteratur")
                ));
            }
        }
        return list;
    }

    public List<Copy> findByTitleId(int titleId) throws SQLException {
        List<Copy> list = new ArrayList<>();
        String sql = "SELECT * FROM Bibblo.Copy WHERE titelId=?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, titleId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Copy(
                      rs.getString("Streckkod"),
                      rs.getInt("platsId"),
                      rs.getInt("titelId"),
                      rs.getBoolean("utlanad"),
                      rs.getBoolean("Referenslitteratur")
                    ));
                }
            }
        }
        return list;
    }
}