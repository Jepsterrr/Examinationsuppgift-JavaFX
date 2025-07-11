package org.openjfx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.openjfx.table.UserType;
import org.openjfx.util.DBConnection;

public class UserTypeDAO implements DAO<UserType, Integer> {

    @Override
    public void add(UserType ut) throws SQLException {
        String sql = "INSERT INTO Bibblo.Användartyp(anvandartypid, typNamn, MaxAntalLan) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ut.getAnvandarId());
            ps.setString(2, ut.getTypNamn());
            ps.setInt(3, ut.getMaxAntalLan());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(UserType ut) throws SQLException {
        String sql = "UPDATE Bibblo.Användartyp SET typNamn=?, MaxAntalLan=? WHERE anvandartypid=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ut.getTypNamn());
            ps.setInt(2, ut.getMaxAntalLan());
            ps.setInt(3, ut.getAnvandarId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM Bibblo.Användartyp WHERE anvandartypid=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public UserType get(Integer id) throws SQLException {
        String sql = "SELECT * FROM Bibblo.Användartyp WHERE anvandartypid=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new UserType(
                      rs.getInt("anvandartypid"),
                      rs.getString("typNamn"),
                      rs.getInt("MaxAntalLan")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<UserType> getAll() throws SQLException {
        List<UserType> list = new ArrayList<>();
        String sql = "SELECT * FROM Bibblo.Användartyp";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new UserType(
                    rs.getInt("anvandartypid"),
                    rs.getString("typNamn"),
                    rs.getInt("MaxAntalLan")
                ));
            }
        }
        return list;
    }

    public int getMaxLoans(int userTypeId) throws SQLException {
        UserType ut = get(userTypeId);
        return ut != null ? ut.getMaxAntalLan() : 0;
    }
}