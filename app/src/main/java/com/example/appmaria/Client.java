package com.example.appmaria;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Client extends AppCompatActivity {
    private EditText EdsearchClient, Ednom, Edprenom, Edadresse, Edtel, Edfax, Edemail;
    private Button addClient, updateCient, deleteClient;
    private TextView txtid;
    private Button saveClient, cancelClient;
    private ImageButton searchClient, first, previous, next, last;
    private String ms = "";
    private int op;
    private Client activity;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        EdsearchClient = (EditText) findViewById(R.id.EditsearchClient);
        Ednom = (EditText) findViewById(R.id.Editnom);
        Edprenom = (EditText) findViewById(R.id.Editprenom);
        Edadresse = (EditText) findViewById(R.id.Editadresse);
        Edtel = (EditText) findViewById(R.id.Edittel);
        Edfax = (EditText) findViewById(R.id.Editfax);
        Edemail = (EditText) findViewById(R.id.Edemail);
        searchClient = (ImageButton) findViewById(R.id.searchClient);
        addClient = (Button) findViewById(R.id.addClient);
        updateCient = (Button) findViewById(R.id.updateClient);
        deleteClient = (Button) findViewById(R.id.deleteClient);
        saveClient = (Button) findViewById(R.id.saveClient);
        cancelClient = (Button) findViewById(R.id.cancelClient);
        first = (ImageButton) findViewById(R.id.first);
        previous = (ImageButton) findViewById(R.id.previous);
        next = (ImageButton) findViewById(R.id.next);
        last = (ImageButton) findViewById(R.id.last);
        txtid = (TextView) findViewById(R.id.txtid);
        this.activity = this;
        Disable();


        addClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addClient();
            }
        });
        searchClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchClient();
            }
        });

        updateCient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateClient();
            }
        });

        saveClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveClient();
            }
        });

        cancelClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelClient();
            }
        });

        deleteClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteClient(activity);
            }
        });

        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstClient();
            }
        });

        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastClient();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextClient();
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousClient();
            }
        });


    }


    void searchClient() {
        bg_RechClient bgs = new bg_RechClient(getApplicationContext());
        bgs.execute(EdsearchClient.getText().toString());
        visible();
        Disable();
    }

    void firstClient() {
        bg_FirstClient bgs = new bg_FirstClient(getApplicationContext());
        bgs.execute();
    }

    void lastClient() {
        bg_LastClient bgs = new bg_LastClient(getApplicationContext());
        bgs.execute();
    }

    void nextClient() {
        int id=Integer.valueOf(txtid.getText().toString());
        id++;
        bg_NextClient bgs = new bg_NextClient(getApplicationContext());
        bgs.execute(String.valueOf(id));
    }

    void previousClient(){
        int id=Integer.valueOf(txtid.getText().toString());
        id--;
        bg_NextClient bgs = new bg_NextClient(getApplicationContext());
        bgs.execute(String.valueOf(id));
    }


    void addClient() {
        op = 1;
        EdsearchClient.setVisibility(View.INVISIBLE);
        searchClient.setVisibility(View.INVISIBLE);
        updateCient.setVisibility(View.INVISIBLE);
        deleteClient.setVisibility(View.INVISIBLE);
        addClient.setEnabled(false);
        saveClient.setVisibility(View.VISIBLE);
        cancelClient.setVisibility(View.VISIBLE);
        viderChamp();
        enable();
        invisible();

    }

    void updateClient() {
        op = 2;
        EdsearchClient.setVisibility(View.INVISIBLE);
        searchClient.setVisibility(View.INVISIBLE);
        addClient.setVisibility(View.INVISIBLE);
        deleteClient.setVisibility(View.INVISIBLE);
        updateCient.setEnabled(false);
        enable();
        saveClient.setVisibility(View.VISIBLE);
        cancelClient.setVisibility(View.VISIBLE);
        invisible();

    }

    void saveClient() {
        if (op == 1) {
            String nom = Ednom.getText().toString();
            String prenom = Edprenom.getText().toString();
            String adresse = Edadresse.getText().toString();
            String tel = Edtel.getText().toString();
            String fax = Edfax.getText().toString();
            String email = Edemail.getText().toString();
            bg_insertion_client bg = new bg_insertion_client(Client.this);
            bg.execute(nom, prenom, adresse, tel, fax, email);
            EdsearchClient.setVisibility(View.VISIBLE);
            searchClient.setVisibility(View.VISIBLE);
            addClient.setVisibility(View.VISIBLE);
            updateCient.setVisibility(View.VISIBLE);
            deleteClient.setVisibility(View.VISIBLE);
            updateCient.setEnabled(true);
            addClient.setEnabled(true);
            saveClient.setVisibility(View.INVISIBLE);
            cancelClient.setVisibility(View.INVISIBLE);
            Disable();
            searchClient.performClick();
            visible();
            viderChamp();
        }
        if (op == 2) {
            String nom = Ednom.getText().toString();
            String prenom = Edprenom.getText().toString();
            String adresse = Edadresse.getText().toString();
            String tel = Edtel.getText().toString();
            String fax = Edfax.getText().toString();
            String email = Edemail.getText().toString();
            bg_UpdateClient bg = new bg_UpdateClient(Client.this);
            bg.execute(nom, prenom, adresse, tel, fax, email);
            EdsearchClient.setVisibility(View.VISIBLE);
            searchClient.setVisibility(View.VISIBLE);
            addClient.setVisibility(View.VISIBLE);
            updateCient.setVisibility(View.VISIBLE);
            deleteClient.setVisibility(View.VISIBLE);
            updateCient.setEnabled(true);
            addClient.setEnabled(true);
            saveClient.setVisibility(View.INVISIBLE);
            cancelClient.setVisibility(View.INVISIBLE);
            Disable();
            searchClient.performClick();
            visible();


        }
    }


    void cancelClient() {
        EdsearchClient.setVisibility(View.VISIBLE);
        searchClient.setVisibility(View.VISIBLE);
        addClient.setVisibility(View.VISIBLE);
        deleteClient.setVisibility(View.VISIBLE);
        updateCient.setVisibility(View.VISIBLE);
        updateCient.setEnabled(true);
        addClient.setEnabled(true);
        saveClient.setVisibility(View.INVISIBLE);
        cancelClient.setVisibility(View.INVISIBLE);
        viderChamp();
        Disable();
        visible();

    }

    void deleteClient(Client activity) {
        AlertDialog.Builder bul1 = new AlertDialog.Builder(activity)
                .setTitle("Confirmation")
                .setMessage("Est ce que vous voulez supprimer ce contrat?")
                .setIcon(R.drawable.validate)
                .setPositiveButton("valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bg_DeleteClient bg = new bg_DeleteClient(Client.this);
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
        bul1.show();

    }

    public void invisible() {
        first.setVisibility(View.INVISIBLE);
        next.setVisibility(View.INVISIBLE);
        previous.setVisibility(View.INVISIBLE);
        last.setVisibility(View.INVISIBLE);
    }

    public void visible() {
        first.setVisibility(View.VISIBLE);
        next.setVisibility(View.VISIBLE);
        previous.setVisibility(View.VISIBLE);
        last.setVisibility(View.VISIBLE);
    }

    public void viderChamp() {
        Ednom.setText("");
        Edprenom.setText("");
        Edadresse.setText("");
        Edtel.setText("");
        Edfax.setText("");
        Edemail.setText("");
    }

    public void Disable() {
        Ednom.setEnabled(false);
        Edprenom.setEnabled(false);
        Edadresse.setEnabled(false);
        Edtel.setEnabled(false);
        Edfax.setEnabled(false);
        Edemail.setEnabled(false);
    }

    public void enable() {
        Ednom.setEnabled(true);
        Edprenom.setEnabled(true);
        Edadresse.setEnabled(true);
        Edtel.setEnabled(true);
        Edfax.setEnabled(true);
        Edemail.setEnabled(true);
    }




    private class bg_insertion_client extends AsyncTask<String, Void, String> {

        AlertDialog dialog;
        Context context;

        public bg_insertion_client(Context context) {
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

            String nom = strings[0];
            String prenom = strings[1];
            String adresse = strings[2];
            String tel = strings[3];
            String fax = strings[4];
            String email = strings[5];

            String connstr = "http://192.168.187.91/Expert/insertion_client.php";
            try {
                URL url = new URL(connstr);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);
                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));

                String data = URLEncoder.encode("nom", "UTF-8") + "=" + URLEncoder.encode(nom, "UTF-8") +
                        "&&" + URLEncoder.encode("prenom", "UTF-8") + "=" + URLEncoder.encode(prenom, "UTF-8") +
                        "&&" + URLEncoder.encode("adresse", "UTF-8") + "=" + URLEncoder.encode(adresse, "UTF-8") +
                        "&&" + URLEncoder.encode("tel", "UTF-8") + "=" + URLEncoder.encode(tel, "UTF-8") +
                        "&&" + URLEncoder.encode("fax", "UTF-8") + "=" + URLEncoder.encode(fax, "UTF-8") +
                        "&&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");

                writer.write(data);
                Log.v("Client", data);
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
                Toast.makeText(context, "Client inséré avec succès.", Toast.LENGTH_LONG).show();
            } else if (s.contains("client existe")){
                Toast.makeText(context, "client existe", Toast.LENGTH_LONG).show();
            }
            else Toast.makeText(context, "probléme d'insertion", Toast.LENGTH_LONG).show();


        }
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
            dialog = new AlertDialog.Builder(Client.this).create();
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

                    txtid.setText(jClientArray.optJSONObject(0).getString("id"));
                    Ednom.setText(jClientArray.optJSONObject(0).getString("nom"));
                    Edprenom.setText(jClientArray.optJSONObject(0).getString("prenom"));
                    Edadresse.setText(jClientArray.optJSONObject(0).getString("adresse"));
                    Edtel.setText(jClientArray.optJSONObject(0).getString("tel"));
                    Edfax.setText(jClientArray.optJSONObject(0).getString("fax"));
                    Edemail.setText(jClientArray.optJSONObject(0).getString("email"));


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
            dialog = new AlertDialog.Builder(Client.this).create();
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
                    txtid.setText(jClientArray.optJSONObject(0).getString("id"));
                    Ednom.setText(jClientArray.optJSONObject(0).getString("nom"));
                    Edprenom.setText(jClientArray.optJSONObject(0).getString("prenom"));
                    Edadresse.setText(jClientArray.optJSONObject(0).getString("adresse"));
                    Edtel.setText(jClientArray.optJSONObject(0).getString("tel"));
                    Edfax.setText(jClientArray.optJSONObject(0).getString("fax"));
                    Edemail.setText(jClientArray.optJSONObject(0).getString("email"));


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
            dialog = new AlertDialog.Builder(Client.this).create();
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
                        txtid.setText(jClientArray.optJSONObject(i).getString("id"));
                        Ednom.setText(jClientArray.optJSONObject(i).getString("nom"));
                        Edprenom.setText(jClientArray.optJSONObject(i).getString("prenom"));
                        Edadresse.setText(jClientArray.optJSONObject(i).getString("adresse"));
                        Edtel.setText(jClientArray.optJSONObject(i).getString("tel"));
                        Edfax.setText(jClientArray.optJSONObject(i).getString("fax"));
                        Edemail.setText(jClientArray.optJSONObject(i).getString("email"));
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
            dialog = new AlertDialog.Builder(Client.this).create();
            dialog.setTitle("Affichage  Client ");
        }
        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            String id=strings[0];
            String connstr = "http://192.168.187.91/Expert/getNext.php";
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
                        txtid.setText(jClientArray.optJSONObject(i).getString("id"));
                        Ednom.setText(jClientArray.optJSONObject(i).getString("nom"));
                        Edprenom.setText(jClientArray.optJSONObject(i).getString("prenom"));
                        Edadresse.setText(jClientArray.optJSONObject(i).getString("adresse"));
                        Edtel.setText(jClientArray.optJSONObject(i).getString("tel"));
                        Edfax.setText(jClientArray.optJSONObject(i).getString("fax"));
                        Edemail.setText(jClientArray.optJSONObject(i).getString("email"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }



    }


    private class bg_UpdateClient extends AsyncTask<String, Void, String> {
        Context context;
        AlertDialog dialog;

        public bg_UpdateClient(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new AlertDialog.Builder(Client.this).create();
            dialog.setTitle("Modifier Client...");
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            String id = txtid.getText().toString();
            String Editnom = strings[0];
            String Editprenom = strings[1];
            String Editadresse = strings[2];
            String Edittel = strings[3];
            String Editfax = strings[4];
            String Edemail = strings[5];

            String connstr = "http://192.168.187.91/Expert/update.php";
            try {
                URL url = new URL(connstr);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);
                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));

                String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") +
                        "&&" + URLEncoder.encode("nom", "UTF-8") + "=" + URLEncoder.encode(Editnom, "UTF-8") +
                        "&&" + URLEncoder.encode("prenom", "UTF-8") + "=" + URLEncoder.encode(Editprenom, "UTF-8") +
                        "&&" + URLEncoder.encode("adresse", "UTF-8") + "=" + URLEncoder.encode(Editadresse, "UTF-8") +
                        "&&" + URLEncoder.encode("tel", "UTF-8") + "=" + URLEncoder.encode(Edittel, "UTF-8") +
                        "&&" + URLEncoder.encode("fax", "UTF-8") + "=" + URLEncoder.encode(Editfax, "UTF-8") +
                        "&&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(Edemail, "UTF-8");

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
                        txtid.setText(jClientArray.optJSONObject(i).getString("id"));
                        Ednom.setText(jClientArray.optJSONObject(i).getString("nom"));
                        Edprenom.setText(jClientArray.optJSONObject(i).getString("prenom"));
                        Edadresse.setText(jClientArray.optJSONObject(i).getString("adresse"));
                        Edtel.setText(jClientArray.optJSONObject(i).getString("tel"));
                        Edfax.setText(jClientArray.optJSONObject(i).getString("fax"));
                        Edemail.setText(jClientArray.optJSONObject(i).getString("email"));

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

    private class bg_DeleteClient extends AsyncTask<String, Void, String> {
        Context context;
        AlertDialog dialog;

        public bg_DeleteClient(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new AlertDialog.Builder(Client.this).create();
            dialog.setTitle("Modifier Client...");
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            String id = txtid.getText().toString();

            String connstr = "http://192.168.187.91/Expert/delete.php";
            try {
                URL url = new URL(connstr);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setDoInput(true);
                http.setDoOutput(true);
                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));

                String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
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
            if (s.contains("client deleted"))
                Toast.makeText(context, "client deleted", Toast.LENGTH_SHORT).show();


        }


    }


}
