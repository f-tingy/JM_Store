import java.util.List;

public class Beacon {
    String ip;
    String port;
    public int CurrentShardNum;
    List<ShardInfo> shardInfos;

    public Beacon(String ip,String port){
        this.ip = ip;
        this.port = port;
    }

    public int getCurrentShardNum() {
        return CurrentShardNum;
    }

    public void setCurrentShardNum(int currentShardNum) {
        CurrentShardNum = currentShardNum;
    }

    public List<ShardInfo> getShardInfos() {
        return shardInfos;
    }

    public void setShardInfos(List<ShardInfo> shardInfos) {
        this.shardInfos = shardInfos;
    }

    public List<ShardInfo> Register(String ip,String port,String pk){
        CurrentShardNum++;
        shardInfos.add(new ShardInfo(ip,port,pk));
        return shardInfos;
    }
}
