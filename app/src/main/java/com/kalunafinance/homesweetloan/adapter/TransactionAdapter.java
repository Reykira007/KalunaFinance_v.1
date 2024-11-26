package com.kalunafinance.homesweetloan.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.kalunafinance.homesweetloan.R;
import com.kalunafinance.homesweetloan.model.Transaction;
import java.util.ArrayList;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private List<Transaction> transactions = new ArrayList<>();

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction currentTransaction = transactions.get(position);
        holder.bind(currentTransaction);
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
        notifyDataSetChanged();
    }

    static class TransactionViewHolder extends RecyclerView.ViewHolder {
        private final ImageView typeImageView;
        private final TextView descriptionTextView;
        private final TextView dateTextView;
        private final TextView amountTextView;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            typeImageView = itemView.findViewById(R.id.image_transaction_type);
            descriptionTextView = itemView.findViewById(R.id.text_description);
            dateTextView = itemView.findViewById(R.id.text_date);
            amountTextView = itemView.findViewById(R.id.text_amount);
        }

        public void bind(Transaction transaction) {
            descriptionTextView.setText(transaction.getDescription());
            dateTextView.setText(transaction.getDate());

            // Set icon based on transaction type
            if (transaction.getType().equals("EXPENSE")) {
                typeImageView.setImageResource(R.drawable.ic_expense);
                amountTextView.setTextColor(itemView.getContext().getResources()
                        .getColor(R.color.expense_red));
                amountTextView.setText(String.format("- Rp %,.0f", transaction.getAmount()));
            } else {
                typeImageView.setImageResource(R.drawable.ic_income);
                amountTextView.setTextColor(itemView.getContext().getResources()
                        .getColor(R.color.income_green));
                amountTextView.setText(String.format("+ Rp %,.0f", transaction.getAmount()));
            }
        }
    }
}
