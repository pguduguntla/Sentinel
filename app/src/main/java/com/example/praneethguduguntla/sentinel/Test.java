package com.example.praneethguduguntla.sentinel;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;

import static android.content.Context.MODE_PRIVATE;

public class Test {

    private String fileName;
    private String school;
    private Boolean isLoggedIn;

    public Test(){
        isLoggedIn = false;
        fileName = null;
    }
//
//    public static void main(String[] args){
//        Test t = new Test();
//        t.write(false);
//        t.read();
//        System.out.println(t.getSchool());
//        System.out.println(t.isLoggedIn);
//    }


    public void write() {

        try {
            FileWriter fw = new FileWriter(new File(fileName));
            fw.write(school + "\n");
            fw.write(isLoggedIn.toString());
            fw.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void read() {
        Scanner scan = null;
        try {
            scan = new Scanner(new File(fileName));
            this.school = scan.nextLine();
            String isLoggedInString = scan.nextLine();

            if(isLoggedInString.trim().equalsIgnoreCase("true")){
                isLoggedIn = true;
            }
            else{
                isLoggedIn = false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getSchool(){
        return this.school;
    }

    public boolean isLoggedIn(){
        return this.isLoggedIn;
    }

    public void setLoggedIn(boolean oLoggedIn){
        isLoggedIn = oLoggedIn;
    }

    public void setSchool(String oSchool){
        school = oSchool;
    }

}
