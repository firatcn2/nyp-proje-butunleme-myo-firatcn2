# Cezaevi Yönetim Sistemi - Nesne Tabanlı Programlama Projesi

Bu proje, Beykoz Üniversitesi Bilgisayar Programcılığı 1. Sınıf Nesne Tabanlı Programlama dersi dönemi ödevi olarak geliştirilmiştir.

# Öğrenci Bilgileri
- Adı Soyadı: FIRAT TAŞ
- Öğrenci Numarası: 2521310048
- Sınıfı / Şubesi: Bilgisayar Programcılığı 1. Sınıf (Pazartesi Grubu)

# Proje Hakkında
Proje, bir cezaevindeki mahkumların yönetimini simüle eden konsol tabanlı bir Java uygulamasıdır. 

# Kullanılan OOP Prensipleri ve Yapılar:
- Kapsülleme (Encapsulation): `Mahkum` sınıfındaki veriler `private` tutulmuş, erişim `Getter ve Setter` metotları ile sağlanmıştır.
- Yapıcı Metotlar (Constructor): Nesne üretilirken başlangıç parametreleri yapıcı metotla atanmıştır.
- Dinamik Bellek Yönetimi: Sabit diziler yerine esnek ve dinamik olan `ArrayList` veri yapısı tercih edilmiştir.
- Metot Ezme (@Override): Java'nın yerleşik `toString()` metodu ezilerek mahkum bilgileri özelleştirilmiştir.

# Nasıl Çalıştırılır?
1. Projeyi IntelliJ IDEA ile açın.
2. `src/Main.java` dosyasını çalıştırın (Run).
3. Konsoldaki menüyü (1-4) kullanarak mahkum ekleyebilir, listeleyebilir veya tahliye edebilirsiniz.