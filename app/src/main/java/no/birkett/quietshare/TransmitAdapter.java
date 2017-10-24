package no.birkett.quietshare;

import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import static android.support.v7.widget.RecyclerView.*;

/**
 * Created by Selva on 10/24/2017.
 */

public class TransmitAdapter extends RecyclerView.Adapter<TransmitAdapter.MyViewHolder>{

    private ArrayList<MessageObject> itemList;

    public TransmitAdapter(ArrayList<MessageObject> messageObject) {
        itemList = messageObject;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_layout, viewGroup,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        MessageObject msgobj = itemList.get(position);
        viewHolder.msg.setText(msgobj.getMessage());
        viewHolder.ts.setText(msgobj.getTimestamp());

    }


    public void add(MessageObject obj){
        itemList.add(obj);
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView msg;
        public TextView ts;

        public MyViewHolder(View itemView) {
            super(itemView);
            msg = (TextView) itemView.findViewById(R.id.received_content);
            ts = (TextView) itemView.findViewById(R.id.receive_status);
        }
    }
}
