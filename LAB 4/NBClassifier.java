/**
 * ISTE-612-2215 Lab #4
 * Mithila Nigudkar
 * 13th April 2022
 */
import java.util.*;

public class NBClassifier {
	private HashMap<Integer, String> doctrain; //training the data
	private int[] classtrain; // training the class values
	private int numberofclasses = 2;
	private int[] docperclass; //doc per class
	private String[] stringclass; //string concate for given class
	private int[] Tokens; //all the tokens per class
	private HashMap<String, Double>[] conditionalProb;
	private HashSet<String> voc; //all
	LoadData parse;

	/**
	 * Build a Naive Bayes classifier using a training document set
	 */
	@SuppressWarnings("unchecked")
	public NBClassifier() {
		preprocess();
		doctrain = parse.docs;
		classtrain = parse.classes;
		docperclass = new int[numberofclasses];
		stringclass = new String[numberofclasses];
		Tokens = new int[numberofclasses];
		conditionalProb = new HashMap[numberofclasses];
		voc = new HashSet<String>();
		for (int i = 0; i < numberofclasses; i++) {
			stringclass[i] = "";
			conditionalProb[i] = new HashMap<String, Double>();
		}
		for (int n = 0; n < classtrain.length; n++) {
			docperclass[classtrain[n]]++;
			stringclass[classtrain[n]] += (doctrain.get(n) + " ");
		}
		for (int m = 0; m < numberofclasses; m++) {
			String[] tokens = stringclass[m].split("[\" ()_,?:;%&-]+");
			Tokens[m] = tokens.length;
			for (String token : tokens) {
				voc.add(token);
				if (conditionalProb[m].containsKey(token)) {
					double count = conditionalProb[m].get(token);
					conditionalProb[m].put(token, count + 1);
				} else {
					conditionalProb[m].put(token, 1.0);
				}
			}
		}
			for (int k = 0; k < numberofclasses; k++) {
				Iterator<Map.Entry<String, Double>> iterator = conditionalProb[k].entrySet().iterator();
				int vSize = voc.size();
				while (iterator.hasNext()) {
					Map.Entry<String, Double> entry = iterator.next();
					String token = entry.getKey();
					Double count = entry.getValue();
					Double prob = (count + 1) / (Tokens[k] + vSize);
					conditionalProb[k].put(token, prob);
				}
			}
		}



	/**
	 * Classify a test doc
	 *
	 * @param doc test doc
	 * @return class label
	 */
	public int classify(String doc) {
		int label = 0;
		int vSize = voc.size();
		double[] score = new double[numberofclasses];
		for (int s = 0; s < numberofclasses; s++) {
			score[s] = Math.log10(docperclass[s] * 1.0 / doctrain.size());

		}
		String[] tokens = doc.split("[\" ()_,?:;%&-]+");
		for (int a = 0; a < numberofclasses; a++) {
			for (String token : tokens) {
				if (!conditionalProb[a].containsKey(token))
					score[a] += Math.log10(1.0 / (Tokens[a] + vSize));
				else {
					score[a] += Math.log10(conditionalProb[a].get(token));
				}

			}
		}
		double largescore = score[0];
		for (int i = 0; i < score.length; i++) {
			if (largescore < score[i])
				label = i;
		}
		return label;

	}

	/**
	 * Load the training documents
	 */
	public void preprocess() {
		parse = new LoadData();
	}


	/**
	 * Classify a set of testing documents and report the accuracy
	 *
	 * @param testDocs fold that contains the testing documents
	 * @return classification accuracy
	 */
	public double classifyAll(HashMap<Integer, String> testDocs, int[] classtrain) {
		float truepos = 0;
		float trueneg = 0;
		float falsepos = 0;
		float falseneg = 0;
		int classifiedcorrectly = 0;
		float precision;
		float recall;
		float acc;
		for (Map.Entry<Integer, String> testDoc : testDocs.entrySet()) {
			int result = classify(testDoc.getValue());
			if (classtrain[testDoc.getKey()] == 1 && result == classtrain[testDoc.getKey()]) {
				truepos++;
			} else if (classtrain[testDoc.getKey()] == 0 && result == classtrain[testDoc.getKey()]) {
				trueneg++;
			} else if (classtrain[testDoc.getKey()] == 0 && result != classtrain[testDoc.getKey()]) {
				falseneg++;
			} else if (classtrain[testDoc.getKey()] == 1 && result != classtrain[testDoc.getKey()]) {
				falsepos++;


			}
		}
		
		classifiedcorrectly = (int) truepos + (int) trueneg;
		acc = (truepos + trueneg) / (truepos + trueneg + falsepos + falseneg);
		System.out.println(" Correctly classified " + classifiedcorrectly + " out of " + testDocs.size());

		return acc;
	}
	/* Function where we choose a file randomly and the classification is performed on all the docs
	 */

	public static void main(String[] args) {
		NBClassifier Naive = new NBClassifier();
		System.out.println(" classification done on a single doc:");
		int randomvalue = new Random().nextInt(Naive.parse.TestDocs.size());
		randomvalue += 1800;
		System.out.println("Doc at index : " + (randomvalue - 1800) + " which is" + (Naive.classify(Naive.parse.TestDocs.get(randomvalue)) == 1 ? "Positive" : "Negative"));
		randomvalue = new Random().nextInt(Naive.parse.TestDocs.size());
		randomvalue += 1800;
		System.out.println(" Doc at index: " + (randomvalue - 1800) + " which is" + (Naive.classify(Naive.parse.TestDocs.get(randomvalue)) == 1 ? "Positive" : "Negative"));
		System.out.println();
		System.out.println("Classification done on all docs:");
		double acc = Naive.classifyAll(Naive.parse.TestDocs, Naive.parse.classes);
		System.out.println("Accuracy: " + acc);
	}
}

