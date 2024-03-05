package Netflix;

public class BTNode {

	protected ProgramaNetflix data;
	protected BTNode parent;
	protected BTNode left;
	protected BTNode right;
	private int fb;  // Fator de balanceamento

	public BTNode(ProgramaNetflix data) {
		this(data, null);
	}

	public BTNode(ProgramaNetflix data, BTNode parent) {
		this.fb = 0;
		this.data = data;
		this.parent = parent;
		this.left = null;
		this.right = null;
	}

	public BTNode(ProgramaNetflix data, int fb, BTNode parent, BTNode left, BTNode right)
	{ 
		this.data = data; 
		this.fb = fb;
		this.parent = parent; 
		this.left = left; 
		this.right = right; 
	}

	public int getFb(){ 
		return fb; 
	}
	
	public void setFb(int fb) { 
		this.fb = fb; 
	}	

	public ProgramaNetflix getData() {
		return data;
	}

	public void setData(ProgramaNetflix data) {
		this.data = data;
	}

	public BTNode getParent() {
		return parent;
	}

	public void setParent(BTNode parent) {
		this.parent = parent;
	}

	public BTNode getLeft() {
		return left;
	}

	public void setLeft(BTNode left) {
		this.left = left;

		if (this.left != null) {
			this.left.setParent(this);
		}
	}

	public BTNode getRight() {
		return right;
	}

	public void setRight(BTNode right) {
		this.right = right;

		if (this.right != null) {
			this.right.setParent(this);
		}
	}

	public boolean hasLeftChild() {
		return left != null;
	}

	public boolean hasRightChild() {
		return right != null;
	}

	public boolean isRoot() {
		return parent == null;
	}

	public boolean isLeaf() {
		return left == null && right == null;
	}

	public int getDegree() {
		int degree = 0;

		if (hasLeftChild()) {
			++degree;
		}

		if (hasRightChild()) {
			++degree;
		}

		return degree;
	}

	public int getLevel() {
		if (isRoot()) {
			return 0;
		}

		return parent.getLevel() + 1;
	}

	public int getHeight() {
		if (isLeaf()) {
			return 0;
		}

		int height = 0;

		if (hasLeftChild()) {
			height = Math.max(height, left.getHeight());
		}

		if (hasRightChild()) {
			height = Math.max(height, right.getHeight());
		}

		return height + 1;
	}
	
	public void updateBalanceFactor() {
	    int leftHeight = (left != null) ? left.getHeight() + 1 : 0;
	    int rightHeight = (right != null) ? right.getHeight() + 1 : 0;
	    fb = leftHeight - rightHeight;
	}

	@Override
	public String toString() {
		return "data: " + data 
				+ ", parent: " + (parent != null ? parent.getData() : "null")
				+ ", left: " + (left != null ? left.getData() : "null")
				+ ", right: " + (right != null ? right.getData() : "null")
				+ ", isRoot(): " + isRoot()
				+ ", isLeaf(): " + isLeaf()
				+ ", getDegree(): " + getDegree()
				+ ", getLevel(): " + getLevel()
				+ ", getHeight(): " + getHeight();
	}

}
