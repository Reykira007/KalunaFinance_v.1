package com.kalunafinance.homesweetloan.ui.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.kalunafinance.homesweetloan.R;
import com.kalunafinance.homesweetloan.model.Transaction;
import com.kalunafinance.homesweetloan.ui.viewmodel.TransactionViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddTransactionActivity extends AppCompatActivity {

    private TransactionViewModel transactionViewModel;
    private MaterialButtonToggleGroup toggleGroup;
    private TextInputEditText editAmount;
    private TextInputEditText editDescription;
    private TextInputEditText editDate;
    private MaterialButton buttonSave;
    private Calendar calendar;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_add_transaction);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize ViewModel
        transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);

        // Initialize date formatter
        calendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", new Locale("id", "ID"));

        // Initialize views
        initViews();

        // Setup listeners
        setupListeners();
    }

    private void initViews() {
        toggleGroup = findViewById(R.id.toggle_transaction_type);
        editAmount = findViewById(R.id.edit_amount);
        editDescription = findViewById(R.id.edit_description);
        editDate = findViewById(R.id.edit_date);
        buttonSave = findViewById(R.id.button_save);

        // Set default date to today
        editDate.setText(dateFormatter.format(calendar.getTime()));

        // Select income by default
        toggleGroup.check(R.id.button_income);
    }

    private void setupListeners() {
        // Date picker
        editDate.setOnClickListener(v -> showDatePicker());

        // Save button
        buttonSave.setOnClickListener(v -> saveTransaction());
    }

    private void showDatePicker() {
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            editDate.setText(dateFormatter.format(calendar.getTime()));
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void saveTransaction() {
        // Validate input
        String amount = editAmount.getText().toString();
        String description = editDescription.getText().toString();
        String date = editDate.getText().toString();

        if (amount.isEmpty()) {
            editAmount.setError(getString(R.string.msg_error_amount));
            return;
        }

        if (description.isEmpty()) {
            editDescription.setError(getString(R.string.msg_error_description));
            return;
        }

        // Get transaction type
        String type = toggleGroup.getCheckedButtonId() == R.id.button_income ?
                "INCOME" : "EXPENSE";

        // Create transaction
        Transaction transaction = new Transaction(
                date,
                description,
                Double.parseDouble(amount),
                type
        );

        // Save transaction using ViewModel
        transactionViewModel.insert(transaction);
        Toast.makeText(this, R.string.msg_transaction_added, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}