package com.journaldev.files;

public interface IHangman {


    public String SetDictionary();

    public String selectRandomSecretWord() throws Exception;

    public String guess(Character c);

    public void setMaxWrongGuesses(Integer max);


}