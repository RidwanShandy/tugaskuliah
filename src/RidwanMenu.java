/*
 * Nama      : M. Ridwan Alsafir Gusnendar
 * NIM       : 202222031
 * Kelompok  : Teknik Informatika (Sore)
 */

import java .io.IOException;
import java.util.Scanner;

import crud.RidwanOperasi;
import crud.RidwanUtility;

public class RidwanMenu {

    public static void main(String[] args) throws IOException {

        Scanner ridwanMenu = new Scanner(System.in);
        String ridwanPilihan;
        boolean ridwanLanjut = true;

        while (ridwanLanjut) {
            RidwanUtility.ridwanHapusLayar();
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
                    RidwanOperasi.ridwanTampilkanData();
                    break;
                case "2":
                    System.out.println("\n=========");
                    System.out.println("CARI BUKU");
                    System.out.println("=========");
                    RidwanOperasi.ridwanCariData();
                    break;
                case "3":
                    System.out.println("\n================");
                    System.out.println("TAMBAH DATA BUKU");
                    System.out.println("================");
                    RidwanOperasi.ridwanTambahData();
                    RidwanOperasi.ridwanTampilkanData();
                    break;
                case "4":
                    System.out.println("\n==============");
                    System.out.println("UBAH DATA BUKU");
                    System.out.println("==============");
                    RidwanOperasi.ridwanUpdateData();
                    break;
                case "5":
                    System.out.println("\n===============");
                    System.out.println("HAPUS DATA BUKU");
                    System.out.println("===============");
                    RidwanOperasi.ridwanHapusData();
                    break;
                case "6":

                    ridwanLanjut = false;
                    ridwanLanjut = RidwanUtility.ridwanYaAtauTidak("Apakah anda ingin keluar?");

                    break;
                default:
                    System.err.println("\nInput anda tidak ditemukan\nSilahkan pilih [1-6]");
            }
            if (ridwanLanjut) {
                ridwanLanjut = RidwanUtility.ridwanYaAtauTidak("Apakah Anda ingin melanjutkan?");
            } else {

            }

        }
    }

}
