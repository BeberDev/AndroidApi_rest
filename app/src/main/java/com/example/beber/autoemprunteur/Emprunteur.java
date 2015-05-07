package com.example.beber.autoemprunteur;

import android.database.MatrixCursor;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class Emprunteur extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empru);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    AffichageJson();
        Log.v("create","bla");
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_empru, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public static ArrayList<Client> getClients() {

        ArrayList<Client> clients = new ArrayList<Client>();

        try {
            String myurl= "http://localhost/api_rest-master/back/emprunteur";

            URL url = new URL(myurl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            /*
             * com.example.beber.autoemprunteur.InputStreamOperations est une classe complémentaire:
             * Elle contient une méthode InputStreamToString.
             */
            String result = InputStreamOperations.InputStreamToString(inputStream);

            // On récupère le JSON complet
            JSONObject jsonObject = new JSONObject(result);
            // On récupère le tableau d'objets qui nous concernent
            JSONArray array = new JSONArray(result);
            Log.v("create","json");
            // Pour tous les objets on récupère les infos
            for (int i = 0; i < array.length(); i++) {
                Log.v("create","bouclejson");
                // On récupère un objet JSON du tableau
                JSONObject obj = new JSONObject(array.getString(i));
                // On fait le lien Client - Objet JSON
                Client client = new Client();
                client.setIdemprunteur(obj.getInt("idemprunteur"));
                client.setNom(obj.getString("nom"));
                client.setPrenom(obj.getString("prenom"));
                // On ajoute la client à la liste
                clients.add(client);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        // On retourne la liste des personnes
        return clients;
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_empru, container, false);
            return rootView;
        }
    }

    public  void AffichageJson(){
        // données du tableau
        Log.v("create","marche !");
        ArrayList<Client>   clients = getClients();
        int taille = clients.size();

        final String [] nom = new String[taille];
        final String [] prenom = new String[taille];
        final int    [] id = new int[taille];
        for (int i=0;i<taille;i++){
            Client client = clients.get(i);
            nom[i] = client.getNom();
            prenom[i] = client.getPrenom();
            id[i] = client.getIdemprunteur();
            Log.v("create","tableau remplis i=" + i);
        }
        TableLayout table = (TableLayout) findViewById(R.id.idTable); // on prend le tableau défini dans le layout
        TableRow row; // création d'un élément : ligne
        TextView tv1,tv2; // création des cellules

// pour chaque ligne
        for(int i=0;i<nom.length;i++) {
            row = new TableRow(this); // création d'une nouvelle ligne
            final int nb = id[i];
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // faites ici ce que vous voulez
                    Log.d("mydebug", "i=" + nb);
                }
            });

            tv1 = new TextView(this); // création cellule
            tv1.setText(nom[i]); // ajout du texte
            tv1.setGravity(Gravity.CENTER); // centrage dans la cellule
            // adaptation de la largeur de colonne à l'écran :
            tv1.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

            // idem 2ème cellule
            tv2 = new TextView(this);
            tv2.setText(prenom[i]);
            tv2.setGravity(Gravity.CENTER);
            tv2.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

            // ajout des cellules à la ligne
            row.addView(tv1);
            row.addView(tv2);

            // ajout de la ligne au tableau
            table.addView(row);
        }
        }
}

