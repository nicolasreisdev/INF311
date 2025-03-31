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

    public void numberAction(View view){
        String currentText = visor.getText().toString();
        String tag = view.getTag().toString();
        if(tag.equals("Zero")){
            char lastCharacter = (currentText.length() == 0) ? 'N' : currentText.charAt(currentText.length()-1);
            char penultimatemoCaracter = (currentText.length() == 0 || currentText.length() == 1) ? 'N' : currentText.charAt(currentText.length()-2);
            if(lastCharacter == '0' && lastCharacter == 'x' || lastCharacter == '+' || lastCharacter == '-' || lastCharacter == '/' || lastCharacter == '.' ){
                visor.setText(currentText);
            }
            else{
                String newText = currentText + "0";
                visor.setText(newText);
            }
        }
        else if(tag.equals("One")){
            String newText = currentText + "1";
            visor.setText(newText);
        }
        else if(tag.equals("Two")){
            String newText = currentText + "2";
            visor.setText(newText);
        }
        else if(tag.equals("Three")){
            String newText = currentText + "3";
            visor.setText(newText);
        }
        else if(tag.equals("Four")){
            String newText = currentText + "4";
            visor.setText(newText);
        }
        else if(tag.equals("Five")){
            String newText = currentText + "5";
            visor.setText(newText);
        }
        else if(tag.equals("Six")){
            String newText = currentText + "6";
            visor.setText(newText);
        }
        else if(tag.equals("Seven")){
            String newText = currentText + "7";
            visor.setText(newText);
        }
        else if(tag.equals("Eight")){
            String newText = currentText + "8";
            visor.setText(newText);
        }
        else if(tag.equals("Nine")){
            String newText = currentText + "9";
            visor.setText(newText);
        }
    }
    public void operatorAction(View view){
        String currentText = visor.getText().toString();
        String tag = view.getTag().toString();
        if(tag.equals("Soma")){
            char lastCharacter = (currentText.length() == 0) ? 'N' : currentText.charAt(currentText.length()-1);
            if(lastCharacter == 'x' || lastCharacter == '+' || lastCharacter == '-' || lastCharacter == '/' || lastCharacter == '.'){
                return;
            }
            else{
                String newText = currentText + "+";
                visor.setText(newText);
            }
        }
        else if(tag.equals("Sub")) {
            char lastCharacter = (currentText.length() == 0) ? 'N' : currentText.charAt(currentText.length()-1);
            if(lastCharacter == 'x' || lastCharacter == '+' || lastCharacter == '-' || lastCharacter == '/' || lastCharacter == '.'){
                return;
            }
            else{
                String newText = currentText + "-";
                visor.setText(newText);
            }
        }
        else if(tag.equals("Div")){
            char lastCharacter = (currentText.length() == 0) ? 'N' : currentText.charAt(currentText.length()-1);
            if(lastCharacter == 'x' || lastCharacter == '+' || lastCharacter == '-' || lastCharacter == '/' || lastCharacter == '.'){
                return;
            }
            else{
                String newText = currentText + "/";
                visor.setText(newText);
            }
        }
        else if(tag.equals("Mult")){
            char lastCharacter = (currentText.length() == 0) ? 'N' : currentText.charAt(currentText.length()-1);
            if(lastCharacter == 'x' || lastCharacter == '+' || lastCharacter == '-' || lastCharacter == '/' || lastCharacter == '.'){
                return;
            }
            else{
                String newText = currentText + "x";
                visor.setText(newText);
            }
        }
        else if(tag.equals("Ponto")){
            char lastCharacter = (currentText.length() == 0) ? 'N' : currentText.charAt(currentText.length()-1);
            if(lastCharacter == 'x' || lastCharacter == '+' || lastCharacter == '-' || lastCharacter == '/' || lastCharacter == '.'){
                return;
            }
            else{
                String newText = currentText + ".";
                visor.setText(newText);
            }
        }
    }
    public void clearAction(View view){
        String currentText = visor.getText().toString();
        String tag = view.getTag().toString();
        if(tag.equals("Backspace")){
            String newText = currentText.substring(0, currentText.length()-1);
            visor.setText(newText);
        }
        else if(tag.equals("Limpar")){
            String newText = "";
            visor.setText(newText);
        }
    }
    public void Result(View view){
        String expression = visor.getText().toString();
        if(expression.isEmpty()){
            visor.setText(expression);
        }
        else{
            expression = expression.replace("x", "*");
            String[] tokens = expression.split("(?<=[+\\-*/])|(?=[+\\-*/])");
            if (tokens.length % 2 == 0) {
                String result = "Expressão incompleta";
                visor.setText(result);
                return;
            }
            // Mult and Div
            double result = Double.parseDouble(tokens[0]);
            for(int i = 1; i < tokens.length;i++){
                String operator = tokens[i];
                double numberTwo = (i == tokens.length-1) ? 0.0 :Double.parseDouble(tokens[i+1]);
                if(operator.equals("*")){
                    result *= numberTwo;
                }
                else if(operator.equals("/")){
                    if(numberTwo == 0){
                        String error = "ERRO! Divisão por zero";
                        visor.setText(error);
                        return;
                    }
                    result /= numberTwo;
                }
            }
            // Soma and Sub
            for(int i = 1; i < tokens.length;i++){
                String operator = tokens[i];
                double numberTwo = (i == tokens.length-1) ? 0.0 : Double.parseDouble(tokens[i+1]);
                if(operator.equals("+")){
                    result += numberTwo;
                }
                else if(operator.equals("-")){
                    result -= numberTwo;
                }
            }
            visor.setText(String.valueOf(result));
        }

    }


}