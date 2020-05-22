package com.example.flupertask;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flupertask.room.AccessProductDatabase;
import com.example.flupertask.room.Stores;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View toolbar = findViewById(R.id.toolbar);
        TextView textView_heading = toolbar.findViewById(R.id.textView_heading);
        textView_heading.setText(getResources().getString(R.string.fluper_task));
        Button button_productOne = findViewById(R.id.button_productOne);
        Button button_productTwo = findViewById(R.id.button_productTwo);
        Button button_productThree = findViewById(R.id.button_productThree);
        Button button_showProducts = findViewById(R.id.button_showProducts);

        button_productOne.setOnClickListener(this);
        button_productTwo.setOnClickListener(this);
        button_productThree.setOnClickListener(this);
        button_showProducts.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.button_productOne:
                byte[] imageOne =  new Utility().convertImageToByte(BitmapFactory.decodeResource(getResources(), R.drawable.blue));
                createProduct(2000,getResources().getString(R.string.bag_one),getResources().getString(R.string.bag_one_desc),
                        "1200", "1000",imageOne,"Red","Blue","Black",
                        10001,"Reliance, Mega Mart","Kanpur","UP");
                break;
            case R.id.button_productTwo:
                byte[] imageTwo =  new Utility().convertImageToByte(BitmapFactory.decodeResource(getResources(), R.drawable.red));
                createProduct(2001,getResources().getString(R.string.bag_two),getResources().getString(R.string.bag_two_desc),"1400",
                        "1100",imageTwo,"Olive","Blue","Brown",
                        10002,"Big Bazaar, Spencers","Noida","UP");
                break;
            case R.id.button_productThree:
                byte[] imageThree = new Utility().convertImageToByte(BitmapFactory.decodeResource(getResources(), R.drawable.yellow));
                createProduct(2003,getResources().getString(R.string.bag_three),getResources().getString(R.string.bag_three_desc),"1000",
                        "900",imageThree,"Green","Olive","Black",
                        10003,"Mega Mart, Big Bazaar","Lucknow","UP");
                break;

            case R.id.button_showProducts:
                Intent intent = new Intent(MainActivity.this,ProductList.class);
                startActivity(intent);
                break;
        }
    }

    private void createProduct(int productId,String productName,String description,
                               String regularPrice,String salePrice,byte[] productImage,
                               String color_One,String color_Two,String color_Three,
                               int storeId,String storeName,String storeCity,
                               String storeState){
        try{
            List<String> colors = new ArrayList<>();
            JSONObject productJSON = new JSONObject();

            productJSON.put("id",productId);
            productJSON.put("name",productName);
            productJSON.put("description",description);
            productJSON.put("regularPrice",regularPrice);
            productJSON.put("salePrice",salePrice);
            productJSON.put("productImage",productImage);

            colors.add(color_One);
            colors.add(color_Two);
            colors.add(color_Three);

            Stores stores = new Stores();
            stores.setStoreId(storeId);
            stores.setStoreName(storeName);
            stores.setStoreCity(storeCity);
            stores.setStoreState(storeState);

            new AccessProductDatabase(MainActivity.this).actionsOnDatabase(1,
                                       productJSON.toString(),productImage,stores);
            Log.i("FLUPER",productJSON.toString());

        }catch(Exception e){
            e.printStackTrace();
        }

    }



}
