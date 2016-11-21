package com.example.alin.metrocard_ninja;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent intent = getIntent();
        double remaining = Double.parseDouble(intent.getStringExtra(MainActivity.EXTRA_MESSAGE));
        ArrayList<Double> wow = DisplayMessageActivity.amountsToAddForNoRemainder(remaining, 80);

        TableLayout layout = (TableLayout) findViewById(R.id.activity_display_message);
        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        tv0.setText("Refill Amt");
        tv0.setTextColor(Color.BLACK);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" Bonus Amt ");
        tv1.setTextColor(Color.BLACK);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText(" Rides ");
        tv2.setTextColor(Color.BLACK);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText(" Total Balance ");
        tv3.setTextColor(Color.BLACK);
        tbrow0.addView(tv3);
        TextView tv4 = new TextView(this);
        tv4.setText(" Ending Balance ");
        tv4.setTextColor(Color.BLACK);
        tbrow0.addView(tv4);

        layout.addView(tbrow0);

        NumberFormat formatter = new DecimalFormat("#0.00");

        for (Double d : wow) {
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);

            TextView v1 = new TextView(this);
            v1.setGravity(Gravity.CENTER);
            v1.setText("$" + formatter.format(d));
            row.addView(v1);

            TextView v2 = new TextView(this);
            v2.setGravity(Gravity.CENTER);
            v2.setText("$" + formatter.format(DisplayMessageActivity.bonusValue(d)));
            row.addView(v2);

            TextView v3 = new TextView(this);
            v3.setGravity(Gravity.CENTER);
            int num = DisplayMessageActivity.numberOfRides(DisplayMessageActivity.round_to_tenths(remaining + d));
            v3.setText(num + "");
            row.addView(v3);

            TextView v4 = new TextView(this);
            v4.setGravity(Gravity.CENTER);
            double total = DisplayMessageActivity.round_to_tenths(remaining + d);
            v4.setText("$" + formatter.format(total));
            row.addView(v4);

            TextView v5 = new TextView(this);
            v5.setGravity(Gravity.CENTER);
            v5.setText("$0.00");
            row.addView(v5);

            layout.addView(row);
        }
    }

    public static int numberOfRides(double amt) {
        double fare = 2.75;
        return (int) (amt / fare);
    }

    public static ArrayList<Double> amountsToAddForNoRemainder(double remaining, double spend_amt) {
        double increment = 0.05;
        ArrayList<Double> amounts = new ArrayList<>();
        for (double i = 0; i <= spend_amt; i += increment) {
            double curr_amt = DisplayMessageActivity.round_to_tenths(remaining + i);
            if (DisplayMessageActivity.leavesZeroBalance(curr_amt)) {
                amounts.add(DisplayMessageActivity.round_to_tenths(i));
            }
        }
        return amounts;
    }

    public static boolean leavesZeroBalance(double amt) {
        double fare = 2.75;
        double total = DisplayMessageActivity.round_to_tenths((amt + DisplayMessageActivity.bonusValue(amt)));
        return total % fare == 0;
    }

    public static double round_to_tenths(double value) {
        value = value * 100.0;
        value = Math.round(value);
        return (value / 100.0);
    }

    public static double bonusValue(double amt) {
        double minimum = 5.50;
        if (amt < minimum) {
            return 0.0;
        }
        double bonus_percentage = 11.0;
        return DisplayMessageActivity.round_to_tenths((bonus_percentage / 100.0) * amt);
    }
}
