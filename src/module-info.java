

module ResourceMonitor {
    requires javafx.controls;
    requires javafx.graphics;
    requires java.sql;
    requires javafx.fxml;
    requires java.sql.rowset;

    opens ResourceMonitor.Controllers   ;
    exports ResourceMonitor.Controllers;
    exports ResourceMonitor.Models;
    exports ResourceMonitor.Utilities;
    exports ResourceMonitor;
}