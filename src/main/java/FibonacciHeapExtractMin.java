import java.util.*;

public class FibonacciHeapExtractMin {

  FibNode	minNode;
  int size = 0;

  public FibNode insert(int key) {

    FibNode node = new FibNode(key);
    minNode = unionRootsLists(minNode, node);
    size++;

    return node;
  }

  private FibNode unionRootsLists(FibNode min, FibNode max) {
    if (min == null) {
      return max;
    }
    if (max == null) {
      return min;
    }
    union(min, max);

    FibNode minimNode;
    if (min.key < max.key) {
      minimNode = min;
    } else {
      minimNode = max;
      max.isMinimum = true;
      min.isMinimum = false;
    }

    return minimNode;
  }

  private void union(FibNode min, FibNode max) {
    max.next = min;
    max.prev = min.prev;
    min.prev.next = max;
    min.prev = max;
  }

  public FibNode extractMin() {
    FibNode extractedMin = null;

    if (minNode != minNode.next && minNode.child == null) {
      FibNode min = minNode.next;
      removeNode(minNode);
      minNode = min;
      consolidate(minNode);
      size--;
      return extractedMin;
    }
    return extractedMin;

  }
  public  void consolidate(FibNode minNode) {
    if (minNode == minNode.next && minNode.child == null) {
      size--;
      minNode = minNode;
      return;
    }
    FibNode start = minNode;
    FibNode curr = start;
    List<FibNode> list = new LinkedList<FibNode>();
    do {
      list.add(curr);
      curr = curr.next;
    } while (curr != start);
    Iterator<FibNode> it = list.iterator();
    FibNode current;
    current = it.next();
    FibNode rankNode = null;
    int index = current.rank;

    Map<Integer, FibNode> rankMap = new HashMap<Integer, FibNode>();
    do {
      if (rankMap.containsKey(index)) {
        rankNode = rankMap.get(index);
        current = linkHeaps(current, rankNode);
        rankMap.remove(index);
        index = current.rank;

      } else {
        rankMap.put(index, current);
        current = it.next();
        index = current.rank;
      }
      if (current.key < minNode.key) {
        minNode = current;
      }
    } while (it.hasNext());

    while (rankMap.containsKey(index)) {
      rankNode = rankMap.get(index);
      current = linkHeaps(current, rankNode);
      rankMap.remove(index);
      index = current.rank;
      if (current.key < minNode.key) {
        minNode = current;
      }
    }

  }
  protected void removeNode(FibNode node) {
    node.next.prev = node.prev;
    node.prev.next = node.next;
    node.next = node;
    node.prev = node;
  }
  private FibNode linkHeaps(FibNode min, FibNode max) {
    if (min.key > max.key) {
      FibNode tmp;
      tmp = min;
      min = max;
      max = tmp;
    }
    removeNode(max);
    max.parent = min;
    if (min.child == null) {
      min.child = max;
    } else {
      union(max, min.child);
    }
    max.isMarked = false;
    max.isMinimum = false;
    min.rank++;
    return min;

  }


  public FibNode getMinNode() {

    return minNode;
  }
}