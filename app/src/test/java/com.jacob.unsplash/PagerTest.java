package com.jacob.unsplash;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class PagerTest {
    @Test
    public void perimeterTest() {
        int width = 2;
        int height = 4;
        int perimeter = (width + height) * 2;
        assertEquals(perimeter, Utils.perimeter(width, height));
    }
}
