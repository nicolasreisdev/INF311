package com.pratica.secondscreen;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends Activity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor sensorLuminosidade;
    private Sensor sensorProximidade;
    private SensorEvent event;
    private float maxLuminosidade;
    private float maxProximidade;
    private float valorLuminosidade;
    private float valorProximidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //Obtem sensorores
        sensorLuminosidade = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorProximidade = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        Log.i("Teste", "Iniciou Segunda Tela") ;
        if(sensorProximidade != null && sensorLuminosidade != null){
            //atualiza o máximo das barras
            maxProximidade = sensorProximidade.getMaximumRange();
            maxLuminosidade = sensorLuminosidade.getMaximumRange();
        }else{
            Toast.makeText(this, "Sensores inexistentes", Toast.LENGTH_LONG).show();
        }

    }

    protected void onResume(){
        super.onResume();
        if(sensorProximidade != null && sensorLuminosidade != null){
            sensorManager.registerListener(this,sensorProximidade,SensorManager.SENSOR_DELAY_GAME);
            sensorManager.registerListener(this,sensorLuminosidade,SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Para de escutar os sensores
        sensorManager.unregisterListener(this);
        Log.i("SENSOR_PAROU", "Parou sensores");
    }


    public void onClickClassification(View v){
        Log.i("Teste", "Retornou") ;
        Intent it = new Intent();
        it.putExtra("valorLuminosidade", valorLuminosidade);
        it.putExtra("valorProximidade", valorProximidade);
        setResult(RESULT_OK, it);
        finish();
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor s = event.sensor; //obtem o sensor que mudou
        Log.i("SENSOR_MUDOU", "Tipo: " + s.getStringType());
        if (s.getType() == Sensor.TYPE_PROXIMITY) {
            valorProximidade = event.values[0];
            Log.i("SENSOR_MUDOU", "Proximidade: " + event.values[0] + " cm.");
        }
        else if(s.getType() == Sensor.TYPE_LIGHT){
            valorLuminosidade = event.values[0];
            Log.i("SENSOR_MUDOU", "Luz: " + event.values[0] + "lx.");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //chamado quando o sensor muda de precisão – variável prec não definida!
        Log.i("SENSOR_PRECISAO", "NOME: " + sensor.getName() + " Precisão: " + accuracy);
    }


}