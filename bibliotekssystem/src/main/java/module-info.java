module org.openjfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires java.sql;
    requires com.zaxxer.hikari;
    requires org.slf4j;

    opens org.openjfx to javafx.fxml;
    exports org.openjfx;

    opens org.openjfx.controller to javafx.fxml;
    exports org.openjfx.controller;

    opens org.openjfx.table to javafx.fxml;
    exports org.openjfx.table;
}
