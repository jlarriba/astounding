module es.jlarriba.astounding {
    requires es.jlarriba.jrmapi;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.materialdesign;

    exports es.jlarriba.astounding;

    opens es.jlarriba.astounding to javafx.fxml;
}