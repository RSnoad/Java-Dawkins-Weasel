import java.util.Arrays;
import java.util.Random;

import static java.lang.Math.random;

public class DawkinsWeasel {

    public static void main(String[] args) {

        System.out.println(evolution("METHINKS IT IS LIKE A WEASEL"));

    }

    public static String progenitor(int length) {
        Random rd = new Random();
        StringBuilder progenitorstring = new StringBuilder();
        String validchars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ ";

        for (int i = 0; i < length; i++) {
            char randomcharacter;
            randomcharacter = validchars.charAt(rd.nextInt(validchars.length()));
            progenitorstring.append(randomcharacter);
        }

        return progenitorstring.toString();
    }

    public static String[] firstGeneration(int length) {
        String[] firstGeneration = new String[100];
        String progenitor = progenitor(length);
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

                parent = parent.substring(0, i) + randomcharacter + parent.substring(i + 1);
            }

        }

        return parent;
    }


    public static String[] reproduceGeneration(String[] oldGeneration) {
        for (int i = 0; i < oldGeneration.length; i++) {
            oldGeneration[i] = reproduce(oldGeneration[i]);
        }
        return oldGeneration;
    }


    public static int compare(String child, String target) {
        int score = 0;
        for (int i = 0; i < child.length(); i++) {
            if (child.charAt(i) == target.charAt(i)) {
                score++;
            }
        }
        return score;
    }


    public static String evolution(String target) {
        String[] firstGeneration = firstGeneration(target.length());
        String fittestString = firstGeneration[0];
        int maxScore = 0;
        int count = 0;

        while (!fittestString.equals(target)) {
            String[] newGeneration = reproduceGeneration(firstGeneration);
            for (String s : newGeneration) {
                int currentScore = compare(s, target);
                if (currentScore > maxScore) {
                    maxScore = currentScore;
                    fittestString = s;
//                        System.out.println(fittestString);
                }
            }
            if (maxScore == target.length()) {
                System.out.println(count);
                return fittestString;
            } else {
                Arrays.fill(firstGeneration, fittestString);
                count++;
            }
        }

        return "you should not get here";

    }


}
