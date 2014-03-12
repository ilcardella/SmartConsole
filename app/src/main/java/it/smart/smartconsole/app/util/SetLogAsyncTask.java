package it.smart.smartconsole.app.util;

import it.smart.smartconsole.app.data.Macchina;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

public class SetLogAsyncTask extends AsyncTask<List<Macchina>, Void, Void> {

    private static final String URL_SET_LOG = "http://smartmanager.apphb.com/SmartService.svc/SetLog";
    private List<Macchina> list = new ArrayList<Macchina>();

    @Override
    protected Void doInBackground(List<Macchina>... params) {

        list = params[0];
        for(int i=0; i<list.size(); i++){
            String json = JSONParser.buildJSONLogStringFromMacchina(list.get(i));
            try {

                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(URL_SET_LOG);
                StringEntity se = new StringEntity(json);
                httpPost.setEntity(se);
                httpPost.setHeader("Content-type", "application/json");

                httpClient.execute(httpPost);

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
