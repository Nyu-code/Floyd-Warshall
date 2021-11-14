import java.util.ArrayList;
import java.util.Arrays;

public class Graphe {
    private int nbSommets;
    private int nbArcs;
    private ArrayList<ArrayList<Integer>> data; //ArrayList contenant des ArrayList contenant chacune [extremite initiale, terminale, valeur arc]
    private int[][] matriceAdjacence;
    private int[][] matriceValeurs;
    private int[][] matriceFloydWarshall;

    public Graphe(int nbSommets, int nbArcs) {
        this.nbSommets = nbSommets;
        this.nbArcs = nbArcs;
    }

    //Constructeur du graphe
    public Graphe(int nbSommets, int nbArcs, ArrayList<ArrayList<Integer>> data) {
        this.nbSommets = nbSommets;
        this.nbArcs = nbArcs;
        this.data = data;
    }

    //Affichage du graphe de manière brute [À DES FINS DE TESTS, INUTILE SINON]
    public void afficherBrut() {
        System.out.println("GRAPHE BRUT");
        System.out.println("- - - - -");
        System.out.println(nbSommets);
        System.out.println(nbArcs);

        for (ArrayList<Integer> lineData : data) {
            System.out.println(lineData);
        }
        System.out.println();
    }

    //Retourne la matrice d'adjacence du graphe
    public int[][] matriceAdj() {
        int[][] mAdj = new int[nbSommets][nbSommets]; //Matrice d'adjacence, valeurs initialisées à 0

        int numSommet;
        int numSommetAdj; // Numéro sommet adjacent

        for (ArrayList<Integer> lineData: data) {
            numSommet = lineData.get(0); //La première valeur de lineData est numSommet
            numSommetAdj = lineData.get(1); //2ème val : sommet adjacent

            //On met la valeur de la matriceAdj à 1 lorsque les sommets sont adjacents
            //On laisse à zéro si ce n'est pas le cas
            mAdj[numSommet][numSommetAdj] = 1;
        }

        matriceAdjacence = mAdj; //On met la matrice d'adjacence en mémoire
        return mAdj;
    }

    //Affiche la matrice d'adjacence
    public void afficherMatriceAdj() {
        System.out.println("Matrice d'adjacence:");
        for(int i = 0; i < matriceAdjacence.length; i++) {
            for(int j = 0; j < matriceAdjacence.length; j++) {
                System.out.print(matriceAdjacence[i][j] + " "); //On affiche ligne par ligne
            }
            System.out.println(); //On va à la ligne lorsque l'on a terminé un sommet
        }
        System.out.println();
    }

    //Retourne la matrice des valeurs des chemins
    public int[][] matriceValeurs() {
        int[][] mValeurs = new int[nbSommets][nbSommets]; //Matrice d'adjacence, valeurs initialisées à 0

        int numSommet;
        int numSommetAdj; // Numéro sommet adjacent
        int valeur; // Valeur du chemin

        for (ArrayList<Integer> lineData: data) {
            numSommet = lineData.get(0); //La première valeur de lineData est numSommet
            numSommetAdj = lineData.get(1); //2ème val : sommet adjacent
            valeur = lineData.get(2); //Valeur du chemin

            //On met la valeur de la matriceAdj à 1 lorsque les sommets sont adjacents
            //On laisse à zéro si ce n'est pas le cas
            mValeurs[numSommet][numSommetAdj] = valeur;
        }

        matriceValeurs = mValeurs; //On met la matrice d'adjacence en mémoire
        return mValeurs;
    }

    //Affiche la matrice des valeurs des chemins
    public void afficherMatriceValeurs() {
        System.out.println("Matrice des valeurs:");
        for(int i = 0; i < matriceValeurs.length; i++) {
            for(int j = 0; j < matriceValeurs.length; j++) {
                System.out.print(matriceValeurs[i][j] + " "); //On affiche ligne par ligne
            }
            System.out.println(); //On va à la ligne lorsque l'on a terminé un sommet
        }
        System.out.println();
    }
    public void floyd_Warshall(){
        matriceFloydWarshall = this.matriceValeurs;
        for (int k = 0 ; k < nbSommets ; k++) { //Nombre de répétitions à faire pour avoir la matrice avec tout les plus courts chemins possible de la matrice
            for (int i = 0; i < nbSommets; i++) { //Boucle pour une matrice (nb sommets * nb sommets)
                for (int j = 0; j < nbSommets; j++) {
                    if (matriceFloydWarshall[i][k] != 0 && matriceFloydWarshall[k][j] != 0) { //On vérifie qu'il n'y a pas de valeur infinie si il y en a un la valeur de M[i,j] reste pareil
                        int val = matriceFloydWarshall[i][k] + matriceFloydWarshall[k][j]; //Sinon on calcule le coût du chemin de i => k => j
                        System.out.println(val);
                        System.out.println(matriceFloydWarshall[i][j]);
                        if ( i!= j & matriceFloydWarshall[i][j] == 0){ //Si le chemin directe de i vers j = 0 ce qui veut dire infinie alors on lui accorde la valeur calculé directement et que le 0 n'est pas égale à une valeur de la diagonale.
                            matriceFloydWarshall[i][j] = val; //Si la valeur n'est pas égale à l'infinie alors on peut commencer la comparaison.
                        }
                        else if (val < matriceFloydWarshall[i][j]) { //On compare la valeur calculé à la valeur initiale si la valeur calculé coûte moins cher que la valeur initiale alors on la remplace par la valeur calculé
                            matriceFloydWarshall[i][j] = val;
                        }
                    }
                }
            }
        }
    }
    public void afficherMatriceFloydWarshall() {
        System.out.println("Matrice avec l'algorithme de Floyd-Warshall : ");
        for(int i = 0; i < matriceFloydWarshall.length; i++) {
            for(int j = 0; j < matriceFloydWarshall.length; j++) {
                System.out.print(matriceFloydWarshall[i][j] + " "); //On affiche ligne par ligne
            }
            System.out.println(); //On va à la ligne lorsque l'on a terminé un sommet
        }
        System.out.println();
    }

    public boolean possedeCircuitAbsorbant(int[][] matriceValeurs){
        for(int i = 0; i < matriceValeurs.length; i++) {
            for(int j = 0; j < matriceValeurs.length; j++) {
                if (i != j){
                    if (matriceValeurs[i][j] < 0){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //GETTER SETTER NBSOMMETS
    public int getNbSommets() {
        return nbSommets;
    }
    public void setNbSommets(int nbSommets) {
        this.nbSommets = nbSommets;
    }

    //GETTER SETTER NBARCS
    public int getNbArcs() {
        return nbArcs;
    }
    public void setNbArcs(int nbArcs) {
        this.nbArcs = nbArcs;
    }

    //GETTER SETTER DATA
    public ArrayList<ArrayList<Integer>> getData() {
        return data;
    }
    public void setData(ArrayList<ArrayList<Integer>> data) {
        this.data = data;
    }
}
