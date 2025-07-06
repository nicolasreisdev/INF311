package com.pratica.checkinlocais.ui;


import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.pratica.checkinlocais.R;
import com.pratica.checkinlocais.data.BancoDados;

public class RelatorioLocaisMaisVisitados extends AppCompatActivity {

    BancoDados bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_locais_mais_visitados);

        LinearLayout content = findViewById(R.id.layoutConteudo);
        LinearLayout qnt = findViewById(R.id.layoutQtd);

        Toolbar header = findViewById(R.id.Header);
        setSupportActionBar(header);

        bd = BancoDados.getInstance();

        Cursor c = bd.buscar("Checkin", new String[] {"Local, qtdVisitas"}, "", "qtdVisitas DESC");
        while(c.moveToNext()){
            int idx = c.getColumnIndex("Local");
            String local = c.getString(idx);
            idx = c.getColumnIndex("qtdVisitas");
            int qtd = c.getInt(idx);

            TextView textView = new TextView(this);
            textView.setText(local);
            textView.setTextSize(20);
            textView.setTextColor(getResources().getColor(R.color.black));
            content.addView(textView);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.gravity = Gravity.END;
            TextView textView2 = new TextView(this);
            textView2.setLayoutParams(params);
            textView2.setText(String.valueOf(qtd));
            textView2.setTextSize(20);
            textView2.setTextColor(getResources().getColor(R.color.black));
            qnt.addView(textView2);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menurelatorio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.voltar) {
            finish();
            return true;

        } else {
            //se nenhum dos itens foi clicado, deixa o sistema tratar
            return super.onOptionsItemSelected(item);
        }
    }

}