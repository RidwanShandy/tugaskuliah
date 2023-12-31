/*
 * Nama      : M. Ridwan Alsafir Gusnendar
 * NIM       : 202222031
 * Kelompok  : Teknik Informatika (Sore)
 */

package crud;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Year;
import java.util.Scanner;
import java.util.StringTokenizer;



public class RidwanUtility {

    static long ambilEntryPerTahun(String penulis, String tahun) throws IOException {
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
        fileInput.close();
        bufferInput.close();
        return entry;
    }

    protected static String ridwanAmbilTahun() throws IOException {
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

    static boolean ridwanCekBukuDiDatabase(String[] kataKunci, boolean isDisplay) throws IOException {

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
        fileInput.close();
        bufferInput.close();
        return isExist;

    }

    public static boolean ridwanYaAtauTidak (String ridwanPesan) {
        Scanner ridwanMenu = new Scanner(System.in);
        System.out.print("\n" + ridwanPesan + "  (y/t) ? ");
        String ridwanPilihan = ridwanMenu.next();

    if (!ridwanPilihan.equalsIgnoreCase("y") && !ridwanPilihan.equalsIgnoreCase("t")) {
        while (!ridwanPilihan.equalsIgnoreCase("y") && !ridwanPilihan.equalsIgnoreCase("t")) {
            System.err.println("Pilihan anda bukan y atau t");
            System.out.print("\n" + ridwanPesan + " (y/t)?");
            ridwanPilihan = ridwanMenu.next();
        }
    } else {
        System.out.println("Dibuat oleh M. Ridwan Alsafir Gusnendar");
    }
    return ridwanPilihan.equalsIgnoreCase("y");

}

    public static void ridwanHapusLayar(){
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