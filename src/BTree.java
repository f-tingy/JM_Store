import java.util.LinkedList;
import java.util.List;

public class BTree <T extends Comparable<T>> {
    private int line=3;//每个节点数据个数
    private Node root;
    private Node head;//叶子节点链表头

    /**
     * 树节点
     * @author Administrator
     *
     */
    private class Node<T>{
        //维护叶子节节点链表顺序使用
        private Node pre;
        //父节点
        private Node parnet;
        //树节点中元素节点集合
        private List<Element<T>> eles;
        //维护叶子节节点链表顺序使用
        private Node next;
        public Node(){
            eles=new LinkedList<>();
        }
        public Node(Element ele){
            this();
            eles.add(ele);
        }
        public Node(List<Element<T>> eles){
            this.eles=new LinkedList<>();
            this.eles.addAll(eles);
        }
        @Override
        public String toString() {
            if(eles==null){
                return null;
            }
            StringBuilder sb=new StringBuilder();
            for(Element e:eles){
                sb.append(e.data).append(",");
            }
            return "Node [eles=" + sb.toString() + "]";
        }

    }

    /**
     * 单个元素节点
     * @param <T>
     */
    private class Element<T>{
        private T data;
        //元素节点的左子树节点
        private Node left;
        //元素节点的右子树节点
        private Node right;
        public Element(T data){
            this.data=data;
        }
        @Override
        public String toString() {
            return "Element [data=" + data + ", left=" + left + ", right=" + right + "]";
        }

    }

    public BTree(int line){
        this.line=line;
    }

    public boolean add(T t){
        //若还root为null,说明该树还没有任何节点
        if(root==null){
            root=new Node(new Element<T>(t));
            head=root;
            return true;
        }
        BTree<T>.Element<T> ele = new Element<>(t);
        Node<T> currentNode=root;
        A:while(true){
            //获取当前访问树节点存储的数据列表
            List<Element<T>> eles = currentNode.eles;
            /**
             *查找要添加元素ele，在当前树节点currentNode数据元素中第一个大于ele的元素节点的
             *下标
             */
            int i=findSetPositInEles(ele, currentNode);

            Node nextNode=null;

            /**
             * i>0,代表i处元素节点a刚好大于要插入节点b，所以b要从a的左边找。由前面我对于
             * B+Tree的结构设计可知，除了第一个元素节点外，其他只有右子树节点。所以a的左边即
             * a前面一个节点的右子树节点
             */
            if(i>0){
                nextNode = eles.get(i-1).right;
            }else{//i<=0，说明要插入节点比该树节点所有元素节点都要小
                //节点最左边往下找
                nextNode = eles.get(0).left;
            }
            if(nextNode==null){
                eles.add(i, ele);
                //判断是否需要分裂
                if(eles.size()==line){
                    //分裂平衡方法
                    spiltNode(currentNode);
                }
                break A;
            }
            currentNode=nextNode;
        }
        return true;
    }

    /**
     * 寻找新ele应该插入节点elements集合中的位置<br>
     * ps:可以用二分法性能更好
     * @param ele
     * @param node
     * @return
     */
    public int findSetPositInEles(Element<T> ele,Node node){
        List<Element<T>> eles = node.eles;
        int i=0;
        for(;i<eles.size();i++){
            //当找到第一个大于等于ele的元素节点，返回对应下标
            if(ele.data.compareTo(eles.get(i).data)<=0){
                return i;
            }
        }
        return i;
    }

    /**
     * 分裂平衡
     */
    @SuppressWarnings("unchecked")
    private void spiltNode(BTree<T>.Node<T> node){
        int mid=line/2;
        A:while(true){
            //是否叶子节点
            boolean isLeafNode=node.next!=null||node.pre!=null||head==node;

            List<Element<T>> eles = node.eles;
            List<BTree<T>.Element<T>> leftEles = eles.subList(0, mid);
            //叶子节点一分为二，并生成一个和中间元素节点相同值的元素节点，向上推到父节点中；非叶子节点一分为三，正中间的元素节点向上推到父节点中
            List<BTree<T>.Element<T>> rightEles = eles.subList(isLeafNode?mid:mid+1,eles.size());

            //分裂出来的左节点
            BTree<T>.Node<T> leftNode=new Node<T>(leftEles);
            //分裂出来的右节点
            BTree<T>.Node<T> rightNode=new Node<T>(rightEles);
            //树节点的中间元素节点
            BTree<T>.Element<T> spiltEle = eles.get(mid);
            BTree<T>.Node<T> parnet = node.parnet;
            /**
             * 更新叶子节点链表<br>
             * <B>该链表是一个双向链表
             */
            //若原来分裂的节点是叶子节点需要更新
            if(isLeafNode){
                //更新叶子节点链表
                if(node.pre!=null){
                    node.pre.next=leftNode;
                }else{
                    head=leftNode;
                }
                if(node.next!=null){
                    node.next.pre=rightNode;
                }
                leftNode.pre=node.pre;
                leftNode.next=rightNode;
                rightNode.pre=leftNode;
                rightNode.next=node.next;

                //叶子节点分裂之后，要包含分裂出去的元素，即使值一样，但对象并不一样
//				rightEles.set(0, new Element<T>(spiltEle.data)); 隐患，由于rightNode中的eles在内存指向的集合和rightEles不是同一个了，这么并没改变rightNode
                rightNode.eles.set(0, new Element<T>(spiltEle.data));
            }
            /**
             * 分裂
             */

            //维护下一层的关系
            //非叶子节点，分裂出去的element的右节点由剩下右半部分的继承
            if(!isLeafNode){
                rightNode.eles.get(0).left=spiltEle.right;
                //分裂之后对于原node节点的子节点来说，它们的父节点对象也产生了变化
                rightNode.eles.get(0).left.parnet=rightNode;

                List<Element<T>> foreachList=leftNode.eles;
                boolean f=false;
                int i=0;
                B:while(true){
                    Element<T> temp=foreachList.get(i);
                    //第一个元素特殊可能左右都有
                    if(i==0){
                        temp.left.parnet=!f?leftNode:rightNode;
                        if(temp.right!=null){
                            temp.right.parnet=!f?leftNode:rightNode;
                        }
                    }else{
                        temp.right.parnet=!f?leftNode:rightNode;
                    }
                    i++;
                    if(!f&&i==foreachList.size()){
                        foreachList=rightNode.eles;
                        i=0;f=true;
                    }else if(f&&i==foreachList.size()){
                        break B;
                    }

                }
            }

            //维护上一层的关系
            if(parnet==null){
                //根节点的分裂
                spiltEle.left=leftNode;
                spiltEle.right=rightNode;
                Node<T> newNode=new Node<>(spiltEle);
                leftNode.parnet=newNode;
                rightNode.parnet=newNode;
                root=newNode;

                return ;
            }else{
                //将spiltEle插入父节点
                List<Element<T>> pEles = parnet.eles;
                int positInEles = findSetPositInEles(spiltEle, parnet);
                pEles.add(positInEles, spiltEle);
                if(positInEles==0){
                    pEles.get(0).left=leftNode;
                    //清空原0位元素的左节点
                    pEles.get(1).left=null;
                }else{
                    pEles.get(positInEles-1).right=leftNode;
                }
                pEles.get(positInEles).right=rightNode;
                leftNode.parnet=parnet;
                rightNode.parnet=parnet;

                //继续判断parent是否需要分裂
                if(pEles.size()>=line){
                    node=parnet;
                }else{
                    return ;
                }
            }
        }

    }

}

