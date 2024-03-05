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
public class Main {
    public static void main(String[] args) {
        Sayuran bayam = new Sayuran("Bayam");
        bayam.memasak();
        bayam.rasa();
        bayam.kandunganGizi();
        bayam.tampilJenis();

        System.out.println();

        Daging sapi = new Daging("Sapi");
        sapi.memasak();
        sapi.rasa();
        sapi.kandunganGizi();
        sapi.tampilJenis();

        System.out.println();

        Buah apel = new Buah("Apel");
        apel.memasak();
        apel.rasa();
        apel.kandunganGizi();
        apel.tampilJenis();
    }
}
