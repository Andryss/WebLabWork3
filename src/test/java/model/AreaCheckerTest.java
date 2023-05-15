package model;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import ru.andryss.weblab3.model.AreaChecker;
import ru.andryss.weblab3.model.Request;

import static org.junit.Assert.*;

@Ignore("Not prepared for interact with FacesContext")
public class AreaCheckerTest {

    static AreaChecker checker;

    @BeforeClass
    public static void setUp() {
//        checker = AreaChecker.instance;
    }

    @Test
    public void whenRadiusIncrease_thenFigureScale() {

        for (double r = 0.5; r < 10; r += 0.5) {
            System.out.println("Test radius " + r);

            // Out of the area
            assertFalse(checker.check(new Request(0.1 * r, 0.1 * r, r)));
            assertFalse(checker.check(new Request(-0.5 * r, -0.5 * r, r)));
            assertFalse(checker.check(new Request(-0.3 * r, 0.3 * r, r)));

            // In the area
            assertTrue(checker.check(new Request(-0.3 * r, -0.3 * r, r)));
            assertTrue(checker.check(new Request(-0.25 * r, 0.25 * r, r)));
            assertTrue(checker.check(new Request(0 * r, 0 * r, r)));

            // Borders
            assertTrue(checker.check(new Request(-0.5 * r, 0 * r, r)));
            assertTrue(checker.check(new Request(0 * r, 0.5 * r, r)));
            assertTrue(checker.check(new Request(0 * r, 0 * r, r)));
            assertTrue(checker.check(new Request(0.5 * r, 0 * r, r)));
            assertTrue(checker.check(new Request(0.5 * r, -1 * r, r)));
            assertTrue(checker.check(new Request(0 * r, -1 * r, r)));
            assertTrue(checker.check(new Request(0 * r, -0.5 * r, r)));
        }
    }

}