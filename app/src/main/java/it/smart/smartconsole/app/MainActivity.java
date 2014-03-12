package it.smart.smartconsole.app;

import java.util.ArrayList;
import java.util.List;

import it.smart.smartconsole.app.data.Macchina;
import it.smart.smartconsole.app.util.DownloadDataAsyncTask;
import it.smart.smartconsole.app.util.SetLogAsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private TextView oreFunzionamentoTV;
    private TextView velocitaTV;
    private TextView pezziProdottiTV;
    private TextView energiaAssorbitaTV;
    private TextView tempMotoreTV;
    private TextView tempSlittaTV;
    //private TextView manutenzioneTV;
    private TextView codiceAllarmeTV;
    private Spinner spinner;
    private Button refreshButton;
    private ProgressDialog dialog;

    private AlertDialog alertDialog;

    private int currentID;
    private List<Macchina> macchineGuaste = new ArrayList<Macchina>();

    private List<Macchina> listaMacchine = new ArrayList<Macchina>();
    private List<Integer> listaID = new ArrayList<Integer>();

    Handler handler = new Handler();
    Runnable timedTask = new Runnable(){

        @Override
        public void run() {
            launchAsyncTaskToRefreshView();
            handler.postDelayed(timedTask, 60000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        oreFunzionamentoTV = (TextView) findViewById(R.id.oreFunzionamentoTV);
        velocitaTV = (TextView) findViewById(R.id.velocitaTV);
        pezziProdottiTV = (TextView) findViewById(R.id.pezziProdottiTV);
        energiaAssorbitaTV = (TextView) findViewById(R.id.energiaAssorbitaTV);
        tempMotoreTV = (TextView) findViewById(R.id.tempMotoreTV);
        tempSlittaTV = (TextView) findViewById(R.id.tempSlittaTV);
        //manutenzioneTV = (TextView) findViewById(R.id.manutenzioneTV);
        codiceAllarmeTV = (TextView) findViewById(R.id.codiceAllarmeTV);
        spinner = (Spinner) findViewById(R.id.spinner1);
        refreshButton = (Button) findViewById(R.id.refreshButton);

        DownloadDataAsyncTask task = new DownloadDataAsyncTask(){
            @Override
            protected void onPostExecute(List<Macchina> result) {
                listaMacchine = result;
                for(int i=0; i<listaMacchine.size(); i++){
                    listaID.add(listaMacchine.get(i).getIdMacchina());
                }
                dialog.dismiss();
                addItemsOnSpinner();
                addListenerOnButton();
                addListenerOnSpinnerItemSelection();
                updateView(listaID.get(0)); // gli passo il primo della lista
                checkForErrorOrMaintenance();
            }
        };
        task.execute();

        dialog = new ProgressDialog(this);
        dialog.setMessage("Connecting...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(timedTask);
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        handler.removeCallbacks(timedTask);
        super.onStop();
    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(timedTask);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(timedTask, 60000);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void addItemsOnSpinner(){
        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_item, listaID);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    private void addListenerOnButton(){

        refreshButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                launchAsyncTaskToRefreshView();
            }
        });
    }

    private void addListenerOnSpinnerItemSelection(){

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                currentID = (Integer) parent.getItemAtPosition(pos);
                updateView(Integer.parseInt( parent.getItemAtPosition(pos).toString() ));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });
    }

    private void updateView(int id){
        Macchina m;
        for(int i=0; i<listaMacchine.size(); i++){
            if(listaMacchine.get(i).getIdMacchina() == id){
                m = listaMacchine.get(i);

                oreFunzionamentoTV.setText(""+m.getOreFunzionamento());
                velocitaTV.setText(""+m.getVelocita());
                pezziProdottiTV.setText(""+m.getPezziProdotti());
                energiaAssorbitaTV.setText(""+m.getEnergiaAssorbita());
                tempMotoreTV.setText(""+m.getTempMotore());
                tempSlittaTV.setText(""+m.getTempSlitta());
                //manutenzioneTV.setText(""+m.getManutenzione());
                codiceAllarmeTV.setText(""+m.getCodiceAllarme());
            }
        }
    }

    private void launchAsyncTaskToRefreshView(){
        DownloadDataAsyncTask task = new DownloadDataAsyncTask(){
            @Override
            protected void onPostExecute(List<Macchina> result) {
                listaMacchine = result;
                listaID.clear();
                for(int i=0; i<listaMacchine.size(); i++){
                    listaID.add(listaMacchine.get(i).getIdMacchina());
                }

                checkForErrorOrMaintenance();
                Toast.makeText(MainActivity.this, "Refreshed", Toast.LENGTH_SHORT).show();
                updateView(currentID);
            }
        };
        task.execute();
    }

    private void checkForErrorOrMaintenance(){
        macchineGuaste.clear();
//		List<Integer> macchineManutenzione = new ArrayList<Integer>();
        // controllo che non ci siano errori o allarmi manutenzione
        for(int i=0; i<listaMacchine.size(); i++){
/*
			if(listaMacchine.get(i).getManutenzione() != 0){
				macchineManutenzione.add(listaMacchine.get(i).getIdMacchina());
			}
*/
            if(listaMacchine.get(i).getCodiceAllarme() != 0){
                macchineGuaste.add(listaMacchine.get(i));
            }
        }
        if(macchineGuaste.size() > 0){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Manutenzione e Allarmi");

            StringBuilder sb = new StringBuilder();
/*
		sb.append("Macchine che richiedono manutenzione:\n");
		for(int i=0; i<macchineManutenzione.size(); i++){
			sb.append(macchineManutenzione.get(i));
			sb.append("\n");
		}
*/
            sb.append("Macchine in allarme:\n");
            for(int i=0; i<macchineGuaste.size(); i++){
                sb.append("Macchina: ");
                sb.append(macchineGuaste.get(i).getIdMacchina());
                sb.append(" con codice: ");
                sb.append(macchineGuaste.get(i).getCodiceAllarme());
                sb.append("\n");
            }

            String string = sb.toString();

            alertDialogBuilder.setMessage(string);
            alertDialogBuilder.setNeutralButton("Risolto", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setMacchineGuasteAsOK();
                    launchAsyncTaskToRefreshView();
                }
            });
            alertDialogBuilder.setNegativeButton("Piu' Tardi", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {dialog.cancel();}
            });
            if(alertDialog!=null)
                alertDialog.dismiss();
            alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    @SuppressWarnings("unchecked")
    public void setMacchineGuasteAsOK(){
        for(int i=0; i<macchineGuaste.size(); i++){
            macchineGuaste.get(i).setCodiceAllarme(0);
        }
        SetLogAsyncTask task = new SetLogAsyncTask();
        task.execute(macchineGuaste);
    }
}