/*
 * Nama      : M. Ridwan Alsafir Gusnendar
 * NIM       : 202222031
 * Kelompok  : Teknik Informatika (Sore)
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Year;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class RidwanTugas_65 {

    public static void main(String[] args) throws IOException {
        //Main Menu
        Scanner ridwanMenu = new Scanner(System.in);
        String ridwanPilihan;
        boolean ridwanLanjut = true;

        while (ridwanLanjut) {
            ridwanHapusLayar();
            System.out.println("Database Perpustakaan\n");
            System.out.println("1.\tLihat seluruh buku");
            System.out.println("2.\tCari data buku");
            System.out.println("3.\tTambah data buku");
            System.out.println("4.\tUbah data buku");
            System.out.println("5.\tHapus data buku");
            System.out.println("6.\tKeluar");

            System.out.print("\n\nPilihan anda: ");
            ridwanPilihan = ridwanMenu.next();

            switch (ridwanPilihan) {
                case "1":
                    System.out.println("\n=================");
                    System.out.println("LIST SELURUH BUKU");
                    System.out.println("=================");
                    ridwanTampilkanData();
                    break;
                case "2":
                    System.out.println("\n=========");
                    System.out.println("CARI BUKU");
                    System.out.println("=========");
                    ridwanCariData();
                    break;
                case "3":
                    System.out.println("\n================");
                    System.out.println("TAMBAH DATA BUKU");
                    System.out.println("================");
                    ridwanTambahData();
                    ridwanTampilkanData();
                    break;
                case "4":
                    System.out.println("\n==============");
                    System.out.println("UBAH DATA BUKU");
                    System.out.println("==============");
                    ridwanUpdateData();
                    break;
                case "5":
                    System.out.println("\n===============");
                    System.out.println("HAPUS DATA BUKU");
                    System.out.println("===============");
                    ridwanHapusData();
                    break;
                case "6":
                    ridwanLanjut = false;
                    ridwanLanjut = ridwanYaAtauTidak("Apakah anda ingin keluar");
                    continue;
                default:
                    System.err.println("\nInput anda tidak ditemukan\nSilahkan pilih [1-6]");
            }

            ridwanLanjut = ridwanYaAtauTidak("Apakah Anda ingin melanjutkan");
        }
    }
    //Operasi
    public static void ridwanUpdateData() throws IOException{
        // kita ambil database original
        File database = new File("database.txt");
        FileReader ridwanFileInput = new FileReader(database);
        BufferedReader bufferedInput = new BufferedReader(ridwanFileInput);

        // kita buat database sementara
        File tempDB = new File("tempDB.txt");
        FileWriter ridwanFileOutput = new FileWriter(tempDB);
        BufferedWriter bufferedOutput = new BufferedWriter(ridwanFileOutput);

        // tampilkan data
        System.out.println("Daftar Buku");
        ridwanTampilkanData();

        // ambil user input / pilihan data
        Scanner terminalInput = new Scanner(System.in);
        System.out.print("\nMasukan nomor buku yang akan diupdate: ");
        int updateNum = terminalInput.nextInt();

        // tampilkan data yang ingin diupdate

        String data = bufferedInput.readLine();
        int entryCounts = 0;

        while (data != null){
            entryCounts++;

            StringTokenizer st = new StringTokenizer(data,",");

            // tampilkan entrycounts == updateNum
            if (updateNum == entryCounts){
                System.out.println("\nData yang ingin anda update adalah:");
                System.out.println("---------------------------------------");
                System.out.println("Referensi           : " + st.nextToken());
                System.out.println("Tahun               : " + st.nextToken());
                System.out.println("Penulis             : " + st.nextToken());
                System.out.println("Penerbit            : " + st.nextToken());
                System.out.println("Judul               : " + st.nextToken());

                // update data
                // mengambil input dari user

                String[] fieldData = {"tahun","penulis","penerbit","judul"};
                String[] tempData = new String[4];

                st = new StringTokenizer(data,",");
                String originalData = st.nextToken();

                for(int i=0; i < fieldData.length ; i++) {
                    boolean isUpdate = ridwanYaAtauTidak("apakah anda ingin merubah " + fieldData[i]);
                    originalData = st.nextToken();
                    if (isUpdate){
                        //user input

                        if (fieldData[i].equalsIgnoreCase("tahun")){
                            System.out.print("masukan tahun terbit, format=(YYYY): ");
                            tempData[i] = ridwanAmbilTahun();
                        } else {
                            terminalInput = new Scanner(System.in);
                            System.out.print("\nMasukan " + fieldData[i] + " baru: ");
                            tempData[i] = terminalInput.nextLine();
                        }

                    } else {
                        tempData[i] = originalData;
                    }
                }

                // tampilkan data baru ke layar
                st = new StringTokenizer(data,",");
                st.nextToken();
                System.out.println("\nData baru anda adalah ");
                System.out.println("---------------------------------------");
                System.out.println("Tahun               : " + st.nextToken() + " --> " + tempData[0]);
                System.out.println("Penulis             : " + st.nextToken() + " --> " + tempData[1]);
                System.out.println("Penerbit            : " + st.nextToken() + " --> " + tempData[2]);
                System.out.println("Judul               : " + st.nextToken() + " --> " + tempData[3]);


                boolean isUpdate = ridwanYaAtauTidak("apakah anda yakin ingin mengupdate data tersebut");

                if (isUpdate){

                    // cek data baru di database
                    boolean isExist = ridwanCekBukuDiDatabase(tempData,false);

                    if(isExist){
                        System.err.println("data buku sudah ada di database, proses update dibatalkan, \nsilahkan delete data yang bersangkutan");
                        // copy data
                        bufferedOutput.write(data);

                    } else {

                        // format data baru kedalam database
                        String tahun = tempData[0];
                        String penulis = tempData[1];
                        String penerbit = tempData[2];
                        String judul = tempData[3];

                        // kita bikin primary key
                        long nomorEntry = ambilEntryPerTahun(penulis, tahun) + 1;

                        String punulisTanpaSpasi = penulis.replaceAll("\\s+","");
                        String primaryKey = punulisTanpaSpasi+"_"+tahun+"_"+nomorEntry;

                        // tulis data ke database
                        bufferedOutput.write(primaryKey + "," + tahun + ","+ penulis +"," + penerbit + ","+judul);
                    }
                } else {
                    // copy data
                    bufferedOutput.write(data);
                }
            } else {
                // copy data
                bufferedOutput.write(data);
            }
            bufferedOutput.newLine();

            data = bufferedInput.readLine();
        }

        // menulis data ke file
        bufferedOutput.flush();
        ridwanFileInput.close();
        ridwanFileOutput.close();
        System.gc();
        database.delete();

        // Delete Original File
        database.delete();
        // Rename File Sementara Ke Database
        tempDB.renameTo(database);
    }

    //Operasi
    public static void ridwanHapusData() throws IOException {
        // Ambil Database Original
        File database = new File("database.txt");
        FileReader fileInput = new FileReader(database);
        BufferedReader bufferedInput = new BufferedReader(fileInput);

        // Buat Database Sementara
        File tempDB = new File("tempDB.txt");
        FileWriter fileOutput = new FileWriter(tempDB);
        BufferedWriter bufferedOutput = new BufferedWriter(fileOutput);

        // Tampilkan Data
        System.out.println("Daftar Buku");
        ridwanTampilkanData();

        // Ambil Input User Untuk Delete Data
        Scanner terminalInput = new Scanner(System.in);
        System.out.print("\nMasukkan nomor buku yang akan dihapus: ");
        int deleteNum = terminalInput.nextInt();

        //Looping Untuk Baca Baris Dan Skip Data Yang Akan Didelete
        boolean isFound = false;
        int entryCounts = 0;

        String data = bufferedInput.readLine();

        while (data != null) {
            entryCounts++;
            boolean isDelete = false;

            StringTokenizer st = new StringTokenizer(data, ",");

            //Tampilkan Data Yang Ingin Dihapus
            if (deleteNum == entryCounts) {
                System.out.println("\nData yang ingin anda hapus adalah: ");
                System.out.println("-------------------------");
                System.out.println("Referensi   : " + st.nextToken());
                System.out.println("Tahun       : " + st.nextToken());
                System.out.println("Penulis     : " + st.nextToken());
                System.out.println("Penerbit    : " + st.nextToken());
                System.out.println("Judul       : " + st.nextToken());

                isDelete = ridwanYaAtauTidak("Apakah anda yakin akan menghapus");
                isFound = true;
            }

            if (isDelete) {

                System.out.println("Data berhasil dihapus");
            } else {

                bufferedOutput.write(data);
                bufferedOutput.newLine();
            }
            data = bufferedInput.readLine();
        }

        if (!isFound) {
            System.err.println("Buku tidak ditemukan");
        }

        bufferedOutput.flush();
        fileInput.close();
        bufferedInput.close();
        fileOutput.close();
        bufferedOutput.close();
        System.gc();
        database.delete();

        // Delete Original File
        if (database.delete()) {
            System.out.println("File: " + database.getName() + " berhasil dihapus");
        } else {
            System.out.println("File: " + database.getName() + " gagal dihapus");
        }
        // Rename File Sementara Ke Database
        if (tempDB.renameTo(database)) {
            System.out.println("File: " + database.getName() + " berhasil direname");
        } else {
            System.out.println("File: " + database.getName() + " gagal direname");
        }

    }
    //Operasi
    private static void ridwanTampilkanData() throws IOException {

        FileReader ridwanFileInput;
        BufferedReader ridwanBufferInput;

        try{
            ridwanFileInput = new FileReader("database.txt");
            ridwanBufferInput = new BufferedReader(ridwanFileInput);
        } catch (Exception e) {
            System.err.println("Database Tidak Ditemukan");
            System.err.println("Silahkan Tambah Data Terlebih Dahulu");
            ridwanTambahData();
            return;
        }

        System.out.println("\n| No |\tTahun |\tPenulis                |\tPenerbit               |\tJudul Buku");
        System.out.println("----------------------------------------------------------------------------------------------------------");

        String ridwanData = ridwanBufferInput.readLine();
        int ridwanNomorData = 0;
        while (ridwanData != null) {
            ridwanNomorData++;

            StringTokenizer ridwanToken = new StringTokenizer (ridwanData, ",");

            ridwanToken.nextToken();
            System.out.printf("| %2d ", ridwanNomorData);
            System.out.printf("|\t%4s ", ridwanToken.nextToken());
            System.out.printf("|\t%-20s ", ridwanToken.nextToken());
            System.out.printf("|\t%-20s ", ridwanToken.nextToken());
            System.out.printf("|\t%s ", ridwanToken.nextToken());
            System.out.println("\n");

            ridwanData = ridwanBufferInput.readLine();
        }

        System.out.println("----------------------------------------------------------------------------------------------------------");
    }
    //Operasi
    private static void ridwanCariData() throws IOException {

        //Membaca database ada atau tidak
        try {
            File file = new File ("database.txt");
        } catch (Exception e) {
            System.err.println("Database tidak ditemukan!");
            System.err.println("Silahkan tambah data terlebih dahulu");
            ridwanTambahData();
            return;
        }

        //Ambil keyword dari user
        Scanner terminalInput = new Scanner(System.in);
        System.out.print("Masukkan kata kunci untuk mencari buku: ");
        String ridwanCariString = terminalInput.nextLine();
        String[] keywords = ridwanCariString.split ("\\s+");

        //Cek Keyword di Database
        ridwanCekBukuDiDatabase (keywords, true);

    }
    //Operasi
    private static void ridwanTambahData() throws IOException {

        FileWriter fileOutput = new FileWriter("database.txt", true);
        BufferedWriter bufferOutput = new BufferedWriter(fileOutput);


        Scanner terminalInput = new Scanner(System.in);
        String penulis, judul, penerbit, tahun;

        System.out.print("Masukkan nama penulis: ");
        penulis = terminalInput.nextLine();
        System.out.print("Masukkan judul buku: ");
        judul = terminalInput.nextLine();
        System.out.print("Masukkan nama penerbit: ");
        penerbit = terminalInput.nextLine();
        System.out.print("Masukkan tahun terbit, format = (YYYY) : ");
        tahun = ridwanAmbilTahun();


        String[] kataKunci = {tahun + "," + penulis + "," + penerbit + "," + judul};
        System.out.println(Arrays.toString(kataKunci));

        boolean isExist = ridwanCekBukuDiDatabase(kataKunci, false);


        if (!isExist) {

            System.out.println(ambilEntryPerTahun(penulis, tahun));
            long nomorEntry = ambilEntryPerTahun(penulis, tahun) + 1;

            String penulisTanpaSpasi = penulis.replaceAll("\\s+", "");
            String primaryKey = penulisTanpaSpasi + "_" + tahun + "_" +nomorEntry;
            System.out.println("\nData yang akan anda masukkan adalah");
            System.out.println("--------------------------");
            System.out.println("Primary Key: " + primaryKey);
            System.out.println("Tahun Terbit: " + tahun);
            System.out.println("Penulis: " + penulis);
            System.out.println("Judul: " + judul);
            System.out.println("Penerbit: "+ penerbit);

            boolean isTambah = ridwanYaAtauTidak("Apakah akan ingin menambah data tersebut");

            if (isTambah) {
                bufferOutput.write(primaryKey + "," + tahun + "," + penulis + "," + penerbit + "," + judul);
                bufferOutput.newLine();
                bufferOutput.flush();
            }

        } else {
            System.out.println("buku yang anda akan masukan sudah tersedia di database dengan data berikut:");
            ridwanCekBukuDiDatabase(kataKunci, true);
        }

        bufferOutput.close();
    }
    //Utility
    private static long ambilEntryPerTahun(String penulis, String tahun) throws IOException {
        FileReader fileInput = new FileReader("database.txt");
        BufferedReader bufferInput = new BufferedReader(fileInput);

        long entry = 0;
        String data = bufferInput.readLine();
        Scanner dataScanner;
        String primaryKey;

        while (data != null) {
            dataScanner = new Scanner(data);
            dataScanner.useDelimiter(",");
            primaryKey = dataScanner.next();
            dataScanner = new Scanner(primaryKey);
            dataScanner.useDelimiter("_");

            penulis = penulis.replaceAll("\\s+", "");

            if (penulis.equalsIgnoreCase(dataScanner.next()) && tahun.equalsIgnoreCase(dataScanner.next()) ) {
                entry = dataScanner.nextInt();
            }

            data = bufferInput.readLine();
        }

        return entry;
    }
    //Utility
    private static boolean ridwanCekBukuDiDatabase(String[] kataKunci, boolean isDisplay) throws IOException {

        FileReader fileInput = new FileReader("database.txt");
        BufferedReader bufferInput = new BufferedReader(fileInput);

        String data = bufferInput.readLine();
        boolean isExist = false;
        int nomorData = 0;

        if (isDisplay) {
            System.out.println("\n| No |\tTahun |\tPenulis                |\tPenerbit               |\tJudul Buku");
            System.out.println("----------------------------------------------------------------------------------------------------------");
        }

        while (data != null) {


            isExist = true;

            for (String keyword : kataKunci) {
                isExist = isExist && data.toLowerCase().contains(keyword.toLowerCase());
            }


            if (isExist) {
                if (isDisplay) {
                    nomorData++;
                    StringTokenizer stringToken = new StringTokenizer(data, ",");

                    stringToken.nextToken();
                    System.out.printf("| %2d ", nomorData);
                    System.out.printf("|\t%4s  ", stringToken.nextToken());
                    System.out.printf("|\t%-20s ", stringToken.nextToken());
                    System.out.printf("|\t%-20s ", stringToken.nextToken());
                    System.out.printf("|\t%s ", stringToken.nextToken());
                    System.out.print("\n");
                } else {
                    break;
                }
            }

            data = bufferInput.readLine();
        }

        if (isDisplay) {
            System.out.println("----------------------------------------------------------------------------------------------------------");
        }

        return isExist;
    }
    //Utility
    private static String ridwanAmbilTahun() throws IOException {
        boolean tahunValid = false;
        Scanner terminalInput = new Scanner(System.in);
        String tahunInput = terminalInput.nextLine();

        while(!tahunValid) {
            try {
                Year.parse(tahunInput);
                tahunValid = true;
            } catch (Exception e) {
                System.out.println("Format tahun yang anda masukkan salah, format = (YYYY)");
                System.out.print("Silahkan masukkan tahun terbit lagi: ");
                tahunValid = false;
                tahunInput = terminalInput.nextLine();
            }
        }
        return tahunInput;
    }
    //Utility
    private static boolean ridwanYaAtauTidak (String ridwanPesan) {
        Scanner ridwanMenu = new Scanner(System.in);
        System.out.print("\n" + ridwanPesan + "  (y/t) ? ");
        String ridwanPilihan = ridwanMenu.next();

        while (!ridwanPilihan.equalsIgnoreCase("y") && !ridwanPilihan.equalsIgnoreCase("t")) {
            System.err.println("Pilihan Anda Bukan y Atau t");
            System.out.print("\n" + ridwanPesan + "  (y/t) ? ");
            ridwanPilihan = ridwanMenu.next();
        }
        if (ridwanPesan.equalsIgnoreCase("Apakah anda ingin keluar")) {
            if (ridwanPilihan.equalsIgnoreCase("y")) {
                System.out.println("Dibuat oleh M. Ridwan Alsafir Gusnendar");
                return ridwanPilihan.equalsIgnoreCase("t");
            } else {
                return ridwanPilihan.equalsIgnoreCase("t");
            }
        } else if (ridwanPesan.equalsIgnoreCase("Apakah Anda ingin melanjutkan")) {
            if (ridwanPilihan.equalsIgnoreCase("y")) {
                return ridwanPilihan.equalsIgnoreCase("y");
            } else {
                System.out.println("Dibuat oleh M. Ridwan Alsafir Gusnendar");
                return ridwanPilihan.equalsIgnoreCase("y");
            }

        } else if ( ridwanPesan.equalsIgnoreCase("Apakah akan ingin menambah data tersebut")) {
            if (ridwanPilihan.equalsIgnoreCase("y")) {
                System.out.println("Data berhasil ditambahkan");
                return ridwanPilihan.equalsIgnoreCase("y");
            } else {
                System.out.println("Data tidak jadi ditambahkan");
                return ridwanPilihan.equalsIgnoreCase("y");
            }

        } else if (ridwanPesan.equalsIgnoreCase("Apakah anda yakin akan menghapus")) {
            if (ridwanPilihan.equalsIgnoreCase("y")) {
                System.out.println("Data berhasil dihapus");
                return ridwanPilihan.equalsIgnoreCase("y");
            } else {
                System.out.println("Data tidak jadi dihapus");
                return ridwanPilihan.equalsIgnoreCase("y");
            }

            } else if (ridwanPesan.equalsIgnoreCase("Apakah anda yakin ingin mengupdate data tersebut")) {
            if (ridwanPilihan.equalsIgnoreCase("y")) {
                System.out.println("Data berhasil diupdate");
                return ridwanPilihan.equalsIgnoreCase("y");
            } else {
                System.out.println("Data tidak jadi diupdate");
                return ridwanPilihan.equalsIgnoreCase("y");
            }

            } else if (ridwanPesan.equalsIgnoreCase("Apakah anda ingin merubah")) {
            if (ridwanPilihan.equalsIgnoreCase("y")) {
                System.out.println("Data berhasil diupdate");
                return ridwanPilihan.equalsIgnoreCase("y");
            } else {
                System.out.println("Data tidak jadi diupdate");
                return ridwanPilihan.equalsIgnoreCase("y");
        }

    } else {
            //System.out.println("Salah pada RidwanPesan");
            return ridwanPilihan.equalsIgnoreCase("y");
        }

    }
    //Utility
    private static void ridwanHapusLayar(){
        try {

            if (System.getProperty("os.name").contains("Windows")){
                new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033\143");
            }
        } catch (Exception ex){
            System.err.println("Tidak bisa hapus layar");
        }
    }
}