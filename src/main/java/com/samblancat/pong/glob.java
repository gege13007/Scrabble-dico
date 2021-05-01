package com.samblancat.pong;

import android.content.SharedPreferences;

import java.util.ArrayList;

//Variables Globales
public class glob {

    //Const dimensions de l'écran
    static int maxx = 319;  //this.getWidth();
    static int maxy = 319;  //this.getHeight();

    //Centre de l'écran
    static int cx, cy;

    //dernière position sauvée
    static public char[] car;

    static public SharedPreferences sharedPref;

    static public ArrayList<String> wordsok;

}