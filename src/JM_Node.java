/**
 * JM_Tree 中节点的数据结构
 */
public class JM_Node {
    //节点的hash值
    public String Hash;
    /**
     * 用于表示叶节点级别 0 1 2
     * 非叶节点的 level = -1
     */
    public int level;

    public JM_Node left;

    public JM_Node right;

    public  JM_Node(String hash,int level){
        this.Hash = hash;
        this.level = level;
    }
    public  JM_Node(String hash,int level,JM_Node left,JM_Node right){
        this.Hash = hash;
        this.level = level;
        this.left = left;
        if(right!=null)
            this.right = right;
    }
    public  JM_Node(String hash,int level,JM_Node left){
        this.Hash = hash;
        this.level = level;
        this.left = left;
    }

    public String getHash() {
        return Hash;
    }

    public void setHash(String hash) {
        Hash = hash;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
