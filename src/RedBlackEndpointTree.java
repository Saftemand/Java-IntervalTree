public class RedBlackEndpointTree {

    protected RedBlackNode root;
    protected RedBlackNode sentinel;

    public RedBlackEndpointTree(){
        sentinel = new RedBlackNode(Integer.MIN_VALUE);
        root = sentinel;
    }

    public RedBlackNode SearchTree(int searchKey){
        RedBlackNode x = this.root;
        while (!isSentinel(x) && x.key != searchKey){
            if(searchKey < x.key){
                x=x.left;
            } else {
                x=x.right;
            }
        }
        return x;
    }

    public void InsertLeftEndpoint(int endpoint){
        RedBlackNode node = new RedBlackNode(endpoint);
        node.value = 1;
        insertRedBlackNode(node);
    }

    public void InsertRightEndpoint(int endpoint){
        RedBlackNode node = new RedBlackNode(endpoint);
        node.value = -1;
        insertRedBlackNode(node);
    }

    public void DeleteRedBlackNode(RedBlackNode z){
        if(isSentinel(z)){ return; }

        RedBlackNode x = null;
        RedBlackNode y = z;
        boolean wasRedOriginally = y.isRed;
        if(isSentinel(z.left)){
            x = z.right;
            transplant(z, z.right);
        } else if(isSentinel(z.right)){
            x = z.left;
            transplant(z, z.left);
        } else {
            y = minimum(z.right);
            wasRedOriginally = y.isRed;
            x = y.right;
            if(y.parent == z){
                x.parent = y;
            } else {
                transplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            transplant(z,y);
            y.left = z.left;
            y.left.parent = y;
            y.isRed = z.isRed;
        }
        if(!wasRedOriginally){
            deleteFixup(x);
        }
    }

    private void insertRedBlackNode(RedBlackNode z){
        RedBlackNode y = this.sentinel;
        RedBlackNode x = this.root;
        while(!isSentinel(x)){
            y = x;
            if(z.key < x.key){
                x = x.left;
            } else if (z.key == x.key && z.value > x.value){
                x = x.left;
            } else {
                x = x.right;
            }
        }
        z.parent = y;
        if(isSentinel(y)){
            this.root = z;
        } else if(z.key < y.key){
            y.left = z;
        } else if(z.key == y.key && z.value > y.value){
            y.left = z;
        } else {
            y.right = z;
        }
        z.left = this.sentinel;
        z.right = this.sentinel;
        z.isRed = true;
        InsertFixup(z);
        maintainValuesForNode(z);
    }

    private void InsertFixup(RedBlackNode z){
        RedBlackNode y = null;
        while(z.parent.isRed){
            if(z.parent == z.parent.parent.left){
                y = z.parent.parent.right;
                if(y.isRed){
                    z.parent.isRed = false;
                    y.isRed = false;
                    z.parent.parent.isRed = true;
                    z = z.parent.parent;
                } else {
                    if(z == z.parent.right){
                        z = z.parent;
                        leftRotate(z);
                    }
                    z.parent.isRed = false;
                    z.parent.parent.isRed = true;
                    rightRotate(z.parent.parent);
                }
            } else {
                y = z.parent.parent.left;
                if(y.isRed){
                    z.parent.isRed = false;
                    y.isRed = false;
                    z.parent.parent.isRed = true;
                    z = z.parent.parent;
                } else {
                    if(z == z.parent.left){
                        z = z.parent;
                        rightRotate(z);
                    }
                    z.parent.isRed = false;
                    z.parent.parent.isRed = true;
                    leftRotate(z.parent.parent);
                }
            }
        }
        this.root.isRed = false;
    }

    private void deleteFixup(RedBlackNode x){
        RedBlackNode w = null;
        while(!isSentinel(x) && !x.isRed){
            if(x == x.parent.left) {
                w = x.parent.right;
                if(w.isRed){
                    w.isRed = false;
                    x.parent.isRed = true;
                    leftRotate(x.parent);
                    w = x.parent.right;
                }
                if(!w.left.isRed && !w.right.isRed){
                    w.isRed = true;
                    x = x.parent;
                } else {
                    if(!w.right.isRed){
                        w.left.isRed = false;
                        w.isRed = true;
                        rightRotate(w);
                        w = x.parent.right;
                    }
                    w.isRed = x.parent.isRed;
                    x.parent.isRed = false;
                    w.right.isRed = false;
                    leftRotate(x.parent);
                    x = this.root;
                }
            } else {
                w = x.parent.left;
                if(w.isRed){
                    w.isRed = false;
                    x.parent.isRed = true;
                    rightRotate(x.parent);
                    w = x.parent.left;
                }
                if(!w.right.isRed && !w.left.isRed){
                    w.isRed = true;
                    x = x.parent;
                } else {
                    if(!w.left.isRed){
                        w.right.isRed = false;
                        w.isRed = true;
                        rightRotate(w);
                        w = x.parent.left;
                    }
                    w.isRed = x.parent.isRed;
                    x.parent.isRed = false;
                    w.left.isRed = false;
                    rightRotate(x.parent);
                    x = this.root;
                }
            }
        }
        x.isRed = false;
    }

    private void leftRotate(RedBlackNode x){
        RedBlackNode y = x.right;
        x.right = y.left;
        if(!isSentinel(y.left)){
            y.left.parent = x;
        }
        y.parent = x.parent;
        if(isSentinel(x.parent)){
            root = y;
        } else if(x == x.parent.left){
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
        maintainValuesForNode(x);
        maintainValuesForNode(y);
    }

    private void rightRotate(RedBlackNode x){
        RedBlackNode y = x.left;
        x.left = y.right;
        if(!isSentinel(y.right)){
            y.right.parent = x;
        }
        y.parent = x.parent;
        if(isSentinel(x.parent)){
            root = y;
        } else if(x == x.parent.right){
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
        maintainValuesForNode(x);
        maintainValuesForNode(y);
    }

    private void transplant(RedBlackNode u, RedBlackNode v){
        if(isSentinel(u.parent)){
            this.root = v;
        } else if(u == u.parent.left){
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        v.parent = u.parent;
    }

    private RedBlackNode minimum(RedBlackNode x){
        while(!isSentinel(x.left)){
            x = x.left;
        }
        return x;
    }

    private boolean isSentinel(RedBlackNode node){
        return node.key == Integer.MIN_VALUE;
    }

    private void maintainValuesForNode(RedBlackNode node){
        while(!isSentinel(node)){
            node.computeValues();
            node = node.parent;
        }
    }

    enum Direction{
        LEFT,MIDDLE,RIGHT
    }

    class RedBlackNode{

        protected RedBlackNode pom;
        protected int key;

        private RedBlackNode parent, left, right;
        private boolean isRed;
        private int value;
        private int sumOfSubtree;
        private int maxInSubtree;
        private Direction direction;

        public RedBlackNode(int key){
            this.key = key;
            this.parent = RedBlackEndpointTree.this.sentinel;
            this.left = RedBlackEndpointTree.this.sentinel;
            this.right = RedBlackEndpointTree.this.sentinel;
            this.pom = null;
            this.direction = Direction.MIDDLE;
            this.sumOfSubtree = 0;
            this.maxInSubtree = 0;
        }

        private void computeValues(){
            calculateSumOfSubtree();
            calculateMaxSumOfSubtree();
        }

        private void calculateSumOfSubtree(){
            if(!RedBlackEndpointTree.this.isSentinel(this)){
                sumOfSubtree = left.sumOfSubtree + value + right.sumOfSubtree;
            } else {
                sumOfSubtree = 0;
            }
        }

        private void calculateMaxSumOfSubtree(){
            if(!RedBlackEndpointTree.this.isSentinel(this)) {
                int leftMax = left.maxInSubtree;
                int middleMax = left.sumOfSubtree + value;
                int rightMax = middleMax + right.maxInSubtree;

                Direction newDirection = findPomDirection(leftMax, middleMax, rightMax);
                direction = newDirection;
                switch(newDirection){
                    case LEFT:
                        maxInSubtree = leftMax;
                        pom = left.pom != null ? left.pom : this;
                        break;
                    case MIDDLE:
                        maxInSubtree = middleMax;
                        pom = this;
                        break;
                    case RIGHT:
                        maxInSubtree = rightMax;
                        pom = right.pom != null ? right.pom : this;
                        break;
                }
            } else {
                maxInSubtree = 0;
            }
        }

        private Direction findPomDirection(int leftMax, int middleMax, int rightMax){
            if (leftMax > middleMax && leftMax > rightMax) {
                return Direction.LEFT;
            }
            else if (middleMax > leftMax && middleMax > rightMax) {
                return Direction.MIDDLE;
            }
            else if (rightMax > leftMax && rightMax > middleMax) {
                return Direction.RIGHT;
            }
            else if(leftMax == middleMax && middleMax == rightMax){
                if(this.value > left.value && this.value > right.value){
                    return Direction.MIDDLE;
                }
                if(left.value == -1 && this.value == -1 && right.value == -1){
                    return Direction.LEFT;
                }
                if(this.value > left.value && this.value == right.value){
                    return Direction.RIGHT;
                }
                if(this.value > right.value && this.value == left.value){
                    return Direction.MIDDLE;
                }
                else {
                    String test = "Happens ever?";
                }
            }
            else if(leftMax == middleMax && leftMax > rightMax){
                //NOT DONE
                if(this.value > left.value){
                    return Direction.MIDDLE;
                }
                if(left.value > this.value){
                    return Direction.LEFT;
                }
                if(this.value == right.value){
                    String test = "Happens ever?";
                }
            }
            else if(leftMax == rightMax && leftMax > middleMax){
                if(left.value > right.value){
                    return Direction.LEFT;
                }
                if(right.value > left.value){
                    return Direction.RIGHT;
                }
                if(left.value == 1 && right.value == 1){
                    //Dunno...
                    return Direction.RIGHT;
                }
                if(left.value == -1 && right.value == -1){
                    return Direction.LEFT;
                }
                String test = "Does this ever happen";
            }
            else if(middleMax == rightMax && middleMax > leftMax){
                if(this.value > right.value){
                    return Direction.MIDDLE;
                }
                if(right.value > this.value){
                    return Direction.RIGHT;
                }
                if(this.value == 1 && right.value == 1){
                    return Direction.RIGHT;
                }
                if(this.value == -1 && right.value ==-1){
                    return Direction.MIDDLE;
                }
            }
            return Direction.MIDDLE;
        }
    }

}

