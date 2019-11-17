package com.example.okkeskilinc.otel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.okkeskilinc.otel.Classes.Rooms;
import com.example.okkeskilinc.otel.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.List;


public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {
    List<Rooms> roomsList;
    LayoutInflater inflater;
    Context context;

    public RoomAdapter(Context context, List<Rooms> rooms){
        inflater=LayoutInflater.from(context);
        this.roomsList=rooms;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.room_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Rooms selectedRoom=roomsList.get(position);
        holder.setData(selectedRoom,position);
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRoom(roomsList.get(position).getId());
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRoom(roomsList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return roomsList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView roomName,roomDurum,roomKat;
        ImageView del,edit;
        public ViewHolder(View v) {
            super(v);
            roomKat=(TextView)v.findViewById(R.id.r_kat);
            roomDurum=(TextView)v.findViewById(R.id.tv_r_durum);
            roomName=(TextView)v.findViewById(R.id.r_no);
            del=(ImageView) v.findViewById(R.id.delete);
            edit=(ImageView)v.findViewById(R.id.edit);
            //del.setOnClickListener(this);
           // edit.setOnClickListener(this);
            v.setOnClickListener(this);
        }

        public void setData(Rooms selectedRoom,int position) {
            this.roomName.setText(selectedRoom.getNo());
            this.roomKat.setText(selectedRoom.getkatNo());
            this.roomDurum.setText(selectedRoom.getDurum());
        }

        @Override
        public void onClick(View v) {
        }
    }
    private Rooms updaterooms;
    private void updateRoom(final Rooms urooms){
        updaterooms=urooms;
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        builder.setView(dialogView);

        final EditText etNo = (EditText) dialogView.findViewById(R.id.et_r_no);
        final EditText etType = (EditText) dialogView.findViewById(R.id.et_r_type);
        final EditText etKat = (EditText) dialogView.findViewById(R.id.et_r_kat);
        final EditText etDurum = (EditText) dialogView.findViewById(R.id.et_r_durum);
        final Button btnRguncelle=(Button)dialogView.findViewById(R.id.btn_r_guncelle);

        etNo.setText(updaterooms.getNo());
        etType.setText(updaterooms.getType());
        etKat.setText(updaterooms.getkatNo());
        etDurum.setText(updaterooms.getDurum());

        builder.setTitle("Room Düzenleme");
        final AlertDialog alert=builder.create();
        alert.show();

        btnRguncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference dR=FirebaseDatabase.getInstance().getReference("Rooms").child(updaterooms.getId());
                Rooms updateRoom=new Rooms(updaterooms.getId(),etNo.getText().toString(),etKat.getText().toString(),etType.getText().toString(),etDurum.getText().toString());
                dR.setValue(updateRoom);
                alert.dismiss();
                Toast.makeText(context,"Room Updated.",Toast.LENGTH_LONG).show();
            }
        });
     }
    private void deleteRoom(String id){
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Rooms").child(id);
        dR.removeValue();
        Toast.makeText(context ,"Oda Silindi", Toast.LENGTH_SHORT).show();
    }
}