
import java.util.Arrays;
import java.util.Random;

import static java.lang.Math.max;
import static java.lang.Math.random;

public class DawkinsWeasel {

    public static void main(String[] args) {



//      System.out.println(progenitor());
//        System.out.println(Arrays.toString(firstGeneration()));
//        String test = "AAAAAAAAAAAAAAAAAAAAAAA";
//        String mutated = reproduce(test);
//        System.out.println(mutated);
//        System.out.println(mutated.length() == test.length());
        System.out.println(evolution());

    }

    public static String progenitor() {
        Random rd = new Random();
        String progenitorstring = "";
        String validchars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ ";

        for (int i = 0; i < 28; i++){
            char randomcharacter;
             randomcharacter = validchars.charAt(rd.nextInt(validchars.length()));
            progenitorstring += randomcharacter;
        }

        return progenitorstring;
    }

    public static String[] firstGeneration() {
        String[] firstGeneration = new String[100];
        String progenitor = progenitor();
        Arrays.fill(firstGeneration, progenitor);

        return firstGeneration;
    }

    // Currently using two different random libraries, may not be ideal.
    public static String reproduce(String parent) {
        double chance;

        for (int i = 0; i < parent.length(); i++) {
            chance = random();
            if (chance < 0.05) {
                Random rd = new Random();
                String validchars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ ";
                char randomcharacter;
                randomcharacter = validchars.charAt(rd.nextInt(validchars.length()));

                parent = parent.substring(0, i) + randomcharacter + parent.substring(i+1);
            }

        }

        return parent;
    }


    public static String[] reproduceGeneration(String[] oldGeneration) {
        for(int i = 0; i < oldGeneration.length; i++) {
            oldGeneration[i] = reproduce(oldGeneration[i]);
        }
        return oldGeneration;
    }


    public static int compare(String child, String target) {
        int score = 0;
        for (int i = 0; i < child.length(); i++) {
            if(child.charAt(i) == target.charAt(i)) {
                score ++;
            }
        }
        return score;
    }


    //TODO Last steps of scoring match to target string and evolving towards it.

    public static String evolution() {
        String[] firstGeneration = firstGeneration();
        String fittestString = firstGeneration[0];
        int maxScore = 0;
        String target = "METHINKS IT IS LIKE A WEASEL";
        String currentString = firstGeneration[0];

        while (!fittestString.equals(target)) {
            String[] newGeneration = reproduceGeneration(firstGeneration);
                for (int i = 0; i < newGeneration.length; i++) {
                    int currentScore = compare(newGeneration[i], target);
                    if (currentScore > maxScore) {
                        maxScore = currentScore;
                        fittestString = newGeneration[i];
                        System.out.println(fittestString);
                    }
                }
            if (maxScore == 28) {
                return fittestString;
            }
            else {
                Arrays.fill(firstGeneration, fittestString);
            }
        }



    return"you should not get here";

    }


}
