
package com.journaldev.files; 
 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Hangman implements IHangman {
     static String name ;

     public String Read() {

            Scanner myObj = new Scanner(System.in);
            System.out.println("enter the file name with (.txt)");
            name = myObj.nextLine();

            return name;

        }


        public String SetDictionary(){

            String filename = Read();
            BufferedReader reader = null;
            StringBuilder stringBuilder = new StringBuilder(filename);

            try {
                reader = new BufferedReader(new FileReader(name));
                String line = null;
                String ls = System.getProperty("line.separator");
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                    stringBuilder.append(ls);
                }

                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null)
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            return stringBuilder.toString();

        }

        public String selectRandomSecretWord() {

            String list  = SetDictionary();

            String [] dic = list.split("\n");

            Random r = new Random();

            int randomNumber=r.nextInt((dic.length)-1);

            String word = dic[randomNumber];
            
            for(int a = 0 ; a < word.length()-1;a++) {
            	
            	if(!Character.isAlphabetic(word.charAt(a))) {
            		
            		System.out.println("buggy secret word");
            		System.exit(0);
            	}
            }


            return word;


        }
        
        static Hangman store = new Hangman();
        static String secret = store.selectRandomSecretWord();
        static String oldkeyword = new String(new char[secret.length()-1]).replace("\0", "_");
        static int Checker = 0;
        static int winner = secret.length()-1;

        public  String guess(Character c){


            String keyword = "";
            int winner = 0;



            for (int i = 0; i < secret.length()-1; i++) {
                if (secret.charAt(i) == c) {
                    keyword += c;

                }
                else if (oldkeyword.charAt(i) != '_') {

                    keyword += secret.charAt(i);

                }
                else {
                    keyword += "_";
                    winner++;
                }

            }

            if (oldkeyword.equals(keyword)) {
                Checker++;
                System.out.println("wrong ! your wrong answers are " + Checker);


            } else {

                oldkeyword = keyword;

            }

            if (winner == 0) {
                System.out.println("Correct! You win! The word was " + secret);
                return null;
            }

            if(Checker == MaxWrongNumber) {

                System.out.println("you lost ! you reached your limit of wrong answers");
                return null;
            }

            return keyword;

        }

        private int MaxWrongNumber;
        public  void setMaxWrongGuesses(Integer max) {

            MaxWrongNumber = max;
            Integer M = max;
            if(M == null) {

                MaxWrongNumber = 1;

            }


        }



        public static void main(String[] args) {


            Scanner myObj = new Scanner(System.in);
            Hangman test = new Hangman();
            System.out.println("enter the maximum number of fails");
            int max = myObj.nextInt();
            test.setMaxWrongGuesses(max);
            while (Checker < max && oldkeyword.contains("_")) {
                System.out.println("Guess any letter in the word");
                System.out.println(oldkeyword);
                char guess = myObj.next().charAt(0);
                String g = test.guess(guess);

            }
            myObj.close();


        }
    }