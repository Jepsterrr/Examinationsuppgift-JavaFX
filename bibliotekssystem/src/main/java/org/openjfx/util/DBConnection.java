package org.openjfx.util; // Se till att detta är korrekt paket för din klass

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {

    // Ersätt dessa med dina faktiska anslutningsuppgifter.
    // I ett riktigt projekt, undvik att hårdkoda lösenord. Läs från konfigurationsfil eller miljövariabler.
    private static final String JDBC_URL = "jdbc:postgresql://aws-0-eu-north-1.pooler.supabase.com:5432/postgres"; // Ex: jdbc:postgresql://aws-0-eu-central-1.pooler.supabase.com:5432/postgres
    private static final String USERNAME = "postgres.uuswoanwqocqhppfqsfc"; // Ex: "postgres"
    private static final String PASSWORD = "Jesper1235!";

    private static HikariDataSource dataSource;

    // Statisk initialiseringsblock körs en gång när klassen laddas
    static {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(JDBC_URL);
            config.setUsername(USERNAME);
            config.setPassword(PASSWORD);

            // Viktiga pool-inställningar (anpassa efter din databasservers gränser, t.ex. Supabase)
            config.setMaximumPoolSize(2);  // Max antal fysiska anslutningar i poolen (börja lågt)
            config.setMinimumIdle(0);      // Minsta antal inaktiva anslutningar som hålls redo
            config.setConnectionTimeout(30000); // Max tid (ms) att vänta på en anslutning från poolen
            config.setIdleTimeout(600000);     // Max tid (ms) en anslutning får vara inaktiv i poolen
            config.setMaxLifetime(1800000);    // Max livstid (ms) för en anslutning i poolen

            // Rekommenderade inställningar för PostgreSQL-prestanda med prepared statements
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            // config.setLeakDetectionThreshold(15000); // För felsökning av anslutningsläckor (kan stängas av i produktion)

            dataSource = new HikariDataSource(config);
            System.out.println("HikariCP anslutningspool har initialiserats.");

        } catch (Exception e) {
            System.err.println("FATAL: Kunde inte skapa HikariCP anslutningspool!");
            e.printStackTrace();
            // Detta är ett kritiskt fel, applikationen kommer troligen inte fungera.
            throw new RuntimeException("Fel vid initialisering av databasanslutningspool.", e);
        }
    }

    /**
     * Hämtar en anslutning från anslutningspoolen.
     * Anslutningen MÅSTE stängas efter användning (helst med try-with-resources)
     * för att den ska kunna returneras till poolen.
     *
     * @return En Connection-instans från poolen.
     * @throws SQLException Om ett databasfel inträffar när en anslutning hämtas.
     */
    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            // Detta bör inte kunna hända om den statiska initialiseraren har körts korrekt.
            System.err.println("KRITISKT FEL: HikariDataSource (anslutningspoolen) är inte initialiserad!");
            throw new SQLException("Anslutningspoolen är inte tillgänglig.");
        }
        return dataSource.getConnection();
    }

    /**
     * Stänger ner anslutningspoolen. Bör anropas när applikationen avslutas
     * (t.ex. i App.stop()-metoden i din JavaFX-applikation).
     */
    public static void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            System.out.println("HikariCP anslutningspool har stängts.");
        }
    }
}