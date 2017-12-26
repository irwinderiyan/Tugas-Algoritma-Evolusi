/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tugasakhirgenetika;

/**
 *
 * @author ASUS
 */
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import Jama.Matrix;
import Jama.SingularValueDecomposition;
import static com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table;
import javax.swing.JLabel;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class alev extends javax.swing.JFrame {

    int jumlahgenerasi = 250;
    int countergenerasi = 0;
    ArrayList<Integer> randompopC = new ArrayList<Integer>();
    ArrayList<Integer> randompop = new ArrayList<Integer>();
    int[][] matrikrawtf;
    int triggerevaluasi = 1;
    public static int simpannilai;
    public static int dekat1;
    public static int dekat2;
    public static String dekat11;
    public static String dekat22;
    public static double nilaidekat;
    public static LinkedList a = new LinkedList();
    public static double aa[][];
    //crossover

    int inialisasipopulasi = 150;
    int titikpotong = 8;
    double Cr = 0.5;
    double offspringC = Math.ceil(Cr * inialisasipopulasi);
    int KamaroffspringC = (int) offspringC;

    //mutasi
    ArrayList<Integer> randompopM = new ArrayList<Integer>();
    double Mr = 0.2;
    double offspringM = Math.ceil(Mr * inialisasipopulasi);
    int KamaroffspringM = (int) offspringM;
    double TerbesarM = 0;

//            = {
//                {1, 0.004307, 0.123709, 0.031368, 0},
//                {0.004307, 1, 0.121281, -0.22182, 0},
//                {0.123709, 0.121281, 1, 0.936957, 0},
//                {0.031368, -0.22182, 0.936957, 1, 0},
//                {0, 0, 0, 0, 1}
//            };
//    ;
    public static double aa2[][];
    public static double simpanmatrikcosine[][];

    private JFileChooser jFileChooser1 = new JFileChooser();
    double rank = 0;
    Matrix V, S, LSI;
    double[][] matrixU;
    double[][] matrixS;
    double[][] matrixV;
    double[][] matrixVt;
    double[][] matrixLSI; //mmulai kene
    double[][] matrixtfidf;
    int trigger = 0;

    double[][] matrixSbaru; //hasil reduce matrik
    double[][] matrixVbaru; //hasil reduce matrik
    String header1[] = {
        "No", "Isi Petisi"
    };
    String header2[];  // term + banyak dokumen
    String header3[];  // term + df + banyak dokumen
    String header4[];  // term + idf + banyak dokumen
    String header5[];  // dokumen
    String header6[];  // dokumen cosine
    String[] data;
    LinkedList hasilterm = new LinkedList();
    String stopWords[] = {"ada", "adalah", "adanya", "adapun", "agak", "agaknya", "agar", "akan", "akankah", "akhir", "akhiri",
        "akhirnya", "aku", "akulah", "amat", "amatlah", "anda", "andalah", "antar", "antara", "antaranya", "apa", "apaan", "apabila",
        "apakah", "apalagi", "apatah", "artinya", "asal", "asalkan", "atas", "atau", "ataukah", "ataupun", "awal", "awalnya", "bagai",
        "bagaikan", "bagaimana", "bagaimanakah", "bagaimanapun", "bagi", "bagian", "bahkan", "bahwa", "bahwasanya", "baik", "bakal",
        "bakalan", "balik", "banyak", "bapak", "baru", "bawah", "beberapa", "begini", "beginian", "beginikah", "beginilah", "begitu",
        "begitukah", "begitulah", "begitupun", "bekerja", "belakang", "belakangan", "belum", "belumlah", "benar", "benarkah",
        "benarlah", "berada", "berakhir", "berakhirlah", "berakhirnya", "berapa", "berapakah", "berapalah", "berapapun", "berarti",
        "berawal", "berbagai", "berdatangan", "beri", "berikan", "berikut", "berikutnya", "berjumlah", "berkali-kali", "berkata",
        "berkehendak", "berkeinginan", "berkenaan", "berlainan", "berlalu", "berlangsung", "berlebihan", "bermacam",
        "bermacam-macam", "bermaksud", "bermula", "bersama", "bersama-sama", "bersiap", "bersiap-siap", "bertanya",
        "bertanya-tanya", "berturut", "berturut-turut", "bertutur", "berujar", "berupa", "besar", "betul", "betulkah", "biasa",
        "biasanya", "bila", "bilakah", "bisa", "bisakah", "boleh", "bolehkah", "bolehlah", "buat", "bukan", "bukankah", "bukanlah",
        "bukannya", "bulan", "bung", "cara", "caranya", "cukup", "cukupkah", "cukuplah", "cuma", "dahulu", "dalam", "dan", "dapat",
        "dari", "daripada", "datang", "dekat", "demi", "demikian", "demikianlah", "dengan", "depan", "di", "dia", "diakhiri",
        "diakhirinya", "dialah", "diantara", "diantaranya", "diberi", "diberikan", "diberikannya", "dibuat", "dibuatnya", "didapat",
        "didatangkan", "digunakan", "diibaratkan", "diibaratkannya", "diingat", "diingatkan", "diinginkan", "dijawab",
        "dijelaskan", "dijelaskannya", "dikarenakan", "dikatakan", "dikatakannya", "dikerjakan", "diketahui", "diketahuinya",
        "dikira", "dilakukan", "dilalui", "dilihat", "dimaksud", "dimaksudkan", "dimaksudkannya", "dimaksudnya", "diminta",
        "dimintai", "dimisalkan", "dimulai", "dimulailah", "dimulainya", "dimungkinkan", "dini", "dipastikan", "diperbuat",
        "diperbuatnya", "dipergunakan", "diperkirakan", "diperlihatkan", "diperlukan", "diperlukannya", "dipersoalkan",
        "dipertanyakan", "dipunyai", "diri", "dirinya", "disampaikan", "disebut", "disebutkan", "disebutkannya", "disini",
        "disinilah", "ditambahkan", "ditandaskan", "ditanya", "ditanyai", "ditanyakan", "ditegaskan", "ditujukan", "ditunjuk",
        "ditunjuki", "ditunjukkan", "ditunjukkannya", "ditunjuknya", "dituturkan", "dituturkannya", "diucapkan", "diucapkannya",
        "diungkapkan", "dong", "dua", "dulu", "empat", "enggak", "enggaknya", "entah", "entahlah", "guna", "gunakan", "hal", "hampir",
        "hanya", "hanyalah", "hari", "harus", "haruslah", "harusnya", "hendak", "hendaklah", "hendaknya", "hingga", "ia", "ialah",
        "ibarat", "ibaratkan", "ibaratnya", "ibu", "ikut", "ingat", "ingat-ingat", "ingin", "inginkah", "inginkan", "ini", "inikah",
        "inilah", "itu", "itukah", "itulah", "jadi", "jadilah", "jadinya", "jangan", "jangankan", "janganlah", "jauh", "jawab",
        "jawaban", "jawabnya", "jelas", "jelaskan", "jelaslah", "jelasnya", "jika", "jikalau", "juga", "jumlah", "jumlahnya",
        "justru", "kala", "kalau", "kalaulah", "kalaupun", "kalian", "kami", "kamilah", "kamu", "kamulah", "kan", "kapan", "kapankah",
        "kapanpun", "karena", "karenanya", "kasus", "kata", "katakan", "katakanlah", "katanya", "ke", "keadaan", "kebetulan", "kecil",
        "kedua", "keduanya", "keinginan", "kelamaan", "kelihatan", "kelihatannya", "kelima", "keluar", "kembali", "kemudian",
        "kemungkinan", "kemungkinannya", "kenapa", "kepada", "kepadanya", "kesampaian", "keseluruhan", "keseluruhannya",
        "keterlaluan", "ketika", "khususnya", "kini", "kinilah", "kira", "kira-kira", "kiranya", "kita", "kitalah", "kok", "kurang",
        "lagi", "lagian", "lah", "lain", "lainnya", "lalu", "lama", "lamanya", "lanjut", "lanjutnya", "lebih", "lewat", "lima", "luar",
        "macam", "maka", "makanya", "makin", "malah", "malahan", "mampu", "mampukah", "mana", "manakala", "manalagi", "masa", "masalah",
        "masalahnya", "masih", "masihkah", "masing", "masing-masing", "mau", "maupun", "melainkan", "melakukan", "melalui",
        "melihat", "melihatnya", "memang", "memastikan", "memberi", "memberikan", "membuat", "memerlukan", "memihak", "meminta",
        "memintakan", "memisalkan", "memperbuat", "mempergunakan", "memperkirakan", "memperlihatkan", "mempersiapkan",
        "mempersoalkan", "mempertanyakan", "mempunyai", "memulai", "memungkinkan", "menaiki", "menambahkan", "menandaskan",
        "menanti", "menanti-nanti", "menantikan", "menanya", "menanyai", "menanyakan", "mendapat", "mendapatkan",
        "mendatang", "mendatangi", "mendatangkan", "menegaskan", "mengakhiri", "mengapa", "mengatakan", "mengatakannya",
        "mengenai", "mengerjakan", "mengetahui", "menggunakan", "menghendaki", "mengibaratkan", "mengibaratkannya",
        "mengingat", "mengingatkan", "menginginkan", "mengira", "mengucapkan", "mengucapkannya", "mengungkapkan",
        "menjadi", "menjawab", "menjelaskan", "menuju", "menunjuk", "menunjuki", "menunjukkan", "menunjuknya", "menurut",
        "menuturkan", "menyampaikan", "menyangkut", "menyatakan", "menyebutkan", "menyeluruh", "menyiapkan", "merasa",
        "mereka", "merekalah", "merupakan", "meski", "meskipun", "meyakini", "meyakinkan", "minta", "mirip", "misal",
        "misalkan", "misalnya", "mula", "mulai", "mulailah", "mulanya", "mungkin", "mungkinkah", "nah", "naik", "namun",
        "nanti", "nantinya", "nyaris", "nyatanya", "oleh", "olehnya", "pada", "padahal", "padanya", "pak", "paling",
        "panjang", "pantas", "para", "pasti", "pastilah", "penting", "pentingnya", "per", "percuma", "perlu",
        "perlukah", "perlunya", "pernah", "persoalan", "pertama", "pertama-tama", "pertanyaan", "pertanyakan",
        "pihak", "pihaknya", "pukul", "pula", "pun", "punya", "rasa", "rasanya", "rata", "rupanya", "saat",
        "saatnya", "saja", "sajalah", "saling", "sama", "sama-sama", "sambil", "sampai", "sampai-sampai",
        "sampaikan", "sana", "sangat", "sangatlah", "satu", "saya", "sayalah", "se", "sebab", "sebabnya",
        "sebagai", "sebagaimana", "sebagainya", "sebagian", "sebaik", "sebaik-baiknya", "sebaiknya", "sebaliknya",
        "sebanyak", "sebegini", "sebegitu", "sebelum", "sebelumnya", "sebenarnya", "seberapa", "sebesar", "sebetulnya",
        "sebisanya", "sebuah", "sebut", "sebutlah", "sebutnya", "secara", "secukupnya", "sedang", "sedangkan", "sedemikian",
        "sedikit", "sedikitnya", "seenaknya", "segala", "segalanya", "segera", "seharusnya", "sehingga", "seingat",
        "sejak", "sejauh", "sejenak", "sejumlah", "sekadar", "sekadarnya", "sekali", "sekali-kali", "sekalian", "sekaligus",
        "sekalipun", "sekarang", "sekarang", "sekecil", "seketika", "sekiranya", "sekitar", "sekitarnya",
        "sekurang-kurangnya", "sekurangnya", "sela", "selain", "selaku", "selalu", "selama", "selama-lamanya",
        "selamanya", "selanjutnya", "seluruh", "seluruhnya", "semacam", "semakin", "semampu", "semampunya", "semasa",
        "semasih", "semata", "semata-mata", "semaunya", "sementara", "semisal", "semisalnya", "sempat", "semua", "semuanya",
        "semula", "sendiri", "sendirian", "sendirinya", "seolah", "seolah-olah", "seorang", "sepanjang", "sepantasnya",
        "sepantasnyalah", "seperlunya", "seperti", "sepertinya", "sepihak", "sering", "seringnya", "serta", "serupa", "sesaat",
        "sesama", "sesampai", "sesegera", "sesekali", "seseorang", "sesuatu", "sesuatunya", "sesudah", "sesudahnya", "setelah",
        "setempat", "setengah", "seterusnya", "setiap", "setiba", "setibanya", "setidak-tidaknya", "setidaknya", "setinggi",
        "seusai", "sewaktu", "siap", "siapa", "siapakah", "siapapun", "sini", "sinilah", "soal", "soalnya", "suatu", "sudah",
        "sudahkah", "sudahlah", "supaya", "tadi", "tadinya", "tahu", "tahun", "tak", "tambah", "tambahnya", "tampak",
        "tampaknya", "tandas", "tandasnya", "tanpa", "tanya", "tanyakan", "tanyanya", "tapi", "tegas", "tegasnya", "telah",
        "tempat", "tengah", "tentang", "tentu", "tentulah", "tentunya", "tepat", "terakhir", "terasa", "terbanyak", "terdahulu",
        "terdapat", "terdiri", "terhadap", "terhadapnya", "teringat", "teringat-ingat", "terjadi", "terjadilah",
        "terjadinya", "terkira", "terlalu", "terlebih", "terlihat", "termasuk", "ternyata", "tersampaikan", "tersebut",
        "tersebutlah", "tertentu", "tertuju", "terus", "terutama", "tetap", "tetapi", "tiap", "tiba", "tiba-tiba", "tidak",
        "tidakkah", "tidaklah", "tiga", "tinggi", "toh", "tunjuk", "turut", "tutur", "tuturnya", "ucap", "ucapnya", "ujar",
        "ujarnya", "umum", "umumnya", "ungkap", "ungkapnya", "untuk", "usah", "usai", "waduh", "wah", "wahai", "waktu", "waktunya",
        "walau", "walaupun", "wong", "yaitu", "yakin", "yakni", "yang", "yg"};

    /**
     * Creates new form skripsiform
     */
    public alev() {
        initComponents();
     
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        jButton9 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable7 = new javax.swing.JTable();
        jButton10 = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTable8 = new javax.swing.JTable();
        jButton11 = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTable9 = new javax.swing.JTable();
        jButton12 = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTable10 = new javax.swing.JTable();
        jButton13 = new javax.swing.JButton();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTable11 = new javax.swing.JTable();
        jButton14 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jTabbedPane5 = new javax.swing.JTabbedPane();
        jPanel20 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        jTable12 = new javax.swing.JTable();
        jButton15 = new javax.swing.JButton();
        jPanel21 = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        jTable13 = new javax.swing.JTable();
        jButton16 = new javax.swing.JButton();
        jPanel22 = new javax.swing.JPanel();
        jScrollPane15 = new javax.swing.JScrollPane();
        jTable14 = new javax.swing.JTable();
        jButton17 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jTabbedPane6 = new javax.swing.JTabbedPane();
        jPanel24 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane16 = new javax.swing.JScrollPane();
        jTable15 = new javax.swing.JTable();
        jButton18 = new javax.swing.JButton();
        jPanel25 = new javax.swing.JPanel();
        jScrollPane17 = new javax.swing.JScrollPane();
        jTable16 = new javax.swing.JTable();
        jButton19 = new javax.swing.JButton();
        jPanel27 = new javax.swing.JPanel();
        jScrollPane18 = new javax.swing.JScrollPane();
        jTable17 = new javax.swing.JTable();
        jButton20 = new javax.swing.JButton();
        jPanel28 = new javax.swing.JPanel();
        jScrollPane19 = new javax.swing.JScrollPane();
        jTable18 = new javax.swing.JTable();
        jButton21 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jTabbedPane7 = new javax.swing.JTabbedPane();
        jPanel29 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane21 = new javax.swing.JScrollPane();
        jTable20 = new javax.swing.JTable();
        jButton23 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        jPanel26 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane22 = new javax.swing.JScrollPane();
        jTable21 = new javax.swing.JTable();
        jButton25 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane23 = new javax.swing.JScrollPane();
        jTable22 = new javax.swing.JTable();
        jButton26 = new javax.swing.JButton();
        jButton27 = new javax.swing.JButton();
        jPanel30 = new javax.swing.JPanel();
        jScrollPane20 = new javax.swing.JScrollPane();
        jTable19 = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane24 = new javax.swing.JScrollPane();
        jTable23 = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jButton22 = new javax.swing.JButton();
        jButton28 = new javax.swing.JButton();
        jButton29 = new javax.swing.JButton();
        jScrollPane25 = new javax.swing.JScrollPane();
        jTable24 = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        jButton30 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane26 = new javax.swing.JScrollPane();
        jTable25 = new javax.swing.JTable();
        jButton31 = new javax.swing.JButton();
        jScrollPane27 = new javax.swing.JScrollPane();
        jTable26 = new javax.swing.JTable();
        jButton32 = new javax.swing.JButton();
        jScrollPane28 = new javax.swing.JScrollPane();
        jTable27 = new javax.swing.JTable();
        jButton33 = new javax.swing.JButton();
        jButton34 = new javax.swing.JButton();
        jButton35 = new javax.swing.JButton();
        jPanel32 = new javax.swing.JPanel();
        jScrollPane29 = new javax.swing.JScrollPane();
        jTable28 = new javax.swing.JTable();
        jButton36 = new javax.swing.JButton();
        jScrollPane30 = new javax.swing.JScrollPane();
        jTable29 = new javax.swing.JTable();
        jScrollPane31 = new javax.swing.JScrollPane();
        jTable30 = new javax.swing.JTable();
        jButton37 = new javax.swing.JButton();
        jButton38 = new javax.swing.JButton();
        jButton45 = new javax.swing.JButton();
        jPanel33 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane32 = new javax.swing.JScrollPane();
        jTable31 = new javax.swing.JTable();
        jButton39 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane33 = new javax.swing.JScrollPane();
        jTable32 = new javax.swing.JTable();
        jButton40 = new javax.swing.JButton();
        jButton46 = new javax.swing.JButton();
        jPanel34 = new javax.swing.JPanel();
        jScrollPane34 = new javax.swing.JScrollPane();
        jTable33 = new javax.swing.JTable();
        jScrollPane35 = new javax.swing.JScrollPane();
        jTable34 = new javax.swing.JTable();
        jScrollPane36 = new javax.swing.JScrollPane();
        jTable35 = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jButton41 = new javax.swing.JButton();
        jButton42 = new javax.swing.JButton();
        jButton43 = new javax.swing.JButton();
        jButton44 = new javax.swing.JButton();
        jPanel35 = new javax.swing.JPanel();
        jScrollPane37 = new javax.swing.JScrollPane();
        jTable36 = new javax.swing.JTable();
        jScrollPane38 = new javax.swing.JScrollPane();
        jTable37 = new javax.swing.JTable();
        jButton48 = new javax.swing.JButton();
        jButton47 = new javax.swing.JButton();
        jPanel31 = new javax.swing.JPanel();
        jScrollPane39 = new javax.swing.JScrollPane();
        jTable38 = new javax.swing.JTable();
        jButton49 = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("tugas alev");

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel1.setText("                                             Menggunakan Algoritma Enhanced Genetic");

        jTabbedPane1.setBackground(new java.awt.Color(51, 102, 0));
        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setText("browse");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Lanjut");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("hapus");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 628, Short.MAX_VALUE)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("input data", jPanel11);

        jButton4.setText("lanjut");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "No", "Isi Petisi"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setToolTipText("");
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTable1.setEnabled(false);
        jScrollPane3.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGap(0, 790, Short.MAX_VALUE)
                .addComponent(jButton4))
            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 849, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(423, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addGap(42, 42, 42))
            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel12Layout.createSequentialGroup()
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 76, Short.MAX_VALUE)))
        );

        jTabbedPane2.addTab("proses data", jPanel12);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(125, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Data", jPanel1);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(jTable3);

        jButton5.setText("lanjut");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 849, Short.MAX_VALUE)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton5))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addContainerGap())
        );

        jTabbedPane3.addTab("hasil lowercase", jPanel13);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jButton6.setText("lanjut");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 849, Short.MAX_VALUE)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton6))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6)
                .addContainerGap(164, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("hapus karakter non abjad", jPanel14);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane3)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane3)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Case folding", jPanel2);

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(jTable4);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel3.setText("Hasil Tokenizing");

        jButton7.setText("lanjut");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane5)
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 781, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton7)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7)
                .addContainerGap(174, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Tokenizing", jPanel3);

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(jTable5);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel4.setText("Hasil filtering");

        jButton8.setText("lanjut");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 854, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton8)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8)
                .addContainerGap(173, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Filtering", jPanel4);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel5.setText("Hasil Stemming Nazief Andriani");

        jTable6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane7.setViewportView(jTable6);

        jButton9.setText("lanjut");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jLabel6.setText(" ");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addGap(38, 38, 38)
                        .addComponent(jButton9))
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 854, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton9)
                    .addComponent(jLabel6))
                .addContainerGap(167, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Stemming", jPanel5);

        jTable7.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane8.setViewportView(jTable7);

        jButton10.setText("lanjut");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 849, Short.MAX_VALUE)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton10))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton10)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("Term Frekuensi", jPanel15);

        jTable8.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane9.setViewportView(jTable8);

        jButton11.setText("lanjut");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 849, Short.MAX_VALUE)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton11))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton11)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("pembobotan term frekuensi", jPanel16);

        jTable9.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane10.setViewportView(jTable9);

        jButton12.setText("lanjut");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 849, Short.MAX_VALUE)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton12))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton12)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("menghitung Dokumen frekuensi", jPanel17);

        jTable10.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane11.setViewportView(jTable10);

        jButton13.setText("lanjut");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 849, Short.MAX_VALUE)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton13))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton13)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("menghitung IDF", jPanel18);

        jTable11.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane12.setViewportView(jTable11);

        jButton14.setText("lanjut");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 849, Short.MAX_VALUE)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton14))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton14)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("TFIDF", jPanel19);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane4)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(136, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("TFIDF", jPanel6);

        jTable12.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane13.setViewportView(jTable12);

        jButton15.setText("lanjut");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 849, Short.MAX_VALUE)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton15))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane5.addTab("SVD Matrix U", jPanel20);

        jTable13.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane14.setViewportView(jTable13);

        jButton16.setText("lanjut");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(0, 790, Short.MAX_VALUE)
                .addComponent(jButton16))
            .addComponent(jScrollPane14, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton16)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane5.addTab("SVD Matrix S", jPanel21);

        jTable14.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane15.setViewportView(jTable14);

        jButton17.setText("lanjut");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 849, Short.MAX_VALUE)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton17))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton17)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane5.addTab("SVD Matrix V transpose", jPanel22);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane5)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(141, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("SVD", jPanel7);

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "100%", "95%", "90%", "85%", "80%", "75%", "70%", "65%", "60%", "55%", "50%", "45%", "40%", "35%", "30%", "25%", "20%", "15%", "10%", "5%", " " }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel7.setText("Set Rank pada matrix LSI");

        jTable15.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane16.setViewportView(jTable15);

        jButton18.setText("lanjut");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(718, Short.MAX_VALUE))
            .addComponent(jScrollPane16)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton18)
                .addContainerGap())
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton18)
                .addContainerGap(58, Short.MAX_VALUE))
        );

        jTabbedPane6.addTab("Pemotongan matrix V transpose", jPanel24);

        jTable16.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane17.setViewportView(jTable16);

        jButton19.setText("lanjut");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 849, Short.MAX_VALUE)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton19))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jButton19)
                .addGap(29, 29, 29))
        );

        jTabbedPane6.addTab("Pemotongan matrix S", jPanel25);

        jTable17.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane18.setViewportView(jTable17);

        jButton20.setText("lanjut");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane18, javax.swing.GroupLayout.DEFAULT_SIZE, 849, Short.MAX_VALUE)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton20))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton20)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jTabbedPane6.addTab("Matrik V", jPanel27);

        jTable18.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane19.setViewportView(jTable18);

        jButton21.setText("lanjut");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane19, javax.swing.GroupLayout.DEFAULT_SIZE, 849, Short.MAX_VALUE)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton21))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton21)
                .addContainerGap(86, Short.MAX_VALUE))
        );

        jTabbedPane6.addTab("Matrik LSI", jPanel28);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(124, 124, 124)
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane6)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 524, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("LSI", jPanel8);

        jTabbedPane7.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane7.setTabPlacement(javax.swing.JTabbedPane.RIGHT);

        jLabel8.setText("Inialisasi Populasi");

        jTable20.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane21.setViewportView(jTable20);

        jButton23.setText("Acak");
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });

        jButton24.setText("Lanjut");
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane21, javax.swing.GroupLayout.DEFAULT_SIZE, 666, Short.MAX_VALUE)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addComponent(jButton23, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton24)))
                .addContainerGap())
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton23)
                    .addComponent(jButton24))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane7.addTab("Inialisasi Populasi", jPanel29);

        jLabel9.setText("Inialisasi Nilai Subset");

        jTable21.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable21.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane22.setViewportView(jTable21);

        jButton25.setText("Acak Nilai Subset");
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });

        jLabel10.setText("Nilai skor kalimat");

        jTable22.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable22.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane23.setViewportView(jTable22);

        jButton26.setText("skor kalimat");
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });

        jButton27.setText("Lanjut");
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(163, 163, 163))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addComponent(jButton26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 257, Short.MAX_VALUE)
                .addComponent(jButton25)
                .addGap(143, 143, 143))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton27))
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jScrollPane23, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGap(28, 28, 28))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane23, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                    .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton27)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane7.addTab("Evaluasi Fitness I (tahap 1)", jPanel26);

        jTable19.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable19.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane20.setViewportView(jTable19);

        jLabel11.setText("Sigma (Xi x Wj)");

        jTable23.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable23.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane24.setViewportView(jTable23);

        jLabel12.setText("2 Subset Tertinggi pada term");

        jButton22.setText("Hitung Sigma (Xi x Wj)");
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });

        jButton28.setText("Hitung 2 Subset");
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });

        jButton29.setText("Hitung Fitness");
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });

        jTable24.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable24.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane25.setViewportView(jTable24);

        jLabel13.setText("Fitness");

        jButton30.setText("Lanjut");
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton30ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(121, 121, 121)
                .addComponent(jButton22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton28)
                .addGap(64, 64, 64)
                .addComponent(jButton29)
                .addGap(35, 35, 35))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                .addContainerGap(81, Short.MAX_VALUE)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                                .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(99, 99, 99)))
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jScrollPane24, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel30Layout.createSequentialGroup()
                                .addComponent(jScrollPane25, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(57, 57, 57))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                        .addComponent(jButton30)
                        .addContainerGap())))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane24, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                    .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane25, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton22)
                    .addComponent(jButton28)
                    .addComponent(jButton29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 118, Short.MAX_VALUE)
                .addComponent(jButton30)
                .addContainerGap())
        );

        jTabbedPane7.addTab("Evaluasi Fitness I (tahap 2)", jPanel30);

        jLabel14.setText("CrossOver");

        jTable25.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane26.setViewportView(jTable25);

        jButton31.setText("Acak Populasi");
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });

        jTable26.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane27.setViewportView(jTable26);

        jButton32.setText("Proses");
        jButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton32ActionPerformed(evt);
            }
        });

        jTable27.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane28.setViewportView(jTable27);

        jButton33.setText("nilai subset offspring");
        jButton33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton33ActionPerformed(evt);
            }
        });

        jButton34.setText("repair");
        jButton34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton34ActionPerformed(evt);
            }
        });

        jButton35.setText("lanjut");
        jButton35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton35ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGap(0, 322, Short.MAX_VALUE)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                .addComponent(jButton31)
                                .addGap(257, 257, 257))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                .addComponent(jButton32)
                                .addGap(273, 273, 273))))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane28)
                            .addComponent(jScrollPane26, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane27)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jButton33)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton34)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton35)))
                        .addContainerGap())
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane26, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane27, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jButton32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane28, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton33)
                    .addComponent(jButton34)
                    .addComponent(jButton35))
                .addContainerGap(79, Short.MAX_VALUE))
        );

        jTabbedPane7.addTab("Reproduksi (Crossover)", jPanel9);

        jTable28.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane29.setViewportView(jTable28);

        jButton36.setText("Acak populasi");
        jButton36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton36ActionPerformed(evt);
            }
        });

        jTable29.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane30.setViewportView(jTable29);

        jTable30.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane31.setViewportView(jTable30);

        jButton37.setText("Hitung subset");
        jButton37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton37ActionPerformed(evt);
            }
        });

        jButton38.setText("repair");
        jButton38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton38ActionPerformed(evt);
            }
        });

        jButton45.setText("Lanjut");
        jButton45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton45ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                        .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane31, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 666, Short.MAX_VALUE)
                            .addComponent(jScrollPane29)
                            .addComponent(jScrollPane30))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                                .addComponent(jButton36)
                                .addGap(261, 261, 261))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                                .addComponent(jButton37)
                                .addGap(253, 253, 253))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                                .addComponent(jButton38)
                                .addGap(270, 270, 270))))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton45)
                .addGap(19, 19, 19))
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jScrollPane29, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane30, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton37)
                .addGap(22, 22, 22)
                .addComponent(jScrollPane31, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton45, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane7.addTab("Reproduksi (Mutasi)", jPanel32);

        jLabel15.setText("Individu parent dan hasil reproduksi");

        jTable31.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane32.setViewportView(jTable31);

        jButton39.setText("proses");
        jButton39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton39ActionPerformed(evt);
            }
        });

        jLabel16.setText("Nilai Subset Parent dan hasil Reproduksi");

        jTable32.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane33.setViewportView(jTable32);

        jButton40.setText("proses");
        jButton40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton40ActionPerformed(evt);
            }
        });

        jButton46.setText("Lanjut");
        jButton46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton46ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(204, 204, 204))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                        .addComponent(jButton39, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(266, 266, 266))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                        .addComponent(jButton40)
                        .addGap(267, 267, 267))))
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton46)
                    .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel33Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jScrollPane33, javax.swing.GroupLayout.DEFAULT_SIZE, 613, Short.MAX_VALUE))
                        .addGroup(jPanel33Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jScrollPane32))
                        .addGroup(jPanel33Layout.createSequentialGroup()
                            .addGap(235, 235, 235)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane32, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton39)
                .addGap(26, 26, 26)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane33, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addComponent(jButton46)
                .addContainerGap())
        );

        jTabbedPane7.addTab("Evaluasi Fitness II (tahap 1)", jPanel33);

        jTable33.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane34.setViewportView(jTable33);

        jTable34.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable34.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane35.setViewportView(jTable34);

        jTable35.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable35.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane36.setViewportView(jTable35);

        jLabel17.setText("Sigma (Xi x Wj)");

        jLabel18.setText("2 Subset Tertinggi");

        jLabel19.setText("Fitness");

        jButton41.setText("Hitung Sigma");
        jButton41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton41ActionPerformed(evt);
            }
        });

        jButton42.setText("Hitung Subset");
        jButton42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton42ActionPerformed(evt);
            }
        });

        jButton43.setText("Hitung Fitness");
        jButton43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton43ActionPerformed(evt);
            }
        });

        jButton44.setText("Lanjut");
        jButton44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton44ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel34Layout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addComponent(jLabel17)
                        .addGap(193, 193, 193)
                        .addComponent(jLabel18)
                        .addGap(33, 33, 33)
                        .addComponent(jLabel19)
                        .addGap(0, 127, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel34Layout.createSequentialGroup()
                        .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel34Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane34, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addGap(18, 18, 18))
                            .addGroup(jPanel34Layout.createSequentialGroup()
                                .addGap(85, 85, 85)
                                .addComponent(jButton41)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel34Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jButton42)
                                .addGap(60, 60, 60)
                                .addComponent(jButton43))
                            .addGroup(jPanel34Layout.createSequentialGroup()
                                .addComponent(jScrollPane35, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane36, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel34Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton44)
                .addGap(20, 20, 20))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel17))
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane35, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane34, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane36, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton41)
                    .addComponent(jButton42)
                    .addComponent(jButton43))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                .addComponent(jButton44)
                .addContainerGap())
        );

        jTabbedPane7.addTab("Evaluasi  Fitness II (tahap 2)", jPanel34);

        jTable36.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane37.setViewportView(jTable36);

        jTable37.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane38.setViewportView(jTable37);

        jButton48.setText("Proses");
        jButton48.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton48ActionPerformed(evt);
            }
        });

        jButton47.setText("SET");
        jButton47.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton47ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane38, javax.swing.GroupLayout.DEFAULT_SIZE, 666, Short.MAX_VALUE)
                    .addComponent(jScrollPane37, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel35Layout.createSequentialGroup()
                        .addComponent(jButton48)
                        .addGap(18, 18, 18)
                        .addComponent(jButton47)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jScrollPane37, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84)
                .addComponent(jScrollPane38, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton48)
                    .addComponent(jButton47))
                .addContainerGap(95, Short.MAX_VALUE))
        );

        jTabbedPane7.addTab("Seleksi (Elitism)", jPanel35);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 845, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane7)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Enhanced Genetic Algorithms", jPanel10);

        jTable38.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane39.setViewportView(jTable38);

        jButton49.setText("proses");
        jButton49.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton49ActionPerformed(evt);
            }
        });

        jLabel20.setText("fitness terbaik");

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addComponent(jScrollPane39, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton49))
                    .addComponent(jLabel20))
                .addContainerGap(624, Short.MAX_VALUE))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addGap(3, 3, 3)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton49)
                    .addComponent(jScrollPane39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(94, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Hasil pengujian", jPanel31);

        jLabel2.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel2.setText("Optimasi Peringkasan Teks Berita Online Dengan Latent Semantic Analysis ");

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 657, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(110, 110, 110))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 657, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(172, 172, 172))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void inialisasipopulasi() {
        String a[] = new String[data.length + 1];
        for (int i = 0; i <= data.length; i++) {
            if (i == 0) {
                a[i] = "Populasi";
            } else if (i >= 1) {
                a[i] = "Urutan-" + String.valueOf((i));
            }
        }
        DefaultTableModel model = new DefaultTableModel(a, inialisasipopulasi);
        jTable20.setModel(model);
        jTable20.setAutoResizeMode(0);
        String label;
        for (int i = 0; i < inialisasipopulasi; i++) {
            label = "p" + (i + 1);
            jTable20.setValueAt(label, i, 0);
        }
        int cek = 0;
        Random random = new Random();
        while (cek < (inialisasipopulasi)) {
            for (int i = 0; i < data.length; i++) {
                randompop.add((i + 1));
            }
            Integer[] ambil = new Integer[data.length];
            for (int i = 0; i < data.length; i++) {
                int n = random.nextInt(randompop.size());
                ambil[i] = randompop.get(n);
                jTable20.setValueAt(ambil[i], cek, (i + 1));
                randompop.remove(n);
            }
            cek++;
        }
        jButton23.setEnabled(false);
    }

    public void FScore() {
        String a[] = new String[2];
        for (int i = 0; i < a.length; i++) {
            if (i == 0) {
                a[i] = "Kalimat Ke-";
            } else if (i == 1) {
                a[i] = "F-Score";
            }
        }
        DefaultTableModel model = new DefaultTableModel(a, data.length);
        jTable22.setModel(model);
        jTable22.setAutoResizeMode(0);
        int b[] = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            b[i] = (i + 1);
            jTable22.setValueAt(b[i], i, 0);
        }
        double tmp[][] = new double[data.length][data.length];
        double arr = 0;

        // set f-score
        for (int i = 0; i < jTable18.getColumnCount(); i++) {
            for (int j = 0; j < jTable18.getRowCount(); j++) {
                arr += Double.valueOf(String.valueOf(jTable18.getValueAt(i, j)));
            }
            jTable22.setValueAt(arr, i, 1);
            arr = 0;
        }
        jButton26.setEnabled(false);
    }

    public void InialisasiSubset() {
        String a[] = new String[data.length + 1];
        for (int i = 0; i <= data.length; i++) {
            if (i == 0) {
                a[i] = "Populasi";
            } else if (i >= 1) {
                a[i] = "Subset-" + String.valueOf((i));
            }
        }
        DefaultTableModel model = new DefaultTableModel(a, inialisasipopulasi);
        jTable21.setModel(model);
        jTable21.setAutoResizeMode(0);
        String label;
        for (int i = 0; i < inialisasipopulasi; i++) {
            label = "p" + (i + 1);
            jTable21.setValueAt(label, i, 0);
        }
        Random r = new Random();
        List<Double> load = new ArrayList<>();
        double sum = 0;
        int cek = 0;
        while (cek < (inialisasipopulasi)) {
            for (int i = 0; i < data.length; i++) {
                double next = r.nextDouble() + 1;
                load.add(next);
                sum += next;
            }
            double scale = 1d * 1 / sum;
            sum = 0;
            for (int i = 0; i < data.length; i++) {
                load.set(i, (load.get(i) * scale));
                sum += load.get(i);
            }

            while (sum++ < 1) {
                int i = r.nextInt(data.length);
                load.set(i, load.get(i));// change
            }
            Double[] ambil = new Double[data.length];
            for (int i = 0; i < data.length; i++) {
                ambil[i] = Double.valueOf(String.valueOf(load.get(i)));
                jTable21.setValueAt(ambil[i], cek, (i + 1));
                ambil[i] = 0d;
            }
            load.clear();
            sum = 0;
            scale = 0;
            cek++;
        }
        jButton25.setEnabled(false);
    }

    public void Fitnes1() {
        String bb[] = new String[data.length + 1];
        for (int k = 0; k <= (data.length); k++) {
            if (k == 0) {
                bb[k] = "offspring";
            } else if (k >= 1) {
                bb[k] = "Urutan-" + (k);
            }
        }
        DefaultTableModel model1 = new DefaultTableModel(bb, (KamaroffspringC + KamaroffspringM + inialisasipopulasi));
        jTable31.setModel(model1);
        jTable31.setAutoResizeMode(0);
        String a[][] = new String[((((KamaroffspringC + KamaroffspringM)) * 2) + (inialisasipopulasi * 2))][data.length];
        int b = inialisasipopulasi;
        int c = 0;
        for (int i = 0; i < (inialisasipopulasi); i++) {
            for (int j = 0; j < data.length + 1; j++) {
                jTable31.setValueAt(jTable20.getValueAt(i, j), i, j);
            }
        }
        for (int i = inialisasipopulasi; i < jTable26.getRowCount() + inialisasipopulasi; i++) {
            for (int j = 0; j < jTable26.getColumnCount(); j++) {
                jTable31.setValueAt(jTable26.getValueAt((i - inialisasipopulasi), j), i, j);
            }
        }

        int d = 1;
        for (int i = inialisasipopulasi + jTable26.getRowCount(); i < jTable26.getRowCount() + inialisasipopulasi + jTable28.getRowCount(); i++) {
            jTable31.setValueAt("c" + (jTable26.getRowCount() + d), i, 0);
            d++;
        }

        System.out.println("haha " + (jTable28.getColumnCount() - 1));
        for (int i = inialisasipopulasi + jTable26.getRowCount(); i < (jTable26.getRowCount() + inialisasipopulasi + jTable28.getRowCount()); i++) {
            for (int j = 0; j < (jTable28.getColumnCount() - 2); j++) {
                jTable31.setValueAt(jTable28.getValueAt((i - (inialisasipopulasi + jTable26.getRowCount())), (j + 2)), i, (j + 1));
            }
        }
        jButton39.setEnabled(false);
    }

    public void HitungXkaliW() {
        String aaa[] = new String[data.length + 2];
        int take = data.length + 1;
        for (int i = 0; i <= (take); i++) {
            if (i == 0) {
                aaa[i] = "Populasi";
            } else if (i >= 1 && i != take) {
                aaa[i] = "(Xi * Wj)" + String.valueOf((i));
            } else if (i == take) {
                aaa[i] = "Sigma";
            }
        }
        DefaultTableModel model = new DefaultTableModel(aaa, inialisasipopulasi);
        jTable19.setModel(model);
        jTable19.setAutoResizeMode(0);
        String label;
        for (int i = 0; i < inialisasipopulasi; i++) {
            label = "p" + (i + 1);
            jTable19.setValueAt(label, i, 0);
        }

        int cek = 1;
        int y = 0;
        double a[][] = new double[inialisasipopulasi][1 + data.length];
        double b[][] = new double[inialisasipopulasi][1 + data.length];
        double c[][] = new double[data.length][data.length];
        double d[][] = new double[inialisasipopulasi][1 + data.length];
        double e = 0;
        boolean ss = true;
        for (int i = 0; i < inialisasipopulasi; i++) {
            for (int j = 1; j <= data.length; j++) {
                a[(i)][(j - 1)] = Double.valueOf(String.valueOf(jTable21.getValueAt(i, j)));
                b[(i)][(j - 1)] = Double.valueOf(String.valueOf(jTable20.getValueAt(i, j)));
            }
        }
        for (int i = 0; i < data.length; i++) {
            c[i][0] = Double.valueOf(String.valueOf(jTable22.getValueAt(i, 0)));
        }
        for (int i = 0; i < inialisasipopulasi; i++) {
            for (int j = 0; j < data.length; j++) {
                for (int k = 0; k < data.length; k++) {
                    if (b[i][j] == c[k][0]) {
                        d[i][j] = Double.valueOf(String.valueOf(jTable22.getValueAt(k, 1)));
                    }
                }
            }
        }
        double sum = 0;
        for (int i = 0; i < inialisasipopulasi; i++) {
            for (int j = 1; j <= (data.length + 1); j++) {
                if (j <= data.length) {
                    e = d[i][(j - 1)] * Double.valueOf(String.valueOf(jTable21.getValueAt(i, j)));
                    sum += e;
                    jTable19.setValueAt(e, i, j);
                } else if (j == (data.length + 1)) {
                    jTable19.setValueAt(sum, i, j);
                }
            }
            sum = 0;
        }
        jButton22.setEnabled(false);
    }

    public void HitungXkaliW2() {
        String aaa[] = new String[data.length + 2];
        int take = data.length + 1;
        for (int i = 0; i <= (take); i++) {
            if (i == 0) {
                aaa[i] = "Populasi";
            } else if (i >= 1 && i != take) {
                aaa[i] = "(Xi * Wj)" + String.valueOf((i));
            } else if (i == take) {
                aaa[i] = "Sigma";
            }
        }
        DefaultTableModel model = new DefaultTableModel(aaa, (KamaroffspringC + KamaroffspringM + inialisasipopulasi));
        jTable33.setModel(model);
        jTable33.setAutoResizeMode(0);
        for (int i = 0; i < jTable32.getRowCount(); i++) {
            jTable33.setValueAt(jTable32.getValueAt(i, 0), i, 0);
        }

        int cek = 1;
        int y = 0;
        double a[][] = new double[inialisasipopulasi + KamaroffspringC + KamaroffspringM][1 + data.length];
        double b[][] = new double[inialisasipopulasi + KamaroffspringC + KamaroffspringM][1 + data.length];
        double c[][] = new double[data.length][data.length];
        double d[][] = new double[inialisasipopulasi + KamaroffspringC + KamaroffspringM][1 + data.length];
        double e = 0;
        boolean ss = true;
        for (int i = 0; i < inialisasipopulasi + KamaroffspringC + KamaroffspringM; i++) {
            for (int j = 1; j <= data.length; j++) {
                a[(i)][(j - 1)] = Double.valueOf(String.valueOf(jTable32.getValueAt(i, j)));
                b[(i)][(j - 1)] = Double.valueOf(String.valueOf(jTable31.getValueAt(i, j)));
            }
        }
        for (int i = 0; i < data.length; i++) {
            c[i][0] = Double.valueOf(String.valueOf(jTable22.getValueAt(i, 0)));
        }
        for (int i = 0; i < inialisasipopulasi + KamaroffspringC + KamaroffspringM; i++) {
            for (int j = 0; j < data.length; j++) {
                for (int k = 0; k < data.length; k++) {
                    if (b[i][j] == c[k][0]) {
                        d[i][j] = Double.valueOf(String.valueOf(jTable22.getValueAt(k, 1)));
                    }
                }
            }
        }
        double sum = 0;
        for (int i = 0; i < inialisasipopulasi + KamaroffspringC + KamaroffspringM; i++) {
            for (int j = 1; j <= (data.length + 1); j++) {
                if (j <= data.length) {
                    e = d[i][(j - 1)] * Double.valueOf(String.valueOf(jTable32.getValueAt(i, j)));
                    sum += e;
                    jTable33.setValueAt(e, i, j);
                } else if (j == (data.length + 1)) {
                    jTable33.setValueAt(sum, i, j);
                }
            }
            sum = 0;
        }
        jButton41.setEnabled(false);
    }

    public void CrossOver() {
        String aaa[] = new String[data.length + 2];
        for (int i = 0; i <= (data.length + 1); i++) {
            if (i == 0) {
                aaa[i] = "CroosOver";
            } else if (i == 1) {
                aaa[i] = "Populasi";
            } else if (i > 1) {
                aaa[i] = "Urutan" + String.valueOf((i - 1));
            }
        }
        DefaultTableModel model = new DefaultTableModel(aaa, ((int) offspringC * 2));
        jTable25.setModel(model);
        jTable25.setAutoResizeMode(0);
        int cekk = 0;
        int ii = 1;
        int jj = 1;
        while (cekk < (offspringC * 2)) {
            if (ii % 2 == 1) {
                jTable25.setValueAt("Crossover" + jj, cekk, 0);
                jj++;
            }
            ii++;
            cekk++;
        }

        System.out.println(offspringC + "OFF");
        System.out.println("Kamar" + KamaroffspringC);
        int cek = 0;

        Random random = new Random();
//        int Makskamar1 = inialisasipopulasi * 2;
        int batasoff = KamaroffspringC * 2;

        String a[][] = new String[batasoff][data.length + 1];
        int offspring[][] = new int[data.length][data.length];
        for (int i = 0; i < inialisasipopulasi; i++) {
            randompopC.add(i + 1);
        }
        int ambil[] = new int[(batasoff)];
        for (int i = 0; i < (batasoff); i++) {
            int n = random.nextInt(randompopC.size());
            ambil[i] = randompopC.get(n);
            System.out.println("AMBIL = " + ambil[i]);
        }
        System.out.println("Length" + ambil.length);

        // ambil data inialisasi populasi bedasarkan random
        for (int i = 0; i < (batasoff); i++) {
            for (int j = 0; j < (data.length + 1); j++) {
                a[i][j] = String.valueOf(jTable20.getValueAt((ambil[i] - 1), (j)));
                System.out.println(a[i][j]);
            }
        }

////        set value
        for (int i = 0; i < batasoff; i++) {
            for (int j = 1; j < data.length + 2; j++) {
                jTable25.setValueAt(a[(i)][(j - 1)], i, (j));
            }
        }
//        int i = 1;
//
//        int k = 0;
//        int l = 1;
//        while (cek < (offspringC * 5)) {
//            if (i % 5 == 1 || i % 5 == 2) {
//                while (l < data.length + 2) {
//                    jTable25.setValueAt(a[(k)][(l - 1)], k, l);
//                    l++;
//                }
//            }
//            l = 1;
//            k = 1;
//            i++;
//            cek++;
//        }
////        
//        // random titik potong
//        int tp[] = new int[data.length];
//        int z = random.nextInt(data.length);
        jButton31.setEnabled(false);
    }

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(8);
        jTabbedPane7.setSelectedIndex(0);
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        // TODO add your handling code here:
        jTabbedPane6.setSelectedIndex(3);
        //disini proses LSI
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        // TODO add your handling code here:
        jTabbedPane6.setSelectedIndex(2);
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // TODO add your handling code here:
        jTabbedPane6.setSelectedIndex(1);
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        reducematrixLSI();
        String a3[] = new String[2];
        for (int i = 0; i < a3.length; i++) {
            if (i == 0) {
                a3[i] = "Generasi";
            } else if (i == 1) {
                a3[i] = "Fitness";
            }
        }
        DefaultTableModel model3 = new DefaultTableModel(a3, jumlahgenerasi);
        jTable38.setModel(model3);
        jTable38.setAutoResizeMode(0);
        for (int i = 0; i < jumlahgenerasi; i++) {
            jTable38.setValueAt("gen ke-" + (i + 1), i, 0);

        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(7);
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
        jTabbedPane5.setSelectedIndex(2);
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        jTabbedPane5.setSelectedIndex(1);
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(6);

        SVD();
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        jTabbedPane4.setSelectedIndex(4);
        pembobotanTFIDF();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        jTabbedPane4.setSelectedIndex(3);

        pembobotanIDF();
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        jTabbedPane4.setSelectedIndex(2);

        perhitunganDF();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        jTabbedPane4.setSelectedIndex(1);

        pembobotanTF();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed

        // TODO add your handling code here:
        //TFIDF
        jTabbedPane1.setSelectedIndex(5);

        rawTF();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        //stemming
        stemmingnazief();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        //filtering
        jTabbedPane1.setSelectedIndex(3);
        filtering();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        //tokenizing
        jTabbedPane1.setSelectedIndex(2);
        tokenizing();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        //hapus karakter non abjad
        jTabbedPane3.setSelectedIndex(1);
        hapusnonabjad();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        //casefolding lowercase
        jTabbedPane1.setSelectedIndex(1);

        lowercase();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        jTextArea1.setText("");
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        //proses data
        jTabbedPane2.setSelectedIndex(1);
        String a = jTextArea1.getText();

        data = a.split("\n\n");

        int k = 0;
        String[] temp;
        for (int i = 0; i < data.length; i++) {
            temp = data[i].split("");
            if (temp.length > k) {
                k = temp.length;

            } else {
                temp = null;
            }

        }

        System.out.println("k =" + k);
        DefaultTableModel tableModel = new DefaultTableModel(header1, data.length);
        jTable1.setModel(tableModel);
        jTable1.setAutoResizeMode(0);
        TableColumnModel columnModel = jTable1.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(40);
        columnModel.getColumn(1).setPreferredWidth(6 * k);
        System.out.println("sizenya : " + jTable1);

        for (int i = 0; i < data.length; i++) {
            jTable1.setRowHeight(i, 30);
            jTable1.setValueAt(i + 1, i, 0);
            jTable1.setValueAt(data[i], i, 1);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (jFileChooser1.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File datateks = jFileChooser1.getSelectedFile();
            String filedatateks = datateks.getAbsolutePath();
            try {
                readDokumenTeks(filedatateks, jTextArea1);
            } catch (IOException ex) {
                Logger.getLogger(alev.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        // TODO add your handling code here:
        inialisasipopulasi();


    }//GEN-LAST:event_jButton23ActionPerformed

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        // TODO add your handling code here:  
        InialisasiSubset();
    }//GEN-LAST:event_jButton25ActionPerformed

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
        // TODO add your handling code here:
        FScore();


    }//GEN-LAST:event_jButton26ActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        // TODO add your handling code here:
        jTabbedPane7.setSelectedIndex(1);
    }//GEN-LAST:event_jButton24ActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        // TODO add your handling code here:
        jTabbedPane7.setSelectedIndex(2);
    }//GEN-LAST:event_jButton27ActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        HitungXkaliW();
    }//GEN-LAST:event_jButton22ActionPerformed

    public static double[] sortArray(double[] nonSortedArray) {
        double[] sortedArray = new double[nonSortedArray.length];
        double temp;
        for (int i = 0; i <= nonSortedArray.length; i++) {
            for (int j = i + 1; j < nonSortedArray.length; j++) {
                if (nonSortedArray[i] < nonSortedArray[j]) {
                    temp = nonSortedArray[i];
                    nonSortedArray[i] = nonSortedArray[j];
                    nonSortedArray[j] = temp;
                    sortedArray = nonSortedArray;
                }
            }
        }
        return sortedArray;
    }

    public void subset1() {
        int[] summatrikrawtf = new int[data.length];
        double a[][] = new double[jTable19.getRowCount()][jTable19.getColumnCount() - 2];//sigma x*w
        double inisialisasikromosom[][] = new double[jTable20.getRowCount()][jTable20.getColumnCount() - 1];//inisialisasi kromosom

        String[] headerr = new String[2];
        headerr[0] = "Populasi";
        headerr[1] = "Persentase";

        //membuat table 23
        DefaultTableModel tableModel23 = new DefaultTableModel(headerr, inialisasipopulasi);
        jTable23.setModel(tableModel23);
        jTable23.setAutoResizeMode(0);
        TableColumnModel columnModel23 = jTable23.getColumnModel();

        for (int i = 0; i < inialisasipopulasi; i++) {
            jTable23.setValueAt("p" + (i + 1), i, 0);
        }

        for (int i = 0; i < jTable7.getRowCount(); i++) {
            for (int j = 0; j < jTable7.getColumnCount() - 1; j++) {
                matrikrawtf[i][j] = Integer.valueOf(String.valueOf(jTable7.getValueAt(i, (j + 1))));
            }
        }
        for (int i = 0; i < jTable20.getRowCount(); i++) {
            for (int j = 0; j < jTable20.getColumnCount() - 1; j++) {
                inisialisasikromosom[i][j] = Integer.valueOf(String.valueOf(jTable20.getValueAt(i, (j + 1))));
            }
        }

        for (int i = 0; i < matrikrawtf[0].length; i++) {
            int temp = 0;
            for (int j = 0; j < matrikrawtf.length; j++) {
                temp = temp + matrikrawtf[j][i];
            }
            summatrikrawtf[i] = temp;
        }

        for (int i = 0; i < jTable19.getRowCount(); i++) {
            for (int j = 0; j < jTable19.getColumnCount() - 2; j++) {
                a[i][j] = Double.valueOf(String.valueOf(jTable19.getValueAt(i, j + 1)));
            }
        }
        //=========================

        double besar1 = 0;
        double besar2 = 0;

        int besar11 = 0;
        int besar22 = 0;
        for (int i = 0; i < a.length; i++) {
            double sorting[] = new double[a[0].length];
            for (int j = 0; j < a[0].length; j++) {
                sorting[j] = a[i][j];
            }
            sorting = sortArray(sorting);//sorting dari besar ke kecil, index 0 terbesar
            besar1 = sorting[0];
            besar2 = sorting[1];
            for (int j = 0; j < a[0].length; j++) {
                if (a[i][j] == sorting[0]) {
                    besar11 = j;
                    break;
                }
            }
            for (int j = 0; j < a[0].length; j++) {
                if (j == besar11) {
                    continue;
                }
                if (a[i][j] == sorting[1]) {
                    besar22 = j;
                    break;
                }
            }

            System.out.println("besar 1 = " + besar1);
            System.out.println("besar 1 index = " + besar11);
            System.out.println("besar 2 = " + besar2);
            System.out.println("besar 2 index = " + besar22);

            besar11 = (int) inisialisasikromosom[i][besar11];
            besar22 = (int) inisialisasikromosom[i][besar22];

            besar1 = summatrikrawtf[besar11 - 1];
            besar2 = summatrikrawtf[besar22 - 1];

            System.out.println("kalimat terbesar 1 = " + besar11);
            System.out.println("kalimat terbesar 2 = " + besar22);

            double jumlah = 0;
            double nilaipersentase = 0;
            for (int j = 0; j < summatrikrawtf.length; j++) {
                jumlah = jumlah + summatrikrawtf[j];
            }

            nilaipersentase = (besar1 + besar2) / jumlah;
            jTable23.setValueAt(nilaipersentase, i, 1);

        }
        jButton28.setEnabled(false);
    }
    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed
        // TODO add your handling code here:
        subset1();
    }//GEN-LAST:event_jButton28ActionPerformed
    public void fitnesfinal1() {
        String[] headerr = new String[2];
        headerr[0] = "Populasi";
        headerr[1] = "Fitness";

        //membuat table 24
        DefaultTableModel tableModel24 = new DefaultTableModel(headerr, inialisasipopulasi);
        jTable24.setModel(tableModel24);
        jTable24.setAutoResizeMode(0);
        TableColumnModel columnModel24 = jTable24.getColumnModel();

        for (int i = 0; i < inialisasipopulasi; i++) {
            jTable24.setValueAt("p" + (i + 1), i, 0);
            jTable24.setValueAt(Double.valueOf(String.valueOf(jTable19.getValueAt(i, jTable19.getColumnCount() - 1))) + Double.valueOf(String.valueOf(jTable23.getValueAt(i, 1))), i, 1);
        }
        jButton29.setEnabled(false);
    }
    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton29ActionPerformed
        // TODO add your handling code here:
        fitnesfinal1();

    }//GEN-LAST:event_jButton29ActionPerformed

    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton30ActionPerformed
        // TODO add your handling code here:
        jTabbedPane7.setSelectedIndex(3);
    }//GEN-LAST:event_jButton30ActionPerformed

    public void CrossOver2() {
        //membuat table 26

        String a[] = new String[data.length + 1];
        for (int i = 0; i < (data.length + 1); i++) {
            if (i == 0) {
                a[i] = "CroosOver";
            } else {
                a[i] = "Urutan" + String.valueOf((i));
            }
        }
        DefaultTableModel tableModel26 = new DefaultTableModel(a, (int) offspringC);
        jTable26.setModel(tableModel26);
        jTable26.setAutoResizeMode(0);
        TableColumnModel columnModel26 = jTable26.getColumnModel();

        for (int i = 0; i < jTable26.getRowCount(); i++) {  //baris
            jTable26.setValueAt("c" + (i + 1), i, 0);
        }

        String[][] cross1 = new String[(int) offspringC][data.length - titikpotong];
        int crosss = 0;
        //CROSSOVER PARENT 1
        int g = 0;
        for (int i = 0; i < jTable25.getRowCount(); i++) {
            if (i % 2 != 0) {
                continue;
            }
            for (int j = 0; j < jTable25.getColumnCount() - 2; j++) {
                if (j < titikpotong) {
                    jTable26.setValueAt(jTable25.getValueAt(i, j + 2), g, j + 1);

                } else {
                    cross1[g][crosss] = String.valueOf(jTable25.getValueAt(i, j + 2));
                    crosss++;
                }
            }
            crosss = 0;
            g = g + 1;
        }

        System.out.println("crosss");
        for (int i = 0; i < cross1.length; i++) {
            for (int j = 0; j < cross1[0].length; j++) {
                System.out.print(" " + cross1[i][j]);
            }
            System.out.println("");
        }
        System.out.println("");

        //CROSSOVER PARENT 2
        int p = 0;
        int x = 0;
        for (int i = 0; i < jTable25.getRowCount(); i++) {
            if (i % 2 == 0) {
                continue;
            }
            for (int j = 0 + 2; j < jTable25.getColumnCount(); j++) {
                System.out.print(" " + jTable25.getValueAt(i, j));
                for (int k = 0; k < cross1[0].length; k++) {
                    if (cross1[p][k].equals(jTable25.getValueAt(i, j))) {
                        //                        System.out.println("cuk masuk");
                        jTable26.setValueAt(cross1[p][k], p, x + 1 + titikpotong);
                        x++;
                        break;
                    }
                }
            }
            System.out.println("");
            p++;
            x = 0;
        }

        //        //crossover parent 3
        //        int v = 0;
        //        for (int i = 0; i < jTable25.getRowCount(); i++) {
        //            if (i % 2 == 0) {
        //                continue;
        //            }
        //            for (int j = titikpotong; j <= jTable25.getColumnCount(); j++) {
        //                jTable26.setValueAt(jTable25.getValueAt(i, j + 2), v, j + 1);
        //            }
        //            v = v + 1;
        //        }
        jButton32.setEnabled(false);
    }

    private void jButton32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton32ActionPerformed
        // TODO add your handling code here:
        CrossOver2();
    }//GEN-LAST:event_jButton32ActionPerformed

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton31ActionPerformed
        // TODO add your handling code here:

        CrossOver();
    }//GEN-LAST:event_jButton31ActionPerformed

    public void CrossOver3() {
        //membuat table 27
        String a[] = new String[data.length + 1];
        for (int i = 0; i < (data.length + 1); i++) {
            if (i == 0) {
                a[i] = "CroosOver";
            } else {
                a[i] = "Subset-" + String.valueOf((i));
            }
        }
        DefaultTableModel tableModel27 = new DefaultTableModel(a, (int) offspringC);
        jTable27.setModel(tableModel27);
        jTable27.setAutoResizeMode(0);
        TableColumnModel columnModel27 = jTable27.getColumnModel();

        for (int i = 0; i < jTable27.getRowCount(); i++) {
            jTable27.setValueAt("c" + (i + 1), i, 0);

        }
        int g = 0;
        int tem = 0;
        String tempp = "";
        for (int i = 0; i < jTable21.getRowCount(); i++) {
            if (i % 2 != 0) {
                continue;
            }
            tempp = String.valueOf(jTable25.getValueAt(i, 1));
            System.out.println("temp = " + tempp);

            for (int c = 0; c < jTable21.getRowCount(); c++) {
                if (tempp.equals(jTable21.getValueAt(c, 0))) {
                    tem = c;
                }
            }

            for (int j = 0; j < jTable21.getColumnCount() - 1; j++) {

                if (j < titikpotong) {
                    jTable27.setValueAt(jTable21.getValueAt(tem, j + 1), g, j + 1);
                }
            }
            g = g + 1;
        }
//-----------------------------------------------------------------------
        int p = 0;

        int[][] temp2 = new int[(int) offspringC][data.length];
        String[] han = new String[(int) offspringC];
        int han2 = 0;
        for (int i = 0; i < jTable25.getRowCount(); i++) {
            if (i % 2 == 0) {
                continue;
            }
            han[han2] = String.valueOf(jTable25.getValueAt(i, 1));
            han2++;
            for (int j = 0; j < jTable25.getColumnCount() - 2; j++) {
                temp2[p][j] = Integer.valueOf(String.valueOf(jTable25.getValueAt(i, j + 2)));
            }

            p++;

        }

        int[][] temp3 = new int[(int) offspringC][data.length - titikpotong];
        for (int i = 0; i < jTable26.getRowCount(); i++) {
            for (int j = 0; j < jTable26.getColumnCount() - 1 - titikpotong; j++) {
                temp3[i][j] = Integer.valueOf(String.valueOf(jTable26.getValueAt(i, j + 1 + titikpotong)));
            }
        }

        System.out.println("hhan");
        for (int i = 0; i < han.length; i++) {
            System.out.print(" " + han[i]);
        }
//
//        ///terlagi
//        System.out.println("temp3");
//        for (int i = 0; i < temp3.length; i++) {
//            for (int j = 0; j < temp3[0].length; j++) {
//                System.out.print(" " + temp3[i][j]);
//            }
//            System.out.println("");
//        }
        int haha = 0;
        int kolom = 0;
        int cuk = 0;
        for (int i = 0; i < temp2.length; i++) {
            for (int j = 0; j < temp2[0].length; j++) {
                for (int k = 0; k < temp3[0].length; k++) {
                    if (temp2[i][j] == temp3[i][k]) {
                        haha = j;

                        System.out.println(" masuk " + temp2[i][j] + " dan " + temp3[i][k]);
                        for (int l = 0; l < jTable21.getRowCount(); l++) {
                            if (String.valueOf(jTable21.getValueAt(l, 0)).equals(han[i])) {
                                cuk = l;
                            }
                        }
                        jTable27.setValueAt(jTable21.getValueAt(cuk, haha + 1), i, kolom + 1 + titikpotong);
                        kolom++;
                        break;
                    }
                }

            }
            haha = 0;
            kolom = 0;

        }
        jButton33.setEnabled(false);
    }
    private void jButton33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton33ActionPerformed
        // TODO add your handling code here:
        CrossOver3();
    }//GEN-LAST:event_jButton33ActionPerformed

    public void CrossOver4() {
        double test = 0;
        for (int i = 0; i < jTable27.getRowCount(); i++) {
            for (int j = 0; j < jTable27.getColumnCount() - 1; j++) {
                test = test + Double.valueOf(String.valueOf(jTable27.getValueAt(i, j + 1)));

            }
            System.out.println("tesst = " + test);
            if (test != 1) {
                for (int j = 0; j < jTable27.getColumnCount() - 1; j++) {
                    jTable27.setValueAt(Double.valueOf(String.valueOf(jTable27.getValueAt(i, j + 1))) / test, i, j + 1);
                }
            }
            test = 0;

        }

        jButton34.setEnabled(false);
    }
    private void jButton34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton34ActionPerformed
        // TODO add your handling code here:
        CrossOver4();
    }//GEN-LAST:event_jButton34ActionPerformed

    public void Mutasi() {
        String aaa[] = new String[data.length + 2];
        for (int i = 0; i <= (data.length + 1); i++) {
            if (i == 0) {
                aaa[i] = "Mutasi";
            } else if (i == 1) {
                aaa[i] = "Populasi";
            } else if (i > 1) {
                aaa[i] = "Urutan" + String.valueOf((i - 1));
            }
        }
        DefaultTableModel model = new DefaultTableModel(aaa, ((int) offspringM));
        jTable28.setModel(model);
        jTable28.setAutoResizeMode(0);
        int cekk = 0;
        int ii = 1;
        int jj = 1;
        while (cekk < (offspringM)) {

            jTable28.setValueAt("Mutasi Ke " + jj, cekk, 0);
            jj++;

            ii++;
            cekk++;
        }

        int cek = 0;

        Random random = new Random();
        int batasoff = KamaroffspringM;

        String a[][] = new String[batasoff][data.length + 1];
        int offspring[][] = new int[data.length][data.length];
        for (int i = 0; i < inialisasipopulasi; i++) {
            randompopM.add(i + 1);
        }
        int ambil[] = new int[(batasoff)];
        for (int i = 0; i < (batasoff); i++) {
            int n = random.nextInt(randompopM.size());
            ambil[i] = randompopM.get(n);
            System.out.println("AMBIL = " + ambil[i]);
        }
        System.out.println("Length" + ambil.length);
        // ambil data inialisasi populasi bedasarkan random
        for (int i = 0; i < (batasoff); i++) {
            for (int j = 0; j < (data.length + 1); j++) {
                a[i][j] = String.valueOf(jTable20.getValueAt((ambil[i] - 1), (j)));
                System.out.println(a[i][j]);
            }
        }
////        set value
        for (int i = 0; i < batasoff; i++) {
            for (int j = 1; j < data.length + 2; j++) {
                jTable28.setValueAt(a[(i)][(j - 1)], i, (j));
            }
        }
        jButton36.setEnabled(false);
    }

    private void jButton36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton36ActionPerformed
        // TODO add your handling code here:

        Mutasi();
    }//GEN-LAST:event_jButton36ActionPerformed

    public void Mutasi2() {

        //aa
        int cekk = 0;
        int ii = 1;
        int jj = 1;
        //bb
        int cek2 = 0;
        int l = 1;
        int m = 1;
        String b[] = new String[data.length + 1];
        for (int k = 0; k <= (data.length); k++) {
            if (k == 0) {
                b[k] = "offspring";
            } else if (k >= 1) {
                b[k] = "urutan ke-" + (k);
            }
        }
        DefaultTableModel model1 = new DefaultTableModel(b, ((int) offspringM * 2));
        jTable29.setModel(model1);
        jTable29.setAutoResizeMode(0);
        //aa
        while (cekk < (offspringM * 2)) {
            if (ii % 2 == 1) {
                jTable29.setValueAt("C" + jj, cekk, 0);

                jj++;
            }
            ii++;
            cekk++;
        }
        //bb
        while (cek2 < (offspringM * 2)) {
            if (l % 2 == 0) {
                jTable29.setValueAt("Subset-" + m, cek2, 0);
                m++;
            }
            l++;
            cek2++;
        }
        //get value  buat mutasi
        int batasoff = KamaroffspringM * 2;
        int y = 0;
        String a[][] = new String[batasoff][data.length + 1];
        for (int i = 0; i < batasoff; i++) {
            for (int j = 1; j < data.length + 1; j++) {
                if (y < batasoff) {
                    jTable29.setValueAt(jTable28.getValueAt(i, (j + 1)), y, j);

                }
            }
            y += 2;
        }
        //get p pada mutasi
        String c[] = new String[KamaroffspringM];
        for (int i = 0; i < KamaroffspringM; i++) {
            c[i] = String.valueOf(jTable28.getValueAt(i, 1));
        }
        String d[] = new String[inialisasipopulasi];
        int e[] = new int[inialisasipopulasi];
        //get p pada inialisasi subset
        for (int i = 0; i < inialisasipopulasi; i++) {
            d[i] = String.valueOf(jTable21.getValueAt(i, 0));
        }

        //table 21 index 0 , mengambil populasi yang sama
        for (int i = 0; i < KamaroffspringM; i++) {
            for (int j = 0; j < inialisasipopulasi; j++) {
                if (c[i].equalsIgnoreCase(d[j])) {
                    e[i] = j;
//                    System.out.println("INIII LHO = "+e[i]);
                }
            }
        }

        double arr1[][] = new double[KamaroffspringM][data.length + 1];
        // mengambil nilai subset pada inialisasi populasi 
        for (int i = 0; i < KamaroffspringM; i++) {
            for (int j = 0; j < data.length; j++) {
                arr1[i][j] = Double.valueOf(String.valueOf(jTable21.getValueAt(e[i], (j + 1))));
            }
        }
        int z = 1;
        int x = 0;
        //set subset pada kamar
        for (int i = 0; i < KamaroffspringM; i++) {
            for (int j = 0; j < data.length; j++) {
                jTable29.setValueAt(arr1[i][(j)], z, (j + 1));

            }
            z += 2;
        }
        jButton37.setEnabled(false);
    }
    private void jButton37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton37ActionPerformed
        // TODO add your handling code here:
        Mutasi2();
    }//GEN-LAST:event_jButton37ActionPerformed
    public void Mutasi3() {
        String b[] = new String[data.length + 1];
        for (int k = 0; k <= (data.length); k++) {
            if (k == 0) {
                b[k] = "Repair";
            } else if (k >= 1) {
                b[k] = "Urutan ke" + k;
            }
        }
        DefaultTableModel model1 = new DefaultTableModel(b, ((int) offspringM));
        jTable30.setModel(model1);
        jTable30.setAutoResizeMode(0);
        for (int i = 0; i < (int) offspringM; i++) {
            jTable30.setValueAt("C" + (i + 1), i, 0);
        }
        double sigma[] = new double[KamaroffspringM];
        double each[][] = new double[KamaroffspringM][data.length + 1];
        double min[] = new double[data.length + 1];
        int y = 1;
        int z = 1;
        int ss = 1;

        //cek kecil
        double aa = 10000;
        int a = 0;
        for (int i = 0; i < KamaroffspringM; i++) {
            for (int j = 0; j < data.length; j++) {
                if (Double.valueOf(String.valueOf(jTable29.getValueAt(y, (j + 1)))) < aa) {
                    aa = Double.valueOf(String.valueOf(jTable29.getValueAt(y, (j + 1))));
                    a = j;
                }
            }
            if (TerbesarM < aa) {
                jTable29.setValueAt(aa, y, a + 1);
            } else if (TerbesarM > aa) {
                jTable29.setValueAt(TerbesarM, y, a + 1);
            }

            aa = 10000;
            a = 0;
            y += 2;

        }
        for (int i = 0; i < KamaroffspringM; i++) {
            for (int j = 0; j < data.length; j++) {
                sigma[i] += Double.valueOf(String.valueOf(jTable29.getValueAt(z, (j + 1))));
            }
            z += 2;
        }
        for (int i = 0; i < KamaroffspringM; i++) {
            for (int j = 0; j < data.length; j++) {
                each[i][j] = Double.valueOf(String.valueOf(jTable29.getValueAt(ss, (j + 1))));
                each[i][j] /= sigma[i];
                jTable30.setValueAt(each[i][j], i, (j + 1));
            }
            ss += 2;
        }
        jButton38.setEnabled(false);
    }
    private void jButton38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton38ActionPerformed
        // TODO add your handling code here:
        Mutasi3();
    }//GEN-LAST:event_jButton38ActionPerformed

    private void jButton39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton39ActionPerformed
        // TODO add your handling code here:
        Fitnes1();
    }//GEN-LAST:event_jButton39ActionPerformed

    private void jButton35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton35ActionPerformed
        // TODO add your handling code here:
        jTabbedPane7.setSelectedIndex(4);
    }//GEN-LAST:event_jButton35ActionPerformed

    public void fitnes2() {
        String bb[] = new String[data.length + 1];
        for (int k = 0; k <= (data.length); k++) {
            if (k == 0) {
                bb[k] = "offspring";
            } else if (k >= 1) {
                bb[k] = "Subset-" + (k);
            }
        }
        DefaultTableModel model32 = new DefaultTableModel(bb, (KamaroffspringC + KamaroffspringM + inialisasipopulasi));
        jTable32.setModel(model32);
        jTable32.setAutoResizeMode(0);
        String a[][] = new String[((((KamaroffspringC + KamaroffspringM)) * 2) + (inialisasipopulasi * 2))][data.length];
        int b = inialisasipopulasi;
        int c = 0;
        for (int i = 0; i < (inialisasipopulasi); i++) {
            for (int j = 0; j < data.length + 1; j++) {
                jTable32.setValueAt(jTable21.getValueAt(i, j), i, j);
            }
        }
        for (int i = inialisasipopulasi; i < jTable26.getRowCount() + inialisasipopulasi; i++) {
            for (int j = 0; j < jTable26.getColumnCount(); j++) {
                jTable32.setValueAt(jTable27.getValueAt((i - inialisasipopulasi), j), i, j);
            }
        }

        int d = 1;
        for (int i = inialisasipopulasi + jTable26.getRowCount(); i < jTable26.getRowCount() + inialisasipopulasi + jTable28.getRowCount(); i++) {
            jTable32.setValueAt("c" + (jTable26.getRowCount() + d), i, 0);
            d++;
        }

        System.out.println("haha " + (jTable28.getColumnCount() - 1));
        for (int i = inialisasipopulasi + jTable26.getRowCount(); i < (jTable26.getRowCount() + inialisasipopulasi + jTable28.getRowCount()); i++) {
            for (int j = 0; j < (jTable30.getColumnCount() - 1); j++) {
                jTable32.setValueAt(jTable30.getValueAt((i - (inialisasipopulasi + jTable26.getRowCount())), (j + 1)), i, (j + 1));
            }
        }
        jButton40.setEnabled(false);
    }
    private void jButton40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton40ActionPerformed
        // TODO add your handling code here:
        fitnes2();
    }//GEN-LAST:event_jButton40ActionPerformed

    public void fitnes21() {
        int[] summatrikrawtf = new int[data.length];
        double a[][] = new double[jTable33.getRowCount()][jTable33.getColumnCount() - 2];//sigma x*w
        double inisialisasikromosom[][] = new double[jTable31.getRowCount()][jTable31.getColumnCount() - 1];//inisialisasi kromosom

        String[] headerr = new String[2];
        headerr[0] = "Populasi";
        headerr[1] = "Persentase";

        //membuat table 34
        DefaultTableModel tableModel34 = new DefaultTableModel(headerr, jTable33.getRowCount());
        jTable34.setModel(tableModel34);
        jTable34.setAutoResizeMode(0);
        TableColumnModel columnModel34 = jTable34.getColumnModel();

        for (int i = 0; i < jTable33.getRowCount(); i++) {
            jTable34.setValueAt(jTable31.getValueAt(i, 0), i, 0);
        }

        for (int i = 0; i < jTable7.getRowCount(); i++) {
            for (int j = 0; j < jTable7.getColumnCount() - 1; j++) {
                matrikrawtf[i][j] = Integer.valueOf(String.valueOf(jTable7.getValueAt(i, (j + 1))));
            }
        }
        for (int i = 0; i < jTable31.getRowCount(); i++) {
            for (int j = 0; j < jTable31.getColumnCount() - 1; j++) {
                inisialisasikromosom[i][j] = Integer.valueOf(String.valueOf(jTable31.getValueAt(i, (j + 1))));
            }
        }

        for (int i = 0; i < matrikrawtf[0].length; i++) {
            int temp = 0;
            for (int j = 0; j < matrikrawtf.length; j++) {
                temp = temp + matrikrawtf[j][i];
            }
            summatrikrawtf[i] = temp;
        }

        for (int i = 0; i < jTable33.getRowCount(); i++) {
            for (int j = 0; j < jTable33.getColumnCount() - 2; j++) {
                a[i][j] = Double.valueOf(String.valueOf(jTable33.getValueAt(i, j + 1)));
            }
        }
        //=========================

        double besar1 = 0;
        double besar2 = 0;

        int besar11 = 0;
        int besar22 = 0;
        for (int i = 0; i < a.length; i++) {
            System.out.println("-----------------------------");
            double sorting[] = new double[a[0].length];
            for (int j = 0; j < a[0].length; j++) {
                sorting[j] = a[i][j];
            }
            sorting = sortArray(sorting);//sorting dari besar ke kecil, index 0 terbesar
            besar1 = sorting[0];
            besar2 = sorting[1];
            for (int j = 0; j < a[0].length; j++) {
                if (a[i][j] == sorting[0]) {
                    besar11 = j;
                    break;
                }
            }
            for (int j = 0; j < a[0].length; j++) {
                if (j == besar11) {
                    continue;
                }
                if (a[i][j] == sorting[1]) {
                    besar22 = j;
                    break;
                }
            }

            System.out.println("besar 1 = " + besar1);
            System.out.println("besar 1 index = " + besar11);
            System.out.println("besar 2 = " + besar2);
            System.out.println("besar 2 index = " + besar22);

            besar11 = (int) inisialisasikromosom[i][besar11];
            besar22 = (int) inisialisasikromosom[i][besar22];

            besar1 = summatrikrawtf[besar11 - 1];
            besar2 = summatrikrawtf[besar22 - 1];

            System.out.println("kalimat terbesar 1 = " + besar11);
            System.out.println("kalimat terbesar 2 = " + besar22);

            double jumlah = 0;
            double nilaipersentase = 0;
            for (int j = 0; j < summatrikrawtf.length; j++) {
                jumlah = jumlah + summatrikrawtf[j];
            }

            nilaipersentase = (besar1 + besar2) / jumlah;
            jTable34.setValueAt(nilaipersentase, i, 1);

        }
        jButton42.setEnabled(false);
    }
    private void jButton42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton42ActionPerformed
        // TODO add your handling code here:
        fitnes21();
    }//GEN-LAST:event_jButton42ActionPerformed

    private void jButton45ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton45ActionPerformed
        // TODO add your handling code here:
        jTabbedPane7.setSelectedIndex(5);
    }//GEN-LAST:event_jButton45ActionPerformed

    private void jButton41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton41ActionPerformed
        // TODO add your handling code here:

        HitungXkaliW2();

    }//GEN-LAST:event_jButton41ActionPerformed

    public void fitnesfinal2() {

        String[] headerr = new String[2];
        headerr[0] = "Populasi";
        headerr[1] = "Fitness";

        //membuat table 35
        DefaultTableModel tableModel35 = new DefaultTableModel(headerr, jTable33.getRowCount());
        jTable35.setModel(tableModel35);
        jTable35.setAutoResizeMode(0);
        TableColumnModel columnModel35 = jTable35.getColumnModel();

        for (int i = 0; i < jTable33.getRowCount(); i++) {
            jTable35.setValueAt(jTable33.getValueAt(i, 0), i, 0);
            jTable35.setValueAt(Double.valueOf(String.valueOf(jTable33.getValueAt(i, jTable33.getColumnCount() - 1))) + Double.valueOf(String.valueOf(jTable34.getValueAt(i, 1))), i, 1);
        }

        jButton43.setEnabled(false);

    }

    private void jButton43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton43ActionPerformed
        // TODO add your handling code here:
        fitnesfinal2();
    }//GEN-LAST:event_jButton43ActionPerformed

    private void jButton46ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton46ActionPerformed
        // TODO add your handling code here:
        jTabbedPane7.setSelectedIndex(6);
    }//GEN-LAST:event_jButton46ActionPerformed

    private void jButton44ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton44ActionPerformed
        // TODO add your handling code here:
        jTabbedPane7.setSelectedIndex(7);
    }//GEN-LAST:event_jButton44ActionPerformed

    public void elitism() {
        String a[] = new String[data.length + 1];
        for (int i = 0; i <= data.length; i++) {
            if (i == 0) {
                a[i] = "Populasi";
            } else if (i >= 1) {
                a[i] = "Subset-" + String.valueOf((i));
            }
        }
        DefaultTableModel model = new DefaultTableModel(a, inialisasipopulasi);
        jTable37.setModel(model);
        jTable37.setAutoResizeMode(0);
        String label;
        for (int i = 0; i < inialisasipopulasi; i++) {
            label = "p" + (i + 1);
            jTable37.setValueAt(label, i, 0);
        }

        String aa[] = new String[data.length + 1];
        for (int i = 0; i <= data.length; i++) {
            if (i == 0) {
                aa[i] = "Populasi";
            } else if (i >= 1) {
                aa[i] = "Urutan-" + String.valueOf((i));
            }
        }
        DefaultTableModel model2 = new DefaultTableModel(aa, inialisasipopulasi);
        jTable36.setModel(model2);
        jTable36.setAutoResizeMode(0);
        String label2;
        for (int i = 0; i < inialisasipopulasi; i++) {
            label2 = "p" + (i + 1);
            jTable36.setValueAt(label2, i, 0);
        }

        double[] simpanfitness = new double[jTable35.getRowCount()];

        for (int i = 0; i < jTable35.getRowCount(); i++) {
            simpanfitness[i] = Double.valueOf(String.valueOf(jTable35.getValueAt(i, 1)));
        }

        Arrays.sort(simpanfitness);

        System.out.println("hasil sorting");
        for (int i = 0; i < simpanfitness.length; i++) {
            System.out.println("" + simpanfitness[i]);
        }

        jTable38.setValueAt(simpanfitness[jTable35.getRowCount() - 1], countergenerasi, 1);
        countergenerasi++;
        int haha = -1;
        int counter = 0;
        int cu = jTable35.getRowCount() - 1;
        for (int i = 0; i < inialisasipopulasi; i++) {
            for (int j = 0; j < jTable35.getRowCount(); j++) {
                if (Double.valueOf(String.valueOf(jTable35.getValueAt(j, 1))) == simpanfitness[cu]) {
                    System.out.println("");
                    if (j == haha) {

                    } else {
                        haha = j;
//                
                        System.out.println("index ke - " + haha);
                        System.out.println("nilai fitnes= " + jTable35.getValueAt(j, 1));

                        for (int k = 0; k < data.length; k++) {
                            jTable36.setValueAt(jTable31.getValueAt(haha, k + 1), counter, k + 1);
                            jTable37.setValueAt(jTable32.getValueAt(haha, k + 1), counter, k + 1);
                        }
                        break;
                    }
                }
            }
            cu--;
            counter++;
        }

        jButton48.setEnabled(false);
    }
    private void jButton48ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton48ActionPerformed
        // TODO add your handling code here:
        elitism();
    }//GEN-LAST:event_jButton48ActionPerformed

    public void set() {
        for (int i = 0; i < jTable36.getRowCount(); i++) {
            for (int j = 0; j < jTable36.getColumnCount(); j++) {
                jTable20.setValueAt(jTable36.getValueAt(i, j), i, j);
            }
        }

        for (int i = 0; i < jTable37.getRowCount(); i++) {
            for (int j = 0; j < jTable37.getColumnCount(); j++) {
                jTable21.setValueAt(jTable37.getValueAt(i, j), i, j);
            }
        }

        double a = 0;
        for (int i = 0; i < jTable37.getColumnCount() - 1; i++) {
            if (Double.valueOf(String.valueOf(jTable37.getValueAt(0, i + 1))) > a) {
                a = Double.valueOf(String.valueOf(jTable37.getValueAt(0, i + 1)));
            }
        }
        TerbesarM = a;

        System.out.println("terbesar = " + TerbesarM);

        //        double besar = 0d;
        //        for (int i = 0; i < data.length; i++) {
        //            for (int j =0; j < data.length; j++) {
        //                if (Double.valueOf(String.valueOf(jTable37.getValueAt(0, (i + 1)-))) > Double.valueOf(String.valueOf(jTable37.getValueAt(0, (j+1))))) {
        //                    besar = Double.valueOf(String.valueOf(jTable37.getValueAt(0, (i + 1))));
        //                } else if (Double.valueOf(String.valueOf(jTable37.getValueAt(0, (j+1)))) > Double.valueOf(String.valueOf(jTable37.getValueAt(0, (i + 1))))) {
        //                    besar = Double.valueOf(String.valueOf(jTable37.getValueAt(0, (j+1))));
        //                }
        //            }
        //        }
        //        TerbesarM = besar;
        //        System.out.println("Ini paling besar ya :" + TerbesarM);
        //
        jButton47.setEnabled(false);
    }


    private void jButton47ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton47ActionPerformed
        // TODO add your handling code here:
        set();
        jTabbedPane1.setSelectedIndex(9);
    }//GEN-LAST:event_jButton47ActionPerformed

    private void jButton49ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton49ActionPerformed
        // TODO add your handling code here:

        for (int i = 0; i < jumlahgenerasi - 1; i++) {
            HitungXkaliW();
            subset1();
            fitnesfinal1();
            CrossOver();
            CrossOver2();
            CrossOver3();
            CrossOver4();
            Mutasi();
            Mutasi2();
            Mutasi3();
            Fitnes1();
            fitnes2();
            HitungXkaliW2();
            fitnes21();
            fitnesfinal2();
            elitism();
            set();
        }
    }//GEN-LAST:event_jButton49ActionPerformed
    public static void caridekat(double a[][]) {
        double temp = -2;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                if (a[i][j] > temp && i != j) {
                    temp = a[i][j];
                    dekat1 = i + 1;
                    dekat2 = j + 1;
                }

            }
        }
        for (int i = 0; i < aa.length; i++) {

            for (int j = 0; j < aa[0].length; j++) {
                System.out.print(aa[i][j] + " ");
            }
            System.out.println("");

        }
        System.out.println("");
        System.out.println("ini baru dekat 1 =" + (dekat1 - 1));

        System.out.println("ini baru dekat 2 =" + (dekat2 - 1));

        nilaidekat = temp;
        update(dekat1, dekat2);
        aa = updatearray(aa);
        ratarata(aa);
    }

    public static void update(int a1, int a2) {

        Object aa1 = a.get(dekat1 - 1);
        Object aa2 = a.get(dekat2 - 1);
        String hahaaa1 = "";
        String hahaaa2 = "";
        String[] hay1 = String.valueOf(aa1).split(",");
        String[] hay2 = String.valueOf(aa2).split(",");
        for (int i = 0; i < hay1.length; i++) {
            hahaaa1 = hahaaa1.concat(hay1[i]);

        }
        for (int i = 0; i < hay2.length; i++) {
            hahaaa2 = hahaaa2.concat(hay2[i]);

        }

        System.out.println("aa1 = " + aa1);
        System.out.println("aa2 = " + aa2);
//        simpannilai = Integer.valueOf(String.valueOf(aa1).concat(String.valueOf(aa2)));
        a.addFirst("(" + String.valueOf(aa1).concat("," + String.valueOf(aa2)) + ")");
        String haha1 = "", haha2 = "";
        int simpanhapus = 0;
        for (int i = 0; i < a.size(); i++) {
            String haha[] = String.valueOf(a.get(i)).split(",");
            for (int j = 0; j < haha.length; j++) {
                haha1 = haha1.concat(haha[j]);
            }
//            System.out.println("haha1   " + haha1);
            if (haha1.equals(String.valueOf(hahaaa1))) {
//                temp.remove(i);
                simpanhapus = i;
                System.out.println("sukses " + i);
                break;
            }
            haha1 = "";
        }
        dekat11 = String.valueOf(a.get(simpanhapus));

        a.remove(simpanhapus);
        int simpanhapus2 = 0;
        for (int i = 0; i < a.size(); i++) {
            String haha[] = String.valueOf(a.get(i)).split(",");
            for (int j = 0; j < haha.length; j++) {
                haha2 = haha2.concat(haha[j]);
            }
            if (haha2.equals(String.valueOf(hahaaa2))) {
//                temp.remove(i);
                simpanhapus2 = i;
                System.out.println("sukses2 " + i);
//                break;
            }
            haha2 = "";
        }
        dekat22 = String.valueOf(a.get(simpanhapus2));
        a.remove(simpanhapus2);

    }

    public static double[][] updatearray(double a[][]) {
        double nilaibaru[][] = new double[a.length - 1][a.length - 1];

        int ii = 0;
        int jj = 0;
        for (int i = 0; i < a.length; i++) {

            if (i == dekat1 - 1 || i == dekat2 - 1) {
                continue;
            }

            for (int j = 0; j < a[0].length; j++) {

                if (j == dekat2 - 1 || j == dekat1 - 1) {
                    continue;
                }
                nilaibaru[ii + 1][jj + 1] = a[i][j];
                jj++;
            }
            jj = 0;
            ii++;
        }

        return nilaibaru;
    }

    public static double jarak(String[] a, String[] b) {

        double hasil = 0;
        int a1 = 0;
        int b1 = 0;
        for (int i = 0; i < a.length; i++) {
            a1 = Integer.valueOf(a[i]);
            for (int j = 0; j < b.length; j++) {
                b1 = Integer.valueOf(b[j]);
//                System.out.println("temp" + a1);
//                System.out.println("b" + b1);
                hasil = hasil + aa2[a1 - 1][b1 - 1];
            }
        }
//        System.out.println("fi");
        return hasil;
    }

    public static void ratarata(double ab[][]) {

        String simpann = String.valueOf(a.get(0));
        System.out.println("simpan nilai = " + simpann);
        simpann = simpann.replace("(", "");
        simpann = simpann.replace(")", "");
        String arrsimpann[] = simpann.split(",");
        double hasil = 0;
        ab[0][0] = 1;
        for (int i = 1; i < a.size(); i++) {
            String simpan2 = String.valueOf(a.get(i));
            simpan2 = simpan2.replace("(", "");
            simpan2 = simpan2.replace(")", "");
            String z[] = simpan2.split(",");
//            System.out.println("  temp  " + z[0]);
            hasil = jarak(arrsimpann, z);
            ab[i][0] = hasil / (arrsimpann.length * z.length);
            ab[0][i] = hasil / (arrsimpann.length * z.length);
            hasil = 0;
        }
//        for (int i = 0; i < ab.length; i++) {
//            ab[i][0] = -0.01;
//        }
//        for (int i = 0; i < ab[0].length; i++) {
//            ab[0][i] = -0.01;
//        }
    }

    public static void view() {

        System.out.println("dekat 1 index = " + (dekat1 - 1));
        System.out.println("dekat 2 index = " + (dekat2 - 1));
//        try {
//
//            System.out.println("dekat 1 real = " + temp.get(dekat1 - 1));
//            System.out.println("dekat 2 real = " + temp.get(dekat2 - 1));
//        } catch (Exception e) {
//        }
        System.out.println("nilai dekat = " + nilaidekat);
        System.out.println("--------------------------------------------");

    }

    public static double cosineSimilarity(double[] dokumenA, double[] dokumenB) {
        double dotProduct = 0.0;
        double penyebutA = 0.0;
        double PenyebutB = 0.0;
        for (int i = 0; i < dokumenA.length; i++) {
            dotProduct += dokumenA[i] * dokumenB[i];
            penyebutA += Math.pow(dokumenA[i], 2);
            PenyebutB += Math.pow(dokumenB[i], 2);
        }
        return dotProduct / (Math.sqrt(penyebutA) * Math.sqrt(PenyebutB));
    }

    public void lowercase() {
        DefaultTableModel tableModel = new DefaultTableModel(header1, data.length);
        jTable3.setModel(tableModel);

        for (int i = 0; i < data.length; i++) {
            jTable3.setRowHeight(i, 30);
            jTable3.setValueAt(i + 1, i, 0);
            jTable3.setValueAt(data[i].toLowerCase(), i, 1);
        }

        int k = 0;
        String[] temp;
        for (int i = 0; i < data.length; i++) {
            temp = String.valueOf(jTable3.getValueAt(i, 1)).split("");
            if (temp.length > k) {
                k = temp.length;

            } else {
                temp = null;
            }

        }

        jTable3.setAutoResizeMode(0);
        TableColumnModel columnModel = jTable3.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(2 * 15);
        columnModel.getColumn(1).setPreferredWidth(6 * k);
    }

    public void hapusnonabjad() {
        DefaultTableModel tableModel2 = new DefaultTableModel(header1, data.length);
        jTable2.setModel(tableModel2);
        jTable2.setAutoResizeMode(0);
        TableColumnModel columnModel = jTable2.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(40);
        columnModel.getColumn(1).setPreferredWidth(6 * 9513);

        for (int i = 0; i < data.length; i++) {
            jTable2.setRowHeight(i, 30);
            jTable2.setValueAt(i + 1, i, 0);
            String a = String.valueOf(jTable3.getValueAt(i, 1)).replaceAll("[^a-zA-Z]", " ");
            jTable2.setValueAt(a, i, 1);
        }
    }

    public void tokenizing() {
        DefaultTableModel tableModel4 = new DefaultTableModel(header1, data.length);
        jTable4.setModel(tableModel4);
        jTable4.setAutoResizeMode(0);
        TableColumnModel columnModel = jTable4.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(40);
        columnModel.getColumn(1).setPreferredWidth(6 * 9513);

        String append = "";
        String[] hasiltoken;
        for (int i = 0; i < data.length; i++) {
            jTable4.setRowHeight(i, 30);
            jTable4.setValueAt(i + 1, i, 0);
            hasiltoken = String.valueOf(jTable2.getValueAt(i, 1)).trim().split("\\s+");
            for (int j = 0; j < hasiltoken.length; j++) {
                if (j == (hasiltoken.length - 1)) {
                    append = append + (hasiltoken[j]);

                } else {
                    append = append + (hasiltoken[j] + " // ");

                }
            }
            jTable4.setValueAt(append, i, 1);
            append = "";
        }
    }

    public void filtering() {
        DefaultTableModel tableModel5 = new DefaultTableModel(header1, data.length);
        jTable5.setModel(tableModel5);
        jTable5.setAutoResizeMode(0);
        TableColumnModel columnModel = jTable5.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(40);
        columnModel.getColumn(1).setPreferredWidth(6 * 9513);
        String append = "";
        String[] hasilfilter;
        for (int i = 0; i < data.length; i++) {
            jTable5.setRowHeight(i, 30);
            jTable5.setValueAt(i + 1, i, 0);

            hasilfilter = String.valueOf(jTable4.getValueAt(i, 1)).trim().split(" // ");
            for (int j = 0; j < hasilfilter.length; j++) {
                for (int k = 0; k < stopWords.length; k++) {
                    if (!hasilfilter[j].equals(stopWords[k]) && k == (stopWords.length - 1)) {
                        if (j == (hasilfilter.length - 1)) {
                            append = append + (hasilfilter[j]);
                            break;

                        } else {
                            append = append + (hasilfilter[j] + " // ");
                            break;

                        }
                    } else if (hasilfilter[j].equals(stopWords[k])) {
                        break;
                    }

                }

            }
            jTable5.setValueAt(append, i, 1);
            append = "";
        }
    }

    public void stemmingnazief() {
        StemmingNaziefAndriani stem = null;
        try {
            stem = new StemmingNaziefAndriani();
        } catch (IOException ex) {
            Logger.getLogger(alev.class.getName()).log(Level.SEVERE, null, ex);
        }

        jTabbedPane1.setSelectedIndex(4);
        DefaultTableModel tableModel6 = new DefaultTableModel(header1, data.length);
        jTable6.setModel(tableModel6);
        jTable6.setAutoResizeMode(0);
        TableColumnModel columnModel = jTable6.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(40);
        columnModel.getColumn(1).setPreferredWidth(6 * 9513);

        String append = "";
        String[] hasiltoken;
        for (int i = 0; i < data.length; i++) {
            jTable6.setRowHeight(i, 30);
            jTable6.setValueAt(i + 1, i, 0);
            jTable5.setValueAt(String.valueOf(jTable5.getValueAt(i, 1)).replaceAll("         |        |       |      |     |    |   |  ", " "), i, 1);
            hasiltoken = String.valueOf(jTable5.getValueAt(i, 1)).trim().split(" // ");
            for (int j = 0; j < hasiltoken.length; j++) {
                if (j == (hasiltoken.length - 1)) {
                    append = append + stem.KataDasar(hasiltoken[j]);

                } else {
                    append = append + (stem.KataDasar(hasiltoken[j]) + " // ");
                }
            }
            jTable6.setValueAt(append, i, 1);
            append = "";
        }
    }

    public void pembentukantermunik() {
        //=========================================================================
        //Proses Menggabungkan semua dokumen menjadi satu variabel term (awal)
        //=========================================================================
        String term = "";
        String[] hasilstemming;
        for (int i = 0; i < data.length; i++) {
            jTable6.setValueAt(String.valueOf(jTable6.getValueAt(i, 1)).replaceAll("//", " "), i, 1);
            hasilstemming = String.valueOf(jTable6.getValueAt(i, 1)).trim().split("\\s+");
            jTable6.setValueAt(String.valueOf(jTable6.getValueAt(i, 1)).replaceAll("\\s+", " // "), i, 1);
            for (int j = 0; j < hasilstemming.length; j++) {
                term = term + (hasilstemming[j] + " ");

            }
        }

        String[] termarray = term.split("\\s+");
        boolean trigger = false;
        for (int i = 0; i < termarray.length; i++) {
            if (i == 0) {
                hasilterm.add(termarray[i]);
            }
            for (int j = 0; j < hasilterm.size(); j++) {

                if (!termarray[i].equalsIgnoreCase((String) hasilterm.get(j))) {
                    trigger = true;
                } else {
                    trigger = false;
                    break;
                }
                if (j == (hasilterm.size() - 1) && trigger == true) {
                    hasilterm.add(termarray[i]);

                }
            }
        }

        //=========================================================================
        //Proses Mencari Term UNIK (akhir)
        //=========================================================================
    }

    public void rawTF() {
        pembentukantermunik();
        header2 = new String[data.length + 1];

        for (int i = 0; i < (data.length + 1); i++) {
            if (i == 0) {
                header2[0] = "Term";
            } else {
                header2[i] = "Dok " + i;

            }
        }

        DefaultTableModel tableModel7 = new DefaultTableModel(header2, hasilterm.size());
        jTable7.setModel(tableModel7);
        jTable7.setAutoResizeMode(0);
        TableColumnModel columnModel7 = jTable7.getColumnModel();
        columnModel7.getColumn(0).setPreferredWidth(90);
        for (int i = 0; i < data.length; i++) {
            columnModel7.getColumn(i + 1).setPreferredWidth(60);

        }

        for (int i = 0; i < hasilterm.size(); i++) {
            jTable7.setValueAt(hasilterm.get(i), i, 0);
        }

        //=========================================================================
        //Proses Mencari frekuensi term raw pada dokumen (awal)
        //=========================================================================
        int jumlahterm = 0;
        String[] hasilstemming2;
        for (int i = 0; i < data.length; i++) {
            jTable6.setValueAt(String.valueOf(jTable6.getValueAt(i, 1)).replaceAll("//", " "), i, 1);
            hasilstemming2 = String.valueOf(jTable6.getValueAt(i, 1)).trim().split("\\s+");
            jTable6.setValueAt(String.valueOf(jTable6.getValueAt(i, 1)).replaceAll("\\s+", " // "), i, 1);
            for (int j = 0; j < hasilterm.size(); j++) {
                for (int k = 0; k < hasilstemming2.length; k++) {
                    if (hasilstemming2[k].equalsIgnoreCase(String.valueOf(hasilterm.get(j)))) {
                        jumlahterm++;
                    }
                }
                jTable7.setValueAt(jumlahterm, j, i + 1);
                jumlahterm = 0;
            }
        }
        //=========================================================================
        //Proses Mencari frekuensi term raw pada dokumen (akhir)
        //=========================================================================
        matrikrawtf = new int[jTable7.getRowCount()][data.length];

    }

    public void pembobotanTF() {
        DefaultTableModel tableModel8 = new DefaultTableModel(header2, hasilterm.size());
        jTable8.setModel(tableModel8);
        jTable8.setAutoResizeMode(0);
        TableColumnModel columnModel8 = jTable8.getColumnModel();
        columnModel8.getColumn(0).setPreferredWidth(90);
        for (int i = 0; i < data.length; i++) {
            columnModel8.getColumn(i + 1).setPreferredWidth(60);

        }

        for (int i = 0; i < hasilterm.size(); i++) {
            jTable8.setValueAt(hasilterm.get(i), i, 0);
            for (int j = 0; j < data.length; j++) {
                if (Double.valueOf(String.valueOf(jTable7.getValueAt(i, j + 1))) == 0) {
                    jTable8.setValueAt(0, i, j + 1);
                } else {
                    jTable8.setValueAt((1 + Math.log10(Double.valueOf(String.valueOf(jTable7.getValueAt(i, j + 1))))), i, j + 1);
                }
            }
        }

    }

    public void perhitunganDF() {
        //menginisialisasi header3
        header3 = new String[data.length + 2];
        for (int i = 0; i < (data.length + 2); i++) {
            if (i == 0) {
                header3[0] = "Term";
            } else if (i == 1) {
                header3[1] = "DF";

            } else {
                header3[i] = "Dok " + (i - 1);

            }
        }

        //membuat table 9
        DefaultTableModel tableModel9 = new DefaultTableModel(header3, hasilterm.size());
        jTable9.setModel(tableModel9);
        jTable9.setAutoResizeMode(0);
        TableColumnModel columnModel9 = jTable9.getColumnModel();

        //mengatur lebar kolom pada term dan df
        columnModel9.getColumn(0).setPreferredWidth(90);
        columnModel9.getColumn(1).setPreferredWidth(60);

        //mengatur lebar kolom pada nilai tf
        for (int i = 0; i < data.length; i++) {
            columnModel9.getColumn(i + 2).setPreferredWidth(60);

        }

        //memasukkan term
        for (int i = 0; i < hasilterm.size(); i++) {
            jTable9.setValueAt(hasilterm.get(i), i, 0);
        }

        //memasukkan data dari table 8 ke table 9 nilai dari TF
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < hasilterm.size(); j++) {
                jTable9.setValueAt(jTable8.getValueAt(j, i + 1), j, (i + 2));
            }
        }

        //=========================================================================
        //Proses Mencari nilai Dokumen frekuensi (DF) (awal)
        //=========================================================================
        int df = 0;
        for (int j = 0; j < hasilterm.size(); j++) {
            for (int i = 0; i < data.length; i++) {
                if (Double.valueOf(String.valueOf(jTable8.getValueAt(j, i + 1))) != 0) {
                    df++;
                }
            }
            jTable9.setValueAt(df, j, 1);
            df = 0;
        }
        //=========================================================================
        //Proses Mencari nilai Dokumen frekuensi (DF) (akhir)
        //=========================================================================

    }

    public void pembobotanIDF() {
        //menginisialisasi header4
        header4 = new String[data.length + 2];
        for (int i = 0; i < (data.length + 2); i++) {
            if (i == 0) {
                header4[0] = "Term";
            } else if (i == 1) {
                header4[1] = "IDF";

            } else {
                header4[i] = "Dok " + (i - 1);

            }
        }
        //membuat table 10
        DefaultTableModel tableModel10 = new DefaultTableModel(header4, hasilterm.size());
        jTable10.setModel(tableModel10);
        jTable10.setAutoResizeMode(0);
        TableColumnModel columnModel10 = jTable10.getColumnModel();

        //mengatur lebar kolom pada term dan idf
        columnModel10.getColumn(0).setPreferredWidth(90);
        columnModel10.getColumn(1).setPreferredWidth(60);

        //mengatur lebar kolom pada nilai tf
        for (int i = 0; i < data.length; i++) {
            columnModel10.getColumn(i + 2).setPreferredWidth(60);
        }

        //memasukkan term
        for (int i = 0; i < hasilterm.size(); i++) {
            jTable10.setValueAt(hasilterm.get(i), i, 0);
            //menghitung IDF
            jTable10.setValueAt(Math.log10(data.length / Double.valueOf(String.valueOf(jTable9.getValueAt(i, 1)))), i, 1);
        }

        //memasukkan data dari table 9 ke table 10 nilai dari TF
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < hasilterm.size(); j++) {
                jTable10.setValueAt(jTable9.getValueAt(j, i + 2), j, (i + 2));
            }
        }
    }

    public void pembobotanTFIDF() {
        //membuat table 11
        DefaultTableModel tableModel11 = new DefaultTableModel(header2, hasilterm.size());
        jTable11.setModel(tableModel11);
        jTable11.setAutoResizeMode(0);
        TableColumnModel columnModel11 = jTable11.getColumnModel();

        //mengatur lebar kolom pada term dan idf
        columnModel11.getColumn(0).setPreferredWidth(90);

        //mengatur lebar kolom pada setiap dokumen
        for (int i = 0; i < data.length; i++) {
            columnModel11.getColumn(i + 1).setPreferredWidth(60);
        }

        //memasukkan term
        for (int i = 0; i < hasilterm.size(); i++) {
            jTable11.setValueAt(hasilterm.get(i), i, 0);
        }

        //=========================================================================
        //Proses perhitungan TFIDF (awal)
        //=========================================================================
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < hasilterm.size(); j++) {
                double tfidf = 0;
                tfidf = Double.valueOf(String.valueOf(jTable10.getValueAt(j, i + 2))) * Double.valueOf(String.valueOf(jTable10.getValueAt(j, 1)));
                jTable11.setValueAt(tfidf, j, i + 1);
            }
        }
        //=========================================================================
        //Proses perhitungan TFIDF (akhir)
        //=========================================================================
    }

    public void SVD() {
        matrixtfidf = new double[hasilterm.size()][data.length];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < hasilterm.size(); j++) {
                matrixtfidf[j][i] = Double.valueOf(String.valueOf(jTable11.getValueAt(j, i + 1)));
            }
        }

        Matrix SVD = new Matrix(matrixtfidf);

        System.out.println("A = U S V^T");
        SingularValueDecomposition s = SVD.svd();

        System.out.println("U = ");
        Matrix U = s.getU();
//        U.print(12, 4);
        matrixU = U.getArray();

        //membuat table 12
        DefaultTableModel tableModel12 = new DefaultTableModel(matrixU.length, matrixU[0].length);
        jTable12.setModel(tableModel12);
        jTable12.setAutoResizeMode(0);
        TableColumnModel columnModel12 = jTable12.getColumnModel();
        jTable12.getTableHeader().setUI(null);
        for (int i = 0; i < matrixU.length; i++) {  //baris
            for (int j = 0; j < matrixU[0].length; j++) { //kolom
                jTable12.setValueAt(matrixU[i][j], i, j);
            }
        }

        System.out.println("Sigma = ");
        Matrix S = s.getS();
//        S.print(12, 4);
        matrixS = S.getArray();

        //membuat table 13
        DefaultTableModel tableModel13 = new DefaultTableModel(matrixS.length, matrixS[0].length);
        jTable13.setModel(tableModel13);
        jTable13.setAutoResizeMode(0);
        TableColumnModel columnModel13 = jTable13.getColumnModel();
        jTable13.getTableHeader().setUI(null);
        for (int i = 0; i < matrixS.length; i++) {  //baris
            for (int j = 0; j < matrixS[0].length; j++) { //kolom
                jTable13.setValueAt(matrixS[i][j], i, j);
            }
        }
        System.out.println("VT = ");
        Matrix V = s.getV();
//        double[][] matrixV = V.getArray();
        Matrix Vt = V.transpose();
        matrixVt = Vt.getArray();
//        Vt.print(12, 4);

        //membuat table 14
        DefaultTableModel tableModel14 = new DefaultTableModel(matrixVt.length, matrixVt[0].length);
        jTable14.setModel(tableModel14);
        jTable14.setAutoResizeMode(0);
        TableColumnModel columnModel14 = jTable14.getColumnModel();
        jTable14.getTableHeader().setUI(null);
        for (int i = 0; i < matrixVt.length; i++) {  //baris
            for (int j = 0; j < matrixVt[0].length; j++) { //kolom
                jTable14.setValueAt(matrixVt[i][j], i, j);
            }
        }
    }

    public void reducematrixLSI() {
        if (jComboBox1.getSelectedIndex() == 0) {
            rank = 100;
        } else if (jComboBox1.getSelectedIndex() == 1) {
            rank = 95;
        } else if (jComboBox1.getSelectedIndex() == 2) {
            rank = 90;
        } else if (jComboBox1.getSelectedIndex() == 3) {
            rank = 85;
        } else if (jComboBox1.getSelectedIndex() == 4) {
            rank = 80;
        } else if (jComboBox1.getSelectedIndex() == 5) {
            rank = 75;
        } else if (jComboBox1.getSelectedIndex() == 6) {
            rank = 70;
        } else if (jComboBox1.getSelectedIndex() == 7) {
            rank = 65;
        } else if (jComboBox1.getSelectedIndex() == 8) {
            rank = 60;
        } else if (jComboBox1.getSelectedIndex() == 9) {
            rank = 55;
        } else if (jComboBox1.getSelectedIndex() == 10) {
            rank = 50;
        } else if (jComboBox1.getSelectedIndex() == 11) {
            rank = 45;
        } else if (jComboBox1.getSelectedIndex() == 12) {
            rank = 40;
        } else if (jComboBox1.getSelectedIndex() == 13) {
            rank = 35;
        } else if (jComboBox1.getSelectedIndex() == 14) {
            rank = 30;
        } else if (jComboBox1.getSelectedIndex() == 15) {
            rank = 25;
        } else if (jComboBox1.getSelectedIndex() == 16) {
            rank = 20;
        } else if (jComboBox1.getSelectedIndex() == 17) {
            rank = 15;
        } else if (jComboBox1.getSelectedIndex() == 18) {
            rank = 10;
        } else if (jComboBox1.getSelectedIndex() == 19) {
            rank = 5;
        }

        double barisVT = matrixVt.length * (rank / 100);
        int brVT = (int) barisVT;

        //membuat table 15
        DefaultTableModel tableModel15 = new DefaultTableModel(brVT, matrixVt[0].length);
        jTable15.setModel(tableModel15);
        jTable15.setAutoResizeMode(0);
        TableColumnModel columnModel15 = jTable15.getColumnModel();
        jTable15.getTableHeader().setUI(null);
        DefaultTableModel tableModel17 = new DefaultTableModel(matrixVt[0].length, brVT);
        jTable17.setModel(tableModel17);
        jTable17.setAutoResizeMode(0);
        TableColumnModel columnModel17 = jTable17.getColumnModel();
        jTable17.getTableHeader().setUI(null);

        for (int i = 0; i < brVT; i++) {  //baris
            for (int j = 0; j < matrixVt[0].length; j++) { //kolom
                jTable15.setValueAt(matrixVt[i][j], i, j);
                jTable17.setValueAt(matrixVt[i][j], j, i);

            }
        }

        double barisS = matrixS.length * (rank / 100);
        int brS = (int) barisS;
        //membuat table 16
        DefaultTableModel tableModel16 = new DefaultTableModel(brS, brS);
        jTable16.setModel(tableModel16);
        jTable16.setAutoResizeMode(0);
        TableColumnModel columnModel16 = jTable16.getColumnModel();
        jTable16.getTableHeader().setUI(null);
        for (int i = 0; i < brS; i++) {  //baris
            for (int j = 0; j < brS; j++) { //kolom
                jTable16.setValueAt(matrixS[i][j], i, j);
            }
        }

        V = Matrix.constructWithCopy(matrixVt);
        V = V.getMatrix(0, brVT - 1, 0, matrixVt[0].length - 1);
        V = V.transpose();
        matrixVbaru = V.getArray();
        System.out.println("matrix V");
        V.print(12, 4);

        S = Matrix.constructWithCopy(matrixS);
        S = S.getMatrix(0, brS - 1, 0, brS - 1);
//        S = S.inverse(); coba ditanyakan ke pak sigit.apa pakai inverse atau enggk
        matrixSbaru = S.getArray();
        System.out.println("matrix S");
        S.print(12, 4);

        LSI = V.times(S);
        System.out.println("matrix LSI");
        LSI.print(12, 4);
        matrixLSI = LSI.transpose().getArray(); // mempermudah menilai perdokumen sehingga ditranspose

        header5 = new String[data.length];

        for (int i = 0; i < (data.length); i++) {

            header5[i] = "Dok " + (i + 1);

        }
        //membuat table 18
        DefaultTableModel tableModel18 = new DefaultTableModel(header5, matrixLSI.length);
        jTable18.setModel(tableModel18);
        jTable18.setAutoResizeMode(0);
        TableColumnModel columnModel18 = jTable18.getColumnModel();

        for (int i = 0; i < matrixLSI.length; i++) {  //baris
            for (int j = 0; j < matrixLSI[0].length; j++) { //kolom
                jTable18.setValueAt(matrixLSI[i][j], i, j);
            }
        }

    }

    public void readDokumenTeks(String bacateks, JTextArea areaTeks) throws FileNotFoundException, IOException {
        String line = "";
        File bacafile = new File(bacateks);//mengubah inputan string mejadi sebuah file
        FileReader inputDokumen = new FileReader(bacafile);//membaca inputan sebuah dokumen
        BufferedReader bf = new BufferedReader(inputDokumen);//buffer dari dokumen ketika dibaca
        StringBuffer content = new StringBuffer();//untuk menampung string dalam bufer
        while ((line = bf.readLine()) != null) {//jika barisdata ada
            //  barisData = barisData.toLowerCase();
            content.append(line + "\n");//mencetak baris kata dalam dokumen
        }
        areaTeks.append(content.toString());
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(alev.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(alev.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(alev.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(alev.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new alev().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton34;
    private javax.swing.JButton jButton35;
    private javax.swing.JButton jButton36;
    private javax.swing.JButton jButton37;
    private javax.swing.JButton jButton38;
    private javax.swing.JButton jButton39;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton40;
    private javax.swing.JButton jButton41;
    private javax.swing.JButton jButton42;
    private javax.swing.JButton jButton43;
    private javax.swing.JButton jButton44;
    private javax.swing.JButton jButton45;
    private javax.swing.JButton jButton46;
    private javax.swing.JButton jButton47;
    private javax.swing.JButton jButton48;
    private javax.swing.JButton jButton49;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane23;
    private javax.swing.JScrollPane jScrollPane24;
    private javax.swing.JScrollPane jScrollPane25;
    private javax.swing.JScrollPane jScrollPane26;
    private javax.swing.JScrollPane jScrollPane27;
    private javax.swing.JScrollPane jScrollPane28;
    private javax.swing.JScrollPane jScrollPane29;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane30;
    private javax.swing.JScrollPane jScrollPane31;
    private javax.swing.JScrollPane jScrollPane32;
    private javax.swing.JScrollPane jScrollPane33;
    private javax.swing.JScrollPane jScrollPane34;
    private javax.swing.JScrollPane jScrollPane35;
    private javax.swing.JScrollPane jScrollPane36;
    private javax.swing.JScrollPane jScrollPane37;
    private javax.swing.JScrollPane jScrollPane38;
    private javax.swing.JScrollPane jScrollPane39;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTabbedPane jTabbedPane5;
    private javax.swing.JTabbedPane jTabbedPane6;
    private javax.swing.JTabbedPane jTabbedPane7;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable10;
    private javax.swing.JTable jTable11;
    private javax.swing.JTable jTable12;
    private javax.swing.JTable jTable13;
    private javax.swing.JTable jTable14;
    private javax.swing.JTable jTable15;
    private javax.swing.JTable jTable16;
    private javax.swing.JTable jTable17;
    private javax.swing.JTable jTable18;
    private javax.swing.JTable jTable19;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable20;
    private javax.swing.JTable jTable21;
    private javax.swing.JTable jTable22;
    private javax.swing.JTable jTable23;
    private javax.swing.JTable jTable24;
    private javax.swing.JTable jTable25;
    private javax.swing.JTable jTable26;
    private javax.swing.JTable jTable27;
    private javax.swing.JTable jTable28;
    private javax.swing.JTable jTable29;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable30;
    private javax.swing.JTable jTable31;
    private javax.swing.JTable jTable32;
    private javax.swing.JTable jTable33;
    private javax.swing.JTable jTable34;
    private javax.swing.JTable jTable35;
    private javax.swing.JTable jTable36;
    private javax.swing.JTable jTable37;
    private javax.swing.JTable jTable38;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTable jTable7;
    private javax.swing.JTable jTable8;
    private javax.swing.JTable jTable9;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
