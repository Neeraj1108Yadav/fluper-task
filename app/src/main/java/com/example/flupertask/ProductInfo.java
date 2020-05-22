package com.example.flupertask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProductInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);

        View toolbar = findViewById(R.id.toolbar);
        TextView textView_heading = toolbar.findViewById(R.id.textView_heading);
        textView_heading.setText(getResources().getString(R.string.product_info));

        ImageView imageView_product = findViewById(R.id.imageView_product);
        TextView textView_productName = findViewById(R.id.textView_productName);
        TextView textView_salePrice = findViewById(R.id.textView_salePrice);
        TextView textView_regularPrice = findViewById(R.id.textView_regularPrice);
        TextView textView_desc = findViewById(R.id.textView_desc);
        TextView textView_stores = findViewById(R.id.textView_stores);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            byte[] image = bundle.getByteArray("image");
            String name = bundle.getString("name","");
            String salePrice = bundle.getString("salePrice","");
            String regPrice = bundle.getString("regPrice","");
            String desc = bundle.getString("desc","");
            String storeName = bundle.getString("storeName","");

            imageView_product.setImageBitmap(convertByteToBitmap(image));
            textView_productName.setText(name);
            textView_salePrice.setText(getResources().getString(R.string.rs)+salePrice);
            textView_regularPrice.setText(getResources().getString(R.string.rs)+regPrice);
            textView_desc.setText(desc);
            textView_stores.setText(storeName);
        }
    }

    private Bitmap convertByteToBitmap(byte[] data){
        return  BitmapFactory.decodeByteArray(data, 0, data.length);
    }
}
