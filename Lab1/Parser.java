import java.io.*;
import java.util.*;
/**
 * ISTE-612 LBE03 Text processing
 * Mithila Nigudkar LAB 1
 */

public class Parser {

    ArrayList<String> termList;
    ArrayList<ArrayList<Integer>> docLists;
    ArrayList<Integer> docList;
//    private String[] myDocs;

    String[] stopWords = null;

    public Parser() throws IOException{
 //reading the stop words from the text file
        File stopWordsFile = new File("stopWords.txt");
        String words = new String();
        Scanner sc = new Scanner(stopWordsFile);
        while(sc.hasNextLine()){
            words+= sc.nextLine().toLowerCase()+" ";
        }
stopWords = words.split ("[ ']");
Arrays.sort(stopWords);


    // Step #1


        String sw = new String();
        for(int i=0;i< stopWords.length;i++) {
            sw += stopWords[i] + " ";
        }

        System.out.println("Stop words: "+ sw);
    }



    //Binary search for a stop word
    public int searchStopWord(String key) {
        int lo = 0;
        int hi = stopWords.length -1;

        while(lo <= hi) {
            int mid = lo + (hi-lo)/2;
            int result = key.compareTo(stopWords[mid]);
            if(result < 0) hi = (mid-1);
            else if(result > 0) lo = mid+1;
            else return mid;
        }
        return -1;
    }

    //Tokenization
    public ArrayList<String> parse(File fileName) throws IOException {
        // step #5
        String[] tokens;
        ArrayList<String> pureTokens = new ArrayList<String>();
        ArrayList<String> stemms = new ArrayList<String>();

        Scanner scan = new Scanner(fileName);
        String allLines = new String();

        while(scan.hasNextLine()) {
            allLines += scan.nextLine().toLowerCase();  //case folding
        }

        //Tokenization
        tokens = allLines.split("[ '.,:\"#$%&?!()\\-\\*]+");
        //Remove stop words
        for(String token:tokens) {
            if(searchStopWord(token) == -1) {
                pureTokens.add(token);
            }
        }

        //Stemming
        Stemmer st = new Stemmer();
        for(String token:pureTokens) {
            st.add(token.toCharArray(), token.length());
            st.stem();
            stemms.add(st.toString());
            st = new Stemmer();
        }
        return stemms;
    }
    // Inverted Index
    public void invertedIndex (ArrayList<ArrayList<String>> result)  {
        termList = new ArrayList<String>();
        //docLists = new  ArrayList<int[]>();  //LBE02
        docLists = new  ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> docList;

        for(int i=0;i<result.size();i++) {
            ArrayList<String> temp = result.get(i); //tokenization
            for(String word:temp) {
                if(!termList.contains(word)) {
                    termList.add(word);
                    //int[] docList = new int[myDocs.length]; //LBE02
                    docList = new  ArrayList<Integer>();
                    //docList[i] = 1;
                    docList.add(i);
                    docLists.add(docList);
                }
                else {
                    int index = termList.indexOf(word);
                    //int[] docList = docLists.remove(index); //LBE02
                    docList = docLists.get(index);
                    //docList[i] = 1;
                    if(!docList.contains(i)) {
                        docList.add(i);
                        docLists.set(index,docList);
                    }
                }
            }
        }
        String resultDocStr = "";
        for(int i=0;i<termList.size();i++) {
            resultDocStr += String.format("%-15s", termList.get(i));
            ArrayList<Integer> doc = docLists.get(i);

            for(int j=0;j<doc.size();j++) {

                //outputString += docList[j] + "\t";
                resultDocStr += doc.get(j) + "\t";
            }
            resultDocStr += "\n";
        }
        System.out.println(resultDocStr);
    }

    public void searchTerm(String term){
        System.out.println("Input term is: "+term);

        if(termList.contains(term)){
            int index= termList.indexOf(term);
            docList=docLists.get(index);
            for (Integer integer : docList) {
                if (integer == 0) {
                    System.out.println("Term is in 1.txt");
                } else if (integer == 1) {
                    System.out.println("Term is in 2.txt");
                } else if (integer == 2) {
                    System.out.println("Term is in 3.txt");
                } else if (integer == 3) {
                    System.out.println("Term is in 4.txt");
                } else if (integer == 4) {
                    System.out.println("Term is in 5.txt");
                } else {
                    System.out.println("Term not present");
                }
            }

        }else {
            System.out.println("Term not present");
        }
    }
// AND Task Task 2
    public void andTask(String s1, String s2){

        if(termList.contains(s1) && termList.contains(s2)){
            System.out.println("Checking for: "+s1+" and "+s2);
            Boolean flag = false;
            int index1 = termList.indexOf(s1);
            int index2 = termList.indexOf(s2);
            ArrayList<Integer> doc1 = docLists.get(index1);
            ArrayList<Integer> doc2 = docLists.get(index2);

            for (int i = 0; i < doc1.size();i++){
                for(int j = 0; j < doc2.size();j++){
                    if(doc1.get(i).equals(doc2.get(j))) {
                        System.out.println("Both the terms are present in " + (doc1.get(i)+1));
                        flag = true;
                    }
                }
            }
            if(!flag){
                System.out.println("Both the terms are not present together in any documents");
            }

        }
        else {
            System.out.println("Both the terms are not present in any documents"); ;
        }
    }
// OR Task Task 3
    public void orTask(String s1, String s2){

        if(termList.contains(s1) && termList.contains(s2)){
            System.out.println("Checking for: "+s1+" or "+s2);
            Boolean flag = false;
            int index1 = termList.indexOf(s1);
            int index2 = termList.indexOf(s2);
            ArrayList<Integer> doc1 = docLists.get(index1);
            ArrayList<Integer> doc2 = docLists.get(index2);

            for (int i = 0; i < doc1.size();i++){
                System.out.println("First term is present in " + (doc1.get(i)+1));
                for(int j = 0; j < doc2.size();j++){
                    if(!doc1.get(i).equals(doc2.get(j))) {
                        System.out.println("Second term is present in " + (doc2.get(j)+1));
                        flag = true;
                    }
                    else{
                        System.out.println("Both the terms are present in " + (doc1.get(i)+1));
                    }
                }
            }
        }
        else {
            System.out.println("Both the terms are not present in any documents"); ;
        }
    }
    //And And Task4
    public void andAndTask(String s1, String s2, String s3){

        if(termList.contains(s1) && termList.contains(s2) && termList.contains(s3)){
            System.out.println("Checking for: "+s1+" and "+s2);
            Boolean flag = false;
            int index1 = termList.indexOf(s1);
            int index2 = termList.indexOf(s2);
            int index3 = termList.indexOf(s3);



            ArrayList<Integer> doc1 = docLists.get(index1);
            ArrayList<Integer> doc2 = docLists.get(index2);
            ArrayList<Integer> doc3 = docLists.get(index3);

            for (int i = 0; i < doc1.size();i++) {
                for (int j = 0; j < doc2.size(); j++) {
                    for (int k = 0; k < doc3.size(); k++) {

                        if(doc1.get(i).equals(doc2.get(k))) {
                            if (doc1.get(j).equals(doc2.get(k))) {
                                System.out.println("The three terms are present in " + (doc1.get(i) + 1));
                                flag = true;
                            }
                        }
                    }
                }
            }
            if(!flag){
                System.out.println("The three terms are not present together in any documents");
            }
        }
        else {
            System.out.println("The three terms are not present in any documents"); ;
        }
    }


    public static void main(String[] args) throws IOException {
        //ParserB p = new ParserB();
        Parser p = new Parser();
        String target_dir = "./DocData";
        File dir = new File(target_dir);
        File[] files = dir.listFiles();
        Arrays.sort(files);
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        for (File file : files) {
            if(file.isFile()) {
                try {

                    //    File file = new File("1.txt");
                    //    // File stopwords = new File("stopwords.txt");
                    ArrayList<String>stemmed  = p.parse(file);
                    result.add(stemmed);
                    // for(String st:stemmed) {
                    //    System.out.println(st);

                    // }
                }
                catch(IOException ioe) {
                    ioe.printStackTrace();
                }
            }
//      System.out.println("End of file");
        }
        p.invertedIndex(result);
        System.out.println(p.termList);
        p.searchTerm("coupl");
        p.andTask("world", "dream");
        p.orTask("chase","disappear");
        p.andAndTask("reason", "fall", "magic");
//        System.out.println(p.termList.contains("magic"));
        //Step #4
        System.out.println("Stop word: " + p.searchStopWord("are"));
        System.out.println("Stop word: " + p.searchStopWord("into"));
        System.out.println("Stop word: " + p.searchStopWord("aa"));

        // Step #6
//        try {
//            File file = new File("StemTest.txt");
//            ArrayList<String> stemmed = p.parse(file);
//
//            for(String st:stemmed) {
//                System.out.println(st);
//            }
//
//        }
//        catch(IOException ioe) {
//            ioe.printStackTrace();
//        }




    }
}
