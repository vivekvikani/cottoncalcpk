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
public class oilCake_fragment_old extends Fragment implements View.OnClickListener, TextWatcher, View.OnFocusChangeListener{
    View rootView;

    private EditText oilDirt;
    private EditText freight;
    private EditText oilRate;
    private EditText banola;
    private EditText outturn;
    private EditText shortage;
    private EditText expenses;
    private EditText cost;

    private TextView oilDirtTxt;
    private TextView freightTxt;
    private TextView oilRateTxt;
    private TextView banolaTxt;
    private TextView outturnTxt;
    private TextView shortageTxt;
    private TextView expensesTxt;
    private TextView costTxt;

    private TextView oilDirtUnit;
    private TextView freightUnit;
    private TextView oilRateUnit;
    private TextView banolaUnit;
    private TextView outturnUnit;
    private TextView shortageUnit;
    private TextView expensesUnit;
    private TextView costUnit;

    double oilDirtDouble =0;
    double freightDouble =0;
    double oilRateDouble =0;
    double banolaDouble =0;
    double outturnDouble =0;
    double shortageDouble =0;
    double expensesDouble =0;

    List<EditText> editTextList = new ArrayList<EditText>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.oil_cake_layout_old,container,false);

        Button shr = (Button) rootView.findViewById(R.id.oilShrBtn);
        shr.setOnClickListener(this);

        Button reset = (Button) rootView.findViewById(R.id.oilRstBtn);
        reset.setOnClickListener(this);

        Button fullVersion = (Button) rootView.findViewById(R.id.gotoFullVersiono);
        fullVersion.setOnClickListener(this);

        return rootView;
    }


    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.oilShrBtn:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                Button s = (Button) getView().findViewById(R.id.oilShrBtn);
                s.setEnabled(false);
                String shareBody;

                String oilDirtS = oilDirt.getText().toString();
                String freightS = freight.getText().toString();
                String oilRateS = oilRate.getText().toString();
                String banolaS = banola.getText().toString();
                String outturnS = outturn.getText().toString();
                String shortageS = shortage.getText().toString();
                String expenseS = expenses.getText().toString();
                String costS = cost.getText().toString();

                expensesUnit = (TextView) getView().findViewById(R.id.expensesUnit);
                String expenseUnitS = expensesUnit.getText().toString();

                banolaUnit = (TextView) getView().findViewById(R.id.banolaUnit);
                String banolaUnitS = banolaUnit.getText().toString();

                freightUnit = (TextView) getView().findViewById(R.id.freightUnit);
                String freightUnitS = freightUnit.getText().toString();

                oilRateUnit = (TextView) getView().findViewById(R.id.oilRateUnit);
                String oilRateUnitS = oilRateUnit.getText().toString();

                oilDirtUnit = (TextView) getView().findViewById(R.id.oilDirtUnit);
                String oilDirtUnitS = oilDirtUnit.getText().toString();

                costUnit = (TextView) getView().findViewById(R.id.costUnit);
                String costUnitS = costUnit.getText().toString();

                shareBody = "Banola Rate - " + banolaS + " " + banolaUnitS + "\nFreight - " + freightS + " " + freightUnitS + "\nOil Rate - " + oilRateS + " " + oilRateUnitS + "\nOil Dirt - " + oilDirtS + " " + oilDirtUnitS +  "\nOil Outturn - " + outturnS + " %\nShortage - " + shortageS + "%\nExpense - " +expenseS+" " +expenseUnitS+"\nCost of Oil Cake- " +costS+ " " +costUnitS+ "\n\n Shared via Cotton Calulator PK";

                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                sharingIntent.setPackage("com.whatsapp");
                startActivity(sharingIntent);
                break;

            case R.id.oilRstBtn:
                Button b = (Button) getView().findViewById(R.id.oilRstBtn);
                b.setEnabled(false);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(false);
                builder.setTitle("Confirm");
                builder.setMessage("Reset all Values?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        oilDirt.setText("");
                        freight.setText("");
                        oilRate.setText("");
                        banola.setText("");
                        outturn.setText("");
                        shortage.setText("");
                        expenses.setText("");

                        banola.requestFocus();

                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(banola, InputMethodManager.SHOW_IMPLICIT);
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

            case R.id.gotoFullVersiono:
                Intent myIntent = new Intent(getActivity().getApplicationContext(), aboutUs.class);
                startActivity(myIntent);
                break;
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SharedPreferences appdata = PreferenceManager.getDefaultSharedPreferences(this.getActivity());

        oilDirt = (EditText) getView().findViewById(R.id.oilDirtEditTxt);
        oilDirt.setText(appdata.getString("oilDirtSPo", ""));
        oilDirt.addTextChangedListener(this);
        oilDirt.setOnFocusChangeListener(this);

        freight = (EditText) getView().findViewById(R.id.freightEditTxt);
        freight.setText(appdata.getString("freightSPo", ""));
        freight.addTextChangedListener(this);
        freight.setOnFocusChangeListener(this);

        oilRate = (EditText) getView().findViewById(R.id.oilRateEditTxt);
        oilRate.setText(appdata.getString("oilRateSPo", ""));
        oilRate.addTextChangedListener(this);
        oilRate.setOnFocusChangeListener(this);

        banola = (EditText) getView().findViewById(R.id.banolaEditTxt);
        banola.setText(appdata.getString("banolaSPo", ""));
        banola.addTextChangedListener(this);
        banola.setOnFocusChangeListener(this);

        outturn = (EditText) getView().findViewById(R.id.outturnEditTxt);
        outturn.setText(appdata.getString("outturnSPo", ""));
        outturn.addTextChangedListener(this);
        outturn.setOnFocusChangeListener(this);

        shortage = (EditText) getView().findViewById(R.id.shortageEditTxt);
        shortage.setText(appdata.getString("shortageSPo", ""));
        shortage.addTextChangedListener(this);
        shortage.setOnFocusChangeListener(this);

        expenses = (EditText) getView().findViewById(R.id.expensesEditTxt);
        expenses.setText(appdata.getString("expenseSPo", ""));
        expenses.addTextChangedListener(this);
        expenses.setOnFocusChangeListener(this);

        cost = (EditText) getView().findViewById(R.id.costEditTxt);
        cost.setText(appdata.getString("costSPo", ""));


        TableRow padtarAnsRow = (TableRow) getView().findViewById(R.id.OilAnsRow);
        TableRow padtarBtnRow = (TableRow) getView().findViewById(R.id.OilBtnRow);
        TableRow padtarExpireAns = (TableRow) getView().findViewById(R.id.OilTrialExpire1);
        TableRow padtarExpireBtn = (TableRow) getView().findViewById(R.id.OilTrialExpire2);
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
        imm.showSoftInput(banola, InputMethodManager.SHOW_IMPLICIT);
        ButtonState();

        editTextList.add(oilDirt);
        editTextList.add(freight);
        editTextList.add(oilRate);
        editTextList.add(banola);
        editTextList.add(outturn);
        editTextList.add(shortage);
        editTextList.add(expenses);
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus)
    {
        if(hasFocus)
        {
            switch (view.getId())
            {
                case R.id.oilDirtEditTxt:
                    oilDirt = (EditText) getView().findViewById(R.id.oilDirtEditTxt);
                    oilDirt.setSelection(oilDirt.getText().length());
                    break;
                case R.id.freightEditTxt:
                    freight = (EditText) getView().findViewById(R.id.freightEditTxt);
                    freight.setSelection(freight.getText().length());
                    break;
                case R.id.oilRateEditTxt:
                    oilRate = (EditText) getView().findViewById(R.id.oilRateEditTxt);
                    oilRate.setSelection(oilRate.getText().length());
                    break;
                case R.id.banolaEditTxt:
                    banola = (EditText) getView().findViewById(R.id.banolaEditTxt);
                    banola.setSelection(banola.getText().length());
                    break;
                case R.id.outturnEditTxt:
                    outturn = (EditText) getView().findViewById(R.id.outturnEditTxt);
                    outturn.setSelection(outturn.getText().length());
                    break;
                case R.id.shortageEditTxt:
                    shortage = (EditText) getView().findViewById(R.id.shortageEditTxt);
                    shortage.setSelection(shortage.getText().length());
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
                case R.id.oilDirtEditTxt:
                    oilDirtDouble = localDouble;
                    break;
                case R.id.freightEditTxt:
                    freightDouble = localDouble;
                    break;
                case R.id.oilRateEditTxt:
                    oilRateDouble = localDouble;
                    break;
                case R.id.banolaEditTxt:
                    banolaDouble = localDouble;
                    break;
                case R.id.outturnEditTxt:
                    outturnDouble = localDouble;
                    break;
                case R.id.shortageEditTxt:
                    shortageDouble = localDouble;
                    break;
                case R.id.expensesEditTxt:
                    expensesDouble = localDouble;
                    break;
            }
            double calcA = (banolaDouble + freightDouble)*100;
            double calcB = (outturnDouble*oilRateDouble);
            double calcC = ((3732.4*(shortageDouble/100)/40)*oilDirtDouble);
            double calcD = calcA-calcB-calcC;
            double calcE = (calcD/(100-outturnDouble-shortageDouble))+expensesDouble;
            if(oilRateDouble==0 ||outturnDouble==0 || banolaDouble==0 || calcE<=0)
                calcE = 0;
            cost = (EditText) getView().findViewById(R.id.costEditTxt);
            String padtarFinal = String.format("%.2f", calcE);
            cost.setText(padtarFinal);
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
        Button b = (Button) getView().findViewById(R.id.oilRstBtn);
        Button s = (Button) getView().findViewById(R.id.oilShrBtn);
        if(oilDirt.getText().toString().isEmpty() &&
                freight.getText().toString().isEmpty() &&
                oilRate.getText().toString().isEmpty() &&
                banola.getText().toString().isEmpty() &&
                outturn.getText().toString().isEmpty() &&
                shortage.getText().toString().isEmpty() &&
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
        editor.putString("oilDirtSPo", oilDirt.getText().toString());
        editor.putString("freightSPo", freight.getText().toString());
        editor.putString("oilRateSPo", oilRate.getText().toString());
        editor.putString("banolaSPo", banola.getText().toString());
        editor.putString("outturnSPo", outturn.getText().toString());
        editor.putString("shortageSPo", shortage.getText().toString());
        editor.putString("expenseSPo", expenses.getText().toString());
        editor.putString("costSPo", cost.getText().toString());
        editor.commit();
    }
}

