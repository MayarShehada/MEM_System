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

import com.birzeit.memsystem.Doctor.DoctorNotificationHomeActivity;
import com.birzeit.memsystem.Models.DoctorNotification;
import com.birzeit.memsystem.R;

import java.util.ArrayList;
import java.util.List;

public class DoctorNotificationAdapter  extends RecyclerView.Adapter<com.birzeit.memsystem.Adapter.DoctorNotificationAdapter.ViewHolder>{

    private Context context;
    private List<DoctorNotification> list;

    public DoctorNotificationAdapter(Context context, List<DoctorNotification> list) {
        this.context = context;
        this.list = list;
    }

        @NonNull
        @Override
        public com.birzeit.memsystem.Adapter.DoctorNotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            CardView v= (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_notification_view,parent,false);
            return new com.birzeit.memsystem.Adapter.DoctorNotificationAdapter.ViewHolder(v);
        }

        public void filteredList(ArrayList<DoctorNotification> filterList) {
            list = filterList;
            notifyDataSetChanged();
        }

        @Override
        public void onBindViewHolder(@NonNull com.birzeit.memsystem.Adapter.DoctorNotificationAdapter.ViewHolder holder, int position) {
            DoctorNotification noti = list.get(position);
            CardView cardView = holder.cardView;

            TextView txt = cardView.findViewById(R.id.notification_title);
            txt.setText(noti.getTitle());

            TextView txt2 = cardView.findViewById(R.id.patientName);
            txt2.setText(noti.getPatientName());

            holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DoctorNotificationHomeActivity.class);

                    String id = noti.getId()+"";
                    intent.putExtra("notiId_data",id);
                    intent.putExtra("title_data",noti.getTitle());
                    intent.putExtra("doctorName_data",noti.getDoctorName());
                    intent.putExtra("patientName_data",noti.getPatientName());
                    intent.putExtra("checkId_data",noti.getCheckId());
                    intent.putExtra("date_data",noti.getDate());

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
