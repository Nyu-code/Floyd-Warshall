import java.util.ArrayList;
import java.util.Arrays;

public class Graphe {
    private int nbSommets;
    private int nbArcs;
    private ArrayList<ArrayList<Integer>> data; //ArrayList contenant des ArrayList contenant chacune [extremite initiale, terminale, valeur arc]
    private int[][] matriceAdjacence;

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

    //Affichage du graphe de manière brute (utilisée à des fins de tests)
    public void afficherBrut() {
        System.out.println("GRAPHE BRUT");
        System.out.println("- - - - -");
        System.out.println(nbSommets);
        System.out.println(nbArcs);

        for (ArrayList<Integer> lineData : data) {
            System.out.println(lineData);
        }
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
        for (int[] ints : matriceAdjacence) {
            for (int j = 0; j < matriceAdjacence.length; j++) {
                System.out.print(ints[j] + " ");
            }
            System.out.println();
        }
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
