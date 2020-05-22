package com.example.flupertask.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flupertask.R;
import com.example.flupertask.room.Product;
import com.example.flupertask.room.Stores;

import java.util.ArrayList;

public class Adapter_ProductList extends RecyclerView.Adapter<Adapter_ProductList.ViewHolder> {

    private Context mContext;
    private ArrayList<Product> mData = new ArrayList<>();
    private ProductActionListener productActionListener;

    public interface ProductActionListener{
        void getPerformAction(int productId,String productName,String productDescription,String regularPrice,
                              String salePrice, byte[] image,Stores stores,int actionType, int position);
        void showProductInfo(byte[] image,String productName,String salePrice,String regularPrice,String productDesc,
                             String storeName);
    }
    public Adapter_ProductList(Context mContext, ArrayList<Product> mData, ProductActionListener productActionListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.productActionListener = productActionListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
        View view = inflater.inflate(R.layout.list_product,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.textView_productName.setText(mData.get(position).getName());
        holder.textView_stores.setText(mData.get(position).getStores().getStoreName());
        holder.textView_salePrice.setText(mContext.getResources().getString(R.string.rs)+ mData.get(position).getSalePrice());
        holder.textView_regularPrice.setText(mContext.getResources().getString(R.string.rs)+ mData.get(position).getRegularPrice());
        Bitmap bitmap = convertByteToBitmap(mData.get(position).getImage());
        holder.imageView_productImage.setImageBitmap(bitmap);

        holder.button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productActionListener.getPerformAction(mData.get(holder.getAdapterPosition()).getId(),
                        mData.get(holder.getAdapterPosition()).getName(),
                        mData.get(holder.getAdapterPosition()).getDescription(),
                        mData.get(holder.getAdapterPosition()).getRegularPrice(),
                        mData.get(holder.getAdapterPosition()).getSalePrice(),
                        mData.get(holder.getAdapterPosition()).getImage(),
                        mData.get(holder.getAdapterPosition()).getStores(),
                        3,holder.getAdapterPosition());
                removeItem(holder.getAdapterPosition());
            }
        });

        holder.button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productActionListener.getPerformAction(mData.get(holder.getAdapterPosition()).getId(),
                        mData.get(holder.getAdapterPosition()).getName(),
                        mData.get(holder.getAdapterPosition()).getDescription(),
                        mData.get(holder.getAdapterPosition()).getRegularPrice(),
                        mData.get(holder.getAdapterPosition()).getSalePrice(),
                        mData.get(holder.getAdapterPosition()).getImage(),
                        mData.get(holder.getAdapterPosition()).getStores(),
                        2,holder.getAdapterPosition());
            }
        });

        holder.cardView_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productActionListener.showProductInfo(mData.get(holder.getAdapterPosition()).getImage(),
                        mData.get(holder.getAdapterPosition()).getName(),
                        mData.get(holder.getAdapterPosition()).getSalePrice(),
                        mData.get(holder.getAdapterPosition()).getRegularPrice(),
                        mData.get(holder.getAdapterPosition()).getDescription(),
                        mData.get(holder.getAdapterPosition()).getStores().getStoreName());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView_productName,textView_salePrice,textView_stores,
                textView_regularPrice;
        private Button button_update,button_delete;
        private ImageView imageView_productImage;
        private CardView cardView_product;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView_productName = itemView.findViewById(R.id.textView_productName);
            textView_salePrice = itemView.findViewById(R.id.textView_salePrice);
            textView_stores = itemView.findViewById(R.id.textView_stores);
            textView_regularPrice = itemView.findViewById(R.id.textView_regularPrice);
            imageView_productImage = itemView.findViewById(R.id.imageView_productImage);
            button_delete = itemView.findViewById(R.id.button_delete);
            button_update = itemView.findViewById(R.id.button_update);
            cardView_product = itemView.findViewById(R.id.cardView_product);
        }
    }

    private void removeItem(int position){
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public void onItemUpdate(int position,int productId, String name,String desc,
                             String sale,String regular, byte[] image,Stores stores){
        Product updateProduct = new Product(productId,name,desc,regular,sale,image,stores);
        mData.set(position,updateProduct);
        notifyItemChanged(position);
    }

    private Bitmap convertByteToBitmap(byte[] data){
        return  BitmapFactory.decodeByteArray(data, 0, data.length);
    }
}
