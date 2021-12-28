package com.susmoy.roomdatabasebasics;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
//import androidx.databinding.Observable;
import androidx.recyclerview.widget.RecyclerView;

//import com.susmoy.roomdatabasebasics.databinding.ActivityMainBinding;

import java.util.List;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder> {
    private List<ListData> listDataList;
    private Activity context;
    private RoomDBContext databaseContext;

    public MainRecyclerAdapter( Activity context,List<ListData> listDataList) {
        this.listDataList = listDataList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_items, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListData listData = listDataList.get(position);
        databaseContext = RoomDBContext.getInstance(context);
        holder.textView.setText(listData.getText());
        holder.btnEdit.setOnClickListener(v -> {
            ListData data  = listDataList.get(holder.getAdapterPosition());
            int sID = data.getID();
            String sText = data.getText();
            Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.data_update_dialog);
            int width = WindowManager.LayoutParams.MATCH_PARENT;
            int height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width,height);
            dialog.show();

            EditText editText = dialog.findViewById(R.id.edit_text);
            Button btnUpdate = dialog.findViewById(R.id.btnUpdate);

            editText.setText(sText);

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     dialog.dismiss();
                     String uText = editText.getText().toString().trim();
                     databaseContext.listDataDao().update(sID,uText);
                     listDataList.clear();
                     listDataList.addAll(databaseContext.listDataDao().getAll());
                     notifyDataSetChanged();
                 }
             });
        });

        holder.btnDelete.setOnClickListener(v->{
            ListData data = listDataList.get(holder.getAdapterPosition());
            databaseContext.listDataDao().delete(data);
            int pos = holder.getAdapterPosition();
            listDataList.remove(pos);
            notifyItemRemoved(pos);
            notifyItemRangeChanged(pos, listDataList.size());

        });
    }

    @Override
    public int getItemCount() {
        return listDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.text_view);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
