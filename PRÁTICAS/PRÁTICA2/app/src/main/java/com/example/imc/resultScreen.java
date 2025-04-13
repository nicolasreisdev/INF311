package com.example.imc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class resultScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_screen);

        Intent it = getIntent();

        IMC imcResult = (IMC) getIntent().getSerializableExtra("IMC");

        TextView name = (TextView) findViewById(R.id.outputName);
        TextView age = (TextView) findViewById(R.id.outputAge);
        TextView weight = (TextView) findViewById(R.id.outputWeight);
        TextView height = (TextView) findViewById(R.id.outputHeight);
        TextView imc = (TextView) findViewById(R.id.outputIMC);
        TextView classification = (TextView) findViewById(R.id.outputClassification);

        name.setText(imcResult.getName());
        age.setText(String.valueOf(imcResult.getAge()));
        weight.setText(String.valueOf(imcResult.getWeight()));
        height.setText(String.valueOf(imcResult.getHeight()));
        imc.setText(String.valueOf(imcResult.getIMC()));

        if(imcResult.getIMC() < 18.5){
            classification.setText("Abaixo do peso");
        }
        else if(imcResult.getIMC() < 24.9){
            classification.setText("Saudável");
        }
        else if(imcResult.getIMC() < 29.9){
            classification.setText("Sobrepeso");
        }
        else if(imcResult.getIMC() < 34.9){
            classification.setText("Obesidade grau 1");
        }
        else if(imcResult.getIMC() < 39.9){
            classification.setText("Obesidade grau 2 (severa");
        }
        else{
            classification.setText("Obesidade grau 3 (mórbida");
        }

    }

    public void onFinish(View view){
        finish();
    }

    private String getClassName(){
        String s = getClass().getName();
        return s;
    }

    protected void onStart(){
        super.onStart();
        Log.i("Ciclo de vida ", getClassName() + "onStart chamado.");
    }

    protected void onRestart(){
        super.onRestart();
        Log.i("Ciclo de vida ", getClassName() + "onRestart chamado.");
    }

    protected void onResume(){
        super.onResume();
        Log.i("Ciclo de vida ", getClassName() + "onResume chamado.");
    }

    protected void onPause(){
        super.onPause();
        Log.i("Ciclo de vida ", getClassName() + "onPause chamado.");
    }

    protected void onStop(){
        super.onStop();
        Log.i("Ciclo de vida ", getClassName() + "onStop chamado.");
    }

    protected void onDestroy(){
        super.onDestroy();
        Log.i("Ciclo de vida ", getClassName() + "onDestroy chamado.");
    }






}