package com.example.appmaria;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Contrat extends AppCompatActivity {
    private EditText  edSearch,edDateD, edDateF, edRedevance,EdemailCl,txtNomC;
    private ImageButton  first, previous,  next, last,RechC, search;
    private Button add, update, delete;
    private ImageView imgSearch;
    private Button save, cancel;
    private  int op;
    private Contrat activity;
    private String x;
    private TextView idClient,id;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrat);


        this.activity=this;
        edSearch=(EditText)findViewById(R.id.edSearch);
        edDateD = (EditText) findViewById(R.id.editDateD);
        edDateF = (EditText) findViewById(R.id.editDateF);
        edRedevance = (EditText) findViewById(R.id.edRedevance);
        EdemailCl = (EditText) findViewById(R.id.EdemailCl);
        imgSearch = (ImageView) findViewById(R.id.search);
        first = (ImageButton) findViewById(R.id.first);
        search = (ImageButton) findViewById(R.id.search);
        previous = (ImageButton) findViewById(R.id.previous);
        next = (ImageButton) findViewById(R.id.next);
        last = (ImageButton) findViewById(R.id.last);
        add=(Button)findViewById(R.id.add);
        update=(Button)findViewById(R.id.update);
        delete=(Button)findViewById(R.id.delete);
        save=(Button)findViewById(R.id.bntSave);
        cancel=(Button)findViewById(R.id.bntCancel2);
        txtNomC=(EditText)findViewById(R.id.EdnomCl);
        idClient=(TextView)findViewById(R.id.idClient);
        id=(TextView)findViewById(R.id.idContrat);
        RechC=(ImageButton) findViewById(R.id.btnSearchCl);
        edDateD.setEnabled(false);
        edDateF.setEnabled(false);
        edRedevance.setEnabled(false);
        txtNomC.setEnabled(false);
        EdemailCl.setEnabled(false);


        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rechercheContrat();
            }
        });
        RechC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rechercherContratClient();
            }
        });/*
         */      add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ajouterContrat();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateContrat();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveContrat();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteContrat(activity);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cancelContrat();
            }
        });
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirstContrat();
            }
        });
        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lastContrat();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nextContrat();
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                previousContrat();
            }
        });




    }


    void rechercheContrat(){
        bg_RechContrat bg = new bg_RechContrat(Contrat.this);
        bg.execute(edSearch.getText().toString());
        visible();
        disable();
    }


    void rechercherContratClient() {
        startActivityForResult(new Intent(getApplicationContext(),rechercher.class),1);


    }

    void FirstContrat(){
        bg_FirstContrat bgs = new bg_FirstContrat(getApplicationContext());
        bgs.execute();
    }

    void lastContrat(){
        bg_LastContrat bgs = new bg_LastContrat(getApplicationContext());
        bgs.execute();
    }
    void nextContrat(){
        int idContrat= Integer.valueOf(id.getText().toString());
        idContrat++;
        bg_NextContrat bgs = new bg_NextContrat(getApplicationContext());
        bgs.execute(String.valueOf(idContrat));

    }

    void previousContrat(){
        int idCon=Integer.valueOf(id.getText().toString());
        idCon--;
        bg_NextContrat bgs = new bg_NextContrat(getApplicationContext());
        bgs.execute(String.valueOf(idCon));
    }

    void ajouterContrat() {
        op = 1;

        imgSearch.setVisibility(View.INVISIBLE);
        update.setVisibility(View.INVISIBLE);
        delete.setVisibility(View.INVISIBLE);
        save.setVisibility(View.VISIBLE);
        cancel.setVisibility(View.VISIBLE);
        add.setEnabled(false);
        enable();
        invisible();
        viderChamp();


    }

    void updateContrat() {

        op = 2;

        imgSearch.setVisibility(View.VISIBLE);
        add.setVisibility(View.INVISIBLE);
        delete.setVisibility(View.INVISIBLE);
        save.setVisibility(View.VISIBLE);
        cancel.setVisibility(View.VISIBLE);
        edSearch.setVisibility(View.INVISIBLE);
        search.setVisibility(View.INVISIBLE);
        update.setEnabled(false);
        enable();
        invisible();
    }

    void saveContrat() {
        if (op == 1) {
            String dated = edDateD.getText().toString();
            String datef = edDateF.getText().toString();
            String redevance = edRedevance.getText().toString();
            String idC = idClient.getText().toString();
            bg_insertionContrat bg = new bg_insertionContrat(Contrat.this);
            bg.execute(dated, datef, redevance, idC);
            viderChamp();



        } else if (op == 2) {
            String dateD = edDateD.getText().toString();
            String dateF = edDateF.getText().toString();
            String redevance = edRedevance.getText().toString();
            bg_UpdateContrat bg = new bg_UpdateContrat(Contrat.this);
            bg.execute(dateD, dateF, redevance);

        }

        imgSearch.setVisibility(View.VISIBLE);
        update.setVisibility(View.VISIBLE);
        delete.setVisibility(View.VISIBLE);
        save.setVisibility(View.INVISIBLE);
        cancel.setVisibility(View.INVISIBLE);
        add.setEnabled(true);
        update.setEnabled(true);
        add.setVisibility(View.VISIBLE);
        disable();
        visible();






    }

    void cancelContrat(){

        imgSearch.setVisibility(View.VISIBLE);
        update.setVisibility(View.VISIBLE);
        delete.setVisibility(View.VISIBLE);
        save.setVisibility(View.INVISIBLE);
        cancel.setVisibility(View.INVISIBLE);
        add.setEnabled(true);
        update.setEnabled(true);
        add.setVisibility(View.VISIBLE);
        disable();
        visible();
        viderChamp();



    }

    void deleteContrat(Contrat activity){


        AlertDialog.Builder bul=new AlertDialog.Builder(activity)
                .setTitle("Confirmation")
                .setMessage("Est ce que vous voulez supprimer ce contrat?")
                .setIcon(R.drawable.validate)
                .setPositiveButton("valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bg_DeleteContrat bg = new bg_DeleteContrat(Contrat.this);
                        bg.execute();
                        viderChamp();
                        dialog.dismiss();

                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        bul.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if (requestCode == 1) {

                String nom = data.getStringExtra("nom");
                String id = data.getStringExtra("id");
                String email=data.getStringExtra("email");
                txtNomC.setText(nom);
                idClient.setText(id);
                EdemailCl.setText(email);



            }
        }
    }
    public void disable(){
        edDateD.setEnabled(false);
        edDateF.setEnabled(false);
        edRedevance.setEnabled(false);
    }
    public void enable(){
        edDateD.setEnabled(true);
        edDateF.setEnabled(true);
        edRedevance.setEnabled(true);
    }
    public void viderChamp(){
        edDateD.setText("");
        edDateF.setText("");
        edRedevance.setText("");
        txtNomC.setText("");
        idClient.setText("");
        EdemailCl.setText("");
    }
    public void visible(){
        first.setVisibility(View.VISIBLE);
        next.setVisibility(View.VISIBLE);
        previous.setVisibility(View.VISIBLE);
        last.setVisibility(View.VISIBLE);
    }
    public void invisible(){
        first.setVisibility(View.INVISIBLE);
        next.setVisibility(View.INVISIBLE);
        previous.setVisibility(View.INVISIBLE);
        last.setVisibility(View.INVISIBLE);
    }

    private class bg_insertionContrat extends AsyncTask<String, Void, String> {

        AlertDialog dialog;
        Context context;

        public bg_insertionContrat(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new AlertDialog.Builder(context).create();
            dialog.setTitle("Etat de connexion");
        }

        @Override
        protected String doInBackground(String... strings) {

            String result = "";

            String edDateD = strings[0];
            String edDateF = strings[1];
            String edRedevance = strings[2];
            String edIdC = strings[3];


            String connstr = "http://192.168.187.91/Expert/insertContrat.php";
            try {
                URL url = new URL(connstr);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);
                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));

                String data = URLEncoder.encode("dateD", "UTF-8") + "=" + URLEncoder.encode(edDateD, "UTF-8") +
                        "&&" + URLEncoder.encode("dateF", "UTF-8") + "=" + URLEncoder.encode(edDateF, "UTF-8") +
                        "&&" + URLEncoder.encode("redevance", "UTF-8") + "=" + URLEncoder.encode(edRedevance, "UTF-8") +
                        "&&" + URLEncoder.encode("idC", "UTF-8") + "=" + URLEncoder.encode(edIdC, "UTF-8") ;

                writer.write(data);
                writer.flush();
                writer.close();
                InputStream ips = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "UTF-8"));
                String ligne = "";
                while ((ligne = reader.readLine()) != null) {
                    result += ligne;
                }
                reader.close();
                ips.close();
                http.disconnect();

                return result;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.contains("succes insertion")) {
                Toast.makeText(context, "Contrat inséré avec succès.", Toast.LENGTH_LONG).show();
            } else if (s.contains("Contrat existe")){
                Toast.makeText(context, "Contrat existe", Toast.LENGTH_LONG).show();
            }
            else Toast.makeText(context, s, Toast.LENGTH_LONG).show();


        }
    }
    private class bg_RechContrat extends AsyncTask<String, Void, String> {
        Context context;
        AlertDialog dialog;

        public bg_RechContrat(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new AlertDialog.Builder(Contrat.this).create();
            dialog.setTitle("Affichage Contrat...");
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            String dateD = strings[0];
            String connstr = "http://192.168.187.91/Expert/rechercherContrat.php";
            try {
                URL url = new URL(connstr);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setDoInput(true);
                http.setDoOutput(true);
                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));

                String data = URLEncoder.encode("dateD", "UTF-8") + "=" + URLEncoder.encode(dateD, "UTF-8");
                writer.write(data);
                writer.flush();
                writer.close();
                InputStream ips = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
                String ligne = "";
                while ((ligne = reader.readLine()) != null) {
                    result += ligne;
                }
                reader.close();
                ips.close();
                http.disconnect();
                return result;
            } catch (IOException e) {
                e.getMessage();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (!s.equals("")) {
                try {
                    JSONArray jClientArray = new JSONArray(s);
                    for (int i = 0; i < jClientArray.length(); i++) {
                        id.setText(jClientArray.optJSONObject(i).getString("id"));
                        edDateD.setText(jClientArray.optJSONObject(i).getString("dateD"));
                        edDateF.setText(jClientArray.optJSONObject(i).getString("dateF"));
                        edRedevance.setText(jClientArray.optJSONObject(i).getString("redevance"));
                        idClient.setText(jClientArray.optJSONObject(i).getString("idC"));
                        txtNomC.setText(jClientArray.optJSONObject(i).getString("nom"));
                        EdemailCl.setText(jClientArray.optJSONObject(i).getString("email"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            if (s.contains("aucun resultat")) {
                Toast.makeText(context, "aucun resultat", Toast.LENGTH_SHORT).show();
            }


        }


    }
    private class bg_FirstContrat extends AsyncTask<String, Void, String> {
        Context context;
        AlertDialog dialog;

        public bg_FirstContrat(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new AlertDialog.Builder(Contrat.this).create();
            dialog.setTitle("Affichage du Premier Contrat...");
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            String connstr = "http://192.168.187.91/Expert/getFirstContrat.php";
            URL url = null;
            try {
                url = new URL(connstr);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("GET");
                http.setDoInput(true);
                //http.setDoOutput(true);

                InputStream ips = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
                String ligne = "";
                while ((ligne = reader.readLine()) != null) {
                    result += ligne;
                }
                reader.close();
                ips.close();
                http.disconnect();
                return result;
            } catch (IOException e) {
                e.getMessage();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (!s.equals("")) {



                try {
                    JSONArray jClientArray = new JSONArray(s);

                    id.setText(jClientArray.optJSONObject(0).getString("id"));
                    edDateD.setText(jClientArray.optJSONObject(0).getString("dateD"));
                    edDateF.setText(jClientArray.optJSONObject(0).getString("dateF"));
                    edRedevance.setText(jClientArray.optJSONObject(0).getString("redevance"));
                    idClient.setText(jClientArray.optJSONObject(0).getString("idC"));
                    txtNomC.setText(jClientArray.optJSONObject(0).getString("nom"));
                    EdemailCl.setText(jClientArray.optJSONObject(0).getString("email"));



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            if (s.contains("aucun resultat")) {
                Toast.makeText(context, "aucun resultat", Toast.LENGTH_SHORT).show();
            }

        }


    }
    private class bg_LastContrat extends AsyncTask<String, Void, String> {
        Context context;
        AlertDialog dialog;

        public bg_LastContrat(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new AlertDialog.Builder(Contrat.this).create();
            dialog.setTitle("Affichage Dernier Contrat...");
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            String connstr = "http://192.168.187.91/Expert/getFirstContrat.php";
            URL url = null;
            try {
                url = new URL(connstr);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("GET");
                http.setDoInput(true);
                //http.setDoOutput(true);

                InputStream ips = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
                String ligne = "";
                while ((ligne = reader.readLine()) != null) {
                    result += ligne;
                }
                reader.close();
                ips.close();
                http.disconnect();
                return result;
            } catch (IOException e) {
                e.getMessage();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (!s.equals("")) {
                try {
                    JSONArray jClientArray = new JSONArray(s);
                    for (int i = 0; i < jClientArray.length(); i++) {
                        // clients.add(new client(jClientArray.optJSONObject(jClientArray.length())));
                        id.setText(jClientArray.optJSONObject(i).getString("id"));
                        edDateD.setText(jClientArray.optJSONObject(i).getString("dateD"));
                        edDateF.setText(jClientArray.optJSONObject(i).getString("dateF"));
                        edRedevance.setText(jClientArray.optJSONObject(i).getString("redevance"));
                        idClient.setText(jClientArray.optJSONObject(i).getString("idC"));
                        txtNomC.setText(jClientArray.optJSONObject(i).getString("nom"));
                        EdemailCl.setText(jClientArray.optJSONObject(i).getString("email"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }


    }
    private class bg_NextContrat extends AsyncTask<String, Void, String> {
        Context context;
        AlertDialog dialog;

        public bg_NextContrat(Context context) {
            this.context = context;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new AlertDialog.Builder(Contrat.this).create();
            dialog.setTitle("Affichage  Contrat ");
        }
        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            String id=strings[0];
            String connstr = "http://192.168.187.91/Expert/getNextContrat.php";
            URL url = null;
            try {
                url = new URL(connstr);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setDoInput(true);
                http.setDoOutput(true);
                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") ;
                writer.write(data);
                Log.v("Contrat", data);
                writer.flush();//vider le buffer est envoyer dans le serveur
                writer.close();
                InputStream ips = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "UTF-8"));
                String ligne = "";
                while ((ligne = reader.readLine()) != null) {
                    result += ligne;
                }
                reader.close();
                ips.close();
                http.disconnect();
                return result;
            } catch (IOException e) {
                result = e.getMessage();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (!s.equals("")) {
                try {
                    JSONArray jClientArray = new JSONArray(s);
                    for (int i = 0; i < jClientArray.length(); i++) {
                        id.setText(jClientArray.optJSONObject(i).getString("id"));
                        edDateD.setText(jClientArray.optJSONObject(i).getString("dateD"));
                        edDateF.setText(jClientArray.optJSONObject(i).getString("dateF"));
                        edRedevance.setText(jClientArray.optJSONObject(i).getString("redevance"));
                        idClient.setText(jClientArray.optJSONObject(i).getString("idC"));
                        txtNomC.setText(jClientArray.optJSONObject(i).getString("nom"));
                        EdemailCl.setText(jClientArray.optJSONObject(i).getString("email"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }



    }
    private class bg_UpdateContrat extends AsyncTask<String, Void, String> {
        Context context;
        AlertDialog dialog;

        public bg_UpdateContrat(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new AlertDialog.Builder(Contrat.this).create();
            dialog.setTitle("Modifier Contrat...");
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            String idCon = id.getText().toString();
            String dateD = strings[0];
            String dateF = strings[1];
            String redevance = strings[2];


            String connstr = "http://192.168.187.91/Expert/updateContrat.php";
            try {
                URL url = new URL(connstr);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);
                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));

                String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(idCon, "UTF-8") +
                        "&&" + URLEncoder.encode("dateD", "UTF-8") + "=" + URLEncoder.encode(dateD, "UTF-8") +
                        "&&" + URLEncoder.encode("dateF", "UTF-8") + "=" + URLEncoder.encode(dateF, "UTF-8") +
                        "&&" + URLEncoder.encode("redevance", "UTF-8") + "=" + URLEncoder.encode(redevance, "UTF-8") ;

                writer.write(data);
                writer.flush();
                writer.close();
                InputStream ips = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
                String ligne = "";
                while ((ligne = reader.readLine()) != null) {
                    result += ligne;
                }
                reader.close();
                ips.close();
                http.disconnect();
                return result;
            } catch (IOException e) {
                e.getMessage();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (!s.equals("")) {
                try {
                    JSONArray jClientArray = new JSONArray(s);

                    for (int i = 0; i < jClientArray.length(); i++) {
                        // clients.add(new client(jClientArray.optJSONObject(jClientArray.length())));
                        id.setText(jClientArray.optJSONObject(i).getString("id"));
                        edDateD.setText(jClientArray.optJSONObject(i).getString("dateD"));
                        edDateF.setText(jClientArray.optJSONObject(i).getString("dateF"));
                        edRedevance.setText(jClientArray.optJSONObject(i).getString("redevance"));
                        idClient.setText(jClientArray.optJSONObject(i).getString("idC"));
                        txtNomC.setText(jClientArray.optJSONObject(i).getString("nom"));
                        EdemailCl.setText(jClientArray.optJSONObject(i).getString("email"));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            if (s.contains("client modified")){
                Toast.makeText(context, "client modified", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(context, "Probleme de connexion", Toast.LENGTH_SHORT).show();


        }


    }

    private class bg_DeleteContrat extends AsyncTask<String, Void, String> {
        Context context;
        AlertDialog dialog;

        public bg_DeleteContrat(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new AlertDialog.Builder(Contrat.this).create();
            dialog.setTitle("Modifier Client...");
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            String idCon = id.getText().toString();

            String connstr = "http://192.168.187.91/Expert/deleteContrat.php";
            try {
                URL url = new URL(connstr);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setDoInput(true);
                http.setDoOutput(true);
                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));

                String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(idCon, "UTF-8");
                writer.write(data);
                writer.flush();
                writer.close();
                InputStream ips = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
                String ligne = "";
                while ((ligne = reader.readLine()) != null) {
                    result += ligne;
                }
                reader.close();
                ips.close();
                http.disconnect();
                return result;
            } catch (IOException e) {
                e.getMessage();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.contains("contrat deleted"))
                Toast.makeText(context, "contrat deleted", Toast.LENGTH_SHORT).show();


        }


    }

}