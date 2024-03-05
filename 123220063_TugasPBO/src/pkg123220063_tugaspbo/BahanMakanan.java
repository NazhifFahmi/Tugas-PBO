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
abstract class BahanMakanan {
    protected String nama;

    BahanMakanan(String nama) {
        this.nama = nama;
    }

    abstract void memasak();
    abstract void rasa();
    abstract void kandunganGizi();
}
