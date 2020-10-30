package ResourceMonitor;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass()
                .getResource("Views/averageUsageView.fxml"));
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        stage.setScene(scene);
        stage.setTitle("System Resource Overview");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
