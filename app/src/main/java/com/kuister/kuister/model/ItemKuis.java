package com.kuister.kuister.model;

import java.util.ArrayList;

/**
 * Created by Rasyadh A Aziz on 13/07/2016.
 */
public class ItemKuis {
    private String item;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public static ArrayList<ItemKuis> getData(String data){
        ArrayList<ItemKuis> dataList = new ArrayList<>();

        switch (data){
            case "SD":
                String itemMPSD[] = {
                        "Bahasa Indonesia", "Matematika", "Ilmu Pengetahuan Alam"
                };

                for (int i = 0; i < itemMPSD.length; i++){
                    ItemKuis itemKuis = new ItemKuis();
                    itemKuis.setItem(itemMPSD[i]);
                    dataList.add(itemKuis);
                }
                break;
            case "SMP":
                String itemMPSMP[] = {
                        "Bahasa Indonesia", "Bahasa Inggris", "Matematika", "Ilmu Pengetahuan Alam"
                };
                for (int i = 0; i < itemMPSMP.length; i++){
                    ItemKuis itemKuis = new ItemKuis();
                    itemKuis.setItem(itemMPSMP[i]);
                    dataList.add(itemKuis);
                }
                break;
            case "SMA":
                String itemMPSMA[] = {
                        "Matematika - IPA", "Fisika", "Kimia", "Biologi", "Matematika - IPS",
                        "Geografi", "Ekonomi", "Sosiologi", "Bahasa Indonesia", "Bahasa Inggris"
                };

                for (int i = 0; i < itemMPSMA.length; i++){
                    ItemKuis itemKuis = new ItemKuis();itemKuis.setItem(itemMPSMA[i]);
                    dataList.add(itemKuis);
                }
        }

        return dataList;
    }
}

