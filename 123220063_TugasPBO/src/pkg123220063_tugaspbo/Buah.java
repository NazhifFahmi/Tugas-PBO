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
public class Buah extends BahanMakanan implements JenisMakanan{
    Buah(String nama) {
        super(nama);
    }

    @Override
    void memasak() {
        System.out.println(nama + " dapat dimakan langsung atau dibuat jus.");
    }

    @Override
    void rasa() {
        System.out.println(nama + " memiliki rasa manis atau asam.");
    }

    @Override
    void kandunganGizi() {
        System.out.println(nama + " mengandung banyak vitamin dan serat.");
    }

    /**
     *
     */
    @Override
    public void tampilJenis() {
        System.out.println(nama + " termasuk dalam jenis buah.");
    }
}
