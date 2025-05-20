package org.openjfx.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.openjfx.table.LoanItem;
import org.openjfx.util.DBConnection;

public class LoanItemDAO implements DAO<LoanItem, Integer> {

    @Override
    public void add(LoanItem li) throws SQLException {
        String sql = "INSERT INTO Bibblo.Låneföremål(lanId, Streckkod, returnerasSenast) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, li.getLanId());
            ps.setString(2, li.getStreckkod());
            ps.setDate(3, Date.valueOf(li.getDueDate()));
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    li.setLoanItemId(keys.getInt(1));
                }
            }
        }
    }

    @Override
    public void update(LoanItem li) throws SQLException {
        String sql = "UPDATE Bibblo.Låneföremål SET returneratDatum=?, returnerasSenast=? WHERE låneFöremålId=?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, li.getReturnedDate() != null ? Date.valueOf(li.getReturnedDate()) : null);
            ps.setDate(2, Date.valueOf(li.getDueDate()));
            ps.setInt(3, li.getLoanItemId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM Bibblo.Låneföremål WHERE låneFöremålId=?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public LoanItem get(Integer id) throws SQLException {
        String sql = "SELECT * FROM Bibblo.Låneföremål WHERE låneFöremålId=?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
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
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
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
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, lanId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(get(rs.getInt("låneFöremålId")));
                }
            }
        }
        return list;
    }

    public List<LoanItem> findByBarcode(String barcode) throws SQLException {
        List<LoanItem> list = new ArrayList<>();
        String sql = "SELECT låneFöremålId FROM Bibblo.Låneföremål WHERE Streckkod=?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, barcode);
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
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(get(rs.getInt("låneFöremålId")));
            }
        }
        return list;
    }
}