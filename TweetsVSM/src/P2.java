public class P2 import java.util.*;
public class P2 {
    private String[] trainingDocs;         //training data
    private int[] trainingClasses;         //training class values
    private int numClasses;
    private int[] classDocCounts;          //number of docs per class
    private String[] classStrings;         //concatenated string for a given class
    private int[] classTokenCounts;        //total number of tokens per class
    private HashMap<String,Double>[] cndProb; //term conditional prob
    private HashSet<String> words;    //entire vocabulary

    @SuppressWarnings("unchecked")
    public P2(String[] docs, int[] classes, int numC) {
        trainingDocs = docs;
        trainingClasses = classes;
        numClasses = numC;

        classDocCounts = new int[numClasses];
        classStrings = new String[numClasses];
        classTokenCounts = new int[numClasses];

        cndProb = new HashMap[numClasses];
        words = new HashSet<String>();

        for(int i=0;i<numClasses;i++){
            classStrings[i] = "";
            cndProb[i] = new HashMap<String,Double>();
        }

        for(int i=0;i<trainingClasses.length;i++){
            classDocCounts[trainingClasses[i]]++;
            classStrings[trainingClasses[i]] += (trainingDocs[i] + " ");
        }

        for(int i=0;i<numClasses;i++){
            String[] tokens = classStrings[i].split(" ");
            classTokenCounts[i] = tokens.length;
            for(String token:tokens){
                words.add(token);
                if(cndProb[i].containsKey(token)){
                    double count = cndProb[i].get(token);
                    cndProb[i].put(token, count+1);
                }
                else
                    cndProb[i].put(token, 1.0);
            }
        }

        /***TO BE COMPLETED***/
        //Compute and output the class conditional probability of each word
        System.out.println("Words   : Count   : Conditional probability" );
        System.out.println("-------------------------------------------" );
        for (int k = 0; k < numClasses; k++) {
            Iterator<Map.Entry<String, Double>> iterator = cndProb[k].entrySet().iterator();
            int vSize = words.size();
            while (iterator.hasNext()) {
                Map.Entry<String, Double> entry = iterator.next();
                String token = entry.getKey();
                Double count = entry.getValue();
                Double prob = (count + 1) / (classTokenCounts[k] + vSize);
                cndProb[k].put(token, prob);
                System.out.println(token+" : "+count+" : "+prob);
            }
        }
    }

    /**
     ***TO BE COMPLETED***
     Classify a test document
     */

    public int classfiy(String doc){
        int label = 0;
        int vSize = words.size();
        double[] score = new double[numClasses];
        for (int s = 0; s < numClasses; s++) {
            score[s] = Math.log10(classDocCounts[s] * 1.0 / trainingDocs.length);

        }
        String[] tokens = doc.split("[\" ()_,?:;%&-]+");
        for (int a = 0; a < numClasses; a++) {
            for (String token : tokens) {
                if (!cndProb[a].containsKey(token))
                    score[a] += Math.log10(1.0 / (classTokenCounts[a] + vSize));
                else {
                    score[a] += Math.log10(cndProb[a].get(token));
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

    public static void main(String[] args){
        String[] trainDocs = {"Chinese Beijing Chinese",
                "Chinese Chinese Shanghai",
                "Chinese Macao",
                "Seoul Korean Chinese"};
        int[] trainLabels = {0,0,0,1};
        int numClass = 2;

        P2 nb = new P2(trainDocs, trainLabels, numClass);
        String testDoc = "Chinese Chinese Chinese Seoul Korea";

        System.out.println("\nClass Prediction: " + nb.classfiy(testDoc));
    }
}
{
}
