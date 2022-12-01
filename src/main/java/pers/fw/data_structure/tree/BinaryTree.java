package pers.fw.data_structure.tree;

import pers.fw.data_structure.Queue;
import pers.fw.data_structure.Stack;
import pers.fw.data_structure.interf.Visitor;

/**
 * 二叉树
 * DFS（深度优先搜索）、BFS（广度优先搜索）
 * todo 有种时间复杂度为O(n)，空间复杂度为O(1)的遍历算法：Morris算法，有时间研究一下
 *
 * @param <E>
 */
public abstract class BinaryTree<E> {
    protected TreeNode<E> root;

    protected static class TreeNode<E> {
        E e;
        TreeNode<E> parent;
        TreeNode<E> left;
        TreeNode<E> right;

        public TreeNode(E e) {
            this.e = e;
        }

        public TreeNode(E e, TreeNode<E> parent) {
            this.e = e;
            this.parent = parent;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public boolean hasTwoChildren() {
            return left != null && right != null;
        }

        /**
         * 节点的度
         *
         * @return
         */
        public int degree() {
            if (left != null && right != null) return 2;
            else if (left == null && right == null) return 0;
            else return 1;
        }
    }

    /**
     * 前序遍历：根->左->右
     */
    public void preOrderTraversal(Visitor<E> visitor) {
        if (root == null) return;
        Stack<TreeNode<E>> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode<E> node = stack.pop();
            visitor.visit(node.e);
            if (node.right != null) stack.push(node.right);
            if (node.left != null) stack.push(node.left);
        }
    }

    /**
     * 中序遍历：左->根->右
     * 实现方式：左链入栈
     */
    public void inOrderTraversal(Visitor<E> visitor) {
        if (root == null) return;
        Stack<TreeNode<E>> stack = new Stack<>();
        TreeNode<E> node = root;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            visitor.visit(node.e);
            node = node.right;
        }
    }

    /**
     * 后序遍历：左->右->根
     * 实现方式：左链入栈，并记录后序遍历上一个节点，若当前节点为叶子节点或当前节点的右子节点为上一个节点，则说明该节点的左右子树都访问了，弹出该节点；若不满足则说明右子树还未遍历
     */
    public void postOrderTraversal(Visitor<E> visitor) {
        if (root == null) return;
        Stack<TreeNode<E>> stack = new Stack<>();
        TreeNode<E> node = root;
        TreeNode<E> prev = null;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            if (node.right == null || node.right == prev) {
                visitor.visit(node.e);
                prev = node;
                node = null;
            } else {
                stack.push(node);
                node = node.right;
            }
        }
    }

    /**
     * 层序遍历
     * 实现方式：利用队列
     */
    public void levelOrderTraversal(Visitor<E> visitor) {
        if (root == null) return;
        Queue<TreeNode> queue = new Queue<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode<E> node = queue.poll();
            visitor.visit(node.e);
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
    }

    /**
     * 计算二叉树高度
     * 实现方式：利用层序遍历
     *
     * @return
     */
    public int height() {
        if (root == null) return 0;
        Queue<TreeNode> queue = new Queue<>();
        queue.offer(root);
        int height = 0;
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            height++;
        }
        return height;
    }

    /**
     * 计算节点数量（todo 子类应该重写这个方法）
     * 实现方式：使用任意遍历计数
     *
     * @return
     */
    public int size() {
        if (root == null) return 0;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        int size = 0;
        while (!stack.isEmpty()) {
            TreeNode TreeNode = stack.pop();
            size++;
            if (TreeNode.right != null) stack.push(TreeNode.right);
            if (TreeNode.left != null) stack.push(TreeNode.left);
        }
        return size;
    }

    /**
     * 是否是真二叉树（所有节点的度都要么为 0，要么为 2）
     * 实现方式：遍历树，判断所有节点的度
     *
     * @return
     */
    public boolean isProper() {
        if (root == null) return true;
        Queue<TreeNode> queue = new Queue<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            int degree = node.degree();
            if (degree == 1) return false;
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
        return true;
    }

    /**
     * 是否是满二叉树（最后一层节点的度都为0，其他节点的度都为2）
     * 实现方式：通过层序遍历，简单粗暴使用每层元素数是否等于2^n判断，缺点在于可能多判断一些元素;
     *
     * @return
     */
    public boolean isFull() {
        if (root == null) return true;
        Queue<TreeNode> queue = new Queue<>();
        queue.offer(root);
        int theoreticalValue = 1;
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            if (theoreticalValue != levelSize) return false;
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            theoreticalValue <<= 1;
        }
        return true;
    }

    /**
     * 是否是完全二叉树（叶子节点只会出现最后 2 层，且最后 1 层的叶子结点都靠左对齐）
     * 实现方式：层序遍历
     *
     * @return
     */
    public boolean isComplete() {
        if (root == null) return true;
        Queue<TreeNode> queue = new Queue<>();
        queue.offer(root);
        boolean leaf = false;
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (leaf && !node.isLeaf()) return false;
            if (node.left != null) {
                queue.offer(node.left);
            } else if (node.right != null) {
                return false;
            }
            if (node.right != null) {
                queue.offer(node.right);
            } else {
                leaf = true;
            }
        }
        return true;
    }

    /**
     * 获取中序遍历的下一个节点
     *
     * @param node
     * @return
     */
    protected TreeNode<E> inOrderNextNode(TreeNode<E> node) {
        if (node == null) return null;
        if (node.right != null) {
            node = node.right;
            while (node.left != null) {
                node = node.left;
            }
            return node;
        }
        while (node.parent != null && node.parent.right == node) {
            node = node.parent;
        }
        return node.parent;
    }

    /**
     * 获取中序遍历的前一个节点
     *
     * @param node
     * @return
     */
    protected TreeNode<E> inOrderPrevNode(TreeNode<E> node) {
        if (node == null) return null;
        if (node.left != null) {
            node = node.left;
            while (node.right != null) {
                node = node.right;
            }
            return node;
        }
        while (node.parent != null && node.parent.left == node) {
            node = node.parent;
        }
        return node.parent;
    }


    //-----------------递归实现-----------------//

    /**
     * 前序遍历：递归实现
     */
    @Deprecated
    public void preOrderTraversalByRecursion(TreeNode<E> root, Visitor<E> visitor) {
        if (root == null) return;
        visitor.visit(root.e);
        preOrderTraversalByRecursion(root.left, visitor);
        preOrderTraversalByRecursion(root.right, visitor);
    }

    /**
     * 中序遍历：递归实现
     */
    @Deprecated
    public void inOrderTraversalByRecursion(TreeNode<E> root, Visitor<E> visitor) {
        if (root == null) return;
        inOrderTraversalByRecursion(root.left, visitor);
        visitor.visit(root.e);
        inOrderTraversalByRecursion(root.right, visitor);
    }

    /**
     * 后序遍历：递归实现
     */
    @Deprecated
    public void postOrderTraversalByRecursion(TreeNode<E> root, Visitor<E> visitor) {
        if (root == null) return;
        postOrderTraversalByRecursion(root.left, visitor);
        postOrderTraversalByRecursion(root.right, visitor);
        visitor.visit(root.e);
    }

    /**
     * 计算高度，递归实现
     */
    @Deprecated
    public int heightByRecursion(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(heightByRecursion(root.left), heightByRecursion(root.right));
    }

    /**
     * 计算节点数量，递归实现
     */
    @Deprecated
    public int sizeByRecursion(TreeNode root) {
        if (root == null) return 0;
        return 1 + sizeByRecursion(root.left) + sizeByRecursion(root.right);
    }
}
