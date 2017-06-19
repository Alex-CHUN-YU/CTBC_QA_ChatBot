/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tw.idv.ken.data;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ken
 */
public class DialogueAnalysisTest {
    
    public DialogueAnalysisTest() {
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

    @Test
    public void Construct_ValidArgs_ShouldSuccess() {
        DialogueAnalysis instance = new DialogueAnalysis("", "question", "act", "target", "v1",
                "o1");
        assertNotNull(instance);
        assertEquals(4, instance.getNumbersOfFoundEntities());
        assertEquals("question", instance.getQuestionWord());
        assertEquals("act", instance.getAct());
        assertEquals("target", instance.getTarget());
        assertEquals(2, instance.getFeatures().size());
    }

    @Test
    public void testNumbersOfFoundEntities() {
        DialogueAnalysis instance = new DialogueAnalysis("", "question", "act", "target", "v1",
                "o1");
        assertEquals(4, instance.getNumbersOfFoundEntities());
        instance = new DialogueAnalysis("", "question", "act", "target");
        assertEquals(3, instance.getNumbersOfFoundEntities());
        instance = new DialogueAnalysis("", "question", "act", "");
        assertEquals(2, instance.getNumbersOfFoundEntities());
        instance = new DialogueAnalysis("", "", "", "target");
        assertEquals(1, instance.getNumbersOfFoundEntities());
        instance = new DialogueAnalysis("", "", "", "","v1");
        assertEquals(1, instance.getNumbersOfFoundEntities());
        instance = new DialogueAnalysis("", "", "", "","v1","o1","v2","o2");
        assertEquals(1, instance.getNumbersOfFoundEntities());
        instance = new DialogueAnalysis("", "", "", "");
        assertEquals(0, instance.getNumbersOfFoundEntities());
        instance = new DialogueAnalysis("", "如何", "變更", "基本資料", "");
        assertEquals(3, instance.getNumbersOfFoundEntities());
        instance = new DialogueAnalysis("", "如何", "", "", "", "", "", "", "");
        assertEquals(1, instance.getNumbersOfFoundEntities());
    }

}
