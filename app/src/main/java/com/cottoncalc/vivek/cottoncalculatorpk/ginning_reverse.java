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
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hitu on 14/07/2015.
 */
public class ginning_reverse extends Fragment implements View.OnClickListener, TextWatcher, View.OnFocusChangeListener{
    View rootView;

    private EditText phutti;
   /* private EditText freight;
    private EditText commission;*/
    private EditText banola;
    private EditText outturn;
    private EditText boutturn;
    private EditText shortage;
    private EditText expenses;
    private EditText cost;

    private TextView phuttiTxt;
   /* private TextView freightTxt;
    private TextView commissionTxt;*/
    private TextView banolaTxt;
    private TextView outturnTxt;
    private TextView shortageTxt;
    private TextView expensesTxt;
    private TextView costTxt;

    private TextView phuttiUnit;
   /* private TextView freightUnit;
    private TextView commissionUnit;*/
    private TextView banolaUnit;
    private TextView outturnUnit;
    private TextView shortageUnit;
    private TextView expensesUnit;
    private TextView costUnit;

    double costDouble =0;
   /* double freightDouble =0;
    double commissionDouble =0;*/
    double banolaDouble =0;
    double outturnDouble =0;
    double boutturnDouble =0;
    double shortageDouble =0;
    double expensesDouble =0;

    List<EditText> editTextList = new ArrayList<EditText>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.ginning_reverse_layout,container,false);

        Button shr = (Button) rootView.findViewById(R.id.reverseShrBtn);
        shr.setOnClickListener(this);

        Button reset = (Button) rootView.findViewById(R.id.reverseRstBtn);
        reset.setOnClickListener(this);

        Button fullVersion = (Button) rootView.findViewById(R.id.gotoFullVersionR);
        fullVersion.setOnClickListener(this);

        return rootView;
    }


    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.reverseShrBtn:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                Button s = (Button) getView().findViewById(R.id.reverseShrBtn);
                s.setEnabled(false);
                String shareBody;

                final String phuttiS = phutti.getText().toString();
              /*  String freightS = freight.getText().toString();
                String commissionS = commission.getText().toString();*/
                String banolaS = banola.getText().toString();
                String outturnS = outturn.getText().toString();
                String shortageS = shortage.getText().toString();
                String expenseS = expenses.getText().toString();
                String costS = cost.getText().toString();
                String boutturnS = boutturn.getText().toString();

                expensesUnit = (TextView) getView().findViewById(R.id.expensesUnit);
                String expenseUnitS = expensesUnit.getText().toString();

                phuttiUnit = (TextView) getView().findViewById(R.id.phuttiUnit);
                String phuttiUnitS = phuttiUnit.getText().toString();

            /*    freightUnit = (TextView) getView().findViewById(R.id.freightUnit);
                String freightUnitS = freightUnit.getText().toString();

                commissionUnit = (TextView) getView().findViewById(R.id.commissionUnit);
                String commissionUnitS = commissionUnit.getText().toString();*/

                banolaUnit = (TextView) getView().findViewById(R.id.banolaUnit);
                String banolaUnitS = banolaUnit.getText().toString();

                costUnit = (TextView) getView().findViewById(R.id.costUnit);
                String costUnitS = costUnit.getText().toString();

                shareBody = "Cotton Rate - " + costS + " " + costUnitS + "\nExpenses - " + expenseS + " " + expenseUnitS + "\nBanola Rate - " +banolaS+ " " +banolaUnitS+ "\nCotton Outturn - " + outturnS + " Kgs\nBanola Outturn - " + boutturnS + " Kgs\nShortage - " + shortageS + " Kgs\nPhutti - " + phuttiS + " " + phuttiUnitS + "\n\nShared via Cotton Calculator PK";

                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                sharingIntent.setPackage("com.whatsapp");
                startActivity(sharingIntent);
                break;

            case R.id.reverseRstBtn:
                Button b = (Button) getView().findViewById(R.id.reverseRstBtn);
                b.setEnabled(false);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(false);
                builder.setTitle("Confirm");
                builder.setMessage("Reset all Values?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cost.setText("");
                        /*freight.setText("");
                        commission.setText("");*/
                        banola.setText("");
                        outturn.setText("");
                        shortage.setText("");
                        expenses.setText("");
                        boutturn.setText("");

                        cost.requestFocus();

                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(cost, InputMethodManager.SHOW_IMPLICIT);
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

            case R.id.gotoFullVersionR:
                Intent myIntent = new Intent(getActivity().getApplicationContext(), aboutUs.class);
                startActivity(myIntent);
                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        ButtonState();

        SharedPreferences appdata = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        TableRow padtarAnsRow = (TableRow) getView().findViewById(R.id.ReverseAnsRow);
        TableRow padtarBtnRow = (TableRow) getView().findViewById(R.id.ReverseBtnRow);
        TableRow padtarExpireAns = (TableRow) getView().findViewById(R.id.ReverseTrialExpire1);
        TableRow padtarExpireBtn = (TableRow) getView().findViewById(R.id.ReverseTrialExpire2);
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SharedPreferences appdata = PreferenceManager.getDefaultSharedPreferences(this.getActivity());

        cost = (EditText) getView().findViewById(R.id.costEditTxt);
        cost.setText(appdata.getString("costSPr", ""));
        cost.addTextChangedListener(this);
        cost.setOnFocusChangeListener(this);

        banola = (EditText) getView().findViewById(R.id.banolaEditTxt);
        banola.setText(appdata.getString("banolaSPr", ""));
        banola.addTextChangedListener(this);
        banola.setOnFocusChangeListener(this);

        outturn = (EditText) getView().findViewById(R.id.outturnEditTxt);
        outturn.setText(appdata.getString("outturnSPr", ""));
        outturn.addTextChangedListener(this);
        outturn.setOnFocusChangeListener(this);

        boutturn = (EditText) getView().findViewById(R.id.banola_outturnEditTxt);
        boutturn.setText(appdata.getString("boutturnSPr", ""));
        boutturn.addTextChangedListener(this);
        boutturn.setOnFocusChangeListener(this);

        shortage = (EditText) getView().findViewById(R.id.shortageEditTxt);
        shortage.setText(appdata.getString("shortageSPr", ""));

        expenses = (EditText) getView().findViewById(R.id.expensesEditTxt);
        expenses.setText(appdata.getString("expenseSPr", ""));
        expenses.addTextChangedListener(this);
        expenses.setOnFocusChangeListener(this);

        phutti = (EditText) getView().findViewById(R.id.phuttiEditTxt);
        phutti.setText(appdata.getString("phuttiSPr", ""));


        TableRow padtarAnsRow = (TableRow) getView().findViewById(R.id.ReverseAnsRow);
        TableRow padtarBtnRow = (TableRow) getView().findViewById(R.id.ReverseBtnRow);
        TableRow padtarExpireAns = (TableRow) getView().findViewById(R.id.ReverseTrialExpire1);
        TableRow padtarExpireBtn = (TableRow) getView().findViewById(R.id.ReverseTrialExpire2);
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
        imm.showSoftInput(cost, InputMethodManager.SHOW_IMPLICIT);
        ButtonState();

        editTextList.add(cost);
        editTextList.add(banola);
        editTextList.add(outturn);
        editTextList.add(boutturn);
        editTextList.add(expenses);
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus)
    {
        if(hasFocus)
        {
            switch (view.getId())
            {
                case R.id.costEditTxt:
                    cost = (EditText) getView().findViewById(R.id.costEditTxt);
                    cost.setSelection(cost.getText().length());
                    break;
               /* case R.id.freightEditTxt:
                    freight = (EditText) getView().findViewById(R.id.freightEditTxt);
                    freight.setSelection(freight.getText().length());
                    break;
                case R.id.commissionEditTxt:
                    commission = (EditText) getView().findViewById(R.id.commissionEditTxt);
                    commission.setSelection(commission.getText().length());
                    break;*/
                case R.id.banolaEditTxt:
                    banola = (EditText) getView().findViewById(R.id.banolaEditTxt);
                    banola.setSelection(banola.getText().length());
                    break;
                case R.id.outturnEditTxt:
                    outturn = (EditText) getView().findViewById(R.id.outturnEditTxt);
                    outturn.setSelection(outturn.getText().length());
                    break;
                case R.id.banola_outturnEditTxt:
                    boutturn = (EditText) getView().findViewById(R.id.banola_outturnEditTxt);
                    boutturn.setSelection(boutturn.getText().length());
                    break;
                case R.id.expensesEditTxt:
                    expenses = (EditText) getView().findViewById(R.id.expensesEditTxt);
                    expenses.setSelection(expenses.getText().length());
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
                case R.id.costEditTxt:
                    costDouble = localDouble;
                    break;
               /* case R.id.freightEditTxt:
                    freightDouble = localDouble;
                    break;
                case R.id.commissionEditTxt:
                    commissionDouble = localDouble;
                    break;*/
                case R.id.banolaEditTxt:
                    banolaDouble = localDouble;
                    break;
                case R.id.outturnEditTxt:
                    outturnDouble = localDouble;
                    break;
                case R.id.banola_outturnEditTxt:
                    boutturnDouble = localDouble;
                    break;
                case R.id.shortageEditTxt:
                    shortageDouble = localDouble;
                    break;
                case R.id.expensesEditTxt:
                    expensesDouble = localDouble;
                    break;
            }
            double calcA = ((costDouble)/37.324) * outturnDouble;
            double calcB = (banolaDouble/37.324) * boutturnDouble;
            //double calcB = ((100 - outturnDouble - shortageDouble) * banolaDouble);
            double calcC = calcA + calcB;
            double calcD = calcC -expensesDouble;
            double shortageAns = 40 - outturnDouble - boutturnDouble;;
            //double calcD = ((calcC/3732.4)*40)-freightDouble-commissionDouble;
             if(boutturnDouble==0 || outturnDouble==0 || costDouble==0 || banolaDouble==0 || calcD<=0) {
                 calcD = 0;
                 shortageAns = 0;
             }
            phutti = (EditText) getView().findViewById(R.id.phuttiEditTxt);

            if(calcD>0){
                String padtarFinal = String.format("%.2f", calcD);
                phutti.setText(padtarFinal);
            }else
            {
                phutti.setText("Invalid!!");
            }

            if(shortageAns>0){
                String ShortageAnsString = String.format("%.2f", shortageAns);
                shortage.setText(String.valueOf(ShortageAnsString));
            }else
            {
                shortage.setText("Invalid!!");
            }
            ButtonState();

            /*String test = expenses.getText().toString();
            int dotpos = test.indexOf(".");
            if (dotpos == -1)
            {
                int expenseLen = expenses.getText().length();
                if(expenseLen >= 3)
                    expenses.getText().replace(2,3,"");
            }

            if(dotpos > 2)
                expenses.getText().replace(2,3,"");

            if(dotpos > -1)
            {int expenseLen = expenses.getText().length();
                if(expenseLen >= dotpos+4)
                expenses.getText().replace(dotpos+3,dotpos+4,"");
            }*/

            /*int expenseLen = expenses.getText().length();
            if(expenseLen >= 3)
            {
               char atthree =  expenses.getText().charAt(2);
                if(atthree != '.')
                    expenses.getText().replace(2,3,"");
            }*/
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public void ButtonState()
    {
        Button b = (Button) getView().findViewById(R.id.reverseRstBtn);
        Button s = (Button) getView().findViewById(R.id.reverseShrBtn);
        if(cost.getText().toString().isEmpty() &&
                /*freight.getText().toString().isEmpty() &&
                commission.getText().toString().isEmpty() &&*/
                banola.getText().toString().isEmpty() &&
                outturn.getText().toString().isEmpty() &&
                boutturn.getText().toString().isEmpty() &&
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
        editor.putString("costSPr", cost.getText().toString());
       /* editor.putString("freightSPr", freight.getText().toString());
        editor.putString("commissionSPr", commission.getText().toString());*/
        editor.putString("banolaSPr", banola.getText().toString());
        editor.putString("outturnSPr", outturn.getText().toString());
        editor.putString("boutturnSPr", boutturn.getText().toString());
        editor.putString("shortageSPr", shortage.getText().toString());
        editor.putString("expenseSPr", expenses.getText().toString());
        editor.putString("phuttiSPr", phutti.getText().toString());
        editor.commit();
    }
}

