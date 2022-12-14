// EFREI PARIS L3NEW
// Projet Graphe S5 - Groupe E3
// BENOUDA Karim - LACHAUD Antoine - TRAN Kevin-Fei - XIONG Nicolas - WU Jacques graph

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class E3_TestGraphe {
    public static void main(String[] args) throws IOException {
        System.out.println("Bienvenue dans le programme de test de l'algorithme de Floyd-Warshall !");
        boolean resultat = true;
        // 1.Choix du graphe
        do {
            Scanner sc = new Scanner(System.in); // Création d'un scanner pour que l'utilisateur entre le numéro du graphe

            System.out.println("Veuillez entrer le numéro du graphe que vous voulez analyser: ");
            String numGraphe = sc.nextLine(); // L'utilisateur entre le numéro
            String path = "Graphes/" + numGraphe + ".txt"; //Chemin vers le graphe mentionné par l'utilisateur
            File f = new File(path);

            // 2.Lecture du graphe sur le fichier et mise en mémoire
            //Si le fichier n'existe pas, arrêter le programme
            if(!f.exists()) {
                System.out.println("[ERR] Fichier introuvable.");
            } else {
                List<String> content = Files.readAllLines(Paths.get(path));

                int nbSommets = Integer.parseInt(content.get(0)); //1ère ligne = nbSommets, donc on l'assigne
                int nbArcs = Integer.parseInt(content.get(1)); //2ème ligne = nbArcs

                String[] lineDataString; //Données de la ligne au format String
                int lineDataIndex; //Donnée concernée (Extrémité Initiale, terminale, ou valeur de l'arc
                ArrayList<ArrayList<Integer>> data = new ArrayList<>(); //ArrayList contenant les données de chaque ligne

                for(int i = 2; i < content.size(); i++) {
                    ArrayList<Integer> lineData = new ArrayList<>(); //Ligne contenant les données
                    lineDataString = content.get(i).split(" "); //Convertion de la ligne en tableau contenant les données de la ligne

                    for (String s : lineDataString) {
                        lineDataIndex = Integer.parseInt(s); //String to int
                        lineData.add(lineDataIndex); //Ajout de la donnée de la ligne
                    }

                    data.add(lineData); //Ajout de la ligne à l'ArrayList générale
                }

                E3_Graphe graphe = new E3_Graphe(nbSommets, nbArcs, data); //Création d'un nouveau graphe contenant les valeurs brutes
                graphe.matriceAdj();
                graphe.matriceValeurs();
                //graphe.afficherBrut(); commande test

                // 3.Affichage du graphe (Matrice d'adjacence et matrice de valeurs)
                graphe.afficherMatriceAdj();
                graphe.afficherMatriceValeurs();
                // 4.Floyd-Warshall
                graphe.floyd_Warshall();

            }
            boolean go = false;
            while(!go) {
                System.out.println("Souhaitez-vous continuer avec un autre graphe ? Entrez 'oui' si vous souhaitez analyser un autre graphe, 'non' si vous souhaitez .");
                Scanner scanner = new Scanner(System.in);
                String reponse = scanner.nextLine();
                if (reponse.equals("non")) { //Si la réponse de l'utilisateur est non alors on arrête tout
                    resultat = false;
                    scanner.close();
                    go = true;
                } else if (reponse.equals("oui")) { //sinon on continue d'analyser des graphes
                    go = true;
                } else { //Si l'utilisateur dit pas soit oui soit non alors on repose la question
                    System.out.println("Je n'ai pas compris votre réponse veuillez écrire 'oui' si vous souhaitez analyser un autre graphe, 'non' si vous souhaitez arrêter.\n");
                }
            }
        } while (resultat);
    }
}
