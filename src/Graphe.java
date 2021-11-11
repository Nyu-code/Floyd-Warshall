import java.util.ArrayList;
import java.util.Arrays;

public class Graphe {
    private int nbSommets;
    private int nbArcs;
    private ArrayList<ArrayList<Integer>> data; //ArrayList contenant des ArrayList contenant chacune [extremite initiale, terminale, valeur arc]

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
