package com.example.flupertask;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flupertask.adapter.Adapter_ProductList;
import com.example.flupertask.room.AccessProductDatabase;
import com.example.flupertask.room.Product;
import com.example.flupertask.room.Stores;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductList extends AppCompatActivity implements AccessProductDatabase.ProductListListener,
                                                              Adapter_ProductList.ProductActionListener,
                                                              UpdateProduct.OnUpdateListener{

    private RecyclerView recyclerView_productList;
    private UpdateProduct updateProduct;
    private int position;
    private Adapter_ProductList adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        View toolbar = findViewById(R.id.toolbar);
        TextView textView_heading = toolbar.findViewById(R.id.textView_heading);
        textView_heading.setText(getResources().getString(R.string.product_list));

        recyclerView_productList = findViewById(R.id.recyclerView_productList);
        new AccessProductDatabase(ProductList.this,this).getProductsFromDatabase();
    }

    @Override
    public void getProductList(List<Product> productList) {
        adapter = new Adapter_ProductList(ProductList.this,(ArrayList<Product>)productList,
                            this);
        recyclerView_productList.setAdapter(adapter);
    }

    @Override
    public void getPerformAction(int productId, String productName, String productDescription,String regularPrice,
                                 String salePrice,byte[] image,Stores stores, int actionType, int itemPosition) {
        try{
            switch (actionType)
            {
                case 2:
                    this.position = itemPosition;
                    updateProduct = new UpdateProduct(productId,productName,productDescription,
                                                      salePrice,regularPrice,image,stores,this);
                     updateProduct.show(getSupportFragmentManager(),"BOTTOM SHEET");
                    break;

                case 3:
                    String productData = deleteOrUpdateProduct(productId,productName,productDescription,salePrice,regularPrice);
                    new AccessProductDatabase(ProductList.this).actionsOnDatabase(actionType,productData,image,stores);
                    break;
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showProductInfo(byte[] image, String productName, String salePrice, String regularPrice,
                                String productDesc, String storeName) {
        Intent intent = new Intent(ProductList.this,ProductInfo.class);
        intent.putExtra("image",image);
        intent.putExtra("name",productName);
        intent.putExtra("salePrice",salePrice);
        intent.putExtra("regPrice",regularPrice);
        intent.putExtra("desc",productDesc);
        intent.putExtra("storeName",storeName);
        startActivity(intent);
    }


    @Override
    public void updateProduct(int productId, String name, String desc, String salePrice, String regularPrice,
                              byte[] image,Stores stores) {
        try{
            String productData = deleteOrUpdateProduct(productId,name,desc,salePrice,regularPrice);
            updateProduct.dismiss();
            adapter.onItemUpdate(position,productId,name,desc,salePrice,regularPrice,image,stores);
            this.position = -1;
            new AccessProductDatabase(ProductList.this).actionsOnDatabase(2,productData,image,stores);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private String deleteOrUpdateProduct(int productId, String name, String desc,
                                         String salePrice, String regularPrice){
        JSONObject productJSON=null;
        try{
            productJSON = new JSONObject();
            productJSON.put("id",productId);
            productJSON.put("name",name);
            productJSON.put("description",desc);
            productJSON.put("regularPrice",regularPrice);
            productJSON.put("salePrice",salePrice);
            productJSON.put("productImage","");
        }catch(Exception e){
            e.printStackTrace();
        }

        return  productJSON.toString();
    }
}
