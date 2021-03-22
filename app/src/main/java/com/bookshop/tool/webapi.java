package com.bookshop.tool;


import android.media.MediaParser;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class webapi {
    public static String localHost = "http://10.0.2.2:5000/";
    public static boolean login(String user,String pwd){

        try{
            JSONObject dlxx=new JSONObject();
            dlxx.put("user_no",user);
            dlxx.put("user_pwd",pwd);
            URL url=new URL(localHost+"api/login");
            HttpURLConnection urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("accept", "application/json");
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type","application/json");
            urlConnection.setDefaultUseCaches(true);
            Log.d("TAG", "login: "+dlxx.toString());
            OutputStream outputStreamWriter=urlConnection.getOutputStream();
            outputStreamWriter.write(dlxx.toString().getBytes());
            outputStreamWriter.flush();
            outputStreamWriter.close();
            if (urlConnection.getResponseCode()==HttpURLConnection.HTTP_OK)
            {

                InputStreamReader inputStreamReader=new InputStreamReader(urlConnection.getInputStream());
                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
                StringBuilder sb=new StringBuilder();
                String s="";
                while ((s=bufferedReader.readLine())!=null)
                {
                    sb.append(s);
                }
                //json解
                JSONObject jsonObject=new JSONObject(sb.toString());
                if (jsonObject.getBoolean("IsSuccess"))
                {
                    return true;
                }
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;

    }
}