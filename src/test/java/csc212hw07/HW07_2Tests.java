package csc212hw07;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.sun.xml.internal.ws.dump.LoggingDumpTube.Position.Before;
import static org.junit.Assert.*;

/**
 * @author jearly
 */
public class HW07_2Tests {
    answerkey.Players _players = answerkey.Players.load();

    public HW07_2Tests() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * The main test runner method. Runs the input into the submitted Main file and asserts the output against the expected output.
     * @param input
     * The input to test against.
     * @param expected
     * The expected output for the test.
     * @param testNumber
     * The number id for this test. Used for error checking, differentiates one test from the other.
     */
    private void runTest(String input, String[] expected, int testNumber) {

        //Store original System.in handle
        InputStream origIn = System.in;
        //Use input string instead
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Store original Syste.out handle
        OutputStream origOut = System.out;
        // Create/set a new output handle
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        // call main and verify the output
        Main.main(new String[]{});

        String[] actual = output.toString().split("\n");

        //String actual = output.toString();
        // Restore orinal I/O
        System.setIn(origIn);
        System.setOut(new PrintStream(origOut));

        // Check the expected number of prompts
        assertTrue("\nTest " + testNumber + ": Incorrect number of output lines - expected " + expected.length + " lines, but found " + actual.length + " lines\n", expected.length == actual.length);

        // Check each prompt for formatting
        //int numPrompts = 5;
        for (int i = 0; i < expected.length; i++) {
            // Debug
            //System.out.println(actual[i] + "--" + expected[i] + "--" + (actual[i].matches(expected[i])));

            assertTrue("\nTest " + testNumber + ": Prompt format mismatch at line " + (i + 1)
                    + "\n\tExpected: " + expected[i] + "\n\tActual: " + actual[i] + "\n", actual[i].equals(expected[i]));
        }
    }

    /**
     * Gets the expected output lines for some input.
     *
     * @param input The input to get expected output line for.
     * @return The expected output lines from the answer key.
     */
    private String[] getExpected(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Store original System.out handle
        OutputStream origOut = System.out;
        // Create/set a new output handle
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        // call main and verify the output
        answerkey.Main.main(new String[]{});
        String[] actual = output.toString().split("\n");

        //clean output stream.
        InputStream origIn = System.in;
        System.setIn(origIn);
        System.setOut(new PrintStream(origOut));
        return actual;
    }

    @Test
    public void testPrompts() throws Exception {
        // input string
        String input = "quit\n";
        String[] expected = getExpected(input);

        runTest(input, expected, 1);
    }


    @Test
    public void testRandomName() throws Exception {
        // input string
        answerkey.Players _players = answerkey.Players.load();
        String input = "name\n" + _players.randPlayer().firstName() + "\nquit\n";

        runTest(input, getExpected(input), 99);
    }

    @Test
    public void testIdExample() throws Exception {
        // Test #3
        // Create a plane, add a passsenger to an empty seats, then show seats
        answerkey.Players _players = answerkey.Players.load();
        // input string
        String input = "id\n" + _players.randPlayer().id() + "\nquit\n";
        String[] expected = getExpected(input);

        runTest(input, expected, 3);
    }

    @Test
    public void testAwardsExample() throws Exception {
        // Test #4
        // Remove a passenger
        // Arbitrary input string
        String input = "awards\n" + _players.randPlayer().id() + "\nquit\n";
        String[] expected = getExpected(input);

        runTest(input, expected, 4);
    }

    @Test
    public void testName2Example() throws Exception {
        // Test # 5
        // Test the second 'name' command
        // input string
        String input = "name\n"
                + _players.randPlayer().firstName() + "\n"
                + "quit\n";

        String[] expected = getExpected(input);

        runTest(input, expected, 5);
    }

    @Test
    public void testAwards2Example() throws Exception {

        String input = "awards\n"
                + _players.randPlayer().id() + "\n"
                + "quit\n";

        String[] expected = getExpected(input);

        runTest(input, expected, 6);

    }

    @Test
    public void testAwards3Example() throws Exception {
        String input = "awards\n"
                + _players.randPlayer().id() + "\n"
                + "quit\n";

        String[] expected = getExpected(input);

        // redirect standard in/out

        runTest(input, expected, 7);
    }

    @Test
    public void testUnknownCommand() throws Exception {
        String input = "test\n"
                + "quit\n";

        String[] expected = getExpected(input);

        runTest(input, expected, 8);
    }

    @Test
    public void testUnseen() throws Exception {
        // Test # 9
        // Test unseen player for name and awards commands
        // input string
        String input = "name\n"
                + "Speaker\n"
                + "awards\n"
                + "speaktr01\n"
                + "quit\n";

        String[] expected = getExpected(input);
        runTest(input, expected, 9);
    }
}