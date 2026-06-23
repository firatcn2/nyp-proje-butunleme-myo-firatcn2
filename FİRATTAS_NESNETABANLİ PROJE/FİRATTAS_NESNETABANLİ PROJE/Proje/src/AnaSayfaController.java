import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AnaSayfaController {
    @FXML private TextField txtId;
    @FXML private TextField txtAdSoyad;
    @FXML private TextField txtSuc;
    @FXML private TextField txtSure;

    @FXML private TableView<Mahkum> tabloMahkum;
    @FXML private TableColumn<Mahkum, String> colId;
    @FXML private TableColumn<Mahkum, String> colAdSoyad;
    @FXML private TableColumn<Mahkum, String> colSuc;
    @FXML private TableColumn<Mahkum, Integer> colSure;

    private CezaeviYonetimi yonetim = new CezaeviYonetimi();
    private int hedefEklemeIndeksi = -1;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAdSoyad.setCellValueFactory(new PropertyValueFactory<>("adSoyad"));
        colSuc.setCellValueFactory(new PropertyValueFactory<>("suc"));
        colSure.setCellValueFactory(new PropertyValueFactory<>("cezaSuresiYil"));

        // 1. UYGULAMA AÇILIRKEN VERİLERİ MYSQL VERİTABANINDAN ÇEKİYORUZ
        veritabanindanVeriCek();
    }

    // VERİTABANINDAN VERİLERİ ÇEKİP TABLOYA DOLDURAN FONKSİYON
    private void veritabanindanVeriCek() {
        yonetim.getMahkumListesi().clear(); // Listeyi temizle
        String sqlSorgusu = "SELECT * FROM mahkumlar";

        try (Connection baglanti = DatabaseHelper.getConnection();
             Statement statement = baglanti.createStatement();
             ResultSet sonucKumesi = statement.executeQuery(sqlSorgusu)) {

            while (sonucKumesi.next()) {
                String id = sonucKumesi.getString("id");
                String adSoyad = sonucKumesi.getString("ad_soyad");
                String suc = sonucKumesi.getString("suc");
                int sure = sonucKumesi.getInt("sure");

                // Veritabanından gelen satırları listeye ekle
                yonetim.mahkumEkle(new Mahkum(id, adSoyad, suc, sure));
            }
            tabloMahkum.setItems(yonetim.getMahkumListesi());

        } catch (Exception e) {
            System.out.println("Veri çekme hatası: " + e.getMessage());
        }
    }

    @FXML
    void tabloGenelTiklama(MouseEvent event) {
        Mahkum seciliMahkum = tabloMahkum.getSelectionModel().getSelectedItem();

        if (seciliMahkum != null) {
            txtId.setText(seciliMahkum.getId());
            txtAdSoyad.setText(seciliMahkum.getAdSoyad());
            txtSuc.setText(seciliMahkum.getSuc());
            txtSure.setText(String.valueOf(seciliMahkum.getCezaSuresiYil()));

            hedefEklemeIndeksi = tabloMahkum.getSelectionModel().getSelectedIndex();
        } else {
            double tiklananY = event.getY();
            double baslikHaricY = tiklananY - 28.0;

            if (baslikHaricY < 0) {
                hedefEklemeIndeksi = 0;
            } else {
                int hesaplananSatir = (int) (baslikHaricY / 24.0);

                if (hesaplananSatir > yonetim.getMahkumListesi().size()) {
                    hedefEklemeIndeksi = yonetim.getMahkumListesi().size();
                } else {
                    hedefEklemeIndeksi = hesaplananSatir;
                }
            }
            txtId.clear();
            txtAdSoyad.clear();
            txtSuc.clear();
            txtSure.clear();
        }
    }

    @FXML
    void mahkumEkle() {
        if (txtId.getText().isEmpty() || txtAdSoyad.getText().isEmpty() ||
                txtSuc.getText().isEmpty() || txtSure.getText().isEmpty()) {
            alarmKutusu("Hata", "Lütfen boş alan bırakmayın!", Alert.AlertType.ERROR);
            return;
        }

        try {
            String id = txtId.getText();
            String adSoyad = txtAdSoyad.getText();
            String suc = txtSuc.getText();
            int sure = Integer.parseInt(txtSure.getText());

            // 2. VERİYİ DOĞRUDAN MYSQL VERİTABANINA KAYDEDİYORUZ
            String sqlSorgusu = "INSERT INTO mahkumlar (id, ad_soyad, suc, sure) VALUES (?, ?, ?, ?)";

            try (Connection baglanti = DatabaseHelper.getConnection();
                 PreparedStatement statement = baglanti.prepareStatement(sqlSorgusu)) {

                statement.setString(1, id);
                statement.setString(2, adSoyad);
                statement.setString(3, suc);
                statement.setInt(4, sure);

                statement.executeUpdate(); // Veritabanına yaz
            }

            // Tabloyu veritabanındaki güncel verilerle yenile
            veritabanindanVeriCek();

            tabloMahkum.getSelectionModel().clearSelection();
            hedefEklemeIndeksi = -1;

            txtId.clear();
            txtAdSoyad.clear();
            txtSuc.clear();
            txtSure.clear();

            alarmKutusu("Başarılı", "Mahkum başarıyla veritabanına kaydedildi.", Alert.AlertType.INFORMATION);
        } catch (NumberFormatException e) {
            alarmKutusu("Hata", "Ceza süresi sayısal bir değer olmalıdır!", Alert.AlertType.ERROR);
        } catch (Exception e) {
            alarmKutusu("Veritabanı Hatası", "Aynı ID ile kayıt eklenemez veya bağlantı yok!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void mahkumSil() {
        Mahkum secili = tabloMahkum.getSelectionModel().getSelectedItem();

        if (secili != null) {
            // 3. SEÇİLİ MAHKUMU MYSQL VERİTABANINDAN SİLİYORUZ
            String sqlSorgusu = "DELETE FROM mahkumlar WHERE id = ?";

            try (Connection baglanti = DatabaseHelper.getConnection();
                 PreparedStatement statement = baglanti.prepareStatement(sqlSorgusu)) {

                statement.setString(1, secili.getId());
                statement.executeUpdate(); // Veritabanından sil

                alarmKutusu("Tahliye Edildi", secili.getAdSoyad() + " başarıyla veritabanından silindi.", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                System.out.println("Silme hatası: " + e.getMessage());
            }

            // Tabloyu veritabanındaki güncel verilerle yenile
            veritabanindanVeriCek();

            txtId.clear();
            txtAdSoyad.clear();
            txtSuc.clear();
            txtSure.clear();
            hedefEklemeIndeksi = -1;
        } else {
            alarmKutusu("Uyarı", "Lütfen silmek istediğiniz mahkumu seçin!", Alert.AlertType.WARNING);
        }
    }

    private void alarmKutusu(String baslik, String icerik, Alert.AlertType tip) {
        Alert alert = new Alert(tip);
        alert.setTitle(baslik);
        alert.setHeaderText(null);
        alert.setContentText(icerik);
        alert.showAndWait();
    }
}