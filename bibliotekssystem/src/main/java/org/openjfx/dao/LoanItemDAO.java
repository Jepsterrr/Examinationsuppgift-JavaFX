package org.openjfx.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.openjfx.table.LoanItem;
import org.openjfx.util.DBConnection;

public class LoanItemDAO implements DAO<LoanItem, Integer> {

    @Override
    public void add(LoanItem li) throws SQLException {
        String sql = "INSERT INTO Bibblo.Låneföremål(låneFöremålId, lanId, Streckkod) VALUES (?, ?, ?)";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, li.getLoanItemId());
            ps.setInt(2, li.getLanId());
            ps.setString(3, li.getStreckkod());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(LoanItem li) throws SQLException {
        String sql = "UPDATE Bibblo.Låneföremål SET returneratDatum=?, returnerasSenast=? WHERE låneFöremålId=?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setDate(1, li.getReturnedDate() != null ? Date.valueOf(li.getReturnedDate()) : null);
            ps.setDate(2, Date.valueOf(li.getDueDate()));
            ps.setInt(3, li.getLoanItemId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(
             "DELETE FROM Bibblo.Låneföremål WHERE låneFöremålId=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public LoanItem get(Integer id) throws SQLException {
        String sql = "SELECT * FROM Bibblo.Låneföremål WHERE låneFöremålId=?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new LoanItem(
                      rs.getInt("låneFöremålId"),
                      rs.getInt("lanId"),
                      rs.getString("Streckkod"),
                      rs.getDate("returneratDatum") != null ? rs.getDate("returneratDatum").toLocalDate() : null,
                      rs.getDate("returnerasSenast").toLocalDate()
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<LoanItem> getAll() throws SQLException {
        List<LoanItem> list = new ArrayList<>();
        String sql = "SELECT * FROM Bibblo.Låneföremål";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new LoanItem(
                    rs.getInt("låneFöremålId"),
                    rs.getInt("lanId"),
                    rs.getString("Streckkod"),
                    rs.getDate("returneratDatum")  != null ? rs.getDate("returneratDatum").toLocalDate()  : null,
                    rs.getDate("returnerasSenast").toLocalDate()
                ));
            }
        }
        return list;
    }

    public List<LoanItem> findByLoan(int lanId) throws SQLException {
        List<LoanItem> list = new ArrayList<>();
        String sql = "SELECT låneFöremålId FROM Bibblo.Låneföremål WHERE lanId=?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, lanId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(get(rs.getInt("låneFöremålId")));
                }
            }
        }
        return list;
    }

    public List<LoanItem> listOverdue() throws SQLException {
        List<LoanItem> list = new ArrayList<>();
        String sql = "SELECT låneFöremålId FROM Bibblo.Låneföremål WHERE returneratDatum IS NULL AND returnerasSenast < CURRENT_DATE";
        try (ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql)) {
            while (rs.next()) {
                list.add(get(rs.getInt("låneFöremålId")));
            }
        }
        return list;
    }
}