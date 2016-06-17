package app;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.concurrent.Task;
import model.StockSymbol;
import service.StockService;

public class JavaFXStockApplication extends Application {

	private StockService stockService = new StockService();

	private TableView<StockSymbol> stockSymbolTable = new TableView<StockSymbol>();

	@Override
	public void start(Stage primaryStage) throws Exception {
		Button btn = new Button();
		btn.setText("Refresh");
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				updateTable();
			}

		});

		StackPane root = new StackPane();
		root.getChildren().add(btn);

		TableColumn symbolCol = new TableColumn("Symbol");
		symbolCol.setMinWidth(150);
		symbolCol.setCellValueFactory(new PropertyValueFactory<StockSymbol, String>("symbol"));

		TableColumn priceCol = new TableColumn("Price");
		priceCol.setMinWidth(100);
		priceCol.setCellValueFactory(new PropertyValueFactory<StockSymbol, String>("price"));

		TableColumn priceChange = new TableColumn("Price Change");
		priceChange.setMinWidth(100);
		priceChange.setCellValueFactory(new PropertyValueFactory<StockSymbol, String>("priceChange"));

		TableColumn changePerc = new TableColumn("Change %");
		changePerc.setMinWidth(100);
		changePerc.setCellValueFactory(new PropertyValueFactory<StockSymbol, String>("changePercentage"));

		stockSymbolTable.getColumns().addAll(symbolCol, priceCol, priceChange, changePerc);
		stockSymbolTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		stockSymbolTable.setItems(FXCollections.observableArrayList(stockService.getStockSymbols()));

		final VBox vbox = new VBox();
		vbox.setSpacing(2);
		vbox.setPrefWidth(450);
		vbox.setMinHeight(700);
		vbox.getChildren().addAll(stockSymbolTable, btn);

		Scene scene = new Scene(new Group());
		((Group) scene.getRoot()).getChildren().addAll(vbox);

		primaryStage.setScene(scene);
		primaryStage.setWidth(450);
		primaryStage.setHeight(460);
		primaryStage.setTitle("World Market");
		primaryStage.show();

		autoRefreshTable();
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	private void updateTable() {

		stockService.update();
		stockSymbolTable.getItems().clear();
		stockSymbolTable.getItems().addAll(stockService.getStockSymbols());
	}

	private void autoRefreshTable() {
		Task<Void> myUpdatingTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				updateTable();
				return null;

			}
		};

		// and then you run it like this:
		Thread hilo = new Thread(myUpdatingTask);
		hilo.setDaemon(true);
		hilo.start();
	}
}
