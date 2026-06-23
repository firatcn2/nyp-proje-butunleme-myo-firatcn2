import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Arayüz dosyasını yükler
        Parent root = FXMLLoader.load(getClass().getResource("arayuz.fxml"));
        primaryStage.setTitle("Cezaevi Yönetim Sistemi");
        primaryStage.setScene(new Scene(root, 850, 500));

        // Hata veren internet logosu kaldırıldı, sistem artık sorunsuz açılacak.

        // Pencereyi ekranda gösterir
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}