package com.cottoncalc.vivek.cottoncalculatorpk;

        import android.annotation.TargetApi;
        import android.app.AlertDialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Build;
        import android.os.Bundle;
        import android.preference.PreferenceManager;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.text.Editable;
        import android.text.TextWatcher;
        import android.view.KeyEvent;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.inputmethod.InputMethodManager;
        import android.widget.Button;
        import android.widget.CompoundButton;
        import android.widget.CompoundButton.OnCheckedChangeListener;
        import android.widget.Switch;
        import android.widget.EditText;
        import android.widget.TableRow;
        import android.widget.TextView;

        import java.util.ArrayList;
        import java.util.List;

/**
 * Created by Hitu on 14/07/2015.
 */
public class oilCake_fragment extends Fragment implements View.OnClickListener, TextWatcher,View.OnFocusChangeListener{
    View rootView;

    private EditText a;
    private EditText b;
    private EditText c;
    private EditText d;
    private EditText e;
    private EditText f;
    private EditText g;
    private EditText h;
    private EditText i;
    private EditText j;
    private EditText k;


    private TextView aTxt;
    private TextView bTxt;
    private TextView cTxt;
    private TextView dTxt;
    private TextView eTxt;
    private TextView fTxt;
    private TextView gTxt;
    private TextView hTxt;
    private TextView iTxt;
    private TextView jTxt;
    private TextView kTxt;

    private TextView aUnit;
    private TextView bUnit;
    private TextView cUnit;
    private TextView dUnit;
    private TextView eUnit;
    private TextView fUnit;
    private TextView gUnit;
    private TextView hUnit;
    private TextView iUnit;
    private TextView jUnit;
    private TextView kUnit;


    double aDouble =0;
    double bDouble =0;
    double cDouble =0;
    double dDouble =0;
    double eDouble =0;
    double fDouble =0;
    double gDouble =0;
    double hDouble =0;
    double iDouble =0;
    double jDouble =0;
    double kDouble =0;

    private Switch mySwitch;
    private Boolean switchOn;

    List<EditText> editTextList = new ArrayList<EditText>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.oil_cake_layout,container,false);

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
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                Button s = (Button) getView().findViewById(R.id.oilShrBtn);
                s.setEnabled(false);
                String shareBody;

                String aS = a.getText().toString();
                String bS = b.getText().toString();
                String cS = c.getText().toString();
                String dS = d.getText().toString();
                String eS = e.getText().toString();
                String fS = f.getText().toString();
                String gS = g.getText().toString();
                String hS = h.getText().toString();
                String iS = i.getText().toString();
                String jS = j.getText().toString();
                String kS = k.getText().toString();

                aUnit = (TextView) getView().findViewById(R.id.oilCakeRateUnit);
                String aUnitS = aUnit.getText().toString();

                bUnit = (TextView) getView().findViewById(R.id.refinedOilRateUnit);
                String bUnitS = bUnit.getText().toString();

                cUnit = (TextView) getView().findViewById(R.id.crushingExpenseUnit);
                String cUnitS = cUnit.getText().toString();

                dUnit = (TextView) getView().findViewById(R.id.crudeOilYieldUnit);
                String dUnitS = dUnit.getText().toString();

                eUnit = (TextView) getView().findViewById(R.id.refinningLossUnit);
                String eUnitS = eUnit.getText().toString();

                fUnit = (TextView) getView().findViewById(R.id.refinedOilYieldUnit);
                String fUnitS = fUnit.getText().toString();

                gUnit = (TextView) getView().findViewById(R.id.oilCakeYieldUnit);
                String gUnitS = gUnit.getText().toString();

                hUnit = (TextView) getView().findViewById(R.id.shortageUnit);
                String hUnitS = hUnit.getText().toString();

                iUnit = (TextView) getView().findViewById(R.id.banolaRealisationUnit);
                String iUnitS = iUnit.getText().toString();

                jUnit = (TextView) getView().findViewById(R.id.banolaMarketRateUnit);
                String jUnitS = jUnit.getText().toString();

                kUnit = (TextView) getView().findViewById(R.id.processingMarginUnit);
                String kUnitS = kUnit.getText().toString();

                shareBody = "Oil Cake Rate - " + aS + " " + aUnitS + "\nRefined Oil Rate - " + bS + " " + bUnitS + "\nExpense - " + cS + " " + cUnitS + "\nCrude Oil Yield - " + dS + " " + dUnitS +  "\nRefining Loss - " + eS + " " + eUnitS + "\nRefined Oil Yield - " + fS + " " + fUnitS + "\nOil Cake Yield - " + gS + " " + gUnitS + " \nShortage - " + hS + " " + hUnitS + " \nBanola Realisation - " +iS+" " +iUnitS+"\nBanola Rate - " +jS+ " " +jUnitS+ "\nProfit/Loss - "+ kS +" "+ kUnitS +"\n\n Shared via Cotton Calulator PK";

                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                sharingIntent.setPackage("com.whatsapp");
                startActivity(sharingIntent);
                break;

            case R.id.oilRstBtn:
                Button b1 = (Button) getView().findViewById(R.id.oilRstBtn);
                b1.setEnabled(false);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(false);
                builder.setTitle("Confirm");
                builder.setMessage("Reset all Values?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        a.setText("");
                        b.setText("");
                        c.setText("");
                        d.setText("");
                        e.setText("");
                        g.setText("");
                        f.setText("");
                        h.setText("");
                        i.setText("");
                        j.setText("");
                        k.setText("");
                        a.requestFocus();

                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(a, InputMethodManager.SHOW_IMPLICIT);
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

        final SharedPreferences appdata = PreferenceManager.getDefaultSharedPreferences(this.getActivity());

        mySwitch = (Switch) getView().findViewById(R.id.switch1);
        switchOn = appdata.getBoolean("switchOn",false);
        mySwitch.setChecked(switchOn);
        if(mySwitch.isChecked())
        {
            mySwitch.setText("Calculation Unit 40 Kgs");
        }
        else
        {
            mySwitch.setText("Calculation Unit 37.324 Kgs");
        }
       // mySwitch.setShowText(false);
        //attach a listener to check for changes in state
        mySwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                d.setText("");
                g.setText("");
                f.setText("");
                h.setText("");
                i.setText("");
                k.setText("");

                SharedPreferences.Editor editor = appdata.edit();
                editor.putBoolean("switchOn", isChecked);
                editor.commit();
            }
        });

        a = (EditText) getView().findViewById(R.id.oilCakeRateEditTxt);
        a.setText(appdata.getString("oilCakeRateSPo", ""));
        a.addTextChangedListener(this);
        a.setOnFocusChangeListener(this);

        b = (EditText) getView().findViewById(R.id.refinedOilRateEditTxt);
        b.setText(appdata.getString("refinedOilRateSPo", ""));
        b.addTextChangedListener(this);
        b.setOnFocusChangeListener(this);

        c = (EditText) getView().findViewById(R.id.crushingExpenseEditTxt);
        c.setText(appdata.getString("crushingExpenseSPo", ""));
        c.addTextChangedListener(this);
        c.setOnFocusChangeListener(this);

        d = (EditText) getView().findViewById(R.id.crudeOilYieldEditTxt);
        d.setText(appdata.getString("crudeOilYieldSPo", ""));
        d.addTextChangedListener(this);
        d.setOnFocusChangeListener(this);

        e = (EditText) getView().findViewById(R.id.refinningLossEditTxt);
        e.setText(appdata.getString("refinningLossSPo", ""));
        e.addTextChangedListener(this);
        e.setOnFocusChangeListener(this);

        f = (EditText) getView().findViewById(R.id.refinedOilYieldEditTxt);
        f.setText(appdata.getString("refinedOilYieldSPo", ""));

        g = (EditText) getView().findViewById(R.id.oilCakeYieldEditTxt);
        g.setText(appdata.getString("oilCakeYieldSPo", ""));
        g.addTextChangedListener(this);
        g.setOnFocusChangeListener(this);

        h = (EditText) getView().findViewById(R.id.shortageEditTxt);
        h.setText(appdata.getString("shortageSPo", ""));

        i = (EditText) getView().findViewById(R.id.banolaRealisationEditText);
        i.setText(appdata.getString("banolaRealisationSPo", ""));

        j = (EditText) getView().findViewById(R.id.banolaMarketRateEditText);
        j.setText(appdata.getString("banolaMarketRateSPo", ""));
        j.addTextChangedListener(this);
        j.setOnFocusChangeListener(this);

        k = (EditText) getView().findViewById(R.id.processingMarginEditText);
        k.setText(appdata.getString("processingMarginSPo", ""));

        TableRow padtarAnsRow1 = (TableRow) getView().findViewById(R.id.OilAndRow1);
        TableRow padtarAnsRow2 = (TableRow) getView().findViewById(R.id.OilAndRow2);
        TableRow padtarAnsRow3 = (TableRow) getView().findViewById(R.id.OilAndRow3);
        TableRow padtarBtnRow = (TableRow) getView().findViewById(R.id.OilBtnRow);
        TableRow padtarExpireAns = (TableRow) getView().findViewById(R.id.OilTrialExpire1);
        TableRow padtarExpireBtn = (TableRow) getView().findViewById(R.id.OilTrialExpire2);
        boolean trialExpired = appdata.getBoolean("trialExpired",false);
        if(trialExpired)
        {
            padtarAnsRow1.setVisibility(View.GONE);
            padtarAnsRow2.setVisibility(View.GONE);
            padtarAnsRow3.setVisibility(View.GONE);
            padtarBtnRow.setVisibility(View.GONE);
            padtarExpireAns.setVisibility(View.VISIBLE);
            padtarExpireBtn.setVisibility(View.VISIBLE);
        }
        else
        {
            padtarExpireAns.setVisibility(View.GONE);
            padtarExpireBtn.setVisibility(View.GONE);
            padtarAnsRow1.setVisibility(View.VISIBLE);
            padtarAnsRow2.setVisibility(View.VISIBLE);
            padtarAnsRow3.setVisibility(View.VISIBLE);
            padtarBtnRow.setVisibility(View.VISIBLE);
        }

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(a, InputMethodManager.SHOW_IMPLICIT);
        ButtonState();

        editTextList.add(a);
        editTextList.add(b);
        editTextList.add(c);
        editTextList.add(d);
        editTextList.add(e);
        editTextList.add(g);
        editTextList.add(j);


    }

    @Override
    public void onResume() {
        super.onResume();
        ButtonState();

        SharedPreferences appdata = PreferenceManager.getDefaultSharedPreferences(this.getActivity());

        TableRow padtarAnsRow1 = (TableRow) getView().findViewById(R.id.OilAndRow1);
        TableRow padtarAnsRow2 = (TableRow) getView().findViewById(R.id.OilAndRow2);
        TableRow padtarAnsRow3 = (TableRow) getView().findViewById(R.id.OilAndRow3);
        TableRow padtarBtnRow = (TableRow) getView().findViewById(R.id.OilBtnRow);
        TableRow padtarExpireAns = (TableRow) getView().findViewById(R.id.OilTrialExpire1);
        TableRow padtarExpireBtn = (TableRow) getView().findViewById(R.id.OilTrialExpire2);
        boolean trialExpired = appdata.getBoolean("trialExpired",false);
        if(trialExpired)
        {
            padtarAnsRow1.setVisibility(View.GONE);
            padtarAnsRow2.setVisibility(View.GONE);
            padtarAnsRow3.setVisibility(View.GONE);
            padtarBtnRow.setVisibility(View.GONE);
            padtarExpireAns.setVisibility(View.VISIBLE);
            padtarExpireBtn.setVisibility(View.VISIBLE);
        }
        else
        {
            padtarExpireAns.setVisibility(View.GONE);
            padtarExpireBtn.setVisibility(View.GONE);
            padtarAnsRow1.setVisibility(View.VISIBLE);
            padtarAnsRow2.setVisibility(View.VISIBLE);
            padtarAnsRow3.setVisibility(View.VISIBLE);
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
                case R.id.oilCakeRateEditTxt:
                    a = (EditText) getView().findViewById(R.id.oilCakeRateEditTxt);
                    a.setSelection(a.getText().length());
                    break;
                case R.id.refinedOilRateEditTxt:
                    b = (EditText) getView().findViewById(R.id.refinedOilRateEditTxt);
                    b.setSelection(b.getText().length());
                    break;
                case R.id.crushingExpenseEditTxt:
                    c = (EditText) getView().findViewById(R.id.crushingExpenseEditTxt);
                    c.setSelection(c.getText().length());
                    break;
                case R.id.crudeOilYieldEditTxt:
                    d = (EditText) getView().findViewById(R.id.crudeOilYieldEditTxt);
                    d.setSelection(d.getText().length());
                    break;
                case R.id.refinningLossEditTxt:
                    e = (EditText) getView().findViewById(R.id.refinningLossEditTxt);
                    e.setSelection(e.getText().length());
                    break;
                  case R.id.oilCakeYieldEditTxt:
                    g = (EditText) getView().findViewById(R.id.oilCakeYieldEditTxt);
                    g.setSelection(g.getText().length());
                    break;
                case R.id.banolaMarketRateEditText:
                    j = (EditText) getView().findViewById(R.id.banolaMarketRateEditText);
                    j.setSelection(j.getText().length());
                    break;
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

    }


    @Override
    public void onTextChanged(CharSequence charSequence, int i1, int i2, int i3) {

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
                case R.id.oilCakeRateEditTxt:
                   aDouble = localDouble;
                    break;
                case R.id.refinedOilRateEditTxt:
                    bDouble = localDouble;
                    break;
                case R.id.crushingExpenseEditTxt:
                    cDouble = localDouble;
                    break;
                case R.id.crudeOilYieldEditTxt:
                   dDouble = localDouble;
                    break;
                case R.id.refinningLossEditTxt:
                    eDouble = localDouble;
                    break;
                  case R.id.oilCakeYieldEditTxt:
                    gDouble = localDouble;
                    break;
                case R.id.banolaMarketRateEditText:
                    jDouble = localDouble;
                    break;

            }

           /* if(mySwitch.isChecked())
            {
                i = (EditText) getView().findViewById(R.id.banolaRealisationEditText);
                i.setText("on");
            }
            else
            {
                i = (EditText) getView().findViewById(R.id.banolaRealisationEditText);
                i.setText("off");
            }*/
            //if(eDouble > 0)
            //{
                fDouble = dDouble-eDouble;
                f = (EditText) getView().findViewById(R.id.refinedOilYieldEditTxt);
                String fvalue = String.format("%.2f", fDouble);
                f.setText(fvalue);
            //}

            //if(gDouble > 0) {
                if(mySwitch.isChecked())
                {
                    mySwitch.setText("Calculation Unit 40 Kgs ");
                    hDouble = 40 - dDouble - gDouble;
                }
                else{
                    mySwitch.setText("Calculation Unit 37.324 Kgs ");
                    hDouble = 37.324 - dDouble - gDouble;
                }
                h = (EditText) getView().findViewById(R.id.shortageEditTxt);
                String hvalue = String.format("%.2f", hDouble);
                h.setText(hvalue);
            //}
            double calcA = (bDouble * fDouble);
            double calcB = (aDouble*gDouble);
            double calcC = 0;
            if(mySwitch.isChecked())
            {
                 calcC = (calcA+calcB)/40;
            }
            else{
                 calcC = (calcA+calcB)/37.324;
            }
            double ans = calcC - cDouble;

            i = (EditText) getView().findViewById(R.id.banolaRealisationEditText);
            String padtarFinal = String.format("%.2f", ans);
            i.setText(padtarFinal);

            if(ans>0 && jDouble>0)
            {
                kDouble = ans-jDouble;
                k = (EditText) getView().findViewById(R.id.processingMarginEditText);
                String kvalue = String.format("%.2f", kDouble);
                k.setText(kvalue);
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
            }*//*

            *//*int expenseLen = expenses.getText().length();
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
        if(a.getText().toString().isEmpty() &&
                b.getText().toString().isEmpty() &&
                c.getText().toString().isEmpty() &&
                d.getText().toString().isEmpty() &&
                e.getText().toString().isEmpty() &&
                j.getText().toString().isEmpty() &&
                g.getText().toString().isEmpty())
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
        editor.putString("oilCakeRateSPo", a.getText().toString());
        editor.putString("refinedOilRateSPo", b.getText().toString());
        editor.putString("crushingExpenseSPo", c.getText().toString());
        editor.putString("crudeOilYieldSPo", d.getText().toString());
        editor.putString("refinningLossSPo", e.getText().toString());
        editor.putString("refinedOilYieldSPo", f.getText().toString());
        editor.putString("oilCakeYieldSPo", g.getText().toString());
        editor.putString("shortageSPo", h.getText().toString());
        editor.putString("banolaRealisationSPo", i.getText().toString());
        editor.putString("banolaMarketRateSPo", j.getText().toString());
        editor.putString("processingMarginSPo", k.getText().toString());
        editor.commit();
    }
}

