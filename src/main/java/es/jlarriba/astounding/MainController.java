package es.jlarriba.astounding;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import es.jlarriba.jrmapi.model.Document;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;

public class MainController {
    
    private Remarkable remarkable;

    @FXML
    private TableView<Document> data;

    @FXML
    private TableColumn<Document, String> type;

    @FXML
    private TableColumn<Document, String> vissibleName;

    @FXML
    public void initialize() {
        remarkable = Remarkable.getInstance();
        
        data.setRowFactory(tv -> {
            TableRow<Document> row = new TableRow<>();
            
            if (row.getItem() != null) {
                if (row.getItem().getType().equals("CollectionType")) {
                    row.setStyle("-fx-background-color:lightgreen");
                }
                System.out.println("Type: " + row.getItem().getType());
            }
            return row ;
        });
        
        vissibleName.setCellValueFactory(new PropertyValueFactory<>("VissibleName"));   
        vissibleName.setCellFactory(tc -> {
            TableCell<Document, String> tableCell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(item);
                    TableRow<Document> row = getTableRow();
                    if (row.getItem() != null) {
                        if (row.getItem().getType().equals("CollectionType")) {
                            //this.setText("[d]" + item);
                            row.setStyle("-fx-background-color:lightgreen");
                        } else {
                            //this.setText("[f]" + item);
                            row.setStyle("-fx-background-color:lightcoral");
                        }
                    }
                }
            };

            return tableCell;
        });
        
        data.setItems(remarkable.loadEbooksByDirectory(""));
    }

    @FXML
    private void showContextMenu(ContextMenuEvent t) {
        ContextMenu cm = new ContextMenu();
        MenuItem mi1 = new MenuItem("Download");
        cm.getItems().add(mi1);
        MenuItem mi2 = new MenuItem("Menu 2");
        cm.getItems().add(mi2);
        cm.show(data, t.getScreenX(), t.getScreenY());
    }

    @FXML
    private void doubleClick(MouseEvent t) {
        if (t.getClickCount() == 2) {
            Document rowData = data.getSelectionModel().getSelectedItem();
            if (rowData.getType().equals("CollectionType")) {
                data.getItems().clear();
                data.setItems(remarkable.loadEbooksByDirectory(rowData.getID()));
            } else {

            }
            System.out.println("Double Clicked on: " + rowData.getVissibleName());
        }
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");

    }
}
