package org.openjfx.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

import org.openjfx.table.User;
import org.openjfx.util.DBConnection;

public class UserDAO implements DAO<User, Integer> {

    @Override
    public void add(User entity) throws SQLException {
        String sql = "INSERT INTO Bibblo.låntagare (anvandarnamn, losenord, epost) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, entity.getUsername());
            ps.setString(2, entity.getPassword());
            ps.setString(3, entity.getEmail());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(User entity) throws SQLException {
        String sql = "UPDATE Bibblo.låntagare SET anvandarnamn = ?, losenord = ?, epost = ? WHERE lantagareId = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, entity.getUsername());
            ps.setString(2, entity.getPassword());
            ps.setString(3, entity.getEmail());
            ps.setInt(4, entity.getLoanUserId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Integer key) throws SQLException {
        String sql = "DELETE FROM Bibblo.låntagare WHERE lantagareId = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, key);
            ps.executeUpdate();
        }
    }

    @Override
    public User get(Integer key) throws SQLException {
        String sql = "SELECT * FROM Bibblo.låntagare WHERE lantagarId = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, key);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("lantagarId"),
                        rs.getInt("anvadrtypId"),
                        rs.getString("fNamn"),
                        rs.getString("eNamn"),
                        rs.getString("epost"),
                        rs.getString("anvandartyp"),
                        rs.getString("anvandarnamn"),
                        rs.getString("losenord")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<User> getAll() throws SQLException {
        String sql = "SELECT * FROM Bibblo.låntagare";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            List<User> users = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) { 
                while (rs.next()) {
                    users.add(new User(
                        rs.getInt("lantagarId"),
                        rs.getInt("anvandartypid"),
                        rs.getString("fnamn"),
                        rs.getString("enamn"),
                        rs.getString("epost"),
                        rs.getString("anvandarTypNamn"),
                        rs.getString("anvandarnamn"),
                        rs.getString("losenord")
                    ));
                }
            }
            return users;
        }
    }

    public User findByUsername(String username) throws SQLException {
        String sql = "SELECT lantagarId, anvandartypid, fnamn, enamn, epost, anvandarTypNamn, anvandarnamn, losenord " +
                     "FROM Bibblo.Låntagare WHERE anvandarnamn = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("lantagarId"),
                        rs.getInt("anvandartypId"),
                        rs.getString("fnamn"),
                        rs.getString("enamn"),
                        rs.getString("epost"),
                        rs.getString("anvandarTypNamn"),
                        rs.getString("anvandarnamn"),
                        rs.getString("losenord")
                    );
                }
            }
        }
        return null;
    }
    
}
