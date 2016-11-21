package com.example.alin.metrocard_ninja;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

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
        LinearLayout layout = (LinearLayout) findViewById(R.id.result_layout);
        NumberFormat formatter = new DecimalFormat("#0.00");

        TextView tv0 = (TextView) findViewById(R.id.result_message);
        tv0.setText(Html.fromHtml("You have <span style='color:#00933C'>$" +  formatter.format(remaining) + "</span> left on your card. To end up with a <span style='color:#00933C'>$0.00</span> balance, you can add ..."));
        tv0.setTextColor(Color.BLACK);
        tv0.setBackgroundColor(Color.parseColor("#FCCC0A"));

        for (Double d : wow) {
            String refill_amt = formatter.format(d);
            double bonus = DisplayMessageActivity.bonusValue(d);
            String bonusText = formatter.format(bonus);
            double total = remaining + d + DisplayMessageActivity.bonusValue(d);
            String totalText = formatter.format(total);
            int rides = DisplayMessageActivity.numberOfRides(DisplayMessageActivity.round_to_tenths(total));
            String ridesText = rides + "";

            TextView v = new TextView(this);
            Resources r = getResources();
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, r.getDisplayMetrics());
            v.setPadding((int) px, 0, (int) px, 15);
            v.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22);

            String text = "<strong><span style='color:#00933C;'>$"+ refill_amt + "</span></strong> to your card for a total of <span style='color:#00933C'>$"+ totalText +"</span>";
            if (bonus > 0.0) {
                text += " (<span style='color:#00933C'>$" + bonusText + " </span> <span style='color:#FCCC0A'>bonus</span>)";
            }

            if (rides > 0) {
                text += " and exactly <span style='color:#0039A6'>" + ridesText + "</span>";
                if (rides > 1) {
                    text += " rides.";
                } else {
                    text += " ride.";
                }
            } else {
                text += " which will give you no rides :(";
            }

            v.setText(Html.fromHtml(text));
            layout.addView(v);
        }
    }

    public static int numberOfRides(double amt) {
        double fare = 2.75;
        return (int) (amt / fare);
    }

    public static ArrayList<Double> amountsToAddForNoRemainder(double remaining, double spend_amt) {
        double increment = 0.05;
        double maximum = 80.0;
        remaining = DisplayMessageActivity.round_to_tenths(remaining);
        ArrayList<Double> amounts = new ArrayList<>();
        for (double i = 0; i <= spend_amt; i += increment) {
            i = DisplayMessageActivity.round_to_tenths(i);
            double bonus = DisplayMessageActivity.bonusValue(i);
            double curr_amt = DisplayMessageActivity.round_to_tenths(remaining + i + bonus);
            if (DisplayMessageActivity.leavesZeroBalance(curr_amt) && curr_amt <= maximum) {
                amounts.add(DisplayMessageActivity.round_to_tenths(i));
            }
        }
        return amounts;
    }

    public static boolean leavesZeroBalance(double amt) {
        double fare = 2.75;
        return amt % fare == 0;
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
