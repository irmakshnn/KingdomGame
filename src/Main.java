//Dicle Irmak Şahin-2421021029

public class Main {

    public static void main(String[] args) {

        // Krallık nesnesi oluşturulur.(oyunun ana yöneticisi)
        Krallik krallik = new Krallik();

        //Oyun başlangıcında kullanılacak şehirler oluşturulur
        //Sehir[] dizisi krallığın yöneteceği şehir listesidir.
        Sehir[] sehirler = new Sehir[3];
        // Şehir nesneleri oluşturulup diziye yerleştirilir
        //Not: Alt sınıflar (TicaretSehri, TarimSehri, SanayiSehri) Sehir'den kalıtım aldığı için sehir tipindeki dizi içinde tutulabilir (polimorfizm).
        sehirler[0] = new TicaretSehri("Istanbul", 200, 21, 20, 22, 70);
        sehirler[1] = new TarimSehri("Manisa", 150, 16, 12, 16, 65);
        sehirler[2] = new SanayiSehri("Izmir", 100, 9, 16, 18, 60);

        //Şehir dizisi krallığa aktarılır
        //Krallik sınıfı şehirlerle ilgili işlemleri bu dizi üzerinden yapar.
        krallik.setSehirler(sehirler);
        krallik.oyunuBaslat();
             //Oyun döngüsü:
        //Oyun bitiş koşulları sağlanmadığı sürece (kaynaklar tükenmedi / tur limiti dolmadı vb.)turuOyna() metodu çalışır ve kullanıcı menüden seçim yapar.
        while (!krallik.oyunBittiMi()) {
            // Tur başı / mevcut durumu basan metot 
            krallik.turuOyna();

        }
    }
}
