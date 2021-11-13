import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TestGraphe {
    public static void main(String[] args) throws IOException {
        System.out.println("Bienvenue dans le programme de test de l'algorithme de Floyd-Warshall !");

        // 1.Choix du graphe
        Scanner sc = new Scanner(System.in); // Création d'un scanner pour que l'utilisateur entre le numéro du graphe

        System.out.println("Veuillez entrer le numéro du graphe que vous voulez analyser: ");
        int numGraphe = sc.nextInt(); // L'utilisateur entre le numéro
        String path = "Graphes/" + numGraphe + ".txt"; //Chemin vers le graphe mentionné par l'utilisateur
        File f = new File(path);
        if(!f.exists()) {
            System.out.println("[ERR] Fichier introuvable.");
        } else {
            // 2.Lecture du graphe sur le fichier et mise en mémoire
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

            Graphe graphe = new Graphe(nbSommets, nbArcs, data); //Création d'un nouveau graphe contenant les valeurs brutes
            graphe.afficherBrut();

            // 3.Affichage du graphe (sous forme de matrice(s))
            // 4.Floyd-Warshall
            // 5.Existence de circuit absorbant ?
            // 5.1.Si NON: affichage des chemins
            //6.Demander si on veut recommencer avec un autre graphe
        }
    }
}
