package com.kalunafinance.homesweetloan.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.kalunafinance.homesweetloan.R;
import com.kalunafinance.homesweetloan.adapter.TransactionAdapter;
import com.kalunafinance.homesweetloan.ui.viewmodel.TransactionViewModel;

public class MainActivity extends AppCompatActivity {

    private TransactionViewModel transactionViewModel;
    private RecyclerView recyclerView;
    private TransactionAdapter adapter;
    private TextView textTotalBalance;
    private TextView textIncome;
    private TextView textExpense;
    private TextView textEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize views
        initViews();

        // Setup RecyclerView
        setupRecyclerView();

        // Setup ViewModel
        setupViewModel();

        // Setup FAB
        setupFab();
    }

    private void initViews() {
        textTotalBalance = findViewById(R.id.text_total_balance);
        textIncome = findViewById(R.id.text_income);
        textExpense = findViewById(R.id.text_expense);
        textEmpty = findViewById(R.id.text_empty);
        recyclerView = findViewById(R.id.recycler_transactions);
    }

    private void setupRecyclerView() {
        adapter = new TransactionAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }

    private void setupViewModel() {
        transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);

        // Observe transactions
        transactionViewModel.getAllTransactions().observe(this, transactions -> {
            adapter.setTransactions(transactions);
            showEmptyState(transactions == null || transactions.isEmpty());
        });

        // Observe income
        transactionViewModel.getTotalIncome().observe(this, income -> {
            if (income != null) {
                textIncome.setText(String.format("Rp %,.0f", income));
                updateBalance();
            }
        });

        // Observe expense
        transactionViewModel.getTotalExpense().observe(this, expense -> {
            if (expense != null) {
                textExpense.setText(String.format("Rp %,.0f", expense));
                updateBalance();
            }
        });
    }

    private void updateBalance() {
        Double income = transactionViewModel.getTotalIncome().getValue();
        Double expense = transactionViewModel.getTotalExpense().getValue();

        if (income != null && expense != null) {
            double balance = income - expense;
            textTotalBalance.setText(String.format("Rp %,.0f", balance));
        }
    }

    private void setupFab() {
        ExtendedFloatingActionButton fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddTransactionActivity.class);
            startActivity(intent);
        });
    }

    private void showEmptyState(boolean show) {
        if (show) {
            recyclerView.setVisibility(View.GONE);
            textEmpty.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            textEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_export) {
            // TODO: Implement Excel export
            Toast.makeText(this, "Export clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id == R.id.action_settings) {
            // TODO: Implement settings
            Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}