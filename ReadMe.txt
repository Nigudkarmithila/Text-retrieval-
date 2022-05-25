Instructions for TweetsVSM:
ReadMe.txt
Author: Team_3


Use the TweetsVSM Folder and open the folder in your Java Compiler like ( IntelliJ )


Below are the steps to successfully run the sample project in your system:

•	Open the VSMTweets Folder > src > TweetsVSM.java or SimpleUI.java

•	External Libraries folder contains add two jar files common-lang3.jar and opencsv-5.6.jar

•	TweetsVSM also contains stopwords.txt and Tweet.csv files in src folder.

•	Before running, add two jar files common-lang3.jar and opencsv-5.6.jar to Files > Project Structures > Dependencies. ( For adding dependencies in JGrasp, 	Settings > Path/ClassPath > Workspace > ClassPath > add two jar files path individually from external libraries folder. )

•	Run the SimpleUI.java program.

•	These are the external libraries used to read csv files in java.

•	All the CSV Files are inside the src folder along with SimpleUI.java and TweetsVSM.java

•	We have used a Tweets data here containing 15000 rows. 

•	We have used Stopwords file to remove stop words as well.

•	All the input for Tweets and stop words have functions but are called in the SimpleUI.java

•	This program gives VSM tf-idf, and Search Query Test Cases for ranked search in our JFrame GUI using cosine similarity for tweets.

•	These similar tweets will be filtered and used to trace the usernames who wrote this tweet, then they will be filtered among their verification status to detect which user is spreading misinformation.

•	The GUI also has summary statistics for each search query when you click on the button.

•	The Ranked output has been sorted in descending order so that we can know which tweet is the most like the test cases we have in the input.

•	Check the pptx file for more detail overview of the project with screenshots.






