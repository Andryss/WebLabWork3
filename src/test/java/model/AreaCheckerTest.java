package model;

import org.junit.Test;

import static org.junit.Assert.*;

public class AreaCheckerTest {

    AreaChecker checker = AreaChecker.instance;

    @Test
    public void testCheck() {

        for (double r = 0.5; r < 10; r += 0.5) {
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