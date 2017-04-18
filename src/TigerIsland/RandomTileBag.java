package TigerIsland;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class RandomTileBag implements TileBag {

    private LinkedList<Tile> bag;
    private int numberOfTilesInBag;

    private Random random;

    public RandomTileBag() {
        bag = new LinkedList<Tile>();
        random = new Random();
        permutationForAllTiles();
    }

    @Override
    public Tile drawTile() {
        numberOfTilesInBag--;
        return this.bag.pop();
    }

    public LinkedList<Tile> getAllTilesInBag() {
        return this.bag;
    }
    @Override
    public int getNumberOfTilesInBag() {
        return numberOfTilesInBag;
    }

    private void permutationForAllTiles() {
        for(Terrain terrain_1 : new Terrain[]{Terrain.GRASS, Terrain.JUNGLE, Terrain.LAKE, Terrain.ROCK, Terrain.PADDY}){
            for(Terrain terrain_2 : new Terrain[]{Terrain.GRASS, Terrain.JUNGLE, Terrain.LAKE, Terrain.ROCK, Terrain.PADDY}){
                Tile newTile = new Tile(terrain_1, terrain_2);
                this.bag.push(newTile);
                this.bag.push(newTile);
                this.bag.push(newTile);

            }
        }

        while(bag.size() > 48) {
            int index = random.nextInt(bag.size());
            this.bag.remove(index);
        }

        Collections.shuffle(bag);

        numberOfTilesInBag = bag.size();
    }

}
