package com.example.atividade2;

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

    private EditText visor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        visor = findViewById(R.id.visor);
    }

    public void acaoNumber(View view){
        String textoAtual = visor.getText().toString();
        String tag = view.getTag().toString();
        if(tag.equals("Zero")){
            char ultimoCaracter = (textoAtual.length() == 0) ? 'N' : textoAtual.charAt(textoAtual.length()-1);
            char penultimoCaracter = (textoAtual.length() == 0 || textoAtual.length() == 1) ? 'N' : textoAtual.charAt(textoAtual.length()-2);
            if(ultimoCaracter == '0' && ultimoCaracter == 'x' || ultimoCaracter == '+' || ultimoCaracter == '-' || ultimoCaracter == '/' || ultimoCaracter == '.' ){
                visor.setText(textoAtual);
            }
            else{
                String newText = textoAtual + "0";
                visor.setText(newText);
            }
        }
        else if(tag.equals("One")){
            String newText = textoAtual + "1";
            visor.setText(newText);
        }
        else if(tag.equals("Two")){
            String newText = textoAtual + "2";
            visor.setText(newText);
        }
        else if(tag.equals("Three")){
            String newText = textoAtual + "3";
            visor.setText(newText);
        }
        else if(tag.equals("Four")){
            String newText = textoAtual + "4";
            visor.setText(newText);
        }
        else if(tag.equals("Five")){
            String newText = textoAtual + "5";
            visor.setText(newText);
        }
        else if(tag.equals("Six")){
            String newText = textoAtual + "6";
            visor.setText(newText);
        }
        else if(tag.equals("Seven")){
            String newText = textoAtual + "7";
            visor.setText(newText);
        }
        else if(tag.equals("Eight")){
            String newText = textoAtual + "8";
            visor.setText(newText);
        }
        else if(tag.equals("Nine")){
            String newText = textoAtual + "9";
            visor.setText(newText);
        }
    }
    public void acaoAlgebra(View view){
        String textoAtual = visor.getText().toString();
        String tag = view.getTag().toString();
        if(tag.equals("Soma")){
            char ultimoCaracter = (textoAtual.length() == 0) ? 'N' : textoAtual.charAt(textoAtual.length()-1);
            if(ultimoCaracter == 'x' || ultimoCaracter == '+' || ultimoCaracter == '-' || ultimoCaracter == '/' || ultimoCaracter == '.'){
                return;
            }
            else{
                String newText = textoAtual + "+";
                visor.setText(newText);
            }
        }
        else if(tag.equals("Sub")) {
            char ultimoCaracter = (textoAtual.length() == 0) ? 'N' : textoAtual.charAt(textoAtual.length()-1);
            if(ultimoCaracter == 'x' || ultimoCaracter == '+' || ultimoCaracter == '-' || ultimoCaracter == '/' || ultimoCaracter == '.'){
                return;
            }
            else{
                String newText = textoAtual + "-";
                visor.setText(newText);
            }
        }
        else if(tag.equals("Div")){
            char ultimoCaracter = (textoAtual.length() == 0) ? 'N' : textoAtual.charAt(textoAtual.length()-1);
            if(ultimoCaracter == 'x' || ultimoCaracter == '+' || ultimoCaracter == '-' || ultimoCaracter == '/' || ultimoCaracter == '.'){
                return;
            }
            else{
                String newText = textoAtual + "/";
                visor.setText(newText);
            }
        }
        else if(tag.equals("Mult")){
            char ultimoCaracter = (textoAtual.length() == 0) ? 'N' : textoAtual.charAt(textoAtual.length()-1);
            if(ultimoCaracter == 'x' || ultimoCaracter == '+' || ultimoCaracter == '-' || ultimoCaracter == '/' || ultimoCaracter == '.'){
                return;
            }
            else{
                String newText = textoAtual + "x";
                visor.setText(newText);
            }
        }
        else if(tag.equals("Ponto")){
            char ultimoCaracter = (textoAtual.length() == 0) ? 'N' : textoAtual.charAt(textoAtual.length()-1);
            if(ultimoCaracter == 'x' || ultimoCaracter == '+' || ultimoCaracter == '-' || ultimoCaracter == '/' || ultimoCaracter == '.'){
                return;
            }
            else{
                String newText = textoAtual + ".";
                visor.setText(newText);
            }
        }
    }
    public void acaoClear(View view){
        String textoAtual = visor.getText().toString();
        String tag = view.getTag().toString();
        if(tag.equals("Backspace")){
            String newText = textoAtual.substring(0, textoAtual.length()-1);
            visor.setText(newText);
        }
        else if(tag.equals("Limpar")){
            String newText = "";
            visor.setText(newText);
        }
    }
    public void Result(View view){
        String expressao = visor.getText().toString();
        if(expressao.isEmpty()){
            visor.setText(expressao);
        }
        else{
            expressao = expressao.replace("x", "*");
            String[] tokens = expressao.split("(?<=[+\\-*/])|(?=[+\\-*/])");
            if (tokens.length % 2 == 0) {
                String resultado = "Expressão incompleta";
                visor.setText(resultado);
                return;
            }
            // Mult and Div
            double resultado = Double.parseDouble(tokens[0]);
            for(int i = 1; i < tokens.length;i++){
                String operador = tokens[i];
                double numberTwo = (i == tokens.length-1) ? 0.0 :Double.parseDouble(tokens[i+1]);
                if(operador.equals("*")){
                    resultado *= numberTwo;
                }
                else if(operador.equals("/")){
                    if(numberTwo == 0){
                        String error = "ERRO! Divisão por zero";
                        visor.setText(error);
                        return;
                    }
                    resultado /= numberTwo;
                }
            }
            // Soma and Sub
            for(int i = 1; i < tokens.length;i++){
                String operador = tokens[i];
                double numberTwo = (i == tokens.length-1) ? 0.0 : Double.parseDouble(tokens[i+1]);
                if(operador.equals("+")){
                    resultado += numberTwo;
                }
                else if(operador.equals("-")){
                    resultado -= numberTwo;
                }
            }
            visor.setText(String.valueOf(resultado));
        }

    }


}