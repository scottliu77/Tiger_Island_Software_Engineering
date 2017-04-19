package TigerIsland;

/**
 * Created by scott on 4/18/17.
 */
public class ServerRequestAskingUsToSendScore {

    private String gid;
    public void setGid(String gid) {
        this.gid = gid;
    }
    public String getGid() {return gid;}

    public ServerRequestAskingUsToSendScore(String gid) {
        this.gid = gid;
    }
}
