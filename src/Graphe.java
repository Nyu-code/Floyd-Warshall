import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Graphe {
    private int nbSommets;
    private int nbArcs;
    private ArrayList<ArrayList<Integer>> data; //ArrayList contenant des ArrayList contenant chacune [extremite initiale, terminale, valeur arc]
    private int[][] matriceAdjacence;
    private String[][] matriceValeurs;
    private String[][] matriceFloydWarshall;

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

    //Retourne la matrice des valeurs des chemins
    public String[][] matriceValeurs() {
        String[][] mValeurs = new String[nbSommets][nbSommets]; //Matrice d'adjacence, valeurs initialisées à 0
        for (int i = 0; i< nbSommets ; i++){
            for (int j = 0; j< nbSommets; j++){
                mValeurs[i][j] = "inf";
                if (i == j) {
                    mValeurs[i][j] = "0";
                }
            }
        }

        int numSommet;
        int numSommetAdj; // Numéro sommet adjacent
        String valeur; // Valeur du chemin

        for (ArrayList<Integer> lineData: data) {
            numSommet = lineData.get(0); //La première valeur de lineData est numSommet
            numSommetAdj = lineData.get(1); //2ème val : sommet adjacent
            valeur = String.valueOf(lineData.get(2)); //Valeur du chemin

            if (mValeurs[numSommet][numSommetAdj] != "0" & mValeurs[numSommet][numSommetAdj] != "inf"){
                if (Integer.parseInt(mValeurs[numSommet][numSommetAdj]) > Integer.parseInt(valeur)){
                    mValeurs[numSommet][numSommetAdj] = valeur;
                }
            }
            else {
                mValeurs[numSommet][numSommetAdj] = valeur;
            }
        }

        matriceValeurs = mValeurs; //On met la matrice d'adjacence en mémoire
        return mValeurs;
    }

    public void floyd_Warshall(){
        matriceFloydWarshall = this.matriceValeurs;
        for (int k = 0 ; k < nbSommets ; k++) { //Nombre de répétitions à faire pour avoir la matrice avec tout les plus courts chemins possible de la matrice
            for (int i = 0; i < nbSommets; i++) { //Boucle pour une matrice (nb sommets * nb sommets)
                //System.out.println("i = " + i);
                for (int j = 0; j < nbSommets; j++) {
                    //System.out.println("j = " + j);
                    if (matriceFloydWarshall[i][k] != "inf" & matriceFloydWarshall[k][j] != "inf" & i!=j) { //On vérifie qu'il n'y a pas de valeur infinie si il y en a un la valeur de M[i,j] reste pareil
                        int val = Integer.parseInt(matriceFloydWarshall[i][k]) + Integer.parseInt(matriceFloydWarshall[k][j]); //Sinon on calcule le coût du chemin de i => k => j
                        //System.out.println(matriceFloydWarshall[i][j]);
                        if (matriceFloydWarshall[i][j] == "inf"){ //Si le chemin directe de i vers j = 0 ce qui veut dire infinie alors on lui accorde la valeur calculé directement et que le 0 n'est pas égale à une valeur de la diagonale.
                            matriceFloydWarshall[i][j] = String.valueOf(val); //Si la valeur n'est pas égale à l'infinie alors on peut commencer la comparaison.
                        }
                        else if (val < Integer.parseInt(matriceFloydWarshall[i][j])) { //On compare la valeur calculé à la valeur initiale si la valeur calculé coûte moins cher que la valeur initiale alors on la remplace par la valeur calculé
                            matriceFloydWarshall[i][j] = String.valueOf(val);
                        }
                    }
                    else if (i==j){
                        if (Integer.parseInt(matriceFloydWarshall[i][j]) > 0 && Integer.parseInt(matriceFloydWarshall[i][j]) != 0){
                            matriceFloydWarshall[i][j] = "0";
                        }
                        else if (Integer.parseInt(matriceFloydWarshall[i][j]) < 0){
                            System.out.println("Circuit absorbant détecté sur la diagonale donc pas de Floyd-Warshall");
                            break;
                        }
                    }
                }
            }
        }
    }

    public boolean possedeCircuitAbsorbant(String[][] matriceValeurs){
        for(int i = 0; i < matriceValeurs.length; i++) {
            for(int j = 0; j < matriceValeurs.length; j++) {
                if (i != j){
                    if (Integer.parseInt(matriceValeurs[i][j]) < 0){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public Integer rechercheMaxLengthColonne(String[][] m){
        int valmaxlen = 0;
        for (int i = 0 ; i < nbSommets ; i++) {
            for (int j = 0; j < nbSommets ; j++) {
                int val = m[i][j].length();
                if (valmaxlen < val) {
                    valmaxlen = val;
                }
            }
        }
        return valmaxlen;
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

    //Affiche la matrice des valeurs des chemins
    public void afficherMatriceValeurs() {
        int MaxValLen = rechercheMaxLengthColonne(this.matriceValeurs);
        System.out.println("Matrice des valeurs : ");
        String fullborder = "      ";
        for(int i = 0 ; i < matriceValeurs.length; i++){
            String space = "";
            for(int k = 0; k< MaxValLen-1 ; k++){
                space += " ";
            }
            fullborder += " " +i + space + "  ";
        }
        String Matrice = fullborder + "\n";
        for(int i = 0; i < matriceValeurs.length; i++) {
            for(int j = 0; j < matriceValeurs.length; j++) {

                int decalage = MaxValLen - matriceValeurs[i][j].length();
                String space = "";
                for(int k = 0; k<decalage ; k++){
                    space += " ";
                }
                String line = "";
                if(j == 0){
                    line += i + "   [ " + matriceValeurs[i][j] + space + " |";
                }
                else if(j == matriceValeurs.length-1){
                    line += " " + matriceValeurs[i][j] + space + " ]"; //On affiche ligne par ligne
                }
                else {
                    line += " " + matriceValeurs[i][j] + space + " |"; //On affiche ligne par ligne
                }
                Matrice += line;
            }
            Matrice += "\n"; //On va à la ligne lorsque l'on a terminé un sommet
        }
        System.out.println(Matrice);
    }

    public void afficherMatriceFloydWarshall() {
        int MaxValLen = rechercheMaxLengthColonne(this.matriceFloydWarshall);
        System.out.println("Matrice avec l'algorithme de Floyd-Warshall : ");
        String fullborder = "      ";
        for(int i = 0 ; i < matriceFloydWarshall.length; i++){
            String space = "";
            for(int k = 0; k< MaxValLen-1 ; k++){
                space += " ";
            }
            fullborder += " " +i + space + "  ";
        }
        String Matrice = fullborder + "\n";
        for(int i = 0; i < matriceFloydWarshall.length; i++) {
            for(int j = 0; j < matriceFloydWarshall.length; j++) {

                int decalage = MaxValLen - matriceFloydWarshall[i][j].length();
                String space = "";
                for(int k = 0; k<decalage ; k++){
                    space += " ";
                }
                String line = "";
                if(j == 0){
                    line += i + "   [ " + matriceFloydWarshall[i][j] + space + " |";
                }
                else if(j == matriceFloydWarshall.length-1){
                    line += " " + matriceFloydWarshall[i][j] + space + " ]"; //On affiche ligne par ligne
                }
                else {
                    line += " " + matriceFloydWarshall[i][j] + space + " |"; //On affiche ligne par ligne
                }
                Matrice += line;
            }
            Matrice += "\n"; //On va à la ligne lorsque l'on a terminé un sommet
        }
        System.out.println(Matrice);
    }

    //GETTER Matrice Floyd-Warshall
    public String [][] getMatriceFloydWarshall(){
        return matriceFloydWarshall;
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
