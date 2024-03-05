/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg123220063_tugaspbo;

/**
 *
 * @author ACER
 */
public class Sayuran extends BahanMakanan implements JenisMakanan {
    Sayuran(String nama) {
        super(nama);
    }

    @Override
    void memasak() {
        System.out.println(nama + " dapat direbus atau di tumis.");
    }

    @Override
    void rasa() {
        System.out.println(nama + " memiliki rasa segar.");
    }

    @Override
    void kandunganGizi() {
        System.out.println(nama + " mengandung banyak serat dan vitamin.");
    }

    @Override
    public void tampilJenis() {
        System.out.println(nama + " termasuk dalam jenis sayuran.");
    }
}
