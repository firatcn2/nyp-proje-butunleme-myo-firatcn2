import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CezaeviYonetimi {
    private ObservableList<Mahkum> mahkumListesi = FXCollections.observableArrayList();

    public void mahkumEkle(Mahkum m) {
        mahkumListesi.add(m);
    }

    public void mahkumSil(String id) {
        mahkumListesi.removeIf(m -> m.getId().equals(id));
    }

    public ObservableList<Mahkum> getMahkumListesi() {
        return mahkumListesi;
    }
}