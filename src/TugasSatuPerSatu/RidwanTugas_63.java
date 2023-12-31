package TugasSatuPerSatu;/*
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

public class RidwanTugas_63 {

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
                    // ubah ridwanData
                    break;
                case "5":
                    System.out.println("\n===============");
                    System.out.println("HAPUS DATA BUKU");
                    System.out.println("===============");
                    // hapus ridwanData
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
    private static void ridwanTampilkanData() throws IOException {

        FileReader ridwanFileInput;
        BufferedReader ridwanBufferInput;

        try{
            ridwanFileInput = new FileReader("database.txt");
            ridwanBufferInput = new BufferedReader(ridwanFileInput);
        } catch (Exception e) {
            System.err.println("Database Tidak Ditemukan");
            System.err.println("Silahkan Tambah Data Terlebih Dahulu");
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
    private static String ridwanAmbilTahun() throws IOException{
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

        } else {
            System.out.println("Salah pada RidwanPesan");
            return ridwanPilihan.equalsIgnoreCase("t");
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