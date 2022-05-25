import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 *
 *  SimpleUI  Class for Creating a GUI for the Code
 *
 */

public class SimpleUI extends JFrame  {
    //Initializing Components
    JLabel lb, lb5, lb1, lb2, lb3, lb4, lb6, lb7, lb8, lb9, lb10, lb11, l1, l2, l3, l4, l6, l7, l8, l9, l10, l11, b1, b2, b3, b4, b6, b7, b8, b9, b10, b11;
    JLabel lbl;
    JLabel totalTweets, totalTerm, simTotal, Verified, Unverified, Percent;
    JLabel Percent2;
    JTextField txt1, txt2, txt3, txt4, txt5, txt6;
    JTextField txt7;
    JTextField tf5, tf1, tf2,tf3,tf4,tf6,tf7,tf8,tf9,tf10,tf11, t1, t2,t3,t4,t6,t7,t8,t9,t10,t11, f1, f2,f3,f4,f6,f7,f8,f9,f10,f11;
    JButton btn, btn2;
    TweetsVSM TweetFile; // Calling the TweetsVSM class from the other Java File

    /**
     *
     *Creating Constructor for initializing JFrame components
     *
     */
    SimpleUI() {
        //Providing Title
        super("Detection of Vaccine Misinformation Tweets: ");
        lb5 = new JLabel("Enter Search Query:");
        lb5.setFont(new Font("Serif",  Font.BOLD, 15));
        lb5.setForeground(Color.white);
        lb5.setBounds(20, 20, 200, 20);

        lbl = new JLabel("Detection of Vaccine Misinformation Tweets");
        lbl.setFont(new Font("Serif",  Font.BOLD, 50));
        lbl.setForeground(Color.white);
        lbl.setBounds(400, 20, 1000, 40);

        tf5 = new JTextField(20);
        tf5.setBounds(170, 20, 200, 20);
        btn = new JButton("Submit");
        btn.setBounds(20, 50, 100, 20);
        btn.addActionListener(this::Submit);

        btn2 = new JButton("Summary Statistics");
        btn2.setBounds(170, 50, 200, 20);
        btn2.addActionListener(this::Summary);
        btn2.setVisible(false);

        lb = new JLabel("Fetching Search Query Result: ");
        lb.setBounds(20, 80, 450, 30);
        lb.setForeground(Color.white);
        lb.setFont(new Font("Serif",  Font.BOLD, 20));
        setVisible(true);
        getContentPane().setBackground(Color.darkGray);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1450, 820);

        // Creating Text field to store all the output of Username of Tweets
        lb1 = new JLabel("Username 1: ");
        lb1.setForeground(Color.white);
        lb1.setBounds(20, 120, 100, 55);
        tf1 = new JTextField(50);
        tf1.setBounds(130, 120, 200, 55);
        lb2 = new JLabel("Username 2: ");
        lb2.setForeground(Color.white);
        lb2.setBounds(20, 180, 100, 55);
        tf2 = new JTextField(100);
        tf2.setBounds(130, 180, 200, 55);
        lb3 = new JLabel("Username 3: ");
        lb3.setForeground(Color.white);
        lb3.setBounds(20, 240, 100, 55);
        tf3 = new JTextField(50);
        tf3.setBounds(130, 240, 200, 55);
        lb4 = new JLabel("Username 4: ");
        lb4.setForeground(Color.white);
        lb4.setBounds(20, 300, 100, 55);
        tf4 = new JTextField(50);
        tf4.setBounds(130, 300, 200, 55);
        lb6 = new JLabel("Username 5: ");
        lb6.setForeground(Color.white);
        lb6.setBounds(20, 360, 100, 55);
        tf6 = new JTextField(50);
        tf6.setBounds(130, 360, 200, 55);
        lb7 = new JLabel("Username 6: ");
        lb7.setForeground(Color.white);
        lb7.setBounds(20, 420, 100, 55);
        tf7 = new JTextField(100);
        tf7.setBounds(130, 420, 200, 55);
        lb8 = new JLabel("Username 7: ");
        lb8.setForeground(Color.white);
        lb8.setBounds(20, 480, 100, 55);
        tf8 = new JTextField(50);
        tf8.setBounds(130, 480, 200, 55);
        lb9 = new JLabel("Username 8: ");
        lb9.setForeground(Color.white);
        lb9.setBounds(20, 540, 100, 55);
        tf9 = new JTextField(50);
        tf9.setBounds(130, 540, 200, 55);
        lb10 = new JLabel("Username 9: ");
        lb10.setForeground(Color.white);
        lb10.setBounds(20, 600, 100, 55);
        tf10 = new JTextField(50);
        tf10.setBounds(130, 600, 200, 55);
        lb11 = new JLabel("Username 10: ");
        lb11.setForeground(Color.white);
        lb11.setBounds(20, 660, 100, 55);
        tf11 = new JTextField(50);
        tf11.setBounds(130, 660, 200, 55);


        // Creating Text Field to Show the Output of If the Usernames are Verified Or Not
        l1 = new JLabel("Verified?: ");
        l1.setForeground(Color.white);
        l1.setBounds(350, 120, 100, 55);
        t1 = new JTextField(50);
        t1.setBounds(410, 120, 100, 55);
        l2 = new JLabel("Verified?: ");
        l2.setForeground(Color.white);
        l2.setBounds(350, 180, 100, 55);
        t2 = new JTextField(100);
        t2.setBounds(410, 180, 100, 55);
        l3 = new JLabel("Verified?: ");
        l3.setForeground(Color.white);
        l3.setBounds(350, 240, 100, 55);
        t3 = new JTextField(50);
        t3.setBounds(410, 240, 100, 55);
        l4 = new JLabel("Verified?: ");
        l4.setForeground(Color.white);
        l4.setBounds(350, 300, 100, 55);
        t4 = new JTextField(50);
        t4.setBounds(410, 300, 100, 55);
        l6 = new JLabel("Verified?: ");
        l6.setForeground(Color.white);
        l6.setBounds(350, 360, 100, 55);
        t6 = new JTextField(50);
        t6.setBounds(410, 360, 100, 55);
        l7 = new JLabel("Verified?: ");
        l7.setForeground(Color.white);
        l7.setBounds(350, 420, 100, 55);
        t7 = new JTextField(100);
        t7.setBounds(410, 420, 100, 55);
        l8 = new JLabel("Verified?: ");
        l8.setForeground(Color.white);
        l8.setBounds(350, 480, 100, 55);
        t8 = new JTextField(50);
        t8.setBounds(410, 480, 100, 55);
        l9 = new JLabel("Verified?: ");
        l9.setForeground(Color.white);
        l9.setBounds(350, 540, 100, 55);
        t9 = new JTextField(50);
        t9.setBounds(410, 540, 100, 55);
        l10 = new JLabel("Verified?: ");
        l10.setForeground(Color.white);
        l10.setBounds(350, 600, 100, 55);
        t10 = new JTextField(50);
        t10.setBounds(410, 600, 100, 55);
        l11 = new JLabel("Verified?: ");
        l11.setForeground(Color.white);
        l11.setBounds(350, 660, 100, 55);
        t11 = new JTextField(50);
        t11.setBounds(410, 660, 100, 55);

        // Creating a Text Area to display all the loaded tweets
        b1 = new JLabel("Tweet: ");
        b1.setForeground(Color.white);
        b1.setBounds(525, 120, 100, 55);
        f1 = new JTextField(50);
        f1.setBounds(570, 120, 850, 55);
        b2 = new JLabel("Tweet: ");
        b2.setForeground(Color.white);
        b2.setBounds(525, 180, 100, 55);
        f2 = new JTextField(50);
        f2.setBounds(570, 180, 850, 55);
        b3 = new JLabel("Tweet: ");
        b3.setForeground(Color.white);
        b3.setBounds(525, 240, 100, 55);
        f3 = new JTextField(50);
        f3.setBounds(570, 240, 850, 55);
        b4 = new JLabel("Tweet: ");
        b4.setForeground(Color.white);
        b4.setBounds(525, 300, 100, 55);
        f4 = new JTextField(50);
        f4.setBounds(570, 300, 850, 55);
        b6 = new JLabel("Tweet: ");
        b6.setForeground(Color.white);
        b6.setBounds(525, 360, 100, 55);
        f6 = new JTextField(50);
        f6.setBounds(570, 360, 850, 55);
        b7 = new JLabel("Tweet: ");
        b7.setForeground(Color.white);
        b7.setBounds(525, 420, 100, 55);
        f7 = new JTextField(50);
        f7.setBounds(570, 420, 850, 55);
        b8 = new JLabel("Tweet: ");
        b8.setForeground(Color.white);
        b8.setBounds(525, 480, 100, 55);
        f8 = new JTextField(50);
        f8.setBounds(570, 480, 850, 55);
        b9 = new JLabel("Tweet: ");
        b9.setForeground(Color.white);
        b9.setBounds(525, 540, 100, 55);
        f9 = new JTextField(50);
        f9.setBounds(570, 540, 850, 55);
        b10 = new JLabel("Tweet: ");
        b10.setForeground(Color.white);
        b10.setBounds(525, 600, 100, 55);
        f10 = new JTextField(50);
        f10.setBounds(570, 600, 850, 55);
        b11 = new JLabel("Tweet: ");
        b11.setForeground(Color.white);
        b11.setBounds(525, 660, 100, 55);
        f11 = new JTextField(50);
        f11.setBounds(570, 660, 850, 55);


        // Creating Text field to store all the output of Username of Tweets
        totalTweets = new JLabel("Total Tweets: ");
        totalTweets.setForeground(Color.white);
        totalTweets.setBounds(20, 120, 200, 55);
        txt1 = new JTextField(50);
        txt1.setBounds(230, 120, 100, 55);
        totalTerm = new JLabel("Total Terms: ");
        totalTerm.setForeground(Color.white);
        totalTerm.setBounds(20, 180, 200, 55);
        txt2 = new JTextField(100);
        txt2.setBounds(230, 180, 100, 55);
        simTotal = new JLabel("Total Similar Tweets: ");
        simTotal.setForeground(Color.white);
        simTotal.setBounds(20, 240, 200, 55);
        txt3 = new JTextField(50);
        txt3.setBounds(230, 240, 100, 55);
        Verified = new JLabel("Total Verified User: ");
        Verified.setForeground(Color.white);
        Verified.setBounds(20, 300, 200, 55);
        txt4 = new JTextField(50);
        txt4.setBounds(230, 300, 100, 55);
        Unverified = new JLabel("Total Unverified User: ");
        Unverified.setForeground(Color.white);
        Unverified.setBounds(20, 360, 200, 55);
        txt5 = new JTextField(50);
        txt5.setBounds(230, 360, 100, 55);
        Percent = new JLabel("Detected Tweets Percent: ");
        Percent.setForeground(Color.white);
        Percent.setBounds(20, 420, 200, 55);
        txt6 = new JTextField(100);
        txt6.setBounds(230, 420, 100, 55);
        Percent2 = new JLabel("Unverified Percent: ");
        Percent2.setForeground(Color.white);
        Percent2.setBounds(20, 480, 200, 55);
        txt7 = new JTextField(100);
        txt7.setBounds(230, 480, 100, 55);

        setLayout(null);

        //Adding components to the JFrame
        add(lb5);add(tf5); add(lbl); add(btn);add(btn2);add(lb);add(lb1);add(tf1);add(lb2);
        add(tf2);add(lb3);add(tf3);add(lb4);add(tf4);add(lb6);add(tf6);
        add(lb7);add(tf7);add(lb8);add(tf8);add(lb9);add(tf9);add(lb10);add(tf10);add(lb11);add(tf11);

        add(l1);add(t1);add(l2);
        add(t2);add(l3);add(t3);add(l4);add(t4);add(l6);add(t6);
        add(l7);add(t7);add(l8);add(t8);add(l9);add(t9);add(l10);add(t10);add(l10);add(t11);add(l11);

        add(totalTweets); add(txt1); add(totalTerm); add(txt2); add(simTotal); add(txt3);
        add(Verified); add(txt4); add(Unverified); add(txt5); add(Percent); add(txt6); add(Percent2); add(txt7);

        add(b1);add(f1);add(b2);
        add(f2);add(b3);add(f3);add(b4);add(f4);add(b6);add(f6);
        add(b7);add(f7);add(b8);add(f8);add(b9);add(f9);add(b10);add(f10);add(b10);add(f11);add(b11);


        // Setting the output to be uneditable and only visible after the button action is complete
        tf1.setEditable(false);tf2.setEditable(false);tf3.setEditable(false);tf4.setEditable(false);
        tf6.setEditable(false);tf7.setEditable(false);tf8.setEditable(false);tf9.setEditable(false);
        tf10.setEditable(false);tf11.setEditable(false);

        tf1.setVisible(false);tf2.setVisible(false);tf3.setVisible(false);tf4.setVisible(false);
        tf6.setVisible(false);tf7.setVisible(false);tf8.setVisible(false);tf9.setVisible(false);
        tf10.setVisible(false);tf11.setVisible(false);

        lb1.setVisible(false);lb2.setVisible(false);lb3.setVisible(false);lb4.setVisible(false);
        lb6.setVisible(false);lb7.setVisible(false);lb8.setVisible(false);lb9.setVisible(false);
        lb10.setVisible(false);lb11.setVisible(false);

        t1.setEditable(false);t2.setEditable(false);t3.setEditable(false);t4.setEditable(false);
        t6.setEditable(false);t7.setEditable(false);t8.setEditable(false);t9.setEditable(false);
        t10.setEditable(false);t11.setEditable(false);

        b1.setVisible(false);b2.setVisible(false);b3.setVisible(false);b4.setVisible(false);
        b6.setVisible(false);b7.setVisible(false);b8.setVisible(false);b9.setVisible(false);
        b10.setVisible(false);b11.setVisible(false);

        t1.setVisible(false);t2.setVisible(false);t3.setVisible(false);t4.setVisible(false);
        t6.setVisible(false);t7.setVisible(false);t8.setVisible(false);t9.setVisible(false);
        t10.setVisible(false);t11.setVisible(false);

        f1.setEditable(false);f2.setEditable(false);f3.setEditable(false);f4.setEditable(false);
        f6.setEditable(false);f7.setEditable(false);f8.setEditable(false);f9.setEditable(false);
        f10.setEditable(false);f11.setEditable(false);

        l1.setVisible(false);l2.setVisible(false);l3.setVisible(false);l4.setVisible(false);
        l6.setVisible(false);l7.setVisible(false);l8.setVisible(false);l9.setVisible(false);
        l10.setVisible(false);l11.setVisible(false);

        f1.setVisible(false);f2.setVisible(false);f3.setVisible(false);f4.setVisible(false);
        f6.setVisible(false);f7.setVisible(false);f8.setVisible(false);f9.setVisible(false);
        f10.setVisible(false);f11.setVisible(false);

        txt1.setEditable(false);txt2.setEditable(false);txt3.setEditable(false);
        txt4.setEditable(false);txt5.setEditable(false);txt6.setEditable(false); txt7.setEditable(false);

        txt1.setVisible(false);txt3.setVisible(false);txt5.setVisible(false);
        txt2.setVisible(false);txt4.setVisible(false);txt6.setVisible(false); txt7.setVisible(false);

        totalTerm.setVisible(false);totalTweets.setVisible(false);simTotal.setVisible(false);
        Verified.setVisible(false);Unverified.setVisible(false);Percent.setVisible(false); Percent2.setVisible(false);

    }

    /**
     * Action Getting Performed after clicking the Submit Button after taking input for search query
     * @param e  Action event to be performed
     *
     */
    public void Submit(ActionEvent e) {

        // Getting Input Search Query Sting from the JFrame Console
        String str = tf5.getText();

        txt1.setVisible(false);txt3.setVisible(false);txt5.setVisible(false);
        txt2.setVisible(false);txt4.setVisible(false);txt6.setVisible(false);txt7.setVisible(false);

        totalTerm.setVisible(false);totalTweets.setVisible(false);simTotal.setVisible(false);
        Verified.setVisible(false);Unverified.setVisible(false);Percent.setVisible(false); Percent2.setVisible(false);

        // Setting all the text fields to be visible after the set action is performed
        tf1.setVisible(true);tf2.setVisible(true);tf3.setVisible(true);tf4.setVisible(true);
        tf6.setVisible(true);tf7.setVisible(true);tf8.setVisible(true);tf9.setVisible(true);
        tf10.setVisible(true);tf11.setVisible(true);

        f1.setVisible(true);f2.setVisible(true);f3.setVisible(true);f4.setVisible(true);
        f6.setVisible(true);f7.setVisible(true);f8.setVisible(true);f9.setVisible(true);
        f10.setVisible(true);f11.setVisible(true);

        t1.setVisible(true);t2.setVisible(true);t3.setVisible(true);t4.setVisible(true);
        t6.setVisible(true);t7.setVisible(true);t8.setVisible(true);t9.setVisible(true);
        t10.setVisible(true);t11.setVisible(true);

        lb1.setVisible(true);lb2.setVisible(true);lb3.setVisible(true);lb4.setVisible(true);
        lb6.setVisible(true);lb7.setVisible(true);lb8.setVisible(true);lb9.setVisible(true);
        lb10.setVisible(true);lb11.setVisible(true);

        l1.setVisible(true);l2.setVisible(true);l3.setVisible(true);l4.setVisible(true);
        l6.setVisible(true);l7.setVisible(true);l8.setVisible(true);l9.setVisible(true);
        l10.setVisible(true);l11.setVisible(true);

        b1.setVisible(true);b2.setVisible(true);b3.setVisible(true);b4.setVisible(true);
        b6.setVisible(true);b7.setVisible(true);b8.setVisible(true);b9.setVisible(true);
        b10.setVisible(true);b11.setVisible(true);

        btn2.setVisible(true);

        // Loading the Tweets CSV file to the program
        java.util.List<java.util.List<String>> result = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader("D:/Spring 22/Team#3 Final Project/TweetsVSM/src/Tweets.csv"))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                result.add(Arrays.asList(values));
            }
            // Exception if the file is not found
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException | CsvValidationException ex) {
            ex.printStackTrace();
        }
        // Assigning docs the input string of tweets from the CSV File.
        String[] docs = new String[result.size()];
        for (int y = 0; y < result.size(); y++){
            // reading all tweets string as lowercase making it easier to compare term
            docs[y] = result.get(y).get(0).toLowerCase();
        }

        // Loading the Username CSV file to the program
        java.util.List<java.util.List<String>> Username = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader("D:/Spring 22/Team#3 Final Project/TweetsVSM/src/Username.csv"))) {
            String[] UValues;
            while ((UValues = csvReader.readNext()) != null) {
                Username.add(Arrays.asList(UValues));
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (CsvValidationException ex) {
            ex.printStackTrace();
        }
        // Assigning UDocs the input Username string of tweets from the CSV File.
        String[] UDocs = new String[Username.size()];
        for (int y = 0; y < Username.size(); y++){
            UDocs[y] = Username.get(y).get(0);
        }

        // Loading the Verified Status CSV file to the program
        List<List<String>> Verified = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader("D:/Spring 22/Team#3 Final Project/TweetsVSM/src/Verified.csv"))) {
            String[] VerValues;
            while ((VerValues = csvReader.readNext()) != null) {
                Verified.add(Arrays.asList(VerValues));
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (CsvValidationException ex) {
            ex.printStackTrace();
        }
        // Assigning VerDocs the input Verified Status string of Username from the CSV File.
        String[] VerDocs = new String[Verified.size()];
        for (int y = 0; y < Verified.size(); y++){
            VerDocs[y] = Verified.get(y).get(0);
        }

        // Instantiating Objecting of type TweetsVSM Class
        TweetFile = new TweetsVSM(docs,UDocs,VerDocs);

        // Calling the Result Method from the TweetsVSM Class to get all the to 10 similar Username
        ArrayList s3 = TweetFile.Result(str);

        // Adding the top 10 similar Usernames to each text field in Console
        tf1.setText((String) s3.get(0));tf2.setText((String) s3.get(1));tf3.setText((String) s3.get(2));
        tf4.setText((String) s3.get(3));tf6.setText((String) s3.get(4));tf7.setText((String) s3.get(5));
        tf8.setText((String) s3.get(6));tf9.setText((String) s3.get(7));tf10.setText((String) s3.get(8));
        tf11.setText((String) s3.get(9));

        // Calling the ResultVerification Method from the TweetsVSM Class to get all the verified status of usernames
        ArrayList s4 = TweetFile.ResultVerification(str);

        // Adding them to text fields
        t1.setText((String) s4.get(0));t2.setText((String) s4.get(1));t3.setText((String) s4.get(2));
        t4.setText((String) s4.get(3));t6.setText((String) s4.get(4));t7.setText((String) s4.get(5));
        t8.setText((String) s4.get(6));t9.setText((String) s4.get(7));t10.setText((String) s4.get(8));
        t11.setText((String) s4.get(9));

        // Calling the Result Method from the TweetsVSM Class to get all the to 10 similar tweets
        ArrayList s5 = TweetFile.ResultTweets(str);

        // Adding them to text fields
        f1.setText((String) s5.get(0));f2.setText((String) s5.get(1));f3.setText((String) s5.get(2));
        f4.setText((String) s5.get(3));f6.setText((String) s5.get(4));f7.setText((String) s5.get(5));
        f8.setText((String) s5.get(6));f9.setText((String) s5.get(7));f10.setText((String) s5.get(8));
        f11.setText((String) s5.get(9));
    }


    /**
     * Action Getting Performed after clicking the Submit Button after taking input for search query
     * @param e  Action event to be performed
     *
     */
    public void Summary(ActionEvent e) {
        // Getting Input Search Query Sting from the JFrame Console
        String str = tf5.getText();

        tf1.setVisible(false);tf2.setVisible(false);tf3.setVisible(false);tf4.setVisible(false);
        tf6.setVisible(false);tf7.setVisible(false);tf8.setVisible(false);tf9.setVisible(false);
        tf10.setVisible(false);tf11.setVisible(false);

        lb1.setVisible(false);lb2.setVisible(false);lb3.setVisible(false);lb4.setVisible(false);
        lb6.setVisible(false);lb7.setVisible(false);lb8.setVisible(false);lb9.setVisible(false);
        lb10.setVisible(false);lb11.setVisible(false);

        b1.setVisible(false);b2.setVisible(false);b3.setVisible(false);b4.setVisible(false);
        b6.setVisible(false);b7.setVisible(false);b8.setVisible(false);b9.setVisible(false);
        b10.setVisible(false);b11.setVisible(false);

        t1.setVisible(false);t2.setVisible(false);t3.setVisible(false);t4.setVisible(false);
        t6.setVisible(false);t7.setVisible(false);t8.setVisible(false);t9.setVisible(false);
        t10.setVisible(false);t11.setVisible(false);

        l1.setVisible(false);l2.setVisible(false);l3.setVisible(false);l4.setVisible(false);
        l6.setVisible(false);l7.setVisible(false);l8.setVisible(false);l9.setVisible(false);
        l10.setVisible(false);l11.setVisible(false);

        f1.setVisible(false);f2.setVisible(false);f3.setVisible(false);f4.setVisible(false);
        f6.setVisible(false);f7.setVisible(false);f8.setVisible(false);f9.setVisible(false);
        f10.setVisible(false);f11.setVisible(false);

        txt1.setVisible(true);txt3.setVisible(true);txt5.setVisible(true);
        txt2.setVisible(true);txt4.setVisible(true);txt6.setVisible(true);txt7.setVisible(true);

        totalTerm.setVisible(true);totalTweets.setVisible(true);simTotal.setVisible(true);
        Verified.setVisible(true);Unverified.setVisible(true);Percent.setVisible(true); Percent2.setVisible(true);

        // Loading the Tweets CSV file to the program
        java.util.List<java.util.List<String>> result = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader("D:/Spring 22/Team#3 Final Project/TweetsVSM/src/Tweets.csv"))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                result.add(Arrays.asList(values));
            }
            // Exception if the file is not found
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException | CsvValidationException ex) {
            ex.printStackTrace();
        }
        // Assigning docs the input string of tweets from the CSV File.
        String[] docs = new String[result.size()];
        for (int y = 0; y < result.size(); y++){
            // reading all tweets string as lowercase making it easier to compare term
            docs[y] = result.get(y).get(0).toLowerCase();
        }

        // Loading the Username CSV file to the program
        java.util.List<java.util.List<String>> Username = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader("D:/Spring 22/Team#3 Final Project/TweetsVSM/src/Username.csv"))) {
            String[] UValues;
            while ((UValues = csvReader.readNext()) != null) {
                Username.add(Arrays.asList(UValues));
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (CsvValidationException ex) {
            ex.printStackTrace();
        }
        // Assigning UDocs the input Username string of tweets from the CSV File.
        String[] UDocs = new String[Username.size()];
        for (int y = 0; y < Username.size(); y++){
            UDocs[y] = Username.get(y).get(0);
        }

        // Loading the Verified Status CSV file to the program
        List<List<String>> Verified = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader("D:/Spring 22/Team#3 Final Project/TweetsVSM/src/Verified.csv"))) {
            String[] VerValues;
            while ((VerValues = csvReader.readNext()) != null) {
                Verified.add(Arrays.asList(VerValues));
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (CsvValidationException ex) {
            ex.printStackTrace();
        }
        // Assigning VerDocs the input Verified Status string of Username from the CSV File.
        String[] VerDocs = new String[Verified.size()];
        for (int y = 0; y < Verified.size(); y++){
            VerDocs[y] = Verified.get(y).get(0);
        }

        // Instantiating Objecting of type TweetsVSM Class
        TweetFile = new TweetsVSM(docs,UDocs,VerDocs);

        // Adding total tweet count
        int total = docs.length;
        String total1 = String.valueOf(total);
        txt1.setText(total1);

        // Adding total term count
        int totalterm = TweetFile.term().size();
        String total2 = String.valueOf(totalterm);
        txt2.setText(total2);

        // Adding total similar tweets
        int totalSimTweets = TweetFile.TweetsDetection(str).size();
        String total3 = String.valueOf(totalSimTweets);
        txt3.setText(total3);

        // Detected Tweets Percent
        float decpercent = ((float) totalSimTweets/(float)total)*100;
        System.out.println(decpercent);
        String total4 = decpercent +"%";
        txt6.setText(total4);

        // Total Verified User addition
        int cnt = 0;
        ArrayList s1 = TweetFile.VerificationDetection(str);
        for (int i=0; i<s1.size();i++){
            if (Objects.equals(s1.get(i).toString(), "True")){
                cnt = cnt+1;
            }
        }
        String total5 = String.valueOf(cnt);
        txt4.setText(total5);

        // Total Unverified User addiction
        int cnt1 = 0;
        ArrayList s2 = TweetFile.VerificationDetection(str);
        for (int i=0; i<s1.size();i++){
            if (Objects.equals(s1.get(i).toString(), "False")){
                cnt1 = cnt1+1;
            }
        }
        String total6 = String.valueOf(cnt1);
        txt5.setText(total6);

        // Unverified Percentage
        float percent1 = ((float) cnt1/(float)totalSimTweets)*100;
        String total7 = percent1 +"%";
        txt7.setText(total7);

    }

    /**
     * Implementing and Running the GUI
     * @param args  commandline input
     *
     */
    public static void main(String args[]) {
        new SimpleUI();
        //covid19 corona fake vaccination vaccine hoax (Sample Test Case)
        // Use this test case in the GUI search box
    }
}

