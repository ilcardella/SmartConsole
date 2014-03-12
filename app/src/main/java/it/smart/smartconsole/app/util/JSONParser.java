package it.smart.smartconsole.app.util;

import it.smart.smartconsole.app.data.Macchina;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

public class JSONParser {

    static InputStream is = null;
    static JSONArray jArray = null;
    static String json = "";

    public JSONParser() {

    }

    public JSONArray getJSONFromUrl(String url) {

        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        try {
            jArray = new JSONArray(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        return jArray;
    }

    public static String buildJSONLogStringFromMacchina(Macchina m){
        String line = "\"{\'idMacchina\': "+m.getIdMacchina()+",\'oreFunzionamento\': "+m.getOreFunzionamento()+",\'velocita\': "+m.getVelocita()+",\'pezziProdotti\': "+m.getPezziProdotti()+",\'codiceAllarme\': "+m.getCodiceAllarme()+",\'energiaAssorbita\': "+m.getEnergiaAssorbita()+",\'tempMotore\': "+m.getTempMotore()+",\'tempSlitta\': "+m.getTempSlitta()+"}\"";
        return line;
    }
}