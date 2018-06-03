package com.example.praneethguduguntla.sentinel;

import android.telephony.SmsManager;
import android.widget.Toast;

import java.util.ArrayList;

public class SendAll {

    private ArrayList<String> phoneNumbers;
    private String message;

    private static final int PERMISSIONS_SEND_SMS = 0;
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 0;


    public SendAll(ArrayList<String> oPhoneNumbers, String oMessage){
        phoneNumbers = oPhoneNumbers;
        message = oMessage;
    }

    public void sendAll(){
        for(int i = 0; i < phoneNumbers.size(); i++){
            sendSMS(phoneNumbers.get(i), message);
        }
    }


    public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
