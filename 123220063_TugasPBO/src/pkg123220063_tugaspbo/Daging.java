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
public class Daging extends BahanMakanan implements JenisMakanan{
     Daging(String nama) {
        super(nama);
    }

     @Override
    void memasak() {
        System.out.println(nama + " dapat dipanggang atau direbus.");
    }

     @Override
    void rasa() {
        System.out.println(nama + " memiliki rasa gurih.");
    }

     @Override
    void kandunganGizi() {
        System.out.println(nama + " kaya akan protein dan zat besi.");
    }

     @Override
    public void tampilJenis() {
        System.out.println(nama + " termasuk dalam jenis daging.");
    }
}
