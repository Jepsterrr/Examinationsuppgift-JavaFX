package org.openjfx.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.openjfx.table.User;
import org.openjfx.util.DBConnection;

public class UserDAO implements DAO<User, Integer> {

    @Override
    public void add(User entity) throws SQLException {
        String sql = "INSERT INTO Bibblo.låntagare (anvandarnamn, losenord, epost) VALUES (?, ?, ?)";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, entity.getUsername());
            ps.setString(2, entity.getPassword());
            ps.setString(3, entity.getEmail());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(User entity) throws SQLException {
        String sql = "UPDATE Bibblo.låntagare SET anvandarnamn = ?, losenord = ?, epost = ? WHERE lantagareId = ?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
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
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, key);
            ps.executeUpdate();
        }
    }

    @Override
    public User get(Integer key) throws SQLException {
        String sql = "SELECT * FROM Bibblo.låntagare WHERE lantagareId = ?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, key);
            var rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("lantagareId"),
                    rs.getInt("userId"),
                    rs.getString("fNamn"),
                    rs.getString("eNamn"),
                    rs.getString("epost"),
                    rs.getString("anvandartyp"),
                    rs.getString("losenord")
                );
            }
        }
        return null;
    }

    @Override
    public List<User> getAll() throws SQLException {
        String sql = "SELECT * FROM Bibblo.låntagare";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            var rs = ps.executeQuery();
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                users.add(new User(
                    rs.getInt("lantagareId"),
                    rs.getInt("userId"),
                    rs.getString("fornamn"),
                    rs.getString("efternamn"),
                    rs.getString("epost"),
                    rs.getString("anvandartyp"),
                    rs.getString("losenord")
                ));
            }
            return users;
        }
    }
    
}
