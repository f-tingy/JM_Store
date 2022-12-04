import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Shard {
    MerkleTrees merkleTree;
    String ip;
    String port;
    String pk;
    String sk;
    int shards_index;
    Beacon beacon;
    List<ShardInfo> shardInfos;

    public void SetUp(){
        /**
         * 0、向Beacon请求其他分片ip+port
         * 1、从其他分片获取状态
         * 2、判断节点级别
         * 3、构建merkel树
         */

        SynBeacon();



    }
    public void SynBeacon(){
        this.shardInfos = beacon.Register(ip,port,pk);
    }

    public List<JM_Node> SynNodes(){
        Queue<JM_Node> list = new PriorityQueue<>((a,b)->(a.getLevel()- b.getLevel()));
        /**
         * 请求节点的TransferNodes函数
         */
    }
    public List<JM_Node> TransferNodes(JM_Node root){

        if(root.left==null&&root.right==null){
            List<JM_Node> list = new ArrayList<>();
            list.add(root);
            return list;
        }
        List<JM_Node> left = TransferNodes(root.left);

        if(root.right.getLevel()==1)
            return left;
        List<JM_Node> right = TransferNodes(root.right);
        left.addAll(right);
        return left;
    }
}
