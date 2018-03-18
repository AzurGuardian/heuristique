import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Proj {

    public static void main(String[] args) throws IOException {

        int nbCapteurs = 0;
        File file = new File("C:\\Users\\Admin\\Documents\\PROJETSIDEA\\ProjetHeuristique\\src\\test.txt");
        Sensors sensors[] = null;
        int nbAreas = 0;
        int nbLignes = 0;
        boolean ok = false;
        Sensors petit =null;



        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        int i = 0;
        while ((line = br.readLine()) != null) {
            if (i == 0) {

                sensors = new Sensors[Integer.parseInt(line)];
            } else if (i == 1) {
                nbAreas = Integer.parseInt(line);

            } else if (i == 2) {

                for (nbCapteurs = 0; nbCapteurs < line.split(" ").length; nbCapteurs++) {
                    sensors[nbCapteurs] = new Sensors(nbCapteurs, Integer.parseInt(line.split(" ")[nbCapteurs]));

                }
            } else{
                if (nbLignes <sensors.length){
                    sensors[nbLignes].setAreas(line.split(" "));
                    System.out.println(nbLignes+" capteur ");
                    nbLignes++;
                }
            }


            i++;
        }

        br.close();
        PrintWriter soluce = new PrintWriter(new FileWriter("C:\\Users\\Admin\\Documents\\PROJETSIDEA\\ProjetHeuristique\\src\\soluce.lp"));


        soluce.print("Maximize ");
        soluce.print("capt" + 1 + " ");
        for (int w = 1; w < sensors.length; w++) {
            soluce.print("+ capt" + (w+1) + " ");
        }
        soluce.println("\n");
        soluce.println("Subject to");


            double tempsmax = 0;
            ArrayList<Sensors> sensorsArrayList ;
            ArrayList<String> areasArrayList ;

                for (int s = 0; s < sensors.length; s++) {
                    if (petit == null || sensors[s].getDuree_vie()<petit.getDuree_vie()){
                        petit = sensors[s];
                    }
                    sensorsArrayList = new ArrayList<>();
                    areasArrayList = new ArrayList<>();
                    tempsmax = 0;
                    if (!sensorsArrayList.contains(sensors[s])) {
                        areasArrayList.addAll(Arrays.asList(sensors[s].getAreas()));
                        sensorsArrayList.add(sensors[s]);
                    }
                    ok = false;
                    for (int sens = s + 1; sens < sensors.length; sens++) {
                        if (sens < sensors.length ){
                            if (!sensorsArrayList.contains(sensors[s])) {
                                sensorsArrayList.add(sensors[s]);
                                ok = false;
                                for (int area = 0; area < sensors[s].getAreas().length; area++) {
                                    if (!areasArrayList.contains(sensors[s].getAreas()[area])){
                                        areasArrayList.add(sensors[s].getAreas()[area]);
                                    }
                                }
                                areasArrayList.addAll(Arrays.asList(sensors[s].getAreas()));
                            }
                            if (!ok) {
                                if (!areasArrayList.containsAll(Arrays.asList(sensors[sens].getAreas()))) {
                                    for (int area = 0; area < sensors[sens].getAreas().length; area++) {
                                        if (!areasArrayList.contains(sensors[sens].getAreas()[area])){
                                            areasArrayList.add(sensors[sens].getAreas()[area]);
                                            ok = true;
                                        }
                                    }
                                }
                                if (ok) {
                                    sensorsArrayList.add(sensors[sens]);
                                }
                                if (areasArrayList.size() == nbAreas) {
                                    soluce.print("      capt" + (sensorsArrayList.get(0).getNumero() + 1));
                                    if (sensorsArrayList.get(0).getDuree_vie() > tempsmax)tempsmax = sensorsArrayList.get(0).getDuree_vie();
                                    sensorsArrayList.remove(0);
                                    for (Sensors se : sensorsArrayList) {
                                        soluce.print(" + capt" + (se.getNumero() + 1) + " ");
                                        if (se.getDuree_vie() > tempsmax) tempsmax = se.getDuree_vie();
                                    }
                                    soluce.println(" =< " + tempsmax);
                                    areasArrayList = new ArrayList<>();
                                    sensorsArrayList = new ArrayList<>();
                                }else ok=false;

                            }

                        }
                    }
                }

        soluce.println("      capt"+petit.getNumero()+" =<"+petit.getDuree_vie());
        soluce.print("\nEND");
        soluce.close();
        for(int j = 0 ; j <4;j++){

            System.out.println("capteur "+j);
            for (int l = 0 ; l <sensors[j].getAreas().length; l++){
                System.out.print(sensors[j].getAreas()[l]);

            }
            System.out.println("");
        }

        try
        {
            Process p = Runtime.getRuntime().exec( "cmd.exe /c glpsol -cpxlp C:\\Users\\Admin\\Documents\\PROJETSIDEA\\ProjetHeuristique\\src\\soluce.lp -o C:\\Users\\Admin\\Documents\\PROJETSIDEA\\ProjetHeuristique\\src\\solution" );
            }
        catch ( IOException e )
        {
            // Erreur
        }

    }
}



