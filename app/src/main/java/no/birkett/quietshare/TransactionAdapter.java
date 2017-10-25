package no.birkett.quietshare;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Selva on 10/25/2017.
 */

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {


    private ArrayList<TransactionObject> itemList;

    public TransactionAdapter(ArrayList<TransactionObject> transactionObject) {
        itemList = transactionObject;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.transaction_layout, viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        TransactionObject transObj = itemList.get(position);
        viewHolder.transferAmount.setText(transObj.getAmount());
        viewHolder.transferTime.setText(AppUtils.getTime());
        viewHolder.transferFrom.setText("Karthik");

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView transferFrom;
        public TextView transferTime;
        public TextView transferAmount;

        public ViewHolder(View itemView) {
            super(itemView);
            transferFrom = (TextView) itemView.findViewById(R.id.transfer_from);
            transferTime = (TextView) itemView.findViewById(R.id.transfer_timestamp);
            transferAmount = (TextView) itemView.findViewById(R.id.transfer_amount);
        }
    }


    public void add(TransactionObject object){
        itemList.add(object);
        notifyDataSetChanged();
    }

}
