package com.example.imc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class inputScreen extends Activity {
    EditText name, age, weight, height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void clickButton(View view){
        name = (EditText) findViewById(R.id.inputName);
        age = (EditText) findViewById(R.id.inputAge);
        weight = (EditText) findViewById(R.id.inputWeight);
        height = (EditText) findViewById(R.id.inputHeight);

        IMC imc = new IMC(name.getText().toString(), Integer.parseInt(age.getText().toString()),
                Double.parseDouble(weight.getText().toString()), Double.parseDouble(height.getText().toString()));


        Intent it = new Intent(getBaseContext(), resultScreen.class);
        it.putExtra("IMC", imc);
        startActivity(it);
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