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

        // 2.Lecture du graphe sur le fichier et mise en mémoire
        String path = "Graphes/" + numGraphe + ".txt"; //Chemin vers le graphe mentionné par l'utilisateur
        List<String> content = Files.readAllLines(Paths.get(path));

        int nbSommets = Integer.parseInt(content.get(0)); //1ère ligne = nbSommets, donc on l'assigne
        int nbArcs = Integer.parseInt(content.get(1)); //2ème ligne = nbArcs

        String[] lineDataString; //Données de la ligne au format String
        Integer[] lineData = {null, null, null};
        int lineDataIndex; //Donnée concernée (Extrémité Initiale, terminale, ou valeur de l'arc
        ArrayList<Integer[]> data = new ArrayList<>(); //ArrayList contenant les données de chaque ligne

        for(int i = 2; i < content.size(); i++) {
            lineDataString = content.get(i).split(" "); //Convertion de la ligne en tableau contenant les données de la ligne

            for(int j = 0; j < lineDataString.length; j++) {
                lineDataIndex = Integer.parseInt(lineDataString[j]); //String to int
                lineData[j] = lineDataIndex; //Ajout de la donnée de la ligne
            }

            data.add(lineData);
        }

        Graphe graphe = new Graphe(nbSommets, nbArcs, data);
        graphe.afficherBrut();

        //BufferedReader permet de lire le contenu d'un fichier
//        try (BufferedReader br = new BufferedReader(new FileReader(grapheFile))) {
//            StringBuilder sb = new StringBuilder(); //Utiliser pour mettre en mémoire le contenu du fichier
//
//            String line = br.readLine(); //Lit la première ligne
//            int nbSommets = Integer.parseInt(line); //1ère ligne = nbSommets, donc on l'assigne
//            line = br.readLine(); //Lit la deuxième ligne
//            int nbArcs = Integer.parseInt(line); //2ème ligne = nbArcs
//            Graphe g = new Graphe(nbSommets, nbArcs);
//            System.out.println(g.getNbSommets());
//            System.out.println(g.getNbArcs());
//
//            String[] lineDataString; //Données de la ligne au format String
//            Integer[] lineData = { 0, 0, 0};
//            int lineDataIndex; //Numéro de la donnée concernée (Extrémité Initiale, terminale, ou valeur de l'arc
//            ArrayList<Integer[]> data = new ArrayList<>(); //ArrayList contenant les données de chaque ligne
//
//            //Tant que le BufferedReader ne renvoit pas nul (=tant qu'on est pas arrivés à la fin du fichier),
//            //On lit et on met en mémoire le contenu du graphe dans un string "contenu"
//            while (line != null) {
//                sb.append(line);
//                sb.append(System.lineSeparator());
//                line = br.readLine();
//                lineDataString = line.split(" "); //Convertion de la ligne en tableau contenant les données de la ligne
//
//                //Pour chaque donnée de ligne, l'ajouter au tableau de la ligne
//                for(int i = 0; i < lineDataString.length; i++) {
//                    lineDataIndex = Integer.parseInt(lineDataString[i]); //String to int
//                    lineData[i] = lineDataIndex; //Ajout de la donnée de la ligne
//                }
//                data.add(lineData);
//            }
//
//            Graphe graphe = new Graphe(nbSommets, nbArcs, data);
//            graphe.afficherBrut();
//            String contenu = sb.toString();
//            System.out.println(contenu); //Affichage du contenu du fichier
//        }
//        catch (Exception e) { //Si le fichier n'est pas trouvé, on affiche une erreur
//            System.out.println("[ERR] Fichier introuvable");
//        }

        // 3.Affichage du graphe (sous forme de matrice(s))
        // 4.Floyd-Warshall
        // 5.Existence de circuit absorbant ?
            // 5.1.Si NON: affichage des chemins
        //6.Demander si on veut recommencer avec un autre graphe
    }
}
