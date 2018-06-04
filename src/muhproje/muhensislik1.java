
package muhproje;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Emel
 */
public class muhensislik1 {

    static int say=0;
    //211 asal sayı oldugu için dizimin boyutu 211'dir
    static int[] HashTablosu=new int[211];//her bir kelimenin key'ini tutuyor
    static String[] veri=new String[101];//dosya'daki kelimeleri atadığım dizi
    static String[] kelimetut=new String[211];//arama fonksiyonunda kullanmış olduğum kelimeleri tuttuğum dizi
    static char[] dizi=new char[211];//yer değiştirerek aramada karakter ile oynadığım için char diziye atadım
	public static void main(String[] args) {
            int size=211;
            String kelime;
	    String str;//her seferinde diziyi sıfırlıyorum ki
                                   //tekrar başka bir kelime çağırdığımda sorun çıkmasın.
                clear(HashTablosu);//her seferinde diziyi sıfırlıyorum ki
                                  //tekrar başka bir kelime çağırdığımda sorun çıkmasın.
                        //YAZDIRMA
                System.out.println("*********HASH TABLOSUNDA KELİME ARAMA ***********"); 
                System.out.println("\n");
                System.out.println("indis        "+"hashtable     "+"kelime       "+"%211         ");
                System.out.println("-----        "+"---------     "+"------"+"    "+"   ----");
		File file=new File("metin.txt");//kelimelerimin bulunduğu metin dosyası
                try {//hata ayıklama
			
                        BufferedReader br=new BufferedReader(new FileReader(file));//dosya okuma
                        int i=0;
			while((str=br.readLine())!=null){//sonuna kadar veri dizi kelimeleri aktar
                            str=str.toLowerCase();//büyük harften küçük harfe çevirme
                            veri[i]=str;   //dosyadaki kelimelrimin diziye atanması
                            i++;
			}
                        
		    }catch (IOException e) {
			
		}
                for(int i=0; i<100; i++){
                    String line=veri[i];
                    int key=keyFind(veri[i]);//dizi deki kelimeleri keyfind fonksiyonu ile keyini bulup bir değişkene atama
                    HashFonc(line,HashTablosu,key,size);
                }
                int secim=1;
                Scanner kel=new Scanner(System.in);//kullanıcıdan bir kelime girilmesi
                System.out.println("\n seçim seçiniz (1-yeni kelime girişi 2-cıkıs)");
                secim=kel.nextInt();
              
              while(true){
             
                    switch(secim){
                        
                        case 1:
                        System.out.println("\n aramak istediginiz kelimeyi giriniz");
                        kelime=kel.next();
                        arama(kelime);
                    break;
                        case 2:
                            System.exit(0);
                            break;
                        default:
                            System.out.println("yanlış seçim yaptınız yeniden seçim yapınız ");
                    }
                    System.out.println("seçim seçiniz (1-yeni kelime girişi 2-cıkıs)");
                    secim=kel.nextInt();
                    
;//arama fonksiyonunu çagırıyorum.
              }
                
              
  
        }  
        public static int keyFind(String kelime){//keyFind fonksiyonum kelimelerin ascii karşılığını
            int toplam=0;                           //agırlıklarının çarpımın toplamı kadar bulunması
            for (int i = 0; i < kelime.length(); i++) {

                  toplam+=(int) kelime.charAt(i)*(i+1);//kelimenin her bir karakterini parçalayarak
                                                        //ve agırlıklarıyla çarparak toplama ataması
            }
            return toplam;
        }
        
        public static void HashFonc(String line,int[] Dizi,int toplam,int N){
            
            //Hash Tablosu yerleşimi ve çakışmanın (collision) ikinci 
            //dereceden tarama (quadratic probing) ile çözülmesi
            // yerleştirileceği dizinin boyutuna göre modunun alınmasıdır.
            int sonuc=toplam%N;
            int i=0;
                while(dizi[i]!=0){
                    sonuc=Math.abs((sonuc+(i*i*i*i))%N);//quadratic probing
                    i++;
                }
                Dizi[sonuc]=toplam;
                
                
                kelimetut[sonuc]=line;//dizimde ki kelimelri tutması için bir diziye atama
                //yazdırma
                System.out.print("\n");
                System.out.printf("%3d",say);
                
                System.out.printf("%15d",toplam);
                System.out.printf("%14s",kelimetut[sonuc]);
                System.out.printf("%11d",sonuc);
                say++;
        }
         
        public static void clear(int[] HashTablosu){//int dizimi başlangıçta sıfırlıyorum
            for (int i = 0; i < HashTablosu.length; i++) {
                HashTablosu[i]=0;
            }
        }
        //kullanıcıdan istediğimiz kelimenin aranması
        //eger kelime dosya da varsa kelime vardır yazcak ve programdan çıkacak
        public static void arama(String aranan){
            int key=keyFind(aranan);//key ile dizideki kelimelri arıyorum
            int sayac=0;
          //  System.out.println(key);
            for(int i=0; i<HashTablosu.length; i++){
                if(key==HashTablosu[i]){
                    sayac++;
                    System.out.println("girdiginiz "+kelimetut[i]+" kelimesi metin dosyasında bulunmaktadir.");
                    break;
                }
                
            }
            
            //Aranan kelime dizide bulunmuyorsa, sadece bir karakteri silinerek yeniden arama yapılmalıdır. ve aynı
            // zamanda aranan kelimenin her komşu iki karakteri yer değiştirilerek yeniden arama yapılmalıdır..
            if(sayac==0){
                eksikAra(aranan);
                degisAra(aranan);
            }
            
        }
       // bulunamayan kelimenin karakterlerinin eksiltilerek dizide aranması
        public static void eksikAra(String aranan){
            System.out.println("######Eksilterek Arama######\n");
            String ekaranan;
            int j=0;//flag sayacım gibi düşünebiliriz
             
            for (int i = 0; i < aranan.length(); i++){//aradığım aranan kelimein uzunluğuna kadar yani 4 harfli bir 
               int sayac=0;                           //kelime ise 4 tane esilterek kelime arayacak
                ekaranan="";
                for (j = 0; j < aranan.length(); j++) {
                    if (j!=i) {
                        ekaranan+=aranan.charAt(j);
                    }
                }
                String gecici1=new String(ekaranan);
                int key=keyFind(gecici1);//keyFind fonksiyonu çağrılarak sayısal değeri üzerinden yazırma yapılıyor.
                
            for(int m=0; m<HashTablosu.length; m++){//eksilmiş kelimeleri ekrana yazma
                if(key==HashTablosu[m]){
                    
                    sayac++;
                    System.out.println("girdiginiz "+aranan+" kelimesi metin dosyasında "+gecici1+" bulunmaktadir.");
                    break;
                }
            }
            if(sayac==0){//eksilterek arama yoksa eger hiç birşey yapma (boş)
                
            }   
            
        }  
    }
        //bulunamayan kelimenin komşu karakterlerinin yer değiştirilmesiyle dizide aranması 
        public static void degisAra(String aranan){
            System.out.println("######Yer Değiştirilerek Arama######");
            int sayac=0;
                for (int i = 0; i < aranan.length()-1; i++) {//aranan kelimenin boyutunun bir eksiği kadar
                    //arama yapacak yani 4 harfli kelime için üç tane kelime arayacak
                int size=aranan.length();
                char [] temp=new char[size];//yeni bir char dizisi tanımlandı 
                //karakterleri yer değiştirceğim için char dizisi tanımlandı
                char k=' ';//gecici olarak buraya atıyoruz
                    for (int j = 0; j < aranan.length(); j++) {
                   
                           temp[j]=aranan.charAt(j);
                    }
                    //karakterlerinin yer değişmesi
                    k=temp[i];
                    temp[i]=temp[i+1];
                    temp[i+1]=k;
                    String gecici=new String(temp);//geçici bir diziye atayıp diziyi yazdırıyorum
                    //ascıı de arama ya gönderiyoruz
                    int key=keyFind(gecici);
                    //bulunan kelimelerin yazılması
                    for(int m=0; m<HashTablosu.length; m++){
                        if(key==HashTablosu[m]){
                            sayac++;
                            System.out.println("girdiginiz "+aranan+" kelimesi metin dosyasında "+gecici+" bulunmaktadir.");
                            break;
                }
                
            }
            if(sayac==0){//eger dosya da iç bir kelime yoksa metin dosyasında bulunmaktadır mesajı veriliyor.
                //System.out.println("Karakterlerin yer değiştirilerek aranma sonucu kelime bulunamadı");
            }
        }   
         System.out.print("\n");
         System.out.println("aradığınız "+aranan+" kelimesi dosyada bulunmamaktadır.");   
    }
}
  
