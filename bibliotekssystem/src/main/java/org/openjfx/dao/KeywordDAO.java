package org.openjfx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.openjfx.util.DBConnection;

public class KeywordDAO {
    public List<String> getAllKeywords() throws SQLException {
        String sql = "SELECT DISTINCT nk.nyckelord\n"
                   + "FROM Bibblo.Nyckelord nk\n"
                   + "JOIN Bibblo.TitelNyckelord tk ON tk.nyckelordId = nk.nyckelordId\n"
                   + "ORDER BY nk.nyckelord";
        List<String> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getString("nyckelord"));
            }
        }
        return list;
    }
}
