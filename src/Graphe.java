import java.util.ArrayList;
import java.util.Objects;

public class Graphe {
    private int nbSommets;
    private int nbArcs;
    private ArrayList<ArrayList<Integer>> data; //ArrayList contenant des ArrayList contenant chacune [extremite initiale, terminale, valeur arc]
    private int[][] matriceAdjacence;
    private String[][] matriceValeurs;
    private String[][] matriceFloydWarshall;
    private String[][] matriceDesChemins;
    //Constructeur de la classe Graphe
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
    public void matriceDesChemins() {
        //On initialise la matrice des valeurs
        //et on met par défaut la valeur inf sur chaque valeur sauf sur la diagonale on met 0
        String[][] mValeurs = new String[nbSommets][nbSommets];
        for (int i = 0; i < nbSommets; i++) {
            for (int j = 0; j < nbSommets; j++) {
                if(this.matriceAdjacence[i][j] == 1){
                    mValeurs[i][j] = String.valueOf(i);
                }
                else{
                    mValeurs[i][j] = "?";
                }
                if (i == j) {
                    mValeurs[i][j] = String.valueOf(i);
                }
            }
        }
        this.matriceDesChemins = mValeurs;
    }

    //Retourne la matrice des valeurs des chemins
    public String[][] matriceValeurs() {
        //On initialise la matrice des valeurs
        //et on met par défaut la valeur inf sur chaque valeur sauf sur la diagonale on met 0
        String[][] mValeurs = new String[nbSommets][nbSommets];
        for (int i = 0; i< nbSommets ; i++){
            for (int j = 0; j< nbSommets; j++){
                mValeurs[i][j] = "inf";
                if (i == j) {
                    mValeurs[i][j] = "0";
                }
            }
        }

        int numSommet;
        int numSommetAdj; // Numéro sommet
        String valeur; // Valeur du chemin
        //Lecture du fichier afin de pouvoir mettre les valeurs dans la matrice
        for (ArrayList<Integer> lineData: data) {
            numSommet = lineData.get(0); //La première valeur de lineData est numSommet
            numSommetAdj = lineData.get(1); //2ème val : sommet adjacent
            valeur = String.valueOf(lineData.get(2)); //Valeur du chemin
            //Si il existe déjà une valeur sur l'arc sur lequel on veut écrasé une valeur, alors on compare ces deux derniers afin de garder la valeur la plus petite
            if (!Objects.equals(mValeurs[numSommet][numSommetAdj], "0") & !Objects.equals(mValeurs[numSommet][numSommetAdj], "inf")){
                if (Integer.parseInt(mValeurs[numSommet][numSommetAdj]) > Integer.parseInt(valeur)){ //Si la valeur de base est > à la valeur qu'on souhaite y écraser alors on l'écrase sinon on touche à rien
                    mValeurs[numSommet][numSommetAdj] = valeur;
                }
            }
            else { //Si il n'existe pas de valeur sur l'arc alors on y ajoute la valeur comme voulu
                mValeurs[numSommet][numSommetAdj] = valeur;
            }
        }
        matriceValeurs = mValeurs; //On met la matrice de valeur
        return mValeurs;
    }

    public void floyd_Warshall(){
        matriceFloydWarshall = this.matriceValeurs; //On initialise la matrice pour y éxécuté Floyd-Warshall.
        matriceDesChemins();
        for (int i = 0; i< nbSommets ; i++){
            for (int j = 0; j< nbSommets; j++){
                if(i==j){ //On regarde la valeur sur la diagonale
                    if (Integer.parseInt(matriceFloydWarshall[i][j]) > 0 && Integer.parseInt(matriceFloydWarshall[i][j]) != 0){ //Si la valeur sur la diagonale est positive et != de 0 alors on écrase la valeur par 0
                        matriceFloydWarshall[i][j] = "0";
                    }
                    else if (Integer.parseInt(matriceFloydWarshall[i][j]) < 0){ //Si la valeur sur la diagonale est négative alors on détecte un circuit absorbant on abonne Floyd-Warshall et ce n'est pas possible d'appliquer Floyd-Warshall
                        System.out.println("Circuit absorbant détecté sur la diagonale donc pas de Floyd-Warshall");
                        this.matriceFloydWarshall = this.matriceValeurs; //On reset la matrice Floyd-Warshall
                        return; //Permet l'arrêt net de la fonction
                    }
                }
            }
        }
        for (int k = 0 ; k < nbSommets ; k++) { //Nombre de répétitions à faire pour avoir la matrice avec tout les plus courts chemins possible de la matrice
            System.out.println("Floyd-Warshall étape : " + (k+1));
            for (int i = 0; i < nbSommets; i++) { //i = la colonne
                for (int j = 0; j < nbSommets; j++) { // j = la ligne
                    if (!Objects.equals(matriceFloydWarshall[i][k], "inf") & !Objects.equals(matriceFloydWarshall[k][j], "inf")) { //On vérifie sur la fermeture transitive si il n'y a pas de valeur infinie si il y en alors la valeur de base sur M[i,j] reste pareil
                        int val = Integer.parseInt(matriceFloydWarshall[i][k]) + Integer.parseInt(matriceFloydWarshall[k][j]); //Sinon on calcule le coût du chemin de i => k => j
                        if (Objects.equals(matriceFloydWarshall[i][j], "inf")){ //Si le chemin directe de i vers j = infini alors on lui accorde la valeur calculé directement
                            matriceFloydWarshall[i][j] = String.valueOf(val);
                            this.matriceDesChemins[i][j] = String.valueOf(k);
                        }
                        else if (val < Integer.parseInt(matriceFloydWarshall[i][j])) { //On compare la valeur calculé à la valeur initiale si la valeur calculé coûte moins cher que la valeur initiale alors on la remplace par la valeur calculé
                            matriceFloydWarshall[i][j] = String.valueOf(val);
                            this.matriceDesChemins[i][j] = String.valueOf(k);
                        }
                    }
                }
            }
            afficherMatriceFloydWarshall();
        }
    }

    public boolean possedeCircuitAbsorbant(String[][] matriceValeurs){
        for(int i = 0; i < matriceValeurs.length; i++) {
            for(int j = 0; j < matriceValeurs.length; j++) {
                if (i != j){
                    if (!Objects.equals(matriceValeurs[i][j], "inf")) {
                        if (Integer.parseInt(matriceValeurs[i][j]) < 0) {
                            System.out.println("Le graphe possède un circuit absorbant");
                            return true;
                        }
                    }
                }
            }
        }
        System.out.println("Le graphe ne possède pas de circuit absorbant");
        return false;
    }
    //Fonction permettant de chercher la valeur avec le plus long caractère de la matrice
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

    public void afficherMatriceDesChemins() {
        int MaxValLen = rechercheMaxLengthColonne(this.matriceDesChemins); //On cherche la valeur de la matrice où il y a le plus long caractère (len)
        System.out.println("Matrice des chemins : ");
        String fullborder = "     ";
        for(int i = 0 ; i < matriceDesChemins.length; i++){ //boucle permettant d'afficher les sommets sur la colonne de la matrice avec le respect des espacements
            String space = "";
            for(int k = 0; k< MaxValLen-1 ; k++){
                space += " ";
            }
            fullborder += " " +i + space + "  ";
        }
        String Matrice = fullborder + "\n";
        for(int i = 0; i < matriceDesChemins.length; i++) { //Boucle permettant le parcours de la matrice, i = ligne
            for(int j = 0; j < matriceDesChemins.length; j++) { // j = colonne

                int decalage = MaxValLen - matriceDesChemins[i][j].length(); //Calcule avec la valeur le nombre d'espace qu'il faudra pour s'aligner avec les autres valeurs de sa colonne
                String space = "";
                for(int k = 0; k<decalage ; k++){
                    space += " ";
                }
                String line = "";
                if(j == 0){ //Si on est à la première colonne de chaque ligne alors on y ajoute le numéro de sommet de la matrice
                    line += i + "   [ " + matriceDesChemins[i][j] + space + " |";
                }
                else if(j == matriceDesChemins.length-1){ //Si on atteint la dernière colonne de la matrice de chaque ligne alors on y ajoute une fermeture pour l'esthétique
                    line += " " + matriceDesChemins[i][j] + space + " ]"; //On affiche ligne par ligne
                }
                else { //affichage normal quand ce n'est pas la dernière ni la première valeur de la matrice
                    line += " " + matriceDesChemins[i][j] + space + " |"; //On affiche ligne par ligne
                }
                Matrice += line;
            }
            Matrice += "\n"; //On va à la ligne lorsque l'on a terminé un sommet
        }
        System.out.println(Matrice);
    }

    //Affiche la matrice des valeurs des chemins
    public void afficherMatriceValeurs() {
        int MaxValLen = rechercheMaxLengthColonne(this.matriceValeurs); //On cherche la valeur de la matrice où il y a le plus long caractère (len)
        System.out.println("Matrice des valeurs : ");
        String fullborder = "      ";
        for(int i = 0 ; i < matriceValeurs.length; i++){ //boucle permettant d'afficher les sommets sur la colonne de la matrice avec le respect des espacements
            String space = "";
            for(int k = 0; k< MaxValLen-1 ; k++){
                space += " ";
            }
            fullborder += " " +i + space + "  ";
        }
        String Matrice = fullborder + "\n";
        for(int i = 0; i < matriceValeurs.length; i++) { //Boucle permettant le parcours de la matrice, i = ligne
            for(int j = 0; j < matriceValeurs.length; j++) { // j = colonne

                int decalage = MaxValLen - matriceValeurs[i][j].length(); //Calcule avec la valeur le nombre d'espace qu'il faudra pour s'aligner avec les autres valeurs de sa colonne
                String space = "";
                for(int k = 0; k<decalage ; k++){
                    space += " ";
                }
                String line = "";
                if(j == 0){ //Si on est à la première colonne de chaque ligne alors on y ajoute le numéro de sommet de la matrice
                    line += i + "   [ " + matriceValeurs[i][j] + space + " |";
                }
                else if(j == matriceValeurs.length-1){ //Si on atteint la dernière colonne de la matrice de chaque ligne alors on y ajoute une fermeture pour l'esthétique
                    line += " " + matriceValeurs[i][j] + space + " ]"; //On affiche ligne par ligne
                }
                else { //affichage normal quand ce n'est pas la dernière ni la première valeur de la matrice
                    line += " " + matriceValeurs[i][j] + space + " |"; //On affiche ligne par ligne
                }
                Matrice += line;
            }
            Matrice += "\n"; //On va à la ligne lorsque l'on a terminé un sommet
        }
        System.out.println(Matrice);
    }

    public void afficherMatriceFloydWarshall() {
        int MaxValLen = rechercheMaxLengthColonne(this.matriceFloydWarshall); //On cherche la valeur de la matrice où il y a le plus long caractère (len)
        System.out.println("Matrice avec l'algorithme de Floyd-Warshall : ");
        String fullborder = "      ";
        for(int i = 0 ; i < matriceFloydWarshall.length; i++){ //boucle permettant d'afficher les sommets sur la colonne de la matrice avec le respect des espacements
            String space = "";
            for(int k = 0; k< MaxValLen-1 ; k++){
                space += " ";
            }
            fullborder += " " +i + space + "  ";
        }
        String Matrice = fullborder + "\n";
        for(int i = 0; i < matriceFloydWarshall.length; i++) { //Boucle permettant le parcours de la matrice, i = ligne
            for(int j = 0; j < matriceFloydWarshall.length; j++) { // j = colonne

                int decalage = MaxValLen - matriceFloydWarshall[i][j].length();//Calcule avec la valeur le nombre d'espace qu'il faudra pour s'aligner avec les autres valeurs de sa colonne
                String space = "";
                for(int k = 0; k<decalage ; k++){
                    space += " ";
                }
                String line = "";
                if(j == 0){ //Si on est à la première colonne de chaque ligne alors on y ajoute le numéro de sommet de la matrice
                    line += i + "   [ " + matriceFloydWarshall[i][j] + space + " |";
                }
                else if(j == matriceFloydWarshall.length-1){ //Si on atteint la dernière colonne de la matrice de chaque ligne alors on y ajoute une fermeture pour l'esthétique
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
