package com.kuister.kuister.model;

import com.kuister.kuister.helper.ReferenceUrl;

import java.util.ArrayList;

/**
 * Created by Rasyadh A Aziz on 14/07/2016.
 */
public class ItemAktifitas {
    private String tingkatPendidikan;
    private String mataPelajaran;
    private String waktu;
    private String skor;

    public String getTingkatPendidikan() {
        return tingkatPendidikan;
    }

    public void setTingkatPendidikan(String tingkatPendidikan) {
        this.tingkatPendidikan = tingkatPendidikan;
    }

    public String getMataPelajaran() {
        return mataPelajaran;
    }

    public void setMataPelajaran(String mataPelajaran) {
        this.mataPelajaran = mataPelajaran;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getSkor() {
        return skor;
    }

    public void setSkor(String skor) {
        this.skor = skor;
    }

    public static ArrayList<ItemAktifitas> getData(){
        ArrayList<ItemAktifitas> dataListAktifitas = new ArrayList<>();

        String[] TingkatPendidikan = {
                "SMA", "SMA", "SMA", "SMA", "SMA", "SMA"
        };

        String[] MataPelajaran = {
                "Bahasa Indonesia", "Bahasa Inggris", "Fisika", "Kimia", "Biologi", "Matematika -IPA"
        };

        String[] Waktu = {
                "12/07/2016", "11/07/2016", "10/07/2016", "09/07/2016", "08/07/2016", "07/07/2016",
        };

        String[] Skor = {
                "90", "80", "60", "80", "70", "100"
        };

        for (int i = 0; i < TingkatPendidikan.length; i++){
            ItemAktifitas itemAktifitas = new ItemAktifitas();
            itemAktifitas.setTingkatPendidikan(TingkatPendidikan[i]);
            itemAktifitas.setMataPelajaran(MataPelajaran[i]);
            itemAktifitas.setWaktu(Waktu[i]);
            itemAktifitas.setSkor(Skor[i]);
            dataListAktifitas.add(itemAktifitas);
        }

        return dataListAktifitas;
    }

    public static String getSkorTotal(){

        ReferenceUrl.localSkor = null;
        int total = 0;

        String[] Skor = {
                "90", "80", "60", "80", "70", "100"
        };

        for (int i = 0; i < Skor.length; i++){
            int temp = Integer.parseInt(Skor[i]);
            total = total +temp;
        }

        ReferenceUrl.localSkor = String.valueOf(total) + " poin";

        return ReferenceUrl.localSkor;
    }
}

