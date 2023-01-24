package com.example.appmaria;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import java.net.URL;
import java.net.URLEncoder;

public class rechercher extends AppCompatActivity {
    private EditText SearchNom, RechNom, RechId,RechEmail;
    private ImageButton imgRech, Rechfirst, Rechprevious, Rechnext, Rechlast;
    private Button btnValider, btnCancel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rechercher);
        SearchNom = (EditText) findViewById(R.id.SearchNom);
        RechNom = (EditText) findViewById(R.id.RechNom);
        RechId = (EditText) findViewById(R.id.RechId);
        RechEmail = (EditText) findViewById(R.id.RechEmail);
        imgRech = (ImageButton) findViewById(R.id.imgRech);
        Rechfirst = (ImageButton) findViewById(R.id.Rechfirst);
        Rechprevious = (ImageButton) findViewById(R.id.Rechprevious);
        Rechnext = (ImageButton) findViewById(R.id.Rechnext);
        Rechlast = (ImageButton) findViewById(R.id.Rechlast);
        btnValider = (Button) findViewById(R.id.btnValider);
        btnCancel = (Button) findViewById(R.id.btnCancel);


        imgRech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RechercherNomClient();
            }
        });
        Rechfirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RechFirst();
            }
        });

        Rechprevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rechprevious();
            }
        });
        Rechnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rechnext();
            }
        });

        Rechlast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rechlast();
            }
        });

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valider();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });




    }

    public void RechercherNomClient() {
        bg_RechClient bgs = new bg_RechClient (getApplicationContext());
        bgs.execute(SearchNom.getText().toString());
        visible();

    }
    public void RechFirst(){
        bg_FirstClient bgs = new bg_FirstClient(getApplicationContext());
        bgs.execute();

    }
    public void Rechnext(){

        int id=Integer.valueOf(RechId.getText().toString());
        id++;
        bg_NextClient bgs = new bg_NextClient(getApplicationContext());
        bgs.execute(String.valueOf(id));
    }
    public  void Rechprevious(){
        int id=Integer.valueOf(RechId.getText().toString());
        id--;
        bg_NextClient bgs = new bg_NextClient(getApplicationContext());
        bgs.execute(String.valueOf(id));
    }

    public void Rechlast(){
        bg_LastClient bgs = new bg_LastClient(getApplicationContext());
        bgs.execute();
    }

    public void valider(){

        String nom=RechNom.getText().toString();
        String id=RechId.getText().toString();
        String email=RechEmail.getText().toString();
        Intent intent=new Intent();
        intent.putExtra("nom",nom);
        intent.putExtra("id",id);
        intent.putExtra("email",email);
        setResult(RESULT_OK,intent);
        finish();


    }

    public void cancel() {
        Intent intent = new Intent();
        intent.putExtra("nom", "");
        intent.putExtra("id", "");
        intent.putExtra("tel", "");

        setResult(RESULT_OK, intent);
        finish();
    }

    public void visible() {
        Rechfirst.setVisibility(View.VISIBLE);
        Rechnext.setVisibility(View.VISIBLE);
        Rechprevious.setVisibility(View.VISIBLE);
        Rechlast.setVisibility(View.VISIBLE);
    }


    private class bg_RechClient extends AsyncTask<String, Void, String> {
        Context context;
        AlertDialog dialog;

        public bg_RechClient(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new AlertDialog.Builder(rechercher.this).create();
            dialog.setTitle("Affichage Client...");
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            String nom = strings[0];
            String connstr = "http://192.168.187.91/Expert/Afficher.php";
            try {
                URL url = new URL(connstr);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setDoInput(true);
                http.setDoOutput(true);
                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));

                String data = URLEncoder.encode("nom", "UTF-8") + "=" + URLEncoder.encode(nom, "UTF-8");
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

                    RechId.setText(jClientArray.optJSONObject(0).getString("id"));
                    RechNom.setText(jClientArray.optJSONObject(0).getString("nom"));
                    RechEmail.setText(jClientArray.optJSONObject(0).getString("email"));



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            if (s.contains("aucun resultat")) {
                Toast.makeText(context, "aucun resultat", Toast.LENGTH_SHORT).show();
            }

        }


    }

    private class bg_FirstClient extends AsyncTask<String, Void, String> {
        Context context;
        AlertDialog dialog;

        public bg_FirstClient(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new AlertDialog.Builder(rechercher.this).create();
            dialog.setTitle("Affichage du Premier Clients...");
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            String connstr = "http://192.168.187.91/Expert/getFirst.php";
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
                    RechId.setText(jClientArray.optJSONObject(0).getString("id"));
                    RechNom.setText(jClientArray.optJSONObject(0).getString("nom"));
                    RechEmail.setText(jClientArray.optJSONObject(0).getString("email"));



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            if (s.contains("aucun resultat")) {
                Toast.makeText(context, "aucun resultat", Toast.LENGTH_SHORT).show();
            }

        }


    }

    private class bg_LastClient extends AsyncTask<String, Void, String> {
        Context context;
        AlertDialog dialog;

        public bg_LastClient(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new AlertDialog.Builder(rechercher.this).create();
            dialog.setTitle("Affichage Dernier Client...");
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            String connstr = "http://192.168.187.91/Expert/getFirst.php";
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
                        RechId.setText(jClientArray.optJSONObject(i).getString("id"));
                        RechNom.setText(jClientArray.optJSONObject(i).getString("nom"));
                        RechEmail.setText(jClientArray.optJSONObject(i).getString("email"));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }


    }

    private class bg_NextClient extends AsyncTask<String, Void, String> {
        Context context;
        AlertDialog dialog;

        public bg_NextClient(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new AlertDialog.Builder(rechercher.this).create();
            dialog.setTitle("Affichage  Client ");
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            String id = strings[0];
            String connstr = "http://192.168.187.91/Expert/getNext.php";
            URL url = null;
            try {
                url = new URL(connstr);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setDoInput(true);
                http.setDoOutput(true);
                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
                writer.write(data);
                Log.v("Client", data);
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
                        RechId.setText(jClientArray.optJSONObject(0).getString("id"));
                        RechNom.setText(jClientArray.optJSONObject(0).getString("nom"));
                        RechEmail.setText(jClientArray.optJSONObject(0).getString("email"));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }
    }




}