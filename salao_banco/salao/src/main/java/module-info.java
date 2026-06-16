module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;

    opens org.example to javafx.fxml;
    exports org.example;

    opens org.example.controller to javafx.fxml;
    exports org.example.controller;

    opens org.example.model to javafx.base;
    exports org.example.model;

    exports org.example.dao;
    exports org.example.util;
}