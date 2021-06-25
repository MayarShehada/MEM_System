package com.birzeit.memsystem.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.birzeit.memsystem.Doctor.ParamedicInfoActivity;
import com.birzeit.memsystem.Models.ParamedicList;
import com.birzeit.memsystem.R;

import java.util.ArrayList;
import java.util.List;

public class ParamedicListAdapter extends RecyclerView.Adapter<ParamedicListAdapter.ViewHolder>{

    private Context context;
    private List<ParamedicList> list;

    public ParamedicListAdapter(Context context, List<ParamedicList> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ParamedicListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CardView v= (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_of_paramedic_view,parent,false);
        return new ParamedicListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ParamedicListAdapter.ViewHolder holder, int position) {
        ParamedicList paramedic = list.get(position);
        CardView cardView = holder.cardView;
        TextView txt = cardView.findViewById(R.id.paramedicText);

        txt.setText(paramedic.getFullname());

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ParamedicInfoActivity.class);

                intent.putExtra("paramadicFullName_data",paramedic.getFullname());
                intent.putExtra("email_data",paramedic.getEmail());
                intent.putExtra("phonenum_data",paramedic.getPhonenum());
                intent.putExtra("gender_data",paramedic.getGender());
                intent.putExtra("ambulanceid_data",paramedic.getAmbulanceid());
                intent.putExtra("doctorName_data",paramedic.getDoctorname());
                intent.putExtra("doctorEmail_data",paramedic.getDoctoremail());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filteredList(ArrayList<ParamedicList> filterList) {
        list = filterList;
        notifyDataSetChanged();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {

        private final CardView cardView;
        LinearLayout mainLayout;
        public ViewHolder(@NonNull CardView cardView) {
            super(cardView);
            this.cardView = cardView;
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
