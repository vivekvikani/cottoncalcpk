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
import android.util.Log;
import android.view.KeyEvent;
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
public class ginning_forward extends Fragment implements View.OnClickListener, TextWatcher, View.OnFocusChangeListener{
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
    private TextView freightTxt;
    private TextView commissionTxt;
    private TextView banolaTxt;
    private TextView outturnTxt;
    private TextView shortageTxt;
    private TextView expensesTxt;
    private TextView costTxt;

    private TextView phuttiUnit;
    /*private TextView freightUnit;
    private TextView commissionUnit;*/
    private TextView banolaUnit;
    private TextView outturnUnit;
    private TextView shortageUnit;
    private TextView expensesUnit;
    private TextView costUnit;

    double phuttiDouble =0;
    double freightDouble =0;
    double commissionDouble =0;
    double banolaDouble =0;
    double outturnDouble =0;
    double boutturnDouble =0;
    double shortageDouble =0;
    double expensesDouble =0;

    List<EditText> editTextList = new ArrayList<EditText>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.ginning_forward_layout,container,false);

        Button shr = (Button) rootView.findViewById(R.id.forwardShrBtn);
        shr.setOnClickListener(this);

        Button reset = (Button) rootView.findViewById(R.id.forwardRstBtn);
        reset.setOnClickListener(this);

        Button fullVersion = (Button) rootView.findViewById(R.id.gotoFullVersionF);
        fullVersion.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.forwardShrBtn:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                Button s = (Button) getView().findViewById(R.id.forwardShrBtn);
                s.setEnabled(false);
                String shareBody;

                String phuttiS = phutti.getText().toString();
             /*   String freightS = freight.getText().toString();
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

             /*   freightUnit = (TextView) getView().findViewById(R.id.freightUnit);
                String freightUnitS = freightUnit.getText().toString();

                commissionUnit = (TextView) getView().findViewById(R.id.commissionUnit);
                String commissionUnitS = commissionUnit.getText().toString();*/

                banolaUnit = (TextView) getView().findViewById(R.id.banolaUnit);
                String banolaUnitS = banolaUnit.getText().toString();

                costUnit = (TextView) getView().findViewById(R.id.costUnit);
                String costUnitS = costUnit.getText().toString();

                shareBody = "Phutti - " + phuttiS + " " + phuttiUnitS +  "\nExpenses - " + expenseS + " " + expenseUnitS + "\nBanola Rate - " +banolaS+ " " +banolaUnitS+ "\nCotton Outturn - " + outturnS + " Kgs\nBanola Outturn - " + boutturnS + " Kgs\nShortage - " + shortageS + " Kgs\nCotton Cost - " + costS + " " + costUnitS + "\n\nShared via Cotton Calculator PK";

                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                sharingIntent.setPackage("com.whatsapp");
                startActivity(sharingIntent);

                break;

            case R.id.forwardRstBtn:
                Button b = (Button) getView().findViewById(R.id.forwardRstBtn);
                b.setEnabled(false);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(false);
                builder.setTitle("Confirm");
                builder.setMessage("Reset all Values?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        phutti.setText("");
                       /* freight.setText("");
                        commission.setText("");*/
                        banola.setText("");
                        outturn.setText("");
                        shortage.setText("");
                        expenses.setText("");
                        boutturn.setText("");

                        phutti.requestFocus();

                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(phutti, InputMethodManager.SHOW_IMPLICIT);
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

            case R.id.gotoFullVersionF:
                Intent myIntent = new Intent(getActivity().getApplicationContext(), aboutUs.class);
                startActivity(myIntent);
                break;
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SharedPreferences appdata = PreferenceManager.getDefaultSharedPreferences(this.getActivity());

        phutti = (EditText) getView().findViewById(R.id.phuttiEditTxt);
        phutti.setText(appdata.getString("phuttiSP", ""));
        phutti.addTextChangedListener(this);
        phutti.setOnFocusChangeListener(this);

        /*freight = (EditText) getView().findViewById(R.id.freightEditTxt);
        freight.setText(appdata.getString("freightSP", ""));
        freight.addTextChangedListener(this);
        freight.setOnFocusChangeListener(this);

        commission = (EditText) getView().findViewById(R.id.commissionEditTxt);
        commission.setText(appdata.getString("commissionSP", ""));
        commission.addTextChangedListener(this);
        commission.setOnFocusChangeListener(this);*/

        banola = (EditText) getView().findViewById(R.id.banolaEditTxt);
        banola.setText(appdata.getString("banolaSP", ""));
        banola.addTextChangedListener(this);
        banola.setOnFocusChangeListener(this);

        outturn = (EditText) getView().findViewById(R.id.outturnEditTxt);
        outturn.setText(appdata.getString("outturnSP", ""));
        outturn.addTextChangedListener(this);
        outturn.setOnFocusChangeListener(this);

        boutturn = (EditText) getView().findViewById(R.id.banola_outturnEditTxt);
        boutturn.setText(appdata.getString("boutturnSP", ""));
        boutturn.addTextChangedListener(this);
        boutturn.setOnFocusChangeListener(this);

        shortage = (EditText) getView().findViewById(R.id.shortageEditTxt);
        shortage.setText(appdata.getString("shortageSP", ""));

        expenses = (EditText) getView().findViewById(R.id.expensesEditTxt);
        expenses.setText(appdata.getString("expenseSP", ""));
        expenses.addTextChangedListener(this);
        expenses.setOnFocusChangeListener(this);

        cost = (EditText) getView().findViewById(R.id.costEditTxt);
        cost.setText(appdata.getString("costSP", ""));


        ////////

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(phutti, InputMethodManager.SHOW_IMPLICIT);
        ButtonState();

        editTextList.add(phutti);
       /* editTextList.add(freight);
        editTextList.add(commission);*/
        editTextList.add(banola);
        editTextList.add(boutturn);
        editTextList.add(outturn);
        editTextList.add(expenses);
    }

    @Override
    public void onResume() {
        super.onResume();
        ButtonState();

        SharedPreferences appdata = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        TableRow padtarAnsRow = (TableRow) getView().findViewById(R.id.ForwardAnsRow);
        TableRow padtarBtnRow = (TableRow) getView().findViewById(R.id.ForwardBtnRow);
        TableRow padtarExpireAns = (TableRow) getView().findViewById(R.id.ForwardTrialExpire1);
        TableRow padtarExpireBtn = (TableRow) getView().findViewById(R.id.ForwardTrialExpire2);
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
    public void onFocusChange(View view, boolean hasFocus)
    {
        if(hasFocus)
        {
            switch (view.getId())
            {
                case R.id.phuttiEditTxt:
                    phutti = (EditText) getView().findViewById(R.id.phuttiEditTxt);
                    phutti.setSelection(phutti.getText().length());
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
                case R.id.phuttiEditTxt:
                    phuttiDouble = localDouble;
                    break;
                /*case R.id.freightEditTxt:
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
            double calcA = (phuttiDouble + expensesDouble);
          //  double calcB = ((banolaDouble/37.324) * (100 - outturnDouble - shortageDouble));
            double calcB = boutturnDouble*(banolaDouble/37.324);
            double calcC = calcA - calcB;
            double calcD = 0;
            double shortageAns = 0;
               /* if(outturnDouble != 0) {
                    // calcD = (calcC/outturnDouble*37.324)+expensesDouble;
                    calcD = (calcC / outturnDouble) * 37.324;
                    shortageAns = 40 - outturnDouble - boutturnDouble;
                }*/
                if(boutturnDouble==0 || outturnDouble==0 || phuttiDouble==0 || banolaDouble==0)
                {
                    calcD = 0;
                    shortageAns = 0;
                }
                else
                {
                    calcD = (calcC / outturnDouble) * 37.324;
                    shortageAns = 40 - outturnDouble - boutturnDouble;
                }

            cost = (EditText) getView().findViewById(R.id.costEditTxt);

            if(calcD>0){
                String padtarFinal = String.format("%.2f", calcD);
                cost.setText(padtarFinal);
            }else
            {
                cost.setText("Invalid!!");
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
        Button b = (Button) getView().findViewById(R.id.forwardRstBtn);
        Button s = (Button) getView().findViewById(R.id.forwardShrBtn);
        if(phutti.getText().toString().isEmpty() &&
               /* freight.getText().toString().isEmpty() &&
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
        editor.putString("phuttiSP", phutti.getText().toString());
       /* editor.putString("freightSP", freight.getText().toString());
        editor.putString("commissionSP", commission.getText().toString());*/
        editor.putString("banolaSP", banola.getText().toString());
        editor.putString("outturnSP", outturn.getText().toString());
        editor.putString("boutturnSP", boutturn.getText().toString());
        editor.putString("shortageSP", shortage.getText().toString());
        editor.putString("expenseSP", expenses.getText().toString());
        editor.putString("costSP", cost.getText().toString());
        editor.commit();
    }
}
