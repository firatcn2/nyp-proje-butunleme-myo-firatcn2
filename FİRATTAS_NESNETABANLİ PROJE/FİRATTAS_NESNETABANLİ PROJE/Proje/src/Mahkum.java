public class Mahkum {
    private String id;
    private String adSoyad;
    private String suc;
    private int cezaSuresiYil;

    public Mahkum(String id, String adSoyad, String suc, int cezaSuresiYil) {
        this.id = id;
        this.adSoyad = adSoyad;
        this.suc = suc;
        this.cezaSuresiYil = cezaSuresiYil;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getAdSoyad() { return adSoyad; }
    public void setAdSoyad(String adSoyad) { this.adSoyad = adSoyad; }

    public String getSuc() { return suc; }
    public void setSuc(String suc) { this.suc = suc; }

    public int getCezaSuresiYil() { return cezaSuresiYil; }
    public void setCezaSuresiYil(int cezaSuresiYil) { this.cezaSuresiYil = cezaSuresiYil; }
}