public class IntervalTree {

    private RedBlackEndpointTree tree;

    public IntervalTree(){
        tree = new RedBlackEndpointTree();
    }

    public void IntervalInsert(int low, int high){
        tree.InsertLeftEndpoint(low);
        tree.InsertRightEndpoint(high);
    }

    public void IntervalDelete(int low, int high){
        tree.DeleteRedBlackNode(tree.SearchTree(low));
        tree.DeleteRedBlackNode(tree.SearchTree(high));
    }

    public int FindPom(){
        return Integer.MIN_VALUE;
    }

}

