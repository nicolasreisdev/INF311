package com.pratica.firstscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import androidx.appcompat.widget.SwitchCompat;


public class MainActivity extends Activity {
    LanternaHelper lanterna;
    MotorHelper motor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Teste", "Iniciou Primeira Tela") ;
    }

    public void onClickClassification(View v){
        Intent it = new Intent("com.pratica.secondscreen.Classification");
        startActivityForResult(it, 1);
    }

    @Override
    protected void onActivityResult(int codigoRequisicao, int codigoResultado, Intent it){
        if(it == null){
            return;
        }else if(codigoRequisicao == 1 && codigoResultado == RESULT_OK){
            lanterna = new LanternaHelper(this);
            motor = new MotorHelper(this);
            float valorLuminosidade = it.getExtras().getFloat("valorLuminosidade");
            float valorProximidade = it.getExtras().getFloat("valorProximidade");
            SwitchCompat switchLanterna = findViewById(R.id.switchLanterna);
            SwitchCompat switchProximidade = findViewById(R.id.switchProximidade);
            Log.i("Teste", "Luminosidade: " + valorLuminosidade);
            Log.i("Teste", "Proximidade: " + valorProximidade);
            if (valorLuminosidade < 20.0) {
                lanterna.ligar();
                switchLanterna.setChecked(true);
                Log.i("Teste", "Ligou lanterna");

            }else{
                Log.i("Teste", "Desligou lanterna");
                lanterna.desligar();
                switchLanterna.setChecked(false);
            }

            if (valorProximidade > 3.0) {
                motor.iniciarVibracao();
                switchProximidade.setChecked(true);
                Log.i("Teste", "Proximidade maior que 3");
            }else{
                Log.i("Teste", "Proximidade menor que 3");
                motor.pararVibracao();
                switchProximidade.setChecked(false);
            }
        }
    }

}