package com.cottoncalc.vivek.cottoncalculatorpk;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Hitu on 14/07/2015.
 */
public class import_Fragment extends Fragment implements View.OnClickListener, TextWatcher, View.OnFocusChangeListener{
    View rootView;

    private EditText foreignRate;
    private EditText exchangeRate;
    private EditText portCost;
    private EditText expensesPer;
    private EditText expenses;
    private EditText factoryCost;

    private TextView foreignRateTxt;
    private TextView exchangeRateTxt;
    private TextView portCostTxt;
    private TextView expensesTxt;
    private TextView factoryCostTxt;

   private TextView foreignRateUnit;
    private TextView exchangeRateUnit;
    private TextView portCostUnit;
    private TextView expensesPerUnit;
    private TextView expensesUnit;
    private TextView factoryCostUnit;

    double foreignRateDouble =0;
    double exchangeRateDouble =0;
    double factoryCostDouble =0;
    double portCostDouble =0;
    double expensesPerDouble=0;
    double expensesDouble =0;

    private Switch mySwitch;
    private Boolean switchReverseImport;
    List<EditText> editTextList = new ArrayList<EditText>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.import_layout,container,false);

        Button shr = (Button) rootView.findViewById(R.id.importShrBtn);
        shr.setOnClickListener(this);

        Button reset = (Button) rootView.findViewById(R.id.importRstBtn);
        reset.setOnClickListener(this);

        Button fullVersion = (Button) rootView.findViewById(R.id.gotoFullVersionI);
        fullVersion.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.importShrBtn:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                Button s = (Button) getView().findViewById(R.id.importShrBtn);
                s.setEnabled(false);
                String shareBody;

                String foreignRateS = foreignRate.getText().toString();
                String exchangeRateS = exchangeRate.getText().toString();
                String expensePerS = expensesPer.getText().toString();
                String portCostS = portCost.getText().toString();
                String factoryCostS = factoryCost.getText().toString();
                String expenseS = expenses.getText().toString();

                exchangeRateUnit = (TextView) getView().findViewById(R.id.exchangeRateUnit);
                String exchangeRateUnitS = exchangeRateUnit.getText().toString();
                foreignRateUnit = (TextView) getView().findViewById(R.id.foreignRateUnit);
                String foreignRateUnitS = foreignRateUnit.getText().toString();
                expensesUnit = (TextView) getView().findViewById(R.id.expensesUnit);
                String expensesUnitS = expensesUnit.getText().toString();

                factoryCostUnit = (TextView) getView().findViewById(R.id.FactoryCostUnit);
                String factoryCostUnitS = factoryCostUnit.getText().toString();

                shareBody = "Cotton Import Rate - " + foreignRateS + " " + foreignRateUnitS + "\nExchange Rate - " + exchangeRateS + " " + exchangeRateUnitS + "\nExpenses % - " +expensePerS + " %\nExpenses - " + expenseS + " " + expensesUnitS + "\nCost at Factory - " + factoryCostS + " " + factoryCostUnitS + "\n\nShared via Cotton Calculator PK";

                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                sharingIntent.setPackage("com.whatsapp");
                startActivity(sharingIntent);
                break;

            case R.id.importRstBtn:
                Button b = (Button) getView().findViewById(R.id.importRstBtn);
                b.setEnabled(false);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(false);
                builder.setTitle("Confirm");
                builder.setMessage("Reset all Values?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        foreignRate.setText("");
                        portCost.setText("");
                        exchangeRate.setText("");
                        expenses.setText("");
                        expensesPer.setText("");
                        foreignRate.requestFocus();

                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(foreignRate, InputMethodManager.SHOW_IMPLICIT);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ButtonState();
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                break;

            case R.id.gotoFullVersionI:
                Intent myIntent = new Intent(getActivity().getApplicationContext(), aboutUs.class);
                startActivity(myIntent);
                break;
        }
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final SharedPreferences appdata = PreferenceManager.getDefaultSharedPreferences(this.getActivity());

        foreignRate = (EditText) getView().findViewById(R.id.foreignRateEditTxt);
        foreignRate.setText(appdata.getString("foreignRateSPi", ""));
        foreignRate.addTextChangedListener(this);
        foreignRate.setOnFocusChangeListener(this);

        foreignRateTxt = (TextView) getView().findViewById(R.id.foreignRateTxt);
        factoryCostTxt = (TextView) getView().findViewById(R.id.factoryCostTxt);
        foreignRateUnit = (TextView) getView().findViewById(R.id.foreignRateUnit);
        factoryCostUnit = (TextView) getView().findViewById(R.id.FactoryCostUnit);

        exchangeRate = (EditText) getView().findViewById(R.id.exchangeRateEditTxt);
        exchangeRate.setText(appdata.getString("exchangeRateSPi", ""));
        exchangeRate.addTextChangedListener(this);
        exchangeRate.setOnFocusChangeListener(this);

        portCost = (EditText) getView().findViewById(R.id.portCostEditTxt);
        portCost.setText(appdata.getString("portCostSPi", ""));
        portCost.addTextChangedListener(this);
        portCost.setOnFocusChangeListener(this);

        expenses = (EditText) getView().findViewById(R.id.expensesEditTxt);
        expenses.setText(appdata.getString("expenseSPi", ""));
        expenses.addTextChangedListener(this);
        expenses.setOnFocusChangeListener(this);

        expensesPer = (EditText) getView().findViewById(R.id.expensesPerEditText);
        expensesPer.setText(appdata.getString("expensePerSPi", ""));
        expensesPer.addTextChangedListener(this);
        expensesPer.setOnFocusChangeListener(this);

        factoryCost = (EditText) getView().findViewById(R.id.factoryCostEditTxt);
        factoryCost.setText(appdata.getString("factoryCostSPi", ""));


        TableRow padtarAnsRow = (TableRow) getView().findViewById(R.id.importAnsRow);
        TableRow padtarBtnRow = (TableRow) getView().findViewById(R.id.importBtnRow);
        TableRow padtarExpireAns = (TableRow) getView().findViewById(R.id.importTrialExpire1);
        TableRow padtarExpireBtn = (TableRow) getView().findViewById(R.id.importTrialExpire2);
        boolean trialExpired = appdata.getBoolean("trialExpired",false);
        if(trialExpired)
        {
            padtarAnsRow.setVisibility(View.GONE);
            padtarBtnRow.setVisibility(View.GONE);
            padtarExpireAns.setVisibility(View.VISIBLE);
            padtarExpireBtn.setVisibility(View.VISIBLE);
        }
        else
        {
            padtarExpireAns.setVisibility(View.GONE);
            padtarExpireBtn.setVisibility(View.GONE);
            padtarAnsRow.setVisibility(View.VISIBLE);
            padtarBtnRow.setVisibility(View.VISIBLE);
        }

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(foreignRate, InputMethodManager.SHOW_IMPLICIT);
        ButtonState();

        editTextList.add(foreignRate);
        editTextList.add(exchangeRate);
        editTextList.add(portCost);
        editTextList.add(expenses);
        editTextList.add(expensesPer);

        mySwitch = (Switch) getView().findViewById(R.id.switch2);
        switchReverseImport = appdata.getBoolean("switchReverseImport",false);
        mySwitch.setChecked(switchReverseImport);
        if(switchReverseImport)
        {
            mySwitch.setText("Reverse :");
            foreignRateTxt.setText("Cotton Rate");
            factoryCostTxt.setText("Import Rate");

            foreignRateUnit.setText("Rs./Maund");
            factoryCostUnit.setText("USC/LBS");
        }
        else
        {
            mySwitch.setText("Forward :");
            foreignRateTxt.setText("Import Rate");
            factoryCostTxt.setText("Landed Cost");

            foreignRateUnit.setText("USC/LBS");
            factoryCostUnit.setText("Rs./Maund");
        }
        //attach a listener to check for changes in state
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(foreignRate, InputMethodManager.SHOW_IMPLICIT);
                factoryCostTxt.setText("0.00");
                foreignRate.setText("");
                if(isChecked)
                {
                    mySwitch.setText("Reverse :");
                    foreignRateTxt.setText("Cotton Rate");
                    factoryCostTxt.setText("Import Rate");

                    foreignRateUnit.setText("Rs./Maund");
                    factoryCostUnit.setText("USC/LBS");
                }
                else
                {
                    mySwitch.setText("Forward :");
                    foreignRateTxt.setText("Import Rate");
                    factoryCostTxt.setText("Landed Cost");

                    foreignRateUnit.setText("USC/LBS");
                    factoryCostUnit.setText("Rs./Maund");
                }
                SharedPreferences.Editor editor = appdata.edit();
                editor.putBoolean("switchReverseImport", isChecked);
                editor.commit();
            }
        });

    }

    @Override
    public void onFocusChange(View view, boolean hasFocus)
    {
        if(hasFocus)
        {
            switch (view.getId())
            {
                case R.id.foreignRateEditTxt:
                    foreignRate = (EditText) getView().findViewById(R.id.foreignRateEditTxt);
                    foreignRate.setSelection(foreignRate.getText().length());
                    break;
                case R.id.exchangeRateEditTxt:
                    exchangeRate = (EditText) getView().findViewById(R.id.exchangeRateEditTxt);
                    exchangeRate.setSelection(exchangeRate.getText().length());
                    break;
                case R.id.portCostEditTxt:
                    portCost = (EditText) getView().findViewById(R.id.portCostEditTxt);
                    portCost.setSelection(portCost.getText().length());
                    break;
                case R.id.expensesEditTxt:
                    expenses = (EditText) getView().findViewById(R.id.expensesEditTxt);
                    expenses.setSelection(expenses.getText().length());
                    break;
                case R.id.expensesPerEditText:
                    expensesPer = (EditText) getView().findViewById(R.id.expensesPerEditText);
                    expensesPer.setSelection(expensesPer.getText().length());
                    break;
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        for(EditText local : editTextList)
        {
            double localDouble = 0;
            String localString = local.getText().toString();
            Boolean localBool = localString.isEmpty();
            if(localString.startsWith("."))
            {
                local.getText().insert(0,"0");
            }
            else if(!localBool)
            {
                localDouble = Double.parseDouble(localString);
            }

            switch (local.getId())
            {
                case R.id.foreignRateEditTxt:
                    foreignRateDouble = localDouble;
                    break;
                case R.id.exchangeRateEditTxt:
                    exchangeRateDouble = localDouble;
                    break;
                case R.id.portCostEditTxt:
                    portCostDouble = localDouble;
                    break;
                case R.id.expensesEditTxt:
                    expensesDouble = localDouble;
                    break;
                case R.id.expensesPerEditText:
                    expensesPerDouble = localDouble;
                    break;
            }
            if(mySwitch.isChecked())
            {
                double calcA = foreignRateDouble-expensesDouble;
                double calcB = (1 + (expensesPerDouble/100));
                double calcC = (((calcA/calcB)/82.28449)/exchangeRateDouble)*100;
                if(calcC <= 0 || calcC > 1000)
                    calcC=0;
                factoryCost = (EditText) getView().findViewById(R.id.factoryCostEditTxt);
                String padtarFinal = String.format("%.2f", calcC);
                factoryCost.setText(padtarFinal);
                ButtonState();
            }
            else {
                double calcA = (((foreignRateDouble / 100) * 82.28449) * exchangeRateDouble);
                double calcB = calcA * (expensesPerDouble / 100);
                double calcC = calcA + calcB + expensesDouble;
                if (exchangeRateDouble == 0 || foreignRateDouble == 0 || calcC <= 0)
                    calcC = 0;
                factoryCost = (EditText) getView().findViewById(R.id.factoryCostEditTxt);
                String padtarFinal = String.format("%.2f", calcC);
                factoryCost.setText(padtarFinal);
                ButtonState();
            }
            /*double calcA = (((foreignRateDouble/100)*82.2845)*exchangeRateDouble);
            double calcB = calcA * (expensesPerDouble/100);
            double calcC = calcA + calcB + expensesDouble;
            if(exchangeRateDouble==0 || foreignRateDouble==0 || calcC<=0)
                calcC = 0;
            factoryCost = (EditText) getView().findViewById(R.id.factoryCostEditTxt);
            String padtarFinal = String.format("%.2f", calcC);
            factoryCost.setText(padtarFinal);
            ButtonState();*/
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public void ButtonState()
    {
        Button b = (Button) getView().findViewById(R.id.importRstBtn);
        Button s = (Button) getView().findViewById(R.id.importShrBtn);
        if(foreignRate.getText().toString().isEmpty() &&
                exchangeRate.getText().toString().isEmpty() &&
                portCost.getText().toString().isEmpty() &&
                expensesPer.getText().toString().isEmpty() &&
                expenses.getText().toString().isEmpty())
        {
            s.setEnabled(false);
            b.setEnabled(false);
        }
        else
        {
            b.setEnabled(true);
            s.setEnabled(true);
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences appdata = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        SharedPreferences.Editor editor = appdata.edit();
        editor.putString("foreignRateSPi", foreignRate.getText().toString());
        editor.putString("exchangeRateSPi", exchangeRate.getText().toString());
        editor.putString("portCostSPi", portCost.getText().toString());
        editor.putString("expenseSPi", expenses.getText().toString());
        editor.putString("expensePerSPi", expensesPer.getText().toString());
        editor.putString("factoryCostSPi", factoryCost.getText().toString());
        editor.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        ButtonState();

        SharedPreferences appdata = PreferenceManager.getDefaultSharedPreferences(this.getActivity());

        TableRow padtarAnsRow = (TableRow) getView().findViewById(R.id.importAnsRow);
        TableRow padtarBtnRow = (TableRow) getView().findViewById(R.id.importBtnRow);
        TableRow padtarExpireAns = (TableRow) getView().findViewById(R.id.importTrialExpire1);
        TableRow padtarExpireBtn = (TableRow) getView().findViewById(R.id.importTrialExpire2);
        boolean trialExpired = appdata.getBoolean("trialExpired",false);
        if(trialExpired)
        {
            padtarAnsRow.setVisibility(View.GONE);
            padtarBtnRow.setVisibility(View.GONE);
            padtarExpireAns.setVisibility(View.VISIBLE);
            padtarExpireBtn.setVisibility(View.VISIBLE);
        }
        else
        {
            padtarExpireAns.setVisibility(View.GONE);
            padtarExpireBtn.setVisibility(View.GONE);
            padtarAnsRow.setVisibility(View.VISIBLE);
            padtarBtnRow.setVisibility(View.VISIBLE);
        }

    }
}
