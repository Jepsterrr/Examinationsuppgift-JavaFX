package org.openjfx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.openjfx.util.DBConnection;

public class LoanTypeDAO {
     public String getTypeFromTitle(int titelId) throws SQLException {
        String typnamn = null; // Variabel för att hålla resultatet

        // SQL-fråga som joinar Titel-tabellen (alias 't') med Lånetyp-tabellen (alias 'lt')
        // och väljer 'typnamn' från Lånetyp-tabellen.
        String sql = "SELECT lt.typnamn " +
                    "FROM Bibblo.titel t " +
                    "JOIN Bibblo.lånetyp lt ON t.lanetypId = lt.lånetypId " + // JOIN-villkoret
                    "WHERE t.titelId = ?";                                  // Filtrera på titelId

        try (Connection conn = DBConnection.getConnection(); // Try-with-resources för anslutningen
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, titelId); // Sätt parametern för titelId

            try (ResultSet rs = ps.executeQuery()) { // Try-with-resources för ResultSet
                if (rs.next()) {
                    // Hämta värdet från kolumnen "typnamn"
                    typnamn = rs.getString("typnamn");
                }
            }
        }
        // Om inget hittades (rs.next() var false), returneras null som initialiserat.
        return typnamn;
    }

    public int getLoanTime(int lanetypId) throws SQLException {
        int loantime = 0;
        String sql = "SELECT lanetid FROM Bibblo.lånetyp WHERE lånetypId = ?";
        return loantime;
    }

    
}
