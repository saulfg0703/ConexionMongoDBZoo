package com.example.conexionmongodbzoo;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private ArrayList<Animal> arrayAnimales;
    private ListView vista;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrayAnimales = new ArrayList<>();
        getMongo();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, arrayAnimales);
        ListView listaComp = findViewById(R.id.listaAnimales);
        listaComp.setAdapter(adaptador);
        listaComp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intento=new Intent(MainActivity.this, MostrarAnimales.class);;
                intento.putExtra("posicion", position);
                intento.putExtra("particularidad", String.valueOf( arrayAnimales.get(position).getParticularidad()));
                intento.putExtra("origen", String.valueOf( arrayAnimales.get(position).getOrigen()));
                startActivity(intento);

            }
        });

    }
//}


    //CONEXION CON MONGODB
    private void getMongo() { // mongod --port 27017 --dbpath C:\MongoDB\data\db --bind_ip_all

        class GetMONGO extends AsyncTask<Void, Void, String> {
            //this method will be called before execution
            //you can display a progress bar or something
            //so that user can understand that he should wait
            //as network operation may take some time
            //Document doc;
            protected void onPreExecute() {
                super.onPreExecute();
            }

            //this method will be called after execution
            //so here we are displaying a toast with the json string
            @Override
            protected void onPostExecute(String jsonStr) {
                super.onPostExecute(jsonStr);
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                Log.e(TAG, "________________________json: " + jsonStr);
                System.out.println(jsonStr);
                Toast.makeText(getApplicationContext(), jsonStr, Toast.LENGTH_SHORT).show();
            }

            //in this method we are fetching the json string
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    String uri = "mongodb://192.168.167.72:27017";
                    MongoClient mongoClient = MongoClients.create(uri);
                    MongoDatabase db = mongoClient.getDatabase("Zoologico");
                    MongoCollection<Document> collection = db.getCollection("Animales");

                    collection.find().forEach(doc -> {
                        System.out.println(doc.toJson());
                        String particularidad = null;
                        String nombre = null;
                        String origen = null;
                        JSONObject jsonObject = new JSONObject(doc);
                        try {
                            particularidad = jsonObject.getString("particularidad");
                            nombre = jsonObject.getString("nombre");
                            origen = jsonObject.getString("origen");
                            //   Toast.makeText(MainActivity.this, "Se han obtenido los datos de mongo", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Animal animal = new Animal(particularidad, nombre, origen);
                        arrayAnimales.add(animal);

                        System.out.println("tamaño del array" +  arrayAnimales.size());

                    });

                    return uri;
                } catch (Exception e) {
                    e.printStackTrace();
                    e.getMessage();
                    return null;
                }

            }
        }

        //creating asynctask object and executing it
        GetMONGO getMongo = new GetMONGO();
        getMongo.execute();

       /* try {
            // MongoClient mongoClient = new MongoClient(new ServerAddress("localhost"));
            MongoClient mongoClient = new MongoClient("192.168.56.1", 27017);
            MongoDatabase db = mongoClient.getDatabase("adivinandoPalabras");
            MongoCollection<Document> collection = db.getCollection("palabras");
            Document doc = (Document) collection.find();
            System.out.println(doc.toJson());
            Toast.makeText(getApplicationContext(), "doc.toJson() ", Toast.LENGTH_SHORT).show();
            mongoClient.close();
        }catch (Exception e){e.printStackTrace();}   */
    }

    //FIN CONEXION CON MONGODB
}