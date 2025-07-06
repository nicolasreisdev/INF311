package com.pratica.checkinlocais.ui;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.pratica.checkinlocais.R;
import com.pratica.checkinlocais.data.BancoDados;

public class GestaoCheckin extends AppCompatActivity {

    private BancoDados bd;
    private LinearLayout content;
    private LinearLayout delete;


    private AlertDialog.Builder getAlerta(String local, ImageButton button, TextView textView) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setMessage("Tem certeza que deseja excluir "+ local + "?");
        alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.i("Teste", "Entrei com "+ button.getTag());
                bd.deletar("Checkin", "Local = '" + button.getTag() + "'");
                content.removeView(textView);
                delete.removeView(button);
            }
        });
        alerta.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        return alerta;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestao_checkin);

        content = findViewById(R.id.layoutConteudo);
        delete = findViewById(R.id.layoutDeletar);

        bd = BancoDados.getInstance();

        Toolbar header = findViewById(R.id.Header);
        setSupportActionBar(header);

        Cursor c = bd.buscar("Checkin", new String[]{"Local"}, "", null);
        while (c.moveToNext()) {
            int idx = c.getColumnIndex("Local");
            String local = c.getString(idx); // recebe o valor da coluna

            TextView textView = new TextView(this);
            textView.setText(local);
            textView.setPadding(5, 15, 5, 15);
            textView.setTextSize(20);
            textView.setTextColor(getResources().getColor(R.color.black));
            content.addView(textView);

            ImageButton button = new ImageButton(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.gravity = Gravity.END;
            button.setLayoutParams(params);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                button.setBackgroundColor(getResources().getColor(R.color.red, null));
            }
            button.setScaleType(ImageButton.ScaleType.FIT_CENTER);
            button.setImageResource(R.drawable.ic_baseline_delete);
            button.setPadding(5, 20, 5, 20);
            button.setTag(local);
            delete.addView(button);

            button.setOnClickListener(view -> {
                AlertDialog.Builder alerta = getAlerta(local, button, textView);
                alerta.show();
            });

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menugestaocheckin, menu);
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