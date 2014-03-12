package it.smart.smartconsole.app.util;

import it.smart.smartconsole.app.data.Macchina;
import it.smart.smartconsole.app.util.JSONParser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class DownloadDataAsyncTask extends AsyncTask<Void, Void, List<Macchina>> {

    private static final String URL_GET_ALL = "http://smartmanager.apphb.com/SmartService.svc/GetAll";
    private List<Macchina> list = new ArrayList<Macchina>();

    @Override
    protected List<Macchina> doInBackground(Void... data) {

        JSONParser jParser = new JSONParser();

        JSONArray jsonArray = jParser.getJSONFromUrl(URL_GET_ALL);

        try {

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject c = jsonArray.getJSONObject(i);

                int id = c.getInt("id");
                int idMacchina = c.getInt("idMacchina");
                int oreFunzionamento = c.getInt("oreFunzionamento");
                int velocita = c.getInt("velocita");
                int pezziProdotti = c.getInt("pezziProdotti");
                //int manutenzione = c.getInt("manutenzione");
                int codiceAllarme = c.getInt("codiceAllarme");
                int energiaAssorbita = c.getInt("energiaAssorbita");
                int tempMotore = c.getInt("tempMotore");
                int tempSlitta = c.getInt("tempSlitta");

                list.add(new Macchina(id, idMacchina, oreFunzionamento, velocita, pezziProdotti, /*manutenzione,*/ codiceAllarme, energiaAssorbita, tempMotore, tempSlitta));
            }
        } catch (JSONException e) {

            e.printStackTrace();
        }

        return list;
    }

}
