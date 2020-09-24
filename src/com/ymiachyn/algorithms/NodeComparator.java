package com.ymiachyn.algorithms;

import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {

	@Override
	public int compare(Node n1, Node n2) {
		int retVal = 0;
		
		if(n1.getF() < n2.getF())
			retVal = -1;
		else if(n1.getF() > n2.getF())
			retVal = 1;
		
		return retVal;
	}

}
