

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
public class MerkleTrees {
    // transaction List
    List<JM_Node> txList;
    // Merkle Root
    JM_Node root;

    /**
     * constructor
     * @param txList transaction List 交易List
     */
    public MerkleTrees(List<JM_Node> txList) {
        this.txList = txList;
        root = new JM_Node("",-1);
    }

    /**
     * execute merkle_tree and set root.
     */
    public void merkle_tree() {

        List<JM_Node> tempTxList = new ArrayList<JM_Node>();

        for (int i = 0; i < this.txList.size(); i++) {
            tempTxList.add(this.txList.get(i));
        }

        List<JM_Node> newTxList = getNewTxList(tempTxList);

        while (newTxList.size() != 1) {
            newTxList = getNewTxList(newTxList);
        }

        this.root = newTxList.get(0);
    }

    /**
     * return Node Hash List.
     * @param tempTxList
     * @return
     */
    private List<JM_Node> getNewTxList(List<JM_Node> tempTxList) {

        List<JM_Node> newTxList = new ArrayList<JM_Node>();
        int index = 0;
        while (index < tempTxList.size()) {
            // left
            JM_Node left_node = tempTxList.get(index);
            JM_Node right_node = null;
            index++;
            // right
            String right = "";
            int level = left_node.getLevel();
            if (index != tempTxList.size()) {
                right_node = tempTxList.get(index);
                right = right_node.getHash();
                int level_right = tempTxList.get(index).getLevel();
                level = level<= level_right ? level: level_right;
            }
            // sha2 hex value
            String sha2HexValue = getSHA2HexValue(left_node.getHash() + right);
            newTxList.add(new JM_Node(sha2HexValue,level,left_node,right_node));
            index++;

        }

        return newTxList;
    }

    /**
     * Return hex string
     * @param str
     * @return
     */
    public String getSHA2HexValue(String str) {
        byte[] cipher_byte;
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(str.getBytes());
            cipher_byte = md.digest();
            StringBuilder sb = new StringBuilder(2 * cipher_byte.length);
            for(byte b: cipher_byte) {
                sb.append(String.format("%02x", b&0xff) );
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * Get Root
     * @return
     */
    public JM_Node getRoot() {
        return this.root;
    }



}
