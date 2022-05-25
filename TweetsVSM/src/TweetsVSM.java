import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Team_3
 *
 * CLass to implement VSM in tweets dataset remove stop word and perform ranked retrieval
 *
 */
public class TweetsVSM {

    //attributes
    private final String[] myDocs; // Tweet Documents
    private final String[] myUDocs; // Username Documents
    private final String[] myVerDocs; // Verification Documents
    private final ArrayList<String> termList;
    private final ArrayList<ArrayList<Doc>> docLists;
    private static ArrayList<String> stopwords = new ArrayList<String>();

    // Method to read the stopword.txt file
    public static void StopWord() throws IOException {
        String StopFile = "D:/Spring 22/Team#3 Final Project/TweetsVSM/src/Stopwords.txt";
        BufferedReader bufReader = new BufferedReader(new FileReader(StopFile)); // read file
        String line = bufReader.readLine();
        line = line.toLowerCase(); // Turns it into lowercase and Reads each line
        while (line != null) {
            stopwords.add(line); // Adds line to the stop words ArrayList
            line = bufReader.readLine();
        }
        bufReader.close();
    }


    /**
     *
     * Constructor for the Class TweetsVSM to create a VSM for the Tweets Docs input
     * @param docs  Taking Arraylist of Tweets input file
     * @param UDocs Taking Arraylist of Username input file
     * @param VerDocs Taking Arraylist of Verification Status input file
     *
     */
    public TweetsVSM(String[] docs, String[] UDocs, String[] VerDocs) {
        myUDocs = UDocs;
        myVerDocs = VerDocs;
        myDocs = docs;
        termList = new ArrayList<String>();
        docLists = new ArrayList<ArrayList<Doc>>();
        ArrayList<Doc> docList;
        for (int i = 0; i < myDocs.length; i++) {
            // this regex takes only alphanumeric values as words with an exception to ' symbol
            // thus, this naturally uses spaces, punctuation and symbols as delimiter except for '
            String[] words = myDocs[i].split("(?!')([\\W]+)"); // Tokenization of the Tweets
            String word;

            // Removing stop words from the tokenized tweets using the stop words arraylist
            ArrayList<String> builder = new ArrayList<String>();
            for (String word1 : words) {
                if (!stopwords.contains(word1)) {
                    builder.add(word1);
                }
            }
            words = builder.toArray(new String[builder.size()]);

            for (String s : words) {
                boolean match = false;
                word = s;
                if (!termList.contains(word)) {
                    termList.add(word);
                    docList = new ArrayList<Doc>();
                    Doc doc = new Doc(i, 1);
                    docList.add(doc);
                    docLists.add(docList);
                } else {
                    int index = termList.indexOf(word);
                    docList = docLists.get(index);

                    for (Doc did : docList) {
                        if (did.docId == i) {
                            did.tw++;
                            match = true;
                            break;
                        }

                    }
                    if (!match) {
                        Doc doc = new Doc(i, 1);
                        docList.add(doc);
                    }
                }
            }

        }

        // This computes the tf-idf
        int N = myDocs.length;
        // This is done for normalization
        double[] docLength = new double[N];
        // Updating the doc length
        for (int i = 0; i < termList.size(); i++) {
            docList = docLists.get(i);
            int df = docList.size();
            Doc doc;
            for (int j = 0; j < docList.size(); j++) {
                doc = docList.get(j);
                double tfidf = (1 + Math.log10(doc.tw)) * Math.log10(N * 1.0 / df);
                docLength[doc.docId] += Math.pow(tfidf, 2);
                doc.tw = tfidf;
                docList.set(j, doc);
            }
        }
        // Updating doc length
        for (int i = 0; i < N; i++) {
            docLength[i] = Math.sqrt(docLength[i]);
        }
    }


    /**
     *  Method for ranked search for the given string input
     * @param query to perform ranked search in the Tweet docs from the given string query
     * @return Hashmap of all the ranked search with tweet docID and score
     */
    public HashMap rankSearch(String query) {
        String[] temp = query.split(" ");
        HashMap<Integer, Double> docs = new HashMap<Integer, Double>();

        ArrayList<Doc> docList;
        for (String term : temp) {
            term = term.toLowerCase();
            int index = termList.indexOf(term);
            if (index < 0) continue;
            docList = docLists.get(index);
            double qtfidf = (1 + Math.log10(1)) * Math.log10(myDocs.length * 1.0 / docList.size());

            Doc doc;
            //Normalize the vectors
            for (int i = 0; i < docList.size(); i++) {
                doc = docList.get(i);
                double score = doc.tw * qtfidf;

                if (!docs.containsKey(doc.docId)) {
                    docs.put(doc.docId, score);
                } else {
                    score += docs.get(doc.docId);
                    docs.put(doc.docId, score);
                }
            }
        }
        // Applying the sort by values method to sort the output in descending order
        docs = sortByValues(docs);
        //System.out.println(docs);
        return docs;
    }

    /**
     *  Method to Detect the Usernames of Tweets that are similar to the test cases.
     *  Ranking is according to ranked retrieval method meaning the most likely username comes first.
     * @param query to detect Username by calling ranked search in the Tweet docs from the given string query
     * @return Arraylist of all the Username of tweet output from ranked search with tweet docID and score
     */
    public ArrayList UserDetection(String query) {
        HashMap TempDoc = rankSearch(query);
        Set TweetKey = TempDoc.keySet();
        int n = TweetKey.size();
        List KeyList = new ArrayList(n);
        KeyList.addAll(TweetKey);
        ArrayList UserList = new ArrayList(n);
        for (Object x : KeyList) {
            UserList.add(myUDocs[(int) x]);
        }
        return UserList;
    }

    // Method to detect Verification Status of the Username of the Resultant Tweets

    /**
     * Method to detect Verification Status of the Username of the Resultant Tweets
     * @param query to detect Verification Status by calling ranked search in the Tweet docs from the given string query
     * @return Arraylist of all the Verification Status of Usernames of tweet output from ranked search with tweet docID and score
     */
    public ArrayList VerificationDetection(String query) {
        HashMap TempDoc = rankSearch(query);
        Set TweetKey = TempDoc.keySet();
        int n = TweetKey.size();
        List KeyList = new ArrayList(n);
        KeyList.addAll(TweetKey);
        ArrayList VerificationList = new ArrayList(n);
        for (Object x : KeyList) {
            VerificationList.add(myVerDocs[(int) x]);
        }
        return VerificationList;
    }


    /**
     * Method to detect Resultant Tweets after the Ranked Retrieval is done.
     * @param query to detect Tweets by calling ranked search in the Tweet docs from the given string query
     * @return Arraylist of all the tweet output from ranked search with tweet docID and score
     */
    public ArrayList TweetsDetection(String query) {
        HashMap TempDoc = rankSearch(query);
        Set TweetKey = TempDoc.keySet();
        int n = TweetKey.size();
        List KeyList = new ArrayList(n);
        KeyList.addAll(TweetKey);
        ArrayList TweetList = new ArrayList(n);
        for (Object x : KeyList) {
            TweetList.add(myDocs[(int) x]);
        }
        return TweetList;
    }

    /**
     * Method to detect Resultant Tweets after the Ranked Retrieval is done and limit it to top 10 result.
     * @param query to detect Tweets by calling ranked search in the Tweet docs from the given string query
     * @return Top 10 Arraylist of all the tweet output from ranked search with tweet docID and score
     */
    public ArrayList ResultTweets(String query) {
        ArrayList Tweets = (ArrayList) TweetsDetection(query).stream().limit(10).collect(Collectors.toList());
        return Tweets;
    }

    /**
     * Method to detect Resultant Usernames after the Ranked Retrieval is done in tweets and limit it to top 10 result.
     * @param query to detect Username by calling UserDetection in the Tweet docs from the given string query
     * @return Top 10 Arraylist of all the Username output from ranked search with tweet docID and score
     */
    public ArrayList ResultUsername(String query) {
        ArrayList Username = (ArrayList) UserDetection(query).stream().limit(10).collect(Collectors.toList());
        return Username;
    }

    /**
     * Method to detect Resultant Usernames Verification Status after the Ranked Retrieval is done in tweets and limit it to top 10 result.
     * @param query to detect Verification Status by calling VerificationDetection in the Tweet docs from the given string query
     * @return Top 10 Arraylist of all the Verification Status of Username output from ranked search with tweet docID and score
     */
    public ArrayList ResultVerification(String query) {
        ArrayList VeriStatus = (ArrayList) VerificationDetection(query).stream().limit(10).collect(Collectors.toList());
        return VeriStatus;
    }

    /**
     * Method to detect Resultant outcomes after the Ranked Retrieval is done in tweets and limit it to top 10 result.
     * @param query to detect Username,tweets,verification by calling all detection methods in the Tweet docs from the given string query
     * @return Top 10 Arraylist of all the Username output from ranked search with tweet docID and score for the GUI and console output to check
     */
    public ArrayList Result(String query) {
        ArrayList Tweets = ResultTweets(query);
        ArrayList Username = ResultUsername(query);
        ArrayList Verification = ResultVerification(query);
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("Username:\t|\t" + "Verification Status:\t|\t" + "Tweets:\t|");
        System.out.println("----------------------------------------------------------------------------------------------------");
        for (int i = 0; i < Tweets.size(); i++) {
            System.out.println(Username.get(i) + "\t|\t" + Verification.get(i) + "\t|\t" + Tweets.get(i) + "\t|\t");
            System.out.println("------------------------------------------------------------------------------------------");
        }
        System.out.println("-----------------------------------------------------------------------------------------------------");
        return Username;
    }

    public ArrayList<String> term() {
        return termList;
    }

    /**
     * Method to print VSM of tweet docs in structured string format
     * will not be included in gui as not needed in front end / will only show if specifically called
     * @return out string of vsm of input tweet docs
     */
    public String toString() {
        String outString = "";
        ArrayList<Doc> docList;
        for (int i = 0; i < termList.size(); i++) {
            outString += String.format("%-15s", termList.get(i));
            docList = docLists.get(i);
            for (Doc doc : docList) {
                outString += doc + "\t";
            }
            outString += "\n";
        }
        return outString;
    }


    /**
     * Method to Sorting the Search Query Output HashMap in Descending Order in terms of score
     * @param map input of ranked search hashmap output
     * @return sortedHashMap in Descending order where larger number being the first outcome
     */
    private static HashMap sortByValues(HashMap map) {
        List list = new LinkedList(map.entrySet());
        // Defined Custom Comparator here
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o2)).getValue()).compareTo(((Map.Entry) (o1)).getValue());
            }
        });
        // Here I am copying the sorted list in HashMap
        // using LinkedHashMap to preserve the insertion order
        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }
}

// Class to instantiate Doc type used in VSM
class Doc {
    int docId ;
    double tw;
    public Doc(int did, double tw) {
        docId = did;
        this.tw = tw;
    }
    public String toString() {
        return docId + " : " + tw;
    }
}

