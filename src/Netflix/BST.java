package Netflix;

public class BST extends BinaryTree {
	
	public BST() {
		super();
	}

	public BST(BTNode root) {
		super(root);
	}
	
	// Busca um nó pelo Id
	public BTNode search_id(String id) { 
		return search_idHelper(super.getRoot(),id);
	}

	private BTNode search_idHelper(BTNode node, String id) {
		if (node == null) {
			return null;
		}

		if (id.compareTo(node.getData().getId()) < 0) {
			return search_idHelper(node.getLeft(), id);
		} else if (id.compareTo(node.getData().getId()) > 0) {
			return search_idHelper(node.getRight(), id);
		} else {
			return node;
		}
	}
	
	// Busca um nó e conta quantas comparações são feitas para encontrar esse nó.
	public BTNode search_countComparisons(String id) { 
		int comparisons = 0;
		return search_countComparisonsHelper(super.getRoot(),id, comparisons);
	}

	private BTNode search_countComparisonsHelper(BTNode node, String id, int comparisons) {
		
		if (node == null) {
			System.out.println("O nó não foi encontrado após " + comparisons + " comparações.");
			return null;
		}
		
		comparisons++;
		if (id.compareTo(node.getData().getId()) < 0) {
			return search_countComparisonsHelper(node.getLeft(), id, comparisons);
		} else if (id.compareTo(node.getData().getId()) > 0) {
			return search_countComparisonsHelper(node.getRight(), id,comparisons);
		} else {
			System.out.println("Comparações: " + comparisons);
			return node;
		}
	}
	
	public BTNode search(ProgramaNetflix data) {
		return searchHelper(super.getRoot(), data);
	}

	private BTNode searchHelper(BTNode node,ProgramaNetflix data) {
		if (node == null) {
			return null;
		}

		if (data.getId().compareTo(node.getData().getId()) < 0) {
			return searchHelper(node.getLeft(), data);
		} else if (data.getId().compareTo(node.getData().getId()) > 0) {
			return searchHelper(node.getRight(), data);
		} else {
			return node;
		}
	}

	public void insert(ProgramaNetflix data) {
		super.setRoot(insertHelper(super.getRoot(), null, data));
	}

	private BTNode insertHelper(BTNode node, BTNode parent, ProgramaNetflix data) {
		if (node == null) {
			return new BTNode(data, parent);
		}

		if (data.getId().compareTo(node.getData().getId()) < 0) {
			node.setLeft(insertHelper(node.getLeft(), node, data));
		} else if (data.getId().compareTo(node.getData().getId()) > 0) {
			node.setRight(insertHelper(node.getRight(), node, data));
		} else {
			// Nessa implementação, não é permitida a inserção de duplicatas na BST.
		}

		return node;
	}

	public void remove(String id) {
		super.setRoot(removeHelper(super.getRoot(), id));
	}

	private BTNode removeHelper(BTNode node, String id) {
		if (node == null) {
			return null;
		}

		if (id.compareTo(node.getData().getId()) < 0) {
			node.setLeft(removeHelper(node.getLeft(), id));
		} else if (id.compareTo(node.getData().getId()) > 0) {
			node.setRight(removeHelper(node.getRight(), id));
		} else {
			node = removeNode(node,id);
		}

		return node;		
	}

	protected BTNode removeNode(BTNode node, String id) {
		if (node.isLeaf()) {
			return null;
		}

		if (!node.hasLeftChild()) {
			return node.getRight();
		} else if (!node.hasRightChild()) {
			return node.getLeft();
		} else {
			BTNode predecessor = findMaxHelper(node.getLeft());
			node.setData(predecessor.getData());
			node.setLeft(removeHelper(node.getLeft(), id));
		}

		return node;		
	}

	public BTNode findMin() {
		return findMinHelper(super.getRoot());
	}

	private BTNode findMinHelper(BTNode node) {
		if (node == null) {
			return null;
		} else {
			while (node.hasLeftChild()) {
				node = node.getLeft();
			}
			return node;
		}
	}

	public BTNode findMax() {
		return findMaxHelper(super.getRoot());
	}

	private BTNode findMaxHelper(BTNode node) {
		if (node == null) {
			return null;
		} else {
			while (node.hasRightChild()) {
				node = node.getRight();
			}
			return node;
		}
	}

	public BTNode findPredecessor(ProgramaNetflix data) {
		return predecessor(data, false);
	}

	public BTNode findPredecessorIgnoreCase(ProgramaNetflix data) {
		return predecessor(data, true);
	}

	private BTNode predecessor(ProgramaNetflix data, boolean ignoreCase) {
		BTNode node = search(data);
		return node == null ? null : predecessorHelper(node, ignoreCase);
	}

	private BTNode predecessorHelper(BTNode node, boolean ignoreCase) {
		if (node.hasLeftChild()) {
			return findMaxHelper(node.getLeft());
		} else {
			BTNode current = node;
			BTNode parent = node.getParent();

			while (parent != null && current == parent.getLeft()) {
				current = parent;
				parent = current.getParent();
			}

			return parent;
		}
	}

	public BTNode findSuccessor(ProgramaNetflix data) {
		return successor(data, false);
	}

	public BTNode findSuccessorIgnoreCase(ProgramaNetflix data) {
		return successor(data, true);
	}

	private BTNode successor(ProgramaNetflix data, boolean ignoreCase) {
		BTNode node = search(data);
		return node == null ? null : successorHelper(node, ignoreCase);		
	}

	private BTNode successorHelper(BTNode node, boolean ignoreCase) {
		if (node.hasRightChild()) {
			return findMinHelper(node.getRight());
		} else {
			BTNode current = node;
			BTNode parent = node.getParent();

			while (parent != null && current == parent.getRight()) {
				current = parent;
				parent = current.getParent();
			}

			return parent;
		}
	}

	@Override
	public String toString() {
		return "BST - isEmpty(): " + isEmpty()
		+ ", getDegree(): " + getDegree()
		+ ", getHeight(): " + getHeight()
		+ ", root => { " + super.getRoot().getData() + " }";				
	}

}

