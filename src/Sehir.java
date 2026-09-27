//Dicle Irmak Şahin-2421021029

public class Sehir {

    private String ad;
    private int uretimKapasitesi;
    private int gelisimseviyesi;
    private int orduseviyesi;
    private int guvenlikseviyesi;
    private int baskinihtimali;// Baskın ihtimali her tur sonunda ordu ve güvenlik seviyesine göre tekrar hesaplanır
    private int halkmemnuniyeti;

    public Sehir() {
        /* Alt sınıflarda (Tarım/Ticaret/Sanayi) farklı başlangıç değerleri verilebildiği için
       boş constructor bırakıldı.*/

    }

    public Sehir(String ad, int uretimKapasitesi, int gelisimseviyesi, int orduseviyesi, int guvenlikseviyesi, int halkmemnuniyeti) {
        this.ad = ad;
        this.uretimKapasitesi = uretimKapasitesi; //Gelişim seviyesine göre değişir.
        this.gelisimseviyesi = gelisimseviyesi;
        this.orduseviyesi = orduseviyesi;
        this.guvenlikseviyesi = guvenlikseviyesi;
        this.baskinihtimali = 50;
        this.halkmemnuniyeti = halkmemnuniyeti;
    }

    public void yatirimYap(int altinMiktari) {

        while (altinMiktari >= 100) {  // Her 100 altın yatırım, şehrin gelişimini ve savunma gücünü artırır.
            // 250 yatırılırsa 2 kez artar (kalan 50 boşa gitmez, sadece sayılmaz).
            this.gelisimseviyesi++;
            this.orduseviyesi++;
            this.guvenlikseviyesi++;
            altinMiktari -= 100;
        }

    }

    public boolean baskinGerceklesiyorMu() {

        int ihtimal = 50;// Baskın ihtimali başlangıçta 50 kabul edilir.
        // Ordu ve güvenlik arttıkça baskın ihtimali azalır.

        if (this.orduseviyesi >= 20) {
            ihtimal -= 15;
        } else if (this.orduseviyesi >= 10) {
            ihtimal -= 8;
        } else {
            ihtimal += 5;
        }

        if (this.guvenlikseviyesi >= 20) {
            ihtimal -= 15;
        } else if (this.guvenlikseviyesi >= 10) {
            ihtimal -= 8;
        } else {
            ihtimal += 5;
        }

        if (ihtimal < 0) {// İhtimali 0 ile 100 arasında sağlamak için
            ihtimal = 0;
        }
        if (ihtimal > 100) {
            ihtimal = 100;
        }

        this.setBaskinihtimali(ihtimal);// Hesaplanan ihtimali kaydet ve set et (her tur güncellenmiş olur)

        int rastgele = (int) (Math.random() * 100);//Math.random 0 ile 1 arası değerler(1 dahil değil)Yani 0.99*100 yapıldı.
        if (rastgele < this.getBaskinihtimali()) {
            return true;
        } else {
            return false;
        }

    }

    public void turSonuUretimHesapla(Krallik krallik) {
        //Gelişim seviyesine göre üretim kapasitesini belirle
        if (this.gelisimseviyesi >= 20) {
            this.uretimKapasitesi = 200;
        } else if (this.gelisimseviyesi >= 10) {
            this.uretimKapasitesi = 150;
        } else {
            this.uretimKapasitesi = 100;
        }

        boolean baskinoldu = baskinGerceklesiyorMu();//Baskın kontrolü: baskın olursa üretim sıfırlanır, güvenlik azalır
        if (baskinoldu) {//Baskın olduysa
            System.out.println("Sehirde baskın oldu.Uretim 0'landı");
            this.uretimKapasitesi = 0;
            this.guvenlikseviyesi--;//baskın güvenlik seviyesini de etkilesin
            if (this.guvenlikseviyesi < 0) {//güvenlik seviyesi 0'ın altına düşmesin
                this.guvenlikseviyesi = 0;
            }

        }
        if (!baskinoldu) {//Baskın olmadıysa ürünler yollanır
            double oran = 0.10; // Vergi etkisi yokken çok az ürün yollar
            if (krallik.getVergisehri() == this) { // sadece seçilen şehir
                oran = krallik.getVergiorani();//Kullanıcıdan alınan değer
            }
            int uretim = (int) (this.uretimKapasitesi * (1 + (this.gelisimseviyesi / 10.0)));//Uretilen altın ve yiyecek oranı için yapılmış bir hesap
            int kralligayollanacak = (int) Math.round(uretim * oran);//Int'e yuvarlerken 0.5 üstünü 1 e yuvarlasın diye eğer altındaysa çok az bir miktar 0 yollanablilir direkt

            int altinPayi = kralligayollanacak / 2;//Üretim altın ve yiyecek olarak 2 ye bölünür.
            int yiyecekPayi = kralligayollanacak - altinPayi;

            krallik.setAltin(krallik.getAltin() + altinPayi);//Şehrin ürettiği altın krallığa eklenir ve güncellenir
            krallik.setYiyecek(krallik.getYiyecek() + yiyecekPayi);//Şehrin ürettiği yiyecek krallığa eklenir ve güncellenir

        }
        //Halk memnuniyeti sadece vergi girilen şehir için güncellenir.
        // Krallıkta vergi girilmedi durumu çarpanı bozmamak için 1 olarak tutulduğu için burada vergiorani != 1 kontrolü yapılır.
        if (krallik.getVergisehri() == this && krallik.getVergiorani() != 1) {//Eğer seçtiğimiz şehir ise
            if (krallik.getVergiorani() < 0.20) {
                this.halkmemnuniyeti += 10;
            } else if (krallik.getVergiorani() <= 0.50) {
                this.halkmemnuniyeti += 5;
            } else if (krallik.getVergiorani() <= 0.70) {
                this.halkmemnuniyeti -= 5;
            } else {
                this.halkmemnuniyeti -= 10;
            }
            // Memnuniyeti 0–100 aralığında tut
            if (this.halkmemnuniyeti < 0) {
                this.halkmemnuniyeti = 0;
            }

            if (this.halkmemnuniyeti > 100) {
                this.halkmemnuniyeti = 100;
            }
        }
    }

    public void bilgiGoster() {//Bilgiler yazdırılır
        System.out.println("Ad: " + ad);
        System.out.println("Uretim kapasitesi: " + uretimKapasitesi);
        System.out.println("Gelisim seviyesi: " + gelisimseviyesi);
        System.out.println("Ordu seviyesi: " + orduseviyesi);
        System.out.println("Guvenlik seviyesi: " + guvenlikseviyesi);
        System.out.println("Halk memnuniyeti:" + halkmemnuniyeti);

    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public int getUretimKapasitesi() {
        return uretimKapasitesi;
    }

    public void setUretimKapasitesi(int uretimKapasitesi) {
        this.uretimKapasitesi = uretimKapasitesi;
    }

    public int getGelisimseviyesi() {
        return gelisimseviyesi;
    }

    public void setGelisimseviyesi(int gelisimseviyesi) {
        this.gelisimseviyesi = gelisimseviyesi;
    }

    public int getOrduseviyesi() {
        return orduseviyesi;
    }

    public void setOrduseviyesi(int orduseviyesi) {
        this.orduseviyesi = orduseviyesi;
    }

    public int getGuvenlikseviyesi() {
        return guvenlikseviyesi;
    }

    public void setGuvenlikseviyesi(int guvenlikseviyesi) {
        this.guvenlikseviyesi = guvenlikseviyesi;
    }

    public int getBaskinihtimali() {
        return baskinihtimali;
    }

    public void setBaskinihtimali(int baskinihtimali) {
        this.baskinihtimali = baskinihtimali;
    }

    public int getHalkmemnuniyeti() {
        return halkmemnuniyeti;
    }

    public void setHalkmemnuniyeti(int halkmemnuniyeti) {
        this.halkmemnuniyeti = halkmemnuniyeti;
    }

}
