package es.jlarriba.astounding;

import es.jlarriba.jrmapi.model.Document;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainController {

    private Remarkable remarkable;
    private static final Logger LOGGER = LogManager.getLogger();

    @FXML
    private FlowPane collections;

    @FXML
    private FlowPane documents;

    @FXML
    public void initialize() throws IOException {
        renderScreen("");
    }

    @FXML
    private void uploadDocument(MouseEvent e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file to upload");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Documents", "*.pdf", "*.epub"));
        Button button = (Button) e.getSource();
        File selectedFile = fileChooser.showOpenDialog(button.getScene().getWindow());
        String parentId = "";
        if (selectedFile != null) {
            remarkable.getApi().uploadDoc(selectedFile, parentId);
            collections.getChildren().clear();
            documents.getChildren().clear();
            renderScreen(parentId);
        }
    }

    @FXML
    private void reload() {
        collections.getChildren().clear();
        documents.getChildren().clear();
        renderScreen("");
    }

    private void buttonDoubleClick(MouseEvent e) {
        if (e.getClickCount() == 2) {
            VBox button = (VBox) e.getSource();
            String docId = button.getParent().getId();

            Document doc = remarkable.getDocById(docId);

            if (doc.getType().equals("CollectionType")) {
                collections.getChildren().clear();
                documents.getChildren().clear();
                renderScreen(docId);
            } else {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Choose download location");
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF files", "*.pdf"));
                File selectedFile = fileChooser.showSaveDialog(button.getScene().getWindow());
                fileChooser.setInitialFileName(doc.getVissibleName());
                if (selectedFile != null) {
                    remarkable.getApi().exportPdf(doc, selectedFile.getParent(), selectedFile.getName());
                }
            }
        }
    }

    private void renderScreen(String dirId) {
        remarkable = Remarkable.getInstance();
        List<Document> colls = remarkable.loadCollectionsByDirectory(dirId);
        List<Document> docs = remarkable.loadEbooksByDirectory(dirId);
        if (!dirId.isEmpty()) {
            collections.getChildren().add(renderBack(dirId));
        }
        for (Document coll:colls) {
            collections.getChildren().add(renderDocument(coll));
        }
        for (Document doc:docs) {
            documents.getChildren().add(renderDocument(doc));
        }
    }

    private VBox renderDocument(Document doc) {
        try {
            VBox vbox = FXMLLoader.load(getClass().getResource("item.fxml"));
            vbox.setId(doc.getID());
            VBox button = (VBox) vbox.getChildren().get(0);

            FontIcon buttonIcon = new FontIcon();
            if (doc.getType().equals("CollectionType")) {
                buttonIcon.setIconLiteral("mdi-folder-outline");
            } else {
                buttonIcon.setIconLiteral("mdi-file-outline");
            }
            buttonIcon.setIconSize(48);
            Label icon = (Label) button.getChildren().get(0);
            icon.setGraphic(buttonIcon);
            Label text = (Label) button.getChildren().get(1);
            text.setText(doc.getVissibleName());
            button.setOnMouseClicked((e) -> {
                buttonDoubleClick(e);
            });
            /*Label label = (Label) vbox.getChildren().get(1);
            label.setOnMouseClicked((e) -> {
                showContextMenu(e, label);
            });*/
            return vbox;
        } catch (IOException e) {
            LOGGER.error("Error reading FXML", e);
        }
        return new VBox();
    }

    private VBox renderBack(String dirId) {
        try {
            VBox vbox = FXMLLoader.load(getClass().getResource("item.fxml"));
            VBox button = (VBox) vbox.getChildren().get(0);

            FontIcon buttonIcon = new FontIcon();
            buttonIcon.setIconLiteral("mdi-skip-previous");
            buttonIcon.setIconSize(48);
            Label icon = (Label) button.getChildren().get(0);
            icon.setGraphic(buttonIcon);
            Label text = (Label) button.getChildren().get(1);
            text.setText("Back");
            button.setOnMouseClicked((e) -> {
                collections.getChildren().clear();
                documents.getChildren().clear();
                renderScreen(remarkable.getDocById(dirId).getParent());
            });
            Label label = (Label) vbox.getChildren().get(1);
            label.setOnMouseClicked((e) -> {
                showContextMenu(e, label);
            });
            return vbox;
        } catch (IOException e) {
            LOGGER.error("Error reading FXML", e);
        }
        return new VBox();
    }

    private void showContextMenu(MouseEvent e, Label label) {
        ContextMenu cm = new ContextMenu();
        MenuItem copy = new MenuItem("Copy");
        cm.getItems().add(copy);
        MenuItem move = new MenuItem("Move");
        cm.getItems().add(move);
        MenuItem rename = new MenuItem("Rename");
        cm.getItems().add(rename);
        MenuItem delete = new MenuItem("Delete");
        cm.getItems().add(delete);
        MenuItem newFolder = new MenuItem("New Folder");
        cm.getItems().add(newFolder);
        MenuItem close = new MenuItem("Close");
        cm.getItems().add(close);
        cm.show(label, e.getScreenX(), e.getScreenY());
    }
}
