package com.example.clift.ui.tutor.ui.Request;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clift.MainActivity;
import com.example.clift.R;
import com.example.clift.data.LoginRepo;
import com.example.clift.ui.tutor.TutorActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Element;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<ListElement> mData;
    private LayoutInflater mInflater;
    private Context context;
    final ListAdapter.OnItemClickListener listener ;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public interface OnItemClickListener{
        void onItemClick(ListElement item);
    }


    public ListAdapter(List<ListElement> itemList, Context context, ListAdapter.OnItemClickListener listener){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.listener = listener;
    }

    @Override
    //devuelve el numero de elementos del arreglo
    public  int getItemCount() {return mData.size();}

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.from(parent.getContext()).inflate(R.layout.list_element,parent,false);
        return new ListAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder,final int position){
        holder.cv.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_transition));
        holder.bindData(mData.get(position));
    }
    //listado nuevo metodo
    public void setItems(List<ListElement> items){mData = items;}

    public  class  ViewHolder extends  RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView name, city, status;
        CardView cv;
        Button sendButton;
        String docId;

        ViewHolder(View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageview);
            name = itemView.findViewById(R.id.nameTextview);
            city = itemView.findViewById(R.id.cityTextview);
            status = itemView.findViewById(R.id.statusTextview);
            cv = itemView.findViewById(R.id.cv);
            sendButton = itemView.findViewById(R.id.button_send);

            sendButton.setOnClickListener(v -> {
                actualizarDocumento(docId);
            });
        }

        void bindData(final ListElement item){
            iconImage.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            name.setText(item.getName());
            city.setText(item.getCity());
            status.setText(item.getStatus());
            itemView.setOnClickListener(v -> listener.onItemClick(item));
            docId = item.getDocId();
        }
    }

    private void actualizarDocumento(String id) {

        LoginRepo loginRepo = LoginRepo.getInstance();

        db.collection("reuniones")
                .document(id)
                .update("estado", "asignado",
                        "correoTutor", loginRepo.getUser().getCorreo())
                .addOnSuccessListener(documentReference -> {
                    Log.d("Hurra", "actualizarDocumento: Actualizado");
                })
                .addOnFailureListener(e -> {
                    Log.d("Cacuteado", "actualizarDocumento: hijole");
                });
    }

}