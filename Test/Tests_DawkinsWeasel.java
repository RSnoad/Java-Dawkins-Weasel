import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.regex.Pattern;

import static java.util.Arrays.copyOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class Tests_DawkinsWeasel {

    String[] firstGeneration = DawkinsWeasel.firstGeneration(28);
    String progenitor = DawkinsWeasel.progenitor(28);
    String target = "METHINKS IT IS LIKE A WEASEL";

    @Test
    public void CanCreateAStringOfLength28() {
        assertEquals(28, progenitor.length());
    }

    @Test
    public void ProgenitorStringOnlyContainsUppercaseLettersAndSpaces() {
        Pattern p = Pattern.compile("[^ A-Z]");

        assertFalse(p.matcher(progenitor).find());
    }


    // May be possible to test with set seed as with python solution, but ran into issues as the
    // randomness is created by a local variable and could not find good way of setting seed without causing major
    // issues. Possibilities include taking seed as method argument(use method overloading so can do unseeded still?),
    // defining the random variable globally (maybe) or finding a way to set the seed in the test as with the python solution.
    @Test
    public void CanGenerateRandomString() {
        String string1 = DawkinsWeasel.progenitor(50);
        String string2 = DawkinsWeasel.progenitor(50);
        String string3 = DawkinsWeasel.progenitor(50);
        String string4 = DawkinsWeasel.progenitor(50);
        String string5 = DawkinsWeasel.progenitor(50);

        assertFalse(string1.equals(string2) && string2.equals(string3) && string3.equals(string4) && string4.equals(string5));
    }


    @Test
    public void FirstGenerationIsOfSize100() {
        assertEquals(100, firstGeneration.length);
    }

    @Test
    public void FirstGenerationIsAllTheSame() {
        for (String copy : firstGeneration) {
            assertEquals(copy, firstGeneration[0]);
        }
    }

    // TODO Find way to improve or change this test so does not give false failures. (Fails fairly frequently.)
    // Test is not ideal (due to real chance of false failures), but have not found way to properly set seed so will do for now.
    // (Increasing length has helped situation)
    @Test
    public void CanMutateAString() {
        String string1 = DawkinsWeasel.progenitor(100);
        String string2 = DawkinsWeasel.progenitor(100);
        String string3 = DawkinsWeasel.progenitor(100);
        String string4 = DawkinsWeasel.progenitor(100);
        String string5 = DawkinsWeasel.progenitor(100);

        String child1 = DawkinsWeasel.reproduce(string1);
        String child2 = DawkinsWeasel.reproduce(string1);
        String child3 = DawkinsWeasel.reproduce(string1);
        String child4 = DawkinsWeasel.reproduce(string1);
        String child5 = DawkinsWeasel.reproduce(string1);

        assertFalse(child1.equals(string1) || child2.equals(string2) || child3.equals(string3) || child4.equals(string4) || child5.equals(string5));
    }

    @Test
    public void ChildStringIsOfCorrectLength() {

        for (int i = 0; i < 100; i++) {
            String child = DawkinsWeasel.reproduce(progenitor);

            assertEquals(28, child.length());
        }
    }

    @Test
    public void CanCreateNextGeneration() {
        // Created copy of array so that when function changes the array can still check equality with the old one
        // (as Java is pass by value?)
        String[] copyOfFirstGeneration = copyOf(firstGeneration, firstGeneration.length);
        String[] newGeneration = DawkinsWeasel.reproduceGeneration(firstGeneration);
        assertThat(newGeneration, not(equalTo(copyOfFirstGeneration)));

    }

    @Test
    public void CanCompareChildToTargetCorrectlyWhenNoSimilarity() {
        String child = "ZZZZZZZZZZZZZZZZZZZZZZZZZZZZ";
        int score = DawkinsWeasel.compare(child, target);
        assertEquals(0, score);
    }

    @Test
    public void CanCompareChildToTargetCorrectlyWhenIdentical() {
        String child = "METHINKS IT IS LIKE A WEASEL";
        int score = DawkinsWeasel.compare(child, target);
        assertEquals(28, score);
    }

    @ParameterizedTest
    @CsvSource({("MOTONSKQ THISQ LEMEQQQQAFLJE, 7"), ("METHISKS THISQ LEMEQQQQAFJR, 11")})
    public void CanCompareChildToTargetCorrectlyWhenSomeSimilarity(String child, int expected) {
        int score = DawkinsWeasel.compare(child, target);
        assertEquals(expected, score);
    }

    // Could set seed to test creating a single new generation, but haven't yet figured out good way of setting random seed.

    @Test
    public void CanEvolveTowardsTarget() {
        assertEquals(target, DawkinsWeasel.evolution(target));
    }

}





