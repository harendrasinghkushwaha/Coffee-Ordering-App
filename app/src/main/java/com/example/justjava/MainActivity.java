package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//import java.text.NumberFormat;
import androidx.appcompat.app.AppCompatActivity;

/*
 *This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    int quantity = 1;
    int creamPrice=5;
    int chocolatePrice=5;
    String address = "singhharendra711@gmail.com";
    /*
     *This method is called when the order button is clicked.
     */
    public void submitOrder(View view){
        //displayPrice(quantity * 2);

        boolean hasWhippedCream;
        hasWhippedCream = checkWhippedCream();
        boolean hasChocolate;
        hasChocolate = checkChocolate();
        int price = calculatePrice(quantity,hasWhippedCream,hasChocolate);
        String name = customerName();
        String orderSummary = createOrderSummary(price,quantity,hasWhippedCream,hasChocolate,name);
        //displayMessage(orderSummary);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { address });
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order for "+ name);
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }



    String customerName(){
        EditText yourEditText = (EditText)findViewById(R.id.name_edittext_view);
        return yourEditText.getText().toString();

    }

    boolean checkChocolate(){
        CheckBox simpleCheckBox = (CheckBox) findViewById(R.id.chocolate_me_checkbox);
        return simpleCheckBox.isChecked();
    }

    boolean checkWhippedCream(){
        CheckBox simpleCheckBox = (CheckBox) findViewById(R.id.cream_me_checkbox);
        return simpleCheckBox.isChecked();
    }
    /**
     * creates the final summary for the order
     *
     * @return the summary
     */
     private String createOrderSummary(int price, int quantity,boolean hasWhippedCream,boolean hasChocolate,String name){
         return "Name: " + name +
                 "\nAdd Whipped cream? "+ hasWhippedCream +
                 "\nAdd Chocolate? "+ hasChocolate +
                 "\nQuantity: " + quantity  +
                 "\nTotal Price : Rs." + price +
                 " \nThank You!";
     }
    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @return the price
     */
    private int calculatePrice(int quantity,boolean hasWhippedCream,boolean hasChocolate) {
        int price = quantity*10;
        if(hasWhippedCream) price = price + creamPrice;
        if(hasChocolate) price = price + chocolatePrice;
        return price;
    }



    public void increment(View view){
        if(quantity<9){
            quantity = quantity + 1;
        }else{
            quantity=10;
            Toast.makeText(getApplicationContext(),"You can not have more than 10 coffee",Toast.LENGTH_SHORT).show();
        }

        displayQuantity(quantity);
    }

    public void decrement(View view){
        if(quantity>1){
            quantity = quantity - 1;
        }else{
            quantity=1;
            Toast.makeText(getApplicationContext(),"You can not have less than 1 coffee",Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);
    }

    /*
     *This method displays the given quantity on the screen.
     */
    private void displayQuantity(int number){
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(""+number);
    }


}
