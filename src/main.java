import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) {
        List<JM_Node> tempTxList = new ArrayList<JM_Node>();
        tempTxList.add(new JM_Node("a",0));
        tempTxList.add(new JM_Node("b",0));
        tempTxList.add(new JM_Node("c",0));
        tempTxList.add(new JM_Node("d",1));
        tempTxList.add(new JM_Node("e",1));
        tempTxList.add(new JM_Node("e",1));
        tempTxList.add(new JM_Node("e",1));
        tempTxList.add(new JM_Node("e",1));
        tempTxList.add(new JM_Node("a",1));
        tempTxList.add(new JM_Node("b",1));
        tempTxList.add(new JM_Node("c",1));
        tempTxList.add(new JM_Node("d",1));
        tempTxList.add(new JM_Node("e",1));
        tempTxList.add(new JM_Node("e",1));
        tempTxList.add(new JM_Node("e",1));
        tempTxList.add(new JM_Node("e",1));
        MerkleTrees merkleTrees = new MerkleTrees(tempTxList);
        merkleTrees.merkle_tree();
//        List<JM_Node> list = merkleTrees.TransferNodes(merkleTrees.getRoot());
//        System.out.println(list.size());
//        for (JM_Node node :
//                list) {
//            System.out.println(node.getHash());
//        }

        Beacon beacon = new Beacon("172.0.0.1","8001");
        Shard shard1 = new Shard()
    }
}
