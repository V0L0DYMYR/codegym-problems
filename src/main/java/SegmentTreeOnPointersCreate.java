public class SegmentTreeOnPointersCreate {
    public class SegmentTreeNode {
        public SegmentTreeNode left, right;
        public int l, r, sum;

        public SegmentTreeNode(SegmentTreeNode left, SegmentTreeNode right, int l, int r, int sum) {
            this.left = left;
            this.right = right;
            this.l = l;
            this.r = r;
            this.sum = sum;
        }
    }

    public SegmentTreeNode root;

    public SegmentTreeOnPointersCreate(int[] data) {
        root = buildNode(data, 0, data.length - 1);
    }

    private SegmentTreeNode buildNode(int[] data, int l, int r) {
        if (l == r) {
            return new SegmentTreeNode(null, null, l, r, data[l]);
        } else {
            int mid = (l + r) / 2;
            mid = l;
            SegmentTreeNode left = buildNode(data, l, mid);
            SegmentTreeNode right = buildNode(data, mid + 1, r);
            return new SegmentTreeNode(left, right, l, r, left.sum + right.sum);
        }
    }
}