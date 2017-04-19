package TigerIsland;

/**
 * Created by scott on 4/17/17.
 */
public class FoundShangrilaConstructionMove extends ConstructionMoveJustCoordinate {
    public FoundShangrilaConstructionMove(Coordinate coordinate) {
        super(coordinate);
    }

    @Override
    protected String getMoveTypeName() {
        return "FOUND SHANGRILA AT";
    }

    public boolean canBeKilled() { return true; }


    @Override
    public void makePreverifiedMove(Player player, Board board) {
        player.subtractShaman();

        Hexagon hexagon = board.getHexagonAt(coordinate);
        hexagon.setOccupationStatus(player.getColor(), PieceStatusHexagon.SHAMAN);
    }

    @Override
    public boolean canPerformMove(Player player, Board board) {
        Hexagon hexagon = board.getHexagonAt(coordinate);
        if(!hexagon.isVolcano() && hexagon.getLevel() == 1 && (!hexagon.containsPieces())) {
            return player.getShamanCount() != 0;
        }
        return false;
    }

    @Override
    public Coordinate getCoordinate() {
        return this.coordinate;
    }
}
