package com.example.flupertask.room;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.room.Room;

import org.json.JSONObject;

import java.util.List;

public class AccessProductDatabase {

    private String DB_NAME="db_product";
    private ProductDatabase productDatabase;
    private List<Product> productData;
    private int deleteResponse=0,updateProduct=0;
    private ProductListListener productListListener;

    public interface ProductListListener{
        void getProductList(List<Product> productList);
    }
    public AccessProductDatabase(Context mContext){
        this.productDatabase = Room.databaseBuilder(mContext,ProductDatabase.class,DB_NAME).build();
    }

    public AccessProductDatabase(Context mContext,ProductListListener productListListener){
        this.productListListener = productListListener;
        this.productDatabase = Room.databaseBuilder(mContext,ProductDatabase.class,DB_NAME).build();
    }

    public void actionsOnDatabase(int actionType,String productInfo,byte[] image,Stores stores){
        try{
            JSONObject productObject = new JSONObject(productInfo);
            int productId= productObject.optInt("id",0);
            String productName = productObject.optString("name","");
            String description = productObject.optString("description","");
            String regularPrice = productObject.optString("regularPrice","");
            String salePrice = productObject.optString("salePrice","");
            Product product = new Product(productId,productName,description,regularPrice,
                                         salePrice,image,stores);

            switch (actionType)
            {
                case 1:
                    new CreateProduct().execute(product);
                    break;
                case 2:
                    new UpdateProducts().execute(product);
                    break;
                case 3:
                    new DeleteProduct().execute(product);
                    break;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public void getProductsFromDatabase(){
        new FetchProducts().execute();
    }

    private class CreateProduct extends AsyncTask<Product,Void,Void>{

        @Override
        protected Void doInBackground(Product... products) {
            Product productInfo = products[0];
            productDatabase.productAccess().insertProducts(productInfo);
            return null;
        }
    }

    /*private class DeleteProduct extends AsyncTask<Integer,Void,String>{

        @Override
        protected String doInBackground(Integer... integers) {
            int productId = integers[0];
            deleteResponse = productDatabase.productAccess().deleteByProductId(productId);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(deleteResponse == 1){
                Log.i("FLUPER","DELETED");
            }else{
                Log.i("FLUPER","DELETION FAILED");
            }
        }
    }*/

    private class DeleteProduct extends AsyncTask<Product,Void,String>{

        @Override
        protected String doInBackground(Product... products) {
            Product productInfo = products[0];
            deleteResponse = productDatabase.productAccess().deleteProduct(productInfo);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(deleteResponse == 1){
                Log.i("FLUPER","DELETED");
            }else{
                Log.i("FLUPER","DELETION FAILED");
            }
        }
    }

    private class FetchProducts extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            productData = productDatabase.productAccess().getAllProducts();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            productListListener.getProductList(productData);
        }
    }

    private class UpdateProducts extends AsyncTask<Product,Void,Void>{

        @Override
        protected Void doInBackground(Product... products) {
            Product productInfo = products[0];
            updateProduct = productDatabase.productAccess().updateProduct(productInfo);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(updateProduct == 1){
                Log.i("FLUPER","UPDATED");
            }else{
                Log.i("FLUPER","UPDATION FAILED");
            }
        }
    }
}
