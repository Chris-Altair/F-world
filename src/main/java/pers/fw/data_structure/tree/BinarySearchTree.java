package pers.fw.data_structure.tree;

/**
 * 二叉搜索树（BST）
 * 这里暂时忽略重复元素
 *
 * @param <E>
 */
public class BinarySearchTree<E extends Comparable<E>> extends BinaryTree<E> {
    private int size;

    private void checkElementNull(E e) {
        if (e == null) throw new NullPointerException("element must not be null");
    }

    public void add(E e) {
        checkElementNull(e);
        if (root == null) {
            root = new TreeNode<>(e);
            ++size;
            return;
        }
        TreeNode<E> node = root;
        TreeNode<E> parent = null;
        while (true) {
            parent = node;
            int compare = e.compareTo(node.e);
            if (compare > 0) {
                node = node.right;
            } else if (compare < 0) {
                node = node.left;
            } else {
                //相等则覆盖，也可直接推出
                node.e = e;
                return;
            }
            if (node == null) {
                if (compare > 0) {
                    parent.right = new TreeNode<>(e, parent);
                } else if (compare < 0) {
                    parent.left = new TreeNode<>(e, parent);
                }
                ++size;
                return;
            }
        }
    }

    private TreeNode<E> findNode(E e) {
        checkElementNull(e);
        TreeNode<E> node = root;
        while (node != null) {
            int compare = e.compareTo(node.e);
            if (compare == 0) {
                return node;
            } else if (compare > 0) {
                node = node.right;
            } else {
                node = node.left;
            }
        }
        return null;
    }

    public boolean contains(E e) {
        return findNode(e) != null;
    }

    /**
     * 核心思路是先找到该节点，然后根据节点的度，分情况处理；设要删除的节点为node
     * 度=2，找到node的左子树最大或右子树最小的节点s(其实就是中序遍历前/后节点，若node的度=2，则前/后节点的度只能为0或1)，用s的值替代node的值，然后删除s
     * 度=1：删除node，并将node的子树接上去
     * 度=0：叶子节点直接删
     */
    public void remove(E e) {
        if (e == null) return;
        remove(findNode(e));
    }

    private void remove(TreeNode node) {
        if (node == null) return;
        --size;
        //度=2时，相当于取node中序遍历的前/后节点（度=0或1）的值赋予node，然后删除前/后节点，这样问题就转化为删除度=0或1的节点了
        if (node.hasTwoChildren()) {
            //这里取的是后节点
            TreeNode nextNode = inOrderNextNode(node);
            node.e = nextNode.e;
            node = nextNode;
        }
        TreeNode childNode = node.left != null ? node.left : node.right;
        if (childNode != null) {
            //度为1
            childNode.parent = node.parent;
            if (node.parent == null) {
                root = childNode;
            } else if (node.parent.left == node) {
                node.parent.left = childNode;
            } else {
                node.parent.right = childNode;
            }
        } else if (node.parent == null) {
            //度=0且为根节点
            root = null;
        } else {
            //度=0的非根节点
            if (node.parent.left == node) {
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }
        }
    }
}
