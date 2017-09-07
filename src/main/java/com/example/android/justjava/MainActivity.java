
package com.example.android.justjava;

import android.content.Intent;
import android.content.res.Configuration;
import android.icu.text.NumberFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import static android.R.attr.x;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 1;
    int pricePerCup = 5;
    double taxValue = 0.07;

    @Override
    /**
     * This overrides the default onCreate method and allows us to provide custom start-up actions
     * on the creation of a new instance.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*To test language settings*/
        /*
        String languageToLoad  = "es"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        this.setContentView(R.layout.activity_main);
        */

        setContentView(R.layout.activity_main);

    }

    /**
     * This method is to increase the quantity ordered by 1 when the '+' button is pushed.
     */
    public void increment(View view) {
        if (quantity < 99) {
            quantity = quantity + 1;
        } else {
            quantity = 99;
            Toast.makeText(this, getString(R.string.max_order), Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);
        displayPrice();
    }

    /**
     * This method is to decrease the quantity ordered by 1 when the '+' button is pushed.
     */
    public void decrement(View view) {
        if (quantity > 1) {
            quantity = quantity - 1;
        } else {
            quantity = 1;
            Toast.makeText(this, getString(R.string.min_order), Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);
        displayPrice();
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        sendEmailOrderSummary(getString(R.string.email_address), createOrderSummary(getPricePerCup()) );
    }


    public void updateOnCheck(View view)
    {
        displayQuantity(quantity);
        displayPrice();
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(number);
    }

    /**
     * This method displays the given price on the screen.
     */
    public void displayPrice() {

        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(getPricePerCup()*quantity));

        TextView taxTextView = (TextView) findViewById(R.id.tax_text_view);
        taxTextView.setText(NumberFormat.getCurrencyInstance().format(calculateTax(quantity, taxValue)) + " " + getString(R.string.tax));

        TextView totalTextView = (TextView) findViewById(R.id.total_text_view);
        totalTextView.setText(NumberFormat.getCurrencyInstance().format(calculatePricePostTax(quantity, taxValue)));
    }

    /**
     * This method creates the order summary
     * @param orderPrice is supposed to be the total cost.
     */
    private String createOrderSummary(double orderPrice) {
        EditText nameEditText = (EditText) findViewById(R.id.name_input_edit_text);
        String orderSummary = getString(R.string.order_summary_name, nameEditText.getText().toString());

        orderSummary += createToppingSummary();
        orderSummary += "\n" + getString(R.string.quantity) + " " + quantity;
        orderSummary += "\n" + getString(R.string.total) +": " + NumberFormat.getCurrencyInstance().format(calculatePricePostTax(quantity, taxValue));
        orderSummary += "\n" + getString(R.string.thank_you);
        return orderSummary;
    }

    /**
     * This method is used to create an intent which will open the default email program and populate and email
     * with the order summary and relevant recipient information regarding the placed order.
     *
     * @param address This is used as the default  email address to receive the orders
     * @param bodyText This is the body of the email which contains the order summary
     */
    public void sendEmailOrderSummary(String address, String bodyText) {
        EditText nameEditText = (EditText) findViewById(R.id.name_input_edit_text);
        if(nameEditText.getText().toString().length() < 1){
            Toast.makeText(this, getString(R.string.valid_name_message), Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, address);
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name) + " " + getString(R.string.order) + " " + getString(R.string.from) + " " + nameEditText.getText().toString());
            intent.putExtra(Intent.EXTRA_TEXT, bodyText);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }

    /**
        *Calculate the total cost with tax
        *@param quantity is the total number of coffees ordered
        *@param taxValue is the local tax rate per dollar
     */
    private double calculatePricePostTax(int quantity, double taxValue) {
        return (quantity * getPricePerCup()) + ((quantity * getPricePerCup()) * taxValue);
    }

    /**
       * Calculate and return ONLY the tax without the total.
       * @param quantity is the total number of coffees ordered
       * @param taxValue is the local tax rate per dollar
    */
    private double calculateTax(int quantity, double taxValue) {
        return ((quantity * getPricePerCup()) * taxValue);
    }

    /**
      *This method calculates the price per cup with the addition of toppings.
     */
    private int getPricePerCup() {
        int totalCupPrice = pricePerCup;
    CheckBox hasWhippedCream = (CheckBox) findViewById(R.id.whipped_cream_check_box);
        CheckBox hasChocolate = (CheckBox) findViewById(R.id.chocolate_check_box);
        if (hasWhippedCream.isChecked()) {
            totalCupPrice += 1;
        }
        if (hasChocolate.isChecked()) {
            totalCupPrice += 2;
        }
        return totalCupPrice;
    }

    /*
    This method creates a string type summary of the toppings ordered.
    Call this method when creating the final order summary.
    */
    private String createToppingSummary() {
        CheckBox hasWhippedCream = (CheckBox) findViewById(R.id.whipped_cream_check_box);
        CheckBox hasChocolate = (CheckBox) findViewById(R.id.chocolate_check_box);
        String toppingSummary = "";

        if (hasWhippedCream.isChecked()) {
            toppingSummary += "\n" + getResources().getString(R.string.whipped_cream_added);
        }
        if (hasChocolate.isChecked()) {

            toppingSummary += "\n" + getString(R.string.chocolate_added);
        }
        return toppingSummary;
    }

}