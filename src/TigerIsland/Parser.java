package TigerIsland;

public class Parser {
    // The Server asks us to make a move and gives us a tile.
    //reads type 1 messages (MAKE YOUR MOVE IN GAME <gid> WITHIN <timemove> SECOND: MOVE <#> PLACE <tile>)
    public static ServerRequestAskingUsToMove commandToObject(String command){
        String[] commandArray = command.split("\\s+");
        final int gidIndex = 5;
        final int timeIndex = 7;
        final int moveNumberIndex = 10;
        String gid = commandArray[gidIndex];
        double time = Double.parseDouble(commandArray[timeIndex]);

        String moveNumber = commandArray[moveNumberIndex];
        Tile tile = tileStringToTile(commandArray[12]);
        return new ServerRequestAskingUsToMove(gid,time,moveNumber,tile);
    }

    public static ServerRequestAskingUsToSendScore commandToSendScoreObject(String command) {
        String[] commandArray = command.split("\\s+");
        final int gidIndex = 1;
        String gid = commandArray[gidIndex];
        return new ServerRequestAskingUsToSendScore(gid);
    }

    // The Server lets us know what our opponent did.
    //reads type 2 messages (GAME X MOVE Y PLAYER someID [...effect of whoever])
    public static MoveInGameIncoming opponentMoveStringToGameMove(String opponentMoveString){
        if (opponentMoveString.contains("FORFEIT") || opponentMoveString.contains("LOST")) {
            //TODO DAMMIT
            return null;
        }
        String[] opponentMoveStringSplitBySpaceArray = opponentMoveString.split("\\s+");

        final int gidIndex = 1;
        final int moveNumberIndex = 3;
        final int pidIndex = 5;

        String gid = opponentMoveStringSplitBySpaceArray[gidIndex];
       String moveNumber = opponentMoveStringSplitBySpaceArray[moveNumberIndex];
        String pid = opponentMoveStringSplitBySpaceArray[pidIndex];

        TileMove tileMove = opponentMoveStringToTileMove(opponentMoveString);
        ConstructionMoveTransmission constructionMoveTransmission = opponentMoveStringToBuildMove(opponentMoveString);
        //System.out.println("sending to postman...");
        return new MoveInGameIncoming(gid, moveNumber, pid, tileMove, constructionMoveTransmission);
    }

    //returns tile from type 1
    private static Tile makeYourMoveStringToTile(String makeYourMoveString) {
        String[] moveStringSplitBySpaceArray = makeYourMoveString.split("\\s+");
        final int tileIndex = 12;
        String tileString = moveStringSplitBySpaceArray[tileIndex];
        return tileStringToTile(tileString);
    }

    private static TileMove opponentMoveStringToTileMove(String opponentMoveString){
        String[] opponentMoveStringSplitBySpaceArray = opponentMoveString.split("\\s+");

        final int tileIndex = 7;
        final int xIndex = 9;
        final int yIndex = 10;
        final int zIndex = 11;
        final int orientationIndex = 12;

        String tileString = opponentMoveStringSplitBySpaceArray[tileIndex];
        Tile tile = tileStringToTile(tileString);

        Coordinate coordinate = extractCoordinateFromString(opponentMoveStringSplitBySpaceArray, xIndex, yIndex, zIndex);

        int orientation = Integer.parseInt(opponentMoveStringSplitBySpaceArray[orientationIndex]);

        //Assign UPPERLEFT direction as arbitrary only to be converted to its actual direction
        //This has to be done because you can not create a null instance of an enum
        HexagonNeighborDirection direction = HexagonNeighborDirection.UPPERLEFT;
        direction = direction.intToDirection(orientation);

        return new TileMove(tile, direction, coordinate);
    }

    private static ConstructionMoveTransmission opponentMoveStringToBuildMove(String opponentMoveString){
        String[] opponentMoveStringSplitBySpaceArray = opponentMoveString.split("\\s+");
        final int buildKeyword1Index = 13;
        final int buildKeyword2Index = 14;
        String buildKeyword1 = opponentMoveStringSplitBySpaceArray[buildKeyword1Index];
        String buildKeyword2 = opponentMoveStringSplitBySpaceArray[buildKeyword2Index];

        if(buildKeyword1.equals("FOUNDED")){
            return opponentFoundedMoveStringToBuildMove(opponentMoveStringSplitBySpaceArray);
        }
        else if(buildKeyword1.equals("EXPANDED")){
            return opponentExpandedMoveStringToBuildMove(opponentMoveStringSplitBySpaceArray);
        }
        else if(buildKeyword1.equals("BUILT") && buildKeyword2.equals("TOTORO")){
            return opponentBuildTotoroMoveStringToBuildMove(opponentMoveStringSplitBySpaceArray);
        }
        else if(buildKeyword1.equals("BUILT") && buildKeyword2.equals("TIGER")){
            return opponentBuildTigerMoveStringToBuildMove(opponentMoveStringSplitBySpaceArray);
        }
        //Should never reach this if statement but I figured returning null and getting an error
        //is better than returning the wrong ConstructionMoveTransmission and not realizing it
        else return null;
    }

    private static ConstructionMoveTransmission opponentFoundedMoveStringToBuildMove(String[] opponentFoundedMoveStringSplitBySpace){
        final int xIndex = 16;
        final int yIndex = 17;
        final int zIndex = 18;

        Coordinate coordinate = extractCoordinateFromString(opponentFoundedMoveStringSplitBySpace, xIndex, yIndex, zIndex);

        BuildOption buildOption = BuildOption.FOUNDSETTLEMENT;

        return new ConstructionMoveTransmission(buildOption, coordinate);
    }

    private static ConstructionMoveTransmission opponentExpandedMoveStringToBuildMove(String[] opponentExpandedMoveStringSplitBySpace){
        final int xIndex = 16;
        final int yIndex = 17;
        final int zIndex = 18;
        final int terrainIndex = 19;

        Coordinate coordinate = extractCoordinateFromString(opponentExpandedMoveStringSplitBySpace, xIndex, yIndex, zIndex);

        BuildOption buildOption = BuildOption.EXPANDSETTLEMENT;

        Terrain terrain = Terrain.valueOf(opponentExpandedMoveStringSplitBySpace[terrainIndex]);

        return new ExpandSettlementMoveTransmission(buildOption, coordinate, terrain);
    }

    private static ConstructionMoveTransmission opponentBuildTotoroMoveStringToBuildMove(String[] opponentBuildTotoroMoveStringSplitBySpace){
        final int xIndex = 17;
        final int yIndex = 18;
        final int zIndex = 19;

        Coordinate coordinate = extractCoordinateFromString(opponentBuildTotoroMoveStringSplitBySpace, xIndex, yIndex, zIndex);

        BuildOption buildOption = BuildOption.BUILDTOTORO;

        return new ConstructionMoveTransmission(buildOption, coordinate);
    }

    private static ConstructionMoveTransmission opponentBuildTigerMoveStringToBuildMove(String[] opponentBuildTigerMoveStringSplitBySpace){
        final int xIndex = 17;
        final int yIndex = 18;
        final int zIndex = 19;

        Coordinate coordinate = extractCoordinateFromString(opponentBuildTigerMoveStringSplitBySpace, xIndex, yIndex, zIndex);

        BuildOption buildOption = BuildOption.BUILDTIGER;

        return new ConstructionMoveTransmission(buildOption, coordinate);
    }

    //Converts string such as "JUNGLE+LAKE" to a tile object
    private static Tile tileStringToTile(String tileString){
        String [] tileStringSplitByPlusSign = tileString.split("\\+");

        final int AterrainIndex = 0;
        final int BterrainIndex = 1;

        Terrain Aterrain = Terrain.valueOf(tileStringSplitByPlusSign[AterrainIndex]);
        Terrain Bterrain = Terrain.valueOf(tileStringSplitByPlusSign[BterrainIndex]);
        return new Tile(Aterrain, Bterrain);
    }

    private static Coordinate extractCoordinateFromString(String[] stringArrayContainingCoordinate, int xIndex, int yIndex, int zIndex){
        int x = Integer.parseInt(stringArrayContainingCoordinate[xIndex]);
        int y = Integer.parseInt(stringArrayContainingCoordinate[yIndex]);
        int z = Integer.parseInt(stringArrayContainingCoordinate[zIndex]);
        return new Coordinate(x, y, z);
    }
}
