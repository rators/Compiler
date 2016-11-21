/**
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212hw07;

/**
 *
 * @author earlyjp
 */

    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import org.junit.*;

import java.lang.reflect.*;

import static org.junit.Assert.assertTrue;

/**
 *
 * @author jearly
 */
public class HW07_1Tests {

    public HW07_1Tests() {
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
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //

    // Declaration of ifFull()
    @Test
    public void declareAwardToString() {

        // Test #1 - test Award toString
        Class<Award> a = Award.class;
        Method[] methods = a.getDeclaredMethods();
        boolean correct = false;
        for (Method m : methods) {
//            System.out.println("\nDEBUG: Method Name - " + m.getName());
//            System.out.println("DEBUG: Return Type - " + m.getReturnType().getCanonicalName());
//            for (int i = 0; i < m.getParameterTypes().length; i++) {
//                System.out.println("DEBUG: Parameter - " + m.getParameterTypes()[i].getCanonicalName());
//            }

            if (m.getName().equals("toString")
                    && m.getParameterTypes().length == 0
                    && m.getReturnType().getCanonicalName().equals("java.lang.String")) {

                // Check indivdual parameters
                //if (m.getParameterTypes()[0].getCanonicalName().equals("java.lang.String")) {
                correct = true;
                //}
            }
        }
        assertTrue("Test 1: The toString() method in the Awards class is not declared correctly", correct);

    }


    @Test
    public void declareAwardsLoad() {
        // Test #2 - Awards load() test
        Class<Awards> a = Awards.class;
        Method[] methods = a.getDeclaredMethods();
        boolean correct = false;
        for (Method m : methods) {
//            System.out.println("\nDEBUG: Method Name - " + m.getName());
//            System.out.println("DEBUG: Return Type-" + m.getReturnType().getSimpleName());
//            for (int i = 0; i < m.getParameterTypes().length; i++) {
//                System.out.println("DEBUG: Parameter-" + m.getParameterTypes()[i]);
//            }

            if (m.getName().equals("load")
                    && m.getParameterTypes().length == 1
                    && m.getReturnType().getCanonicalName().equals("void")) {

                // Check indivdual parameters
                if (m.getParameterTypes()[0].getCanonicalName().equals("java.lang.String")) {
                    correct = true;
                }
            }
        }
        assertTrue("Test 2: The load() method in the Awards class is not declared correctly", correct);

    }


    @Test
    public void declareAwardsPrintAwards() {
        // Test #3 - Awards printAwards()
        Class<Awards> a = Awards.class;
        Method[] methods = a.getDeclaredMethods();
        boolean correct = false;
        for (Method m : methods) {
//            System.out.println("\nDEBUG: Method Name - " + m.getName());
//            System.out.println("DEBUG: Return Type-" + m.getReturnType().getSimpleName());
//            for (int i = 0; i < m.getParameterTypes().length; i++) {
//                System.out.println("DEBUG: Parameter-" + m.getParameterTypes()[i]);
//            }

            if (m.getName().equals("printAwards")
                    && m.getParameterTypes().length == 1
                    && m.getReturnType().getCanonicalName().equals("void")) {

                // Check indivdual parameters
                if (m.getParameterTypes()[0].getCanonicalName().equals("java.lang.String")) {
                    correct = true;
                }

            }
        }
        assertTrue("Test 3: The printAwards() method in the Awards class is not declared correctly", correct);
    }



}