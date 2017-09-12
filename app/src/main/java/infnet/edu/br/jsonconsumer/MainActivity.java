package infnet.edu.br.jsonconsumer;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    ArrayList<HashMap<String, String>> listaReqRes;
    public String urlApi = "https://reqres.in/api/users?page=2";
    public ListView lv;
    public String jsonStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("a","a");
        listaReqRes = new ArrayList<>();

        lv = (ListView) findViewById(R.id.lista);

        new pegaJson().execute();

    }


    /**
     * classe asynctask
     */
    private class pegaJson extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                HttpHandler sh = new HttpHandler();
                jsonStr = sh.makeServiceCall(urlApi);
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray contacts = jsonObj.getJSONArray("data");

                // looping through All Contacts
                for (int i = 0; i < contacts.length(); i++) {
                    HashMap<String, String> item = new HashMap<>();
                    JSONObject c = contacts.getJSONObject(i);
                    String id = c.getString("id");
                    String nome = c.getString("first_name");
                    String sobrenome = c.getString("last_name");
                    String avatar = c.getString("avatar");
                    item.put("id", id);
                    item.put("nome", nome);
                    item.put("sobrenome", sobrenome);
                    item.put("avatar", avatar);
                    listaReqRes.add(item);
                }
            } catch (JSONException j) {


            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, listaReqRes,
                    R.layout.item_lista, new String[]{"nome", "sobrenome",
                    "avatar"}, new int[]{
                        R.id.nome,
                        R.id.sobrenome,
                        R.id.avatar
                    });

            lv.setAdapter(adapter);
        }

    }

}
