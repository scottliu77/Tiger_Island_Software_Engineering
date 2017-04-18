package TigerIsland.UnitTests;

/**
 * Created by scott on 4/17/17.
 */

import TigerIsland.Terrain;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;


public class TerrainTest {

    @Test
    public void testThereExistsEightTerrainValues() {
        assertEquals(Terrain.values().length, 8);
    }
}
