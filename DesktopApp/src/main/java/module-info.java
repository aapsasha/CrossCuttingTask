module org.aapsasha.desktopapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.jfr;
    requires aquafx;
    requires org.aapsasha.businessLogic;
    requires java.xml;

    opens org.aapsasha.desktopapp to javafx.fxml;
    exports org.aapsasha.desktopapp;
}