package com.birzeit.memsystem.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.birzeit.memsystem.Doctor.CheckInfoActivity;
import com.birzeit.memsystem.Models.Check;
import com.birzeit.memsystem.Patient.CheckActivity;
import com.birzeit.memsystem.R;

import java.util.ArrayList;
import java.util.List;

public class CheckAdapter  extends RecyclerView.Adapter<CheckAdapter.ViewHolder>{

    private Context context;
    private List<Check> checks;

    public CheckAdapter(Context context, List<Check> checks) {
        this.context = context;
        this.checks = checks;
    }

    @NonNull
    @Override
    public CheckAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CardView v= (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_of_checks_view,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Check check = checks.get(position);
        CardView cardView = holder.cardView;

        TextView txt = cardView.findViewById(R.id.checkText);
        txt.setText(check.getDateOfCheck());

        ImageView img_view = cardView.findViewById(R.id.img_view);

        if(check.getRole().equals("Doctor")){
            if(check.getFlag().equals("0")) {
                holder.mainLayout.setBackgroundResource(R.drawable.box3);
                img_view.setBackgroundResource(R.drawable.greencheck);
                holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, CheckInfoActivity.class);

                        intent.putExtra("checkId_data", check.getCheckId());
                        intent.putExtra("heartBeat_data", check.getHeartBeat());
                        intent.putExtra("bodyTemp_data", check.getBodyTemp());
                        intent.putExtra("bloodPressure_data", check.getBloodPressure());
                        intent.putExtra("dateOfCheck_data", check.getDateOfCheck());
                        intent.putExtra("fullnameData", check.getFullname());
                        intent.putExtra("emailData", check.getEmail());

                        context.startActivity(intent);
                    }
                });
            }else if (check.getFlag().equals("1")) {
                holder.mainLayout.setBackgroundResource(R.drawable.box3);
                img_view.setBackgroundResource(R.drawable.redcheck);
                holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, CheckInfoActivity.class);

                        intent.putExtra("checkId_data", check.getCheckId());
                        intent.putExtra("heartBeat_data", check.getHeartBeat());
                        intent.putExtra("bodyTemp_data", check.getBodyTemp());
                        intent.putExtra("bloodPressure_data", check.getBloodPressure());
                        intent.putExtra("dateOfCheck_data", check.getDateOfCheck());
                        intent.putExtra("fullnameData", check.getFullname());
                        intent.putExtra("emailData", check.getEmail());

                        context.startActivity(intent);
                    }
                });
            }
        }else if(check.getRole().equals("Patient")) {
            if(check.getFlag().equals("0")) {
                holder.mainLayout.setBackgroundResource(R.drawable.box2);
                img_view.setBackgroundResource(R.drawable.greencheck);
                holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, CheckActivity.class);

                        intent.putExtra("checkId_data", check.getCheckId());
                        intent.putExtra("heartBeat_data", check.getHeartBeat());
                        intent.putExtra("bodyTemp_data", check.getBodyTemp());
                        intent.putExtra("bloodPressure_data", check.getBloodPressure());
                        intent.putExtra("dateOfCheck_data", check.getDateOfCheck());
                        intent.putExtra("fullnameData", check.getFullname());
                        intent.putExtra("emailData", check.getEmail());

                        context.startActivity(intent);
                    }
                });
            }else if (check.getFlag().equals("1")) {
                    holder.mainLayout.setBackgroundResource(R.drawable.box2);
                    img_view.setBackgroundResource(R.drawable.redcheck);
                    holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, CheckActivity.class);

                            intent.putExtra("checkId_data", check.getCheckId());
                            intent.putExtra("heartBeat_data", check.getHeartBeat());
                            intent.putExtra("bodyTemp_data", check.getBodyTemp());
                            intent.putExtra("bloodPressure_data", check.getBloodPressure());
                            intent.putExtra("dateOfCheck_data", check.getDateOfCheck());
                            intent.putExtra("fullnameData", check.getFullname());
                            intent.putExtra("emailData", check.getEmail());

                            context.startActivity(intent);
                        }
                    });
                }
            }
        }


    @Override
    public int getItemCount() {
        return checks.size();
    }

    public void filteredList(ArrayList<Check> filterList) {
        checks = filterList;
        notifyDataSetChanged();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {

        private final CardView cardView;
        ConstraintLayout mainLayout;
        public ViewHolder(@NonNull CardView cardView) {
            super(cardView);
            this.cardView = cardView;
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
