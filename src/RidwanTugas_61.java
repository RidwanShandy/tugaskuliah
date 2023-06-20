/*
 * Nama      : M. Ridwan Alsafir Gusnendar
 * NIM       : 202222031
 * Kelompok  : Teknik Informatika (Sore)
 */

// Baca Data
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class RidwanTugas_61 {

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
                    // cari ridwanData
                    break;
                case "3":
                    System.out.println("\n================");
                    System.out.println("TAMBAH DATA BUKU");
                    System.out.println("================");
                    // tambah ridwanData
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

        System.out.println("\n| No |\tTahun |\tPenulis |\tPenerbit |\tJudul Buku");
        System.out.println("------------------------------------------------------");

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

        System.out.println("------------------------------------------------------");
    }
    //Utility
    private static boolean ridwanYaAtauTidak(String ridwanPesan) {
        Scanner ridwanMenu = new Scanner(System.in);
        System.out.print("\n"+ridwanPesan+" (y/t)? ");
        String ridwanPilihan = ridwanMenu.next();

        while (!ridwanPilihan.equalsIgnoreCase("y") && !ridwanPilihan.equalsIgnoreCase("t")) {
            System.err.println("Pilihan anda bukan y atau t");
            System.out.print("\n"+ridwanPesan+" (y/t)? ");
            ridwanPilihan = ridwanMenu.next();
        }
        if (ridwanPesan.equalsIgnoreCase("Apakah anda ingin keluar")) {
            if (ridwanPilihan.equalsIgnoreCase("y")) {
                System.out.println("Dibuat oleh M. Ridwan Alsafir Gusnendar");
                return ridwanPilihan.equalsIgnoreCase("t");
            } else {
                return ridwanPilihan.equalsIgnoreCase("t");
            }
        } else if (ridwanPesan.equalsIgnoreCase("Apakah anda ingin melanjutkan")) {
            if (ridwanPilihan.equalsIgnoreCase("y")) {
                return ridwanPilihan.equalsIgnoreCase("y");
            } else {
                System.out.println("Dibuat oleh M. Ridwan Alsafir Gusnendar");
                return ridwanPilihan.equalsIgnoreCase("y");
            }
        } else {
            //System.out.println("Salah pada RidwanPesan");
            return ridwanPilihan.equalsIgnoreCase("t");
        }
    }

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