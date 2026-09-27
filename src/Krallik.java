//Dicle Irmak Şahin-2421021029

import java.util.Scanner;

public class Krallik {

    private Scanner input = new Scanner(System.in);
    private int altin;
    private int yiyecek;
    private int ordugucu;
    private int halkmemnuniyeti;
    private int guvenlikseviyesi;
    private Sehir[] sehirler;
    private int tursayisi;
    private double vergiorani;
    private Sehir vergisehri;//Verginin yapıldığı şehri tutuyor
    private double[] sehirVergioranlari;

    public Krallik() {

    }

    public void oyunuBaslat() {
        //Oyunun başlangıç değerleri
        this.tursayisi = 1;
        this.altin = 500;
        this.yiyecek = 500;
        this.ordugucu = 300;
        this.vergiorani = 1;/* Vergi oranı üretim hesabında çarpan olarak kullanıldığı için
    vergi girilmedi durumunda çarpanın etkisiz eleman olması gerekir.
    Bu yüzden başlangıçta vergiorani = 1 yapılır (uretim * 1 = uretim).*/

        // Oyun başlarken krallik halk memnuniyeti ve guvenlik seviyesi sehirlerin ortalamasi olarak ayarlanir.

        if (this.sehirler != null) {//Eğer null değilse çalışsın
            int toplamHalk = 0;
            int toplamGuvenlik = 0;
            int sehirSayisi = 0;

            for (int i = 0; i < this.sehirler.length; i++) {
                if (this.sehirler[i] == null) {
                    continue;
                }

                toplamHalk += this.sehirler[i].getHalkmemnuniyeti();
                toplamGuvenlik += this.sehirler[i].getGuvenlikseviyesi();
                sehirSayisi++;
            }

            if (sehirSayisi > 0) {
                this.halkmemnuniyeti = toplamHalk / sehirSayisi;//Döngüden çıkınca şehir sayısı 3 oldu
                this.guvenlikseviyesi = toplamGuvenlik / sehirSayisi;/*Halk memnuniyeti ve güvenlik seviyesinin ondalıklı olması saçma olacağı için int seçilerek 
                ortalama bir değer kullanılıdı.*/
            }
        }
        /* sehirler dizisi setSehirler ile oyundan önce verilmişse,(null kontrolü) ve
     her şehir için ayrı vergi oranı tutacağımız diziyi burada hazırlarız*/
        if (this.sehirler != null && this.sehirVergioranlari == null) {
            this.sehirVergioranlari = new double[this.sehirler.length];
            // vergi yoksa az yollasın” mantığın için 0.10
            for (int i = 0; i < this.sehirVergioranlari.length; i++) {
                this.sehirVergioranlari[i] = 1;/*Vergi oranını burada tuttuğumuz için ve şehirlerde vergi oranını çarpma işleminde kullandığı için vergi oranı istenmeyen
                bir şehirde vergi oranı çarpanı etkisiz eleman olsun diye 1 ile başlatıldı.Zaten halk memnuniyeti koşullarında(vergi oranına bağlı)  vergi oranı 1'e 
                eşit değilse if bloklarına gir diye bir komut ayarlandı*/

            }
        }

    }

    public void turuOyna() {

        System.out.println("-----Kralliginizin mevcut durumu-----");
        System.out.println("Tur: " + getTursayisi());
        System.out.println("Altin: " + getAltin());
        System.out.println("Yiyecek: " + getYiyecek());
        System.out.println("Ordu gucu:" + getOrdugucu());
        System.out.println("Halk memnuniyeti: " + getHalkmemnuniyeti());
        System.out.println("Guvenlik seviyesi: " + getGuvenlikseviyesi());

        System.out.println("--------------------------------------");

        System.out.println("---- Sehirlerin Ozet Durumu ----");

        for (int i = 0; i < getSehirler().length; i++) { //şehir bilgileri
            Sehir cagir = getSehirler()[i];

            System.out.println((i + 1) + "- " + "Ad: " + cagir.getAd() + "   Uretim Kapasitesi: " + cagir.getUretimKapasitesi() + "   Gelisim: " + cagir.getGelisimseviyesi()
                    + "   Ordu: " + cagir.getOrduseviyesi()
                    + "   Guvenlik: " + cagir.getGuvenlikseviyesi() + "  Halk memnuniyeti:" + cagir.getHalkmemnuniyeti());//Şehirler için başlangıç değerleri
        }

        int secim = 0;
        while (secim != 4) { //4 olursa döngü bitsin diye.

            System.out.println("----- Menu -----");
            System.out.println("1- Sehirleri goruntule");
            System.out.println("2- Sehre yatirim yap");
            System.out.println("3- Vergi oranini ayarla");
            System.out.println("4- Turu bitir");

            System.out.print("Lutfen seciminizi yapiniz(1-4): ");
            secim = input.nextInt();

            switch (secim) {
                case 1:
                    sehirleriGoruntule();
                    break;

                case 2:
                    sehireYatirimYap();
                    break;

                case 3:
                    vergiyiAyarla(getSehirler());//Parametre olarak Sehir sınıfından oluşan arrayin elemanları yollandı.
                    break;

                case 4:
                    System.out.println("Tur bitirildi.");//Turu bitirir
                    turSonuUretim();
                    this.tursayisi++;
                    break;

                default:
                    System.out.println("Gecersiz secim!");//4 den başka bir sayı girilirse diye.
            }
        }

    }

    public void sehirleriGoruntule() {

        for (int i = 0; i < getSehirler().length; i++) {//Şehirleri gezip hepsi için bilgiler gösterilir.
            System.out.println((i + 1) + ". sehir");
            getSehirler()[i].bilgiGoster();
            System.out.println("-----------------");
        }

    }

    public void sehireYatirimYap() {

        int altinmiktari;
        System.out.println("Yatirim yapmak istediginiz sehri seciniz(1-3):");
        for (int i = 0; i < getSehirler().length; i++) {
            System.out.println((i + 1) + "- " + getSehirler()[i].getAd());
        }
        int secim;
        Sehir secilenSehir;

        while (true) {//Döngüye koşulsuz girilir.
            System.out.println("Seciminizi giriniz:");
            secim = input.nextInt();

            if (secim < 1 || secim > getSehirler().length) { // Aralık kontrolü
                System.out.println("Gecersiz secim! Lutfen tekrar deneyin.");
                continue;
            }

            if (getSehirler()[secim - 1] == null) {// Null kontrolü
                System.out.println("Sehir bulunamadi. Lutfen tekrar secin.");
                continue;
            }

            // Buraya geldiysek seçim geçerli
            secilenSehir = getSehirler()[secim - 1];/*Şehir objesinin referansı secilenSehir değişkenine veriliyor(1 çıkartma sebebi secim'in 1'den başlaması
            ama arrayler 0'dan başlar*/
            break;
        }
        System.out.println("Yatirmak istediginiz altin miktarini giriniz:");
        altinmiktari = input.nextInt();
        if (altinmiktari <= this.altin) {
            secilenSehir.yatirimYap(altinmiktari);
            this.altin -= altinmiktari;
        } else {
            System.out.println("Yeterli altin yok");
        }
    }

    public void vergiyiAyarla(Sehir[] sehir) {/*Şehir seçme işleminin bu metodun içinde  yapılması isteniyor.Bu yüzden tek bir şehir yollamak yerine
        tüm şehirleri içeren Sehir[] arrayi parametre olarak gönderildi.Metod içerisinde kullanıcıdan şehir seçimi alınarak ilgili şehir belirlenir.*/
        int secim3;
        double vergiartismiktari;
        System.out.println("Vergiyi ayarlamak istediginiz sehri seciniz:");
        for (int i = 0; i < sehir.length; i++) {
            if (sehir[i] != null) {
                System.out.println((i + 1) + "- " + sehir[i].getAd());
            }
        }
        System.out.println("Seciminizi giriniz:");
        while (true) {//Kesin olarak döngüye girilsin diye direkt true ile başlatıldı.
            secim3 = input.nextInt();

            if (secim3 < 1 || secim3 > getSehirler().length) {//Eğer kullanıcı  yanlışlıkla geçersiz sayı girerse uyarı verip oyunu devam ettirir
                System.out.println("Gecersiz secim! Tekrar giriniz:");
                continue;
            }
            if (getSehirler()[secim3 - 1] == null) {
                System.out.println("Sehir bulunamadi! Tekrar seciniz:");
                continue;
            }
            break;
        }
        Sehir secilenSehir = sehir[secim3 - 1];
        this.vergisehri = secilenSehir;
        System.out.println("Vergi oranini giriniz:");
        vergiartismiktari = input.nextDouble();
        this.vergiorani = vergiartismiktari / 100;
        this.sehirVergioranlari[secim3 - 1] = this.vergiorani;//Şehirler için girilen vergi oranları artık tek tek arrayde tutuluyor.

        System.out.println(secilenSehir.getAd() + " icin vergi orani ayarlandi:%" + this.vergiorani);

    }

    public void turSonuUretim() {

        System.out.println("---- TUR SONU URETIM DETAYI ----");

        for (int i = 0; i < getSehirler().length; i++) { // Her şehir için sırayla tur sonu üretim hesaplatılır
            if (getSehirler()[i] == null) { // Şehir dizisinde boş eleman varsa atla
                continue;
            }
            int oncealtin = this.altin;//Bu şehrin üretiminden önce krallığın mevcut altın/yiyecek değerlerini saklıyoruz.
            int onceyiyecek = this.yiyecek;//Böylece şehir üretim yaptıktan sonra farkı hesaplayıp bu şehir ne kazandırdı diyebiliyoruz.
            this.vergisehri = getSehirler()[i];//Vergi şehri belirlendi
            this.vergiorani = sehirVergioranlari[i];/*Tuttuğumuz vergi oranları tek tek kullanıldı ve çağrıldı böylece sadece son girdiğimiz değer değil 
            bütün şehirler için olan vergi oranları kullanıldı(döngü sayesinde hepsini gezebildik) yani halk memnuniyeti hepsi için değişebilir eğer vergi oranı girildiyse*/
            getSehirler()[i].turSonuUretimHesapla(this);  //üretim (şehir krallığa altın/yiyecek ekler)

            int altinKazanc = this.altin - oncealtin; // Şehir üretimi sonrası fark alınır: o şehrin krallığa kazandırdığı net miktar

            int yiyecekKazanc = this.yiyecek - onceyiyecek;

            System.out.println((i + 1) + "- " + getSehirler()[i].getAd() + "  sehrinin bu tur icin kralliga kazandirdigi altin : " + altinKazanc
                    + "  sehrinin bu tur icin kralliga kazandirdigi yiyecek :" + yiyecekKazanc);
        }
        // Tur sonunda krallığın güncel kaynakları
        System.out.println("Guncel Krallik -> Altin: " + this.altin + "   Yiyecek: " + this.yiyecek);

        //Tur sonunda şehirlerin güncel halk memnuniyeti ve güvenlik ortalamasını krallığa yansıtır
        //Her tur bittiğinde diğerine geçerken güncel bi şekilde yazılsın diye.
        if (this.sehirler != null) {//Eğer null değilse çalışsın
            int toplamHalk = 0;
            int toplamGuvenlik = 0;
            int sehirSayisi = 0;

            for (int i = 0; i < this.sehirler.length; i++) {
                if (this.sehirler[i] == null) {
                    continue;
                }

                toplamHalk += this.sehirler[i].getHalkmemnuniyeti();
                toplamGuvenlik += this.sehirler[i].getGuvenlikseviyesi();
                sehirSayisi++;
            }

            if (sehirSayisi > 0) {
                this.halkmemnuniyeti = toplamHalk / sehirSayisi;//Döngüden çıkınca şehir sayısı 3 oldu
                this.guvenlikseviyesi = toplamGuvenlik / sehirSayisi;/*Halk memnuniyeti ve güvenlik seviyesinin ondalıklı olması saçma olacağı için int seçilerek 
                ortalama bir değer kullanılıd.*/
            }
        }
    }

    public boolean oyunBittiMi() {

        if (this.altin <= 0) {
            return true;
        }
        if (this.yiyecek <= 0) {
            return true;
        }
        if (this.halkmemnuniyeti <= 0) {
            return true;
        }
        if (this.guvenlikseviyesi <= 0) {
            return true;
        }
        if (this.tursayisi > 10) {
            return true;
        }
        return false;

    }

    public Scanner getInput() {
        return input;
    }

    public void setInput(Scanner input) {
        this.input = input;
    }

    public int getAltin() {
        return altin;
    }

    public void setAltin(int altin) {
        this.altin = altin;
    }

    public int getYiyecek() {
        return yiyecek;
    }

    public void setYiyecek(int yiyecek) {
        this.yiyecek = yiyecek;
    }

    public int getOrdugucu() {
        return ordugucu;
    }

    public void setOrdugucu(int ordugucu) {
        this.ordugucu = ordugucu;
    }

    public int getHalkmemnuniyeti() {
        return halkmemnuniyeti;
    }

    public void setHalkmemnuniyeti(int halkmemnuniyeti) {
        this.halkmemnuniyeti = halkmemnuniyeti;
    }

    public double getVergiorani() {
        return vergiorani;
    }

    public void setVergiorani(double vergiorani) {
        this.vergiorani = vergiorani;
    }

    public int getGuvenlikseviyesi() {
        return guvenlikseviyesi;
    }

    public void setGuvenlikseviyesi(int guvenlikseviyesi) {
        this.guvenlikseviyesi = guvenlikseviyesi;
    }

    public Sehir[] getSehirler() {
        return sehirler;
    }

    public void setSehirler(Sehir[] sehirler) {
        this.sehirler = sehirler;
    }

    public int getTursayisi() {
        return tursayisi;
    }

    public void setTursayisi(int tursayisi) {
        this.tursayisi = tursayisi;
    }

    public Sehir getVergisehri() {
        return vergisehri;
    }

    public void setVergisehri(Sehir vergisehri) {
        this.vergisehri = vergisehri;
    }

    public double[] getSehirVergioranlari() {
        return sehirVergioranlari;
    }

    public void setSehirVergioranlari(double[] sehirVergioranlari) {
        this.sehirVergioranlari = sehirVergioranlari;
    }

}
