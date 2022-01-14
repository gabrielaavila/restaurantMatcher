package com.gabrielaavila.restaurantMatcher.chainOfHandlers;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static com.gabrielaavila.restaurantMatcher.mothers.ParamsMother.getMapWith1Param;
import static com.gabrielaavila.restaurantMatcher.mothers.ParamsMother.getMapWithAllParameters;
import static com.gabrielaavila.restaurantMatcher.mothers.ParamsMother.getMapWithNoSequentialParams;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChainCreatorTest {

    private ChainCreatorService creator = new ChainCreatorService();


    @Test
    public void testCreateChainWithAllParameters() {
        Handler chain = creator.createChainOfMatchers(getMapWithAllParameters(), null);

        assertTrue(chain instanceof RestaurantNameMatcher);
        chain = chain.getNext();
        assertTrue(chain instanceof CuisineMatcher);
        chain = chain.getNext();
        assertTrue(chain instanceof DistanceMatcher);
        chain = chain.getNext();
        assertTrue(chain instanceof RatingMatcher);
        chain = chain.getNext();
        assertTrue(chain instanceof PriceMatcher);
        chain = chain.getNext();
        assertNull(chain);
    }

    @Test
    public void testCreateChainWithNoParameters() {
        Handler chain = creator.createChainOfMatchers(new HashMap<>(), null);
        assertNull(chain);
    }

    @Test
    public void testCreateChainWithOneParameter() {
        Handler chain = creator.createChainOfMatchers(getMapWith1Param(), null);
        assertTrue(chain instanceof RestaurantNameMatcher);
        assertNull(chain.getNext());
    }

    @Test
    public void testCreateChainWithNonSequentialParameters() {
        Handler chain = creator.createChainOfMatchers(getMapWithNoSequentialParams(), null);
        assertTrue(chain instanceof CuisineMatcher);
        chain = chain.getNext();
        assertTrue(chain instanceof PriceMatcher);
        assertNull(chain.getNext());
    }

}