package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends Activity {
    EditText One, Two;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

    }

    public void acaoBotao(View v){
        One = (EditText) findViewById(R.id.Valor1Entrada);
        Two = (EditText) findViewById(R.id.Valor1Entrada);
        TextView txt = (TextView) findViewById(R.id.Resultado);
        String tag = v.getTag().toString();

        if(tag.equals("Soma")){
            double numberOne = Double.parseDouble(One.getText().toString());
            double numberTwo= Double.parseDouble(One.getText().toString());
            double sum = (numberOne+numberTwo);
            String result = String.valueOf(sum);
            txt.setText(result);
        } else if (tag.equals("Sub")) {
            double numberOne = Double.parseDouble(One.getText().toString());
            double numberTwo= Double.parseDouble(One.getText().toString());
            double sub = (numberOne-numberTwo);
            String result = String.valueOf(sub);
            txt.setText(result);
        } else if (tag.equals("Mult")) {
            double numberOne = Double.parseDouble(One.getText().toString());
            double numberTwo= Double.parseDouble(One.getText().toString());
            double mult = (numberOne*numberTwo);
            String result = String.valueOf(mult);
            txt.setText(result);
        } else if (tag.equals("Div")) {
            double numberOne = Double.parseDouble(One.getText().toString());
            double numberTwo= Double.parseDouble(One.getText().toString());
            if(numberTwo == 0){
                txt.setText("Erro!");
            }
            else {
                double div = (numberOne / numberTwo);
                String result = String.valueOf(div);
                txt.setText(result);
            }
        }
    }

}