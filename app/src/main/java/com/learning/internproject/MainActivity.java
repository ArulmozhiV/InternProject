package com.learning.internproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String json = getJSONfromAsset();
        List<DataObject> dataObjects = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                DataObject dataObj = new DataObject();
                MediaObject mediaObj = new MediaObject();

                if(jsonObject.has("title"))
                    dataObj.title = jsonObject.getString("title");

                if(jsonObject.has("text"))
                    dataObj.text = jsonObject.getString("text");

                if(jsonObject.has("media")){
                    JSONObject mediaJSONObj = jsonObject.getJSONObject("media");
                    JSONArray dataArray;

                    if(mediaJSONObj.has("image")){
                        dataArray = mediaJSONObj.getJSONArray("image");
                        if(dataArray.length()>0){
                            mediaObj.image = jsonToStringArray(dataArray);
                        }
                    }

                    if(mediaJSONObj.has("video")){
                        dataArray = mediaJSONObj.getJSONArray("video");
                        if(dataArray.length()>0){
                            mediaObj.video = jsonToStringArray(dataArray);
                        }
                    }
                    dataObj.setMedia(mediaObj);
                }


                dataObjects.add(dataObj);
            }

            recyclerView = (RecyclerView)findViewById(R.id.verticalRV);
            RecyclerView.Adapter verticalAdapter = new VerticalAdapter(MainActivity.this,dataObjects);
            recyclerView.setAdapter(verticalAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));



        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public String getJSONfromAsset(){
        String json=null;
        try{

            InputStream inputStream = getAssets().open("data.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer,"UTF-8");

        }catch(Exception e){
            e.printStackTrace();
        }

        return json;
    }

    public String[] jsonToStringArray(JSONArray dataArray){
        String[] strArray = new String[dataArray.length()];
        for(int j=0;j<dataArray.length();j++){
            try {
                strArray[j] = dataArray.getString(j);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return  strArray;
    }
}
