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

import com.birzeit.memsystem.Doctor.PatientInfoActivity;
import com.birzeit.memsystem.Models.PatientList;
import com.birzeit.memsystem.R;

import java.util.ArrayList;
import java.util.List;

public class PatientListAdapter  extends RecyclerView.Adapter<PatientListAdapter.ViewHolder>{

    private Context context;
    private List<PatientList> list;

    public PatientListAdapter(Context context, List<PatientList> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PatientListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CardView v= (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_of_patient_view,parent,false);
        return new ViewHolder(v);
    }

    public void filteredList(ArrayList<PatientList> filterList) {
        list = filterList;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull PatientListAdapter.ViewHolder holder, int position) {
        PatientList check = list.get(position);
        CardView cardView = holder.cardView;
        TextView txt = cardView.findViewById(R.id.patientText);
        txt.setText(check.getFullname());

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PatientInfoActivity.class);

                String id = check.getId()+"";
                intent.putExtra("patientId_data",id);
                intent.putExtra("patientFullName_data",check.getFullname());
                intent.putExtra("email_data",check.getEmail());
                intent.putExtra("phonenum_data",check.getPhonenum());
                intent.putExtra("gender_data",check.getGender());
                intent.putExtra("address_data",check.getAddress());
                intent.putExtra("relative1_data",check.getRelative1());
                intent.putExtra("relative2_data",check.getRelative2());
                intent.putExtra("Doctor_NameData",check.getDoctor_fullName());
                intent.putExtra("Doctor_EmailData",check.getDoctor_email());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
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