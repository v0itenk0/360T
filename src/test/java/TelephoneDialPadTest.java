import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.lang.Object;
import static org.junit.Assert.*;
import static sun.security.krb5.Confounder.longValue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TelephoneDialPadTest {
    // PRIORITY_1  - HIGH priority
    // PRIORITY_2  - MEDIUM priority
    // PRIORITY_3  - LOW priority


    //This test passes an empty string as an input parameter to the function retrieveCombinations .
    //The function is expected to return an empty collection of results
    @Test
    public void PRIORITY_1_testEmptyCollection() {
        TelephoneDialPad telephoneDialPad = new TelephoneDialPad();
        LinkedList<String> actualResult = telephoneDialPad.retrieveCombinations("");
        assertTrue("Error! Collection is not empty", actualResult.isEmpty());
    }

    //This test compares the combinations obtained from the function with the predefined results.
    //I deliberately decided to make a small set so that I could check it manually.
    @Test
    public void PRIORITY_1_successShortSequence() {
        final String inputSequence = "910";
        List<String> expectedResult = Arrays.asList("W10", "X10", "Y10", "Z10");
        TelephoneDialPad telephoneDialPad = new TelephoneDialPad();
        LinkedList<String> actualResult = telephoneDialPad.retrieveCombinations(inputSequence);
        Assert.assertEquals("Lists are not the same", expectedResult, actualResult);
    }


    // The test checks the length of the input numerical combination with the length of each output in letters
    //combination.
    @Test
    public void PRIORITY_1_checkLengthInputAndOutputCombinations() {
        String input = "789345";
        TelephoneDialPad telephoneDialPad = new TelephoneDialPad();
        LinkedList<String> actualResult = telephoneDialPad.retrieveCombinations(input);
        for (int i = 0; i < actualResult.size(); i++) {
            assertEquals("The length of collections are not the same", input.length(), actualResult.get(i).length());
        }
    }

    //This test compares the number of output combinations received from the function with the manually calculated
    //number of combinations.
    @Test
    public void PRIORITY_1_collectionLength_shortCollection() {
        TelephoneDialPad telephoneDialPad = new TelephoneDialPad();
        LinkedList<String> actualResult = telephoneDialPad.retrieveCombinations("12345");
        assertEquals("The number of combinations is not the same", 81, actualResult.size());
    }

    //This test checks the number of combinations when we input a 20 number sequence.
    //The function we are testing is recursive - hence it must handle the java.lang.OutOfMemoryError exception.
    @Test
    public void PRIORITY_1_collectionLength_bigCollection() {
        String inputSequence = "";
        for (int i = 0; i < 20; i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(2, 7);
            inputSequence += randomNum;
        }

        System.out.println("Number of characters: " + inputSequence.length()); //20
        System.out.println("Sequence: " + inputSequence);
        // For this collection with 20 number
        // we expect to get 3^20 = 3486784401 letter combinations
        TelephoneDialPad telephoneDialPad = new TelephoneDialPad();
        LinkedList<String> actualResult = telephoneDialPad.retrieveCombinations(inputSequence);
        assertEquals("The number of combinations is not the same", 3486784401L, actualResult.size());
    }

    //This test checks the number of output combinations when as an input we have a collection with 13 numbers.
    @Test
    public void PRIORITY_1_collectionLength_mediumCollection() {
        TelephoneDialPad telephoneDialPad = new TelephoneDialPad();
        LinkedList<String> actualResult = telephoneDialPad.retrieveCombinations("4915122713608");
        assertEquals("The number of combinations is not the same", 34992, actualResult.size());
    }

    // Checking for an invalid input sequence
    @Test(expected=NumberFormatException.class)
    public void PRIORITY_2_illegalInputCharacter_space() {
        final String inputSequence = "98782 321";
        TelephoneDialPad telephoneDialPad = new TelephoneDialPad();
        telephoneDialPad.retrieveCombinations(inputSequence);
    }

    // Checking for an invalid input sequence
    @Test(expected=NumberFormatException.class)
    public void PRIORITY_2_illegalInputCharacter_specialCharacter() {
        final String inputSequence = " _9910";
        TelephoneDialPad telephoneDialPad = new TelephoneDialPad();
        telephoneDialPad.retrieveCombinations(inputSequence);
    }

    // Checking for an invalid input sequence
    @Test(expected=NumberFormatException.class)
    public void PRIORITY_2_illegalInputCharacter_letter() {
        final String inputSequence = "19A10";
        TelephoneDialPad telephoneDialPad = new TelephoneDialPad();
        telephoneDialPad.retrieveCombinations(inputSequence);
    }

    //Test checks if the given combination appeared in the output of the function.
    @Test
    public void PRIORITY_2_success_mediumSequence() {
        final String inputSequence = "4918122713608";
        final String expectedCombination = "GW1T1AAP1DN0T";
        TelephoneDialPad telephoneDialPad = new TelephoneDialPad();
        assertTrue("Output does not contain" + expectedCombination, telephoneDialPad.retrieveCombinations(inputSequence).contains(expectedCombination));
    }


    //This test checks whether the output sequences of letters correspond to the given
    // original input numerical combination.
    @Test
    public void PRIORITY_3_getNumbersHavingLetters() {
        final String inputSequence = "6790321";
        TelephoneDialPad telephoneDialPad = new TelephoneDialPad();
        LinkedList<String> actualResult = telephoneDialPad.retrieveCombinations(inputSequence);
        for (int i=0; i < actualResult.size(); i++) {
            assertEquals("The original number sequences not correct", inputSequence, getNumberFromLetter(actualResult.get(i)));
        }
    }


    // This test checks whether an exception outOfMemory appears
    // if as an input we have a very long sequence of numbers.
    @Test(expected = Error.class)
    public void PRIORITY_3_testBigCollection_outOfMemoryError() {
        TelephoneDialPad telephoneDialPad = new TelephoneDialPad();
        LinkedList<String> actualResult = new LinkedList<String>();
        try {
            // The code we are testing lead to the error when trying to enter long sequences
            actualResult = telephoneDialPad.retrieveCombinations("1234542342342342342342342342");
        } catch (Throwable t) {
            // free enough heap memory so JUnit can handle exception
            actualResult = null;
            throw t;
        }
    }

    // This test is rather auxiliary. We take the input sequences
    // from the CSV file and check the number of output combinations.
    @Test
    public void PRIORITY_3_testCollectionFromCSV() {
        TelephoneDialPad telephoneDialPad = new TelephoneDialPad();
        LinkedList<String> actualResult = new LinkedList<String>();
        List<Integer> sizeValues = Arrays.asList(432, 324, 324, 1, 1, 1, 1);
        List<String[]> data = com.company.CSVReader.readerCSV("C:/TestOleksiiVoitenko/CSVDATA/data.csv");
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(i).length; j++) {
                try {
                    actualResult = telephoneDialPad.retrieveCombinations(data.get(i)[j]);
                    assertEquals("Error! The number of combinations is not the same", sizeValues.get(j).longValue(), actualResult.size());
                }
                catch(Exception e) {
                    System.out.println(e.toString());
                }
            }
        }
    }

    public String getNumberFromLetter(String input) {
        String NumberSequence = "";
        for (int k=0; k < input.length(); k++) {
            for (int i = 0; i < TelephoneDialPad.dialPadMap.length; i++) {
                for (int j = 0; j < TelephoneDialPad.dialPadMap[i].length; j++) {
                    if (Objects.equals(TelephoneDialPad.dialPadMap[i][j], input.substring(k, k+1))) {
                        NumberSequence +=i;
                    }
                }
            }
        }
        return NumberSequence;
    }

    public void printAllValues(LinkedList<String> result) {
        for (int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i));
        }
        System.out.println("Size: "+result.size());
    }
}

