package es.jlarriba.astounding;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;
    
    @Override
    public void init() {
        Remarkable.getInstance().init();
    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("main"), 805, 600);
        scene.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
        stage.setTitle("asTounding for reMarkable");
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}