package TigerIsland;

public class Match {
    // Members
    // TODO: Match probably doesnt need a postman anymore

    private Referee referee;
    private PostMan postMan;
    private TileBag tileBag;
    private GameState gameState;
    private Player player1;
    private Player player2;

    String gameID;

    public String getGameID() {
        return gameID;
    }

    Match(PostMan postMan, PlayerController player_01, PlayerController player_02, String gameID, OutputPlayerAI output) {
        this.postMan = postMan;
        this.gameID = gameID;
        this.tileBag = new NetworkTileBag(this.postMan, this.gameID);
        this.referee = new Referee(player_01, player_02, output, tileBag);
    }

    public void makeMove() {
        this.referee.ControllerTakesTurn();
    }

    public String sendScore(String pid, String opponentPid) {
        int score1 = this.player1.getScore();
        int score2 = this.player2.getScore();
        String message = "GAME " + gameID + " OVER PLAYER " + pid + " " + score1 + " PLAYER " + opponentPid + " " + score2;
        return message;
    }


}
