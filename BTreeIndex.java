//import com.sun.source.tree.BinaryTree;

import java.util.*;



public class BTreeIndex{
	String[] myDocs;
	BinaryTree termList;
	BTNode root;
	
	/**
	 * Construct binary search tree to store the term dictionary 
	 * @param docs List of input strings
	 * 
	 */

	ArrayList<ArrayList<Integer>> docLists;
	public BTreeIndex(String[]docs) {
		myDocs = docs;
		ArrayList<String> in = new ArrayList<String>();
		docLists = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> docList = new ArrayList<Integer>();
		termList = new BinaryTree();
		for (int k = 0; k < myDocs.length; k++) {
			String[] words = myDocs[k].split(" ");
			for (String word : words) {
				if (!in.contains(word)) {
					in.add(word);
				}
			}
		}
		Collections.sort(in);
		System.out.println("Sorted names are: ");
		System.out.println(in + "\n");
		int start = 0;
		int end = in.size() - 1;
		int mid = (start + end) / 2;
		BTNode bt = new BTNode(in.get(mid), docList);
		root = bt;

		for (int n = 0; n < myDocs.length; n++) {
			String[] tokens = myDocs[n].split(" ");

			for (String token : tokens) {
				if (termList.search(bt, token) == null) {
					docList = new ArrayList<Integer>();
					docList.add(new Integer(n));
					docLists.add(docList);
					termList.add(root, new BTNode(token, docList));
				} else {
					BTNode indexNode = termList.search(bt, token);
					docList = indexNode.docLists;
					if (!docList.contains(new Integer(n))) {
						docList.add(new Integer(n));
					}
					indexNode.docLists = docList;
				}
			}
		}
		System.out.println("\nResult in order:");
		termList.printInOrder(root);
	}

		/**
		 * Single keyword search
		 * @param query the query string
		 * @return doclists that contain the term
		 */
		public ArrayList<Integer> search(String query)
		{
			BTNode node = termList.search(root, query);
			if (node == null) {
				return null;
			}
			else {
				return node.docLists;
			}
		}

		/**
		 * conjunctive query search
		 * @param query the set of query terms
		 * @return doclists that contain all the query terms
		 */
		public ArrayList<Integer> search(String[]query)
		{

			ArrayList<Integer> result = search(query[0]);
			System.out.println(Arrays.toString(query));
			int termId = 1;
			while (termId < query.length) {
				ArrayList<Integer> result1 = search(query[termId]);
				result = merge(result, result1);
				termId++;
			}
			return result;
		}

		/**
		 *
		 * @param wildcard the wildcard query, e.g., ho (so that home can be located)
		 * @return a list of ids of documents that contain terms matching the wild card
		 */
		public ArrayList<Integer> wildCardSearch (String wildcard)
		{
			ArrayList<Integer> output = new ArrayList<Integer>();
			ArrayList<BTNode> results = termList.wildCardSearch(root, wildcard, new ArrayList<BTNode>());
			if (results.size() > 0) {
				BTNode start = results.get(0);
				output = start.docLists;
				if (results.size() > 1) {
					for (BTNode node : results) {
						output = join(output, node.docLists);

					}
				}
			}
			return output;
		}
		public ArrayList<Integer> join(ArrayList < Integer > a1, ArrayList < Integer > a2)
		{
			ArrayList<Integer> output = new ArrayList<Integer>();
			int m = a1.size();
			int n = a2.size();
			int i = 0, j = 0;
			while (i < m && j < n) {
				if (a1.get(i) < a2.get(j))
					output.add(a1.get(i++));
				else if (a2.get(j) < a1.get(i))
					output.add(a2.get(j++));
				else {
					output.add(a2.get(j++));
					i++;
				}
			}

		while (i < m)
			output.add(a1.get(i++));
		while (j < n)
			output.add(a2.get(j++));
		return output;
	}
	private ArrayList<Integer> merge(ArrayList<Integer> l1, ArrayList<Integer> l2)
	{
		ArrayList<Integer> mergedList = new ArrayList<Integer>();
		int id1 = 0, id2=0;
		while(id1<l1.size()&&id2<l2.size()){
			if(l1.get(id1).intValue()==l2.get(id2).intValue()){
				mergedList.add(l1.get(id1));
				id1++;
				id2++;
			}
			else if(l1.get(id1)<l2.get(id2))
				id1++;
			else
				id2++;
		}
		return mergedList;
	}
	
	
	/**
	 * Test cases
	 * @param args commandline input
	 */
	public static void main(String[] args)
	{
		String[] docs = {"text warehousing over big data",
                       "dimensional data warehouse over big data",
                       "nlp before text mining",
                       "nlp before text classification"};
		//TO BE COMPLETED with testcases

		BTreeIndex binTree = new BTreeIndex(docs);
		//Single Query
		System.out.println("\nSingle Query");
		String[] oneword = {"nlp"};
	System.out.println(Arrays.toString(oneword));
		for (int j =0; j<oneword.length; j++)
		{
			ArrayList<Integer> result = binTree.search(oneword[j]);
			if(result != null) {
				System.out.println(oneword[j] + ": " + result);
			}
			else
			{
				System.out.println("Word not found");
			}
		}
		//Conjunctive queries
		 String[] query ={"nlp" , "mining" , "text"};
		System.out.print("\nConjuctive query");
		ArrayList<Integer> output1 = binTree.search(query);
		if(output1 != null && !output1.isEmpty()) {
			System.out.println("Result for the query: "+ output1);
		}
		else{
			System.out.println("Word not found");
		}

		//Wildcard Queries
		System.out.println("\nWildcard Query");
		String[] wildcard = { "nl" };
		System.out.println(Arrays.toString(wildcard));
		for(int i = 0 ; i< wildcard.length; i++)
		{
			ArrayList<Integer> output2 = binTree.wildCardSearch(wildcard[i]);
			if(output2 != null && !output2.isEmpty()) {
				System.out.println(wildcard[i] + " : " + output2);
			}
			else{
			System.out.println("word not found");
							}
					}
			}
}