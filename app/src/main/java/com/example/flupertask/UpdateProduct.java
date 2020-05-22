package com.example.flupertask;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.example.flupertask.room.Stores;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class UpdateProduct extends BottomSheetDialogFragment {

    public static UpdateProduct newInstance() {
        return new UpdateProduct();
    }

    public UpdateProduct() {}

    private int productId;
    private String name,desc,salePrice,regularPrice;
    private byte[] image;
    private OnUpdateListener onUpdateListener;
    private Stores stores;

    public interface OnUpdateListener{
        void updateProduct(int productId,String name,String desc,String salePrice,
                           String regularPrice,byte[] image,Stores stores);
    }

    public UpdateProduct(int productId, String name, String desc, String salePrice,
                         String regularPrice,byte[] image, Stores stores,
                         OnUpdateListener onUpdateListener) {
        this.productId = productId;
        this.name = name;
        this.desc = desc;
        this.salePrice = salePrice;
        this.image = image;
        this.regularPrice = regularPrice;
        this.onUpdateListener = onUpdateListener;
        this.stores = stores;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_update_product, container, false);

        final EditText editText_desc = view.findViewById(R.id.editText_desc);
        final EditText editText_productName = view.findViewById(R.id.editText_productName);
        final EditText editText_salePrice = view.findViewById(R.id.editText_salePrice);
        final EditText editText_regularPrice = view.findViewById(R.id.editText_regularPrice);
        Button button_update = view.findViewById(R.id.button_update);

        editText_productName.setText(name);
        editText_desc.setText(desc);
        editText_salePrice.setText(salePrice);
        editText_regularPrice.setText(regularPrice);

        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onUpdateListener.updateProduct(productId,editText_productName.getText().toString(),
                        editText_desc.getText().toString(),editText_salePrice.getText().toString(),
                        editText_regularPrice.getText().toString(),image,stores);
            }
        });

        return view;
    }

}
