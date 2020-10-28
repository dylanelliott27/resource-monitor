

module ResourceMonitor {
    requires javafx.controls;
    requires javafx.graphics;
    requires java.sql;
    requires javafx.fxml;

    opens ResourceMonitor.Controllers   ;
    exports ResourceMonitor.Controllers;
    exports ResourceMonitor;
}