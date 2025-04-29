package org.openjfx.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.openjfx.table.Loan;
import org.openjfx.util.DBConnection;

public class LoanDAO implements DAO<Loan, Integer> {

    @Override
    public void add(Loan loan) throws SQLException {
        String sql = "INSERT INTO Bibblo.Lån(lantagarId, låneDatum) VALUES (?, ?)";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, loan.getLantagarId());
            ps.setDate(2, Date.valueOf(loan.getLoanDate()));
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    loan.setLanId(keys.getInt(1));
                }
            }
        }
    }

    @Override
    public void update(Loan loan) throws SQLException {
        String sql = "UPDATE Bibblo.Lån SET aterlamningsDatum=?, returDatum=? WHERE lanId=?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setDate(1, loan.getDueDate() != null ? Date.valueOf(loan.getDueDate()) : null);
            ps.setDate(2, loan.getReturnDate() != null ? Date.valueOf(loan.getReturnDate()) : null);
            ps.setInt(3, loan.getLanId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM Bibblo.Lån WHERE lanId=?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Loan get(Integer id) throws SQLException {
        String sql = "SELECT * FROM Bibblo.Lån WHERE lanId=?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Loan(
                      rs.getInt("lanId"),
                      rs.getInt("lantagarId"),
                      rs.getDate("låneDatum").toLocalDate(),
                      rs.getDate("aterlamningsDatum") != null ? rs.getDate("aterlamningsDatum").toLocalDate() : null,
                      rs.getDate("returDatum") != null ? rs.getDate("returDatum").toLocalDate() : null
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Loan> getAll() throws SQLException {
        List<Loan> list = new ArrayList<>();
        String sql = "SELECT * FROM Bibblo.Lån";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Loan(
                    rs.getInt("lanId"),
                    rs.getInt("lantagarId"),
                    rs.getDate("låneDatum").toLocalDate(),
                    rs.getDate("aterlamningsDatum") != null ? rs.getDate("aterlamningsDatum").toLocalDate() : null,
                    rs.getDate("returDatum")       != null ? rs.getDate("returDatum").toLocalDate()       : null
                ));
            }
        }
        return list;
    }

    public List<Loan> findByUser(int userId) throws SQLException {
        List<Loan> list = new ArrayList<>();
        String sql = "SELECT * FROM Bibblo.Lån WHERE lantagarId=?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(get(rs.getInt("lanId")));
                }
            }
        }
        return list;
    }
}