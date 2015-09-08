import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class SegmentTreeOnPointersCreateTest extends AbstractTest {
    int[] input;
    Runnable task = new Runnable() {
        @Override
        public void run() {
            SegmentTreeOnPointersCreate tree = new SegmentTreeOnPointersCreate(input);
            validateTree(input, tree);
        }
    };

    static void validateTree(int[] data, SegmentTreeOnPointersCreate tree) {
        Common.assertCondition(tree != null, "Segment tree should not be null");
        Common.assertCondition(tree.root != null, "Tree root should not be null");
        Common.assertCondition(tree.root.l == 0, "Tree root should cover entire array");
        Common.assertCondition(tree.root.r == data.length - 1, "Tree root should cover entire array");

        validateTreeNode(data, tree.root);
    }

    static int getSize(SegmentTreeOnPointersCreate.SegmentTreeNode node) {
        return node.r - node.l + 1;
    }

    static void validateTreeNode(int[] data, SegmentTreeOnPointersCreate.SegmentTreeNode node) {
        Common.assertCondition((node.left == null) == (node.right == null), "Nodes should have either 0 or 2 children");
        Common.assertCondition(node.l <= node.r, "node.l <= node.r");
        int expectedSum = 0;
        for (int i = node.l ; i <= node.r ; i++) expectedSum += data[i];

        Common.assertCondition(node.sum == expectedSum, "Node sum was calculated incorrectly");

        if (node.l == node.r) {
            Common.assertCondition(node.left == null, "Node with single item should have no children");
        } else {
            validateTreeNode(data, node.left);
            validateTreeNode(data, node.right);

            Common.assertCondition(node.left != null, "Node covering several numbers cannot be leaf");
            Common.assertCondition(node.left.l == node.l, "node.left.l == node.l");
            Common.assertCondition(node.right.r == node.r, "node.right.r == node.r");
            Common.assertCondition(node.left.r + 1 == node.right.l, "node.right.r == node.r");

            Common.assertCondition(Math.abs(getSize(node.left) - getSize(node.right)) <= 1,
                    "Node with " + getSize(node) + " items was split into children with " + getSize(node.left) + " and " + getSize(node.right) + " items which is suboptimal");
        }
    }

    public SegmentTreeOnPointersCreateTest(int[] input) {
        super("SegmentTreeOnPointersCreate");
        this.input = input;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                { new int[] { 1, 3 }},
                { new int[] { 1, 3, 6, 2 }},
        });
    }

    @Override
    protected Runnable getTask() {
        return task;
    }

    @Override
    protected String lastInput() {
        return Common.printArray(input);
    }
}

