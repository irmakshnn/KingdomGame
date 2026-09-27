//Dicle Irmak Şahin-2421021029

public class TarimSehri extends Sehir {

    private int uretim;//Uretim kapasitesiyle şehrin ürettiği ürünleri birbirinden ayırmak için

    public TarimSehri() {// Şehir dizisi oluşturulurken new ile boş constructor çağrılabildiği için eklendi.

    }

    public TarimSehri(String ad, int uretimKapasitesi, int gelisimseviyesi, int orduseviyesi, int guvenlikseviyesi, int halkmemnuniyeti) {
        super(ad, uretimKapasitesi, gelisimseviyesi, orduseviyesi, guvenlikseviyesi, halkmemnuniyeti);
    }

    @Override
    public void turSonuUretimHesapla(Krallik krallik) {// Bu metot Sehir sınıfındaki turSonuUretimHesapla metodunu override eder.
        //Gelişime göre üretim kapasitesi belirler
        if (getGelisimseviyesi() >= 20) {
            setUretimKapasitesi(200);
        } else if (getGelisimseviyesi() >= 10) {
            setUretimKapasitesi(150);
        } else {
            setUretimKapasitesi(100);
        }

        /* Baskın olasılığı (ihtimal hesaplama ve rastgelelik) Sehir sınıfında
        baskinGerceklesiyorMu() metodunda tanımlandığı için burada tekrar yazılmamıştır.
 Böylece kod tekrarı önlenmiş ve ortak davranış üst sınıfta tutulmuştur.*/
        boolean baskinoldu = baskinGerceklesiyorMu();//Baskın kontrolü: baskın olursa üretim sıfırlanır, güvenlik azalır
        if (baskinoldu) {
            System.out.println(getAd() + " sehrinde baskin oldu.Uretim 0'landi");
            setUretim(0);
            setGuvenlikseviyesi(getGuvenlikseviyesi() - 1);//Güvenlik seviyesi azaldı
            if (getGuvenlikseviyesi() < 0) {
                setGuvenlikseviyesi(0);
            }
        }

//Baskın yoksa: vergi oranına göre krallığa gidecek üretim
        if (!baskinoldu) {
            double oran = 0.10; // Vergi etkisi yokken çok az ürün yollar
            if (krallik.getVergisehri() == this) {   // sadece seçilen şehir
                oran = krallik.getVergiorani();
            }
            setUretim((int) (this.getUretimKapasitesi() * (1 + (this.getGelisimseviyesi() / 10.0))));//Uretilen altın ve yiyecek oranı için yapılmış bir hesap
            int kralligayollanacak = (int) Math.round(getUretim() * oran);

            // Tarım şehri: yiyecek ağırlıklı
            int yiyecekPayi = (int) (kralligayollanacak * 0.95);//%5 altın %95 yiyecek yollanır
            int altinPayi = kralligayollanacak - yiyecekPayi;

            krallik.setAltin(krallik.getAltin() + altinPayi);// Krallığın kaynakları güncellenir  
            krallik.setYiyecek(krallik.getYiyecek() + yiyecekPayi);
        }

        //Halk memnuniyeti sadece vergi girilen şehir için güncellenir.
        // Krallıkta vergi girilmedi durumu çarpanı bozmamak için 1 olarak tutulduğu için burada vergiorani != 1 kontrolü yapılır.
        if (krallik.getVergisehri() == this && krallik.getVergiorani() != 1) {//Eğer seçtiğimiz şehir ise
            if (krallik.getVergiorani() < 0.20) {
                setHalkmemnuniyeti(getHalkmemnuniyeti() + 10);
            } else if (krallik.getVergiorani() <= 0.50) {
                setHalkmemnuniyeti(getHalkmemnuniyeti() + 5);
            } else if (krallik.getVergiorani() <= 0.70) {
                setHalkmemnuniyeti(getHalkmemnuniyeti() - 5);
            } else {
                setHalkmemnuniyeti(getHalkmemnuniyeti() - 10);
            }

//0–100 aralığına sabitle
            if (getHalkmemnuniyeti() < 0) {
                setHalkmemnuniyeti(0);
            }
            if (getHalkmemnuniyeti() > 100) {
                setHalkmemnuniyeti(100);
            }
        }
    }

    public int getUretim() {
        return uretim;
    }

    public void setUretim(int uretim) {
        this.uretim = uretim;
    }

}
