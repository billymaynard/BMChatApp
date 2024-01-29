module org.project.bmchatapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires Message;


    opens org.project.bmchatapp to javafx.fxml;
    exports org.project.bmchatapp;
}