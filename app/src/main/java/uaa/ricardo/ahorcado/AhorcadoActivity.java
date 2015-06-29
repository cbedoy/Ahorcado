package uaa.ricardo.ahorcado;

import android.app.Activity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Carlos Bedoy on 6/29/15.
 * <p/>
 * Mobile App Developer - Exchange Android
 * <p/>
 * Pademobile
 */
public class AhorcadoActivity extends Activity
{
    private List<String> mLetras;

    private List<String> mFrases;

    private String fraseParaAhorcado;
    private String fraseOculta;

    private int intentos;
    private int maxIntentos;

    private TextView textView;

    private ImageView imageView;

    private int[] estadosAhorcado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFrases = new ArrayList<>();
        mFrases.add("pokemon");
        mFrases.add("el escorpion dorado");
        mFrases.add("hipster");
        mFrases.add("material design");
        mFrases.add("tarjeta de regalo");
        mFrases.add("dragon ball super");

        mLetras = new ArrayList<>();
        for(char i = 'A'; i<='Z'; i++){
            mLetras.add(String.valueOf(i));
        }

        Collections.shuffle(mLetras);

        Collections.shuffle(mFrases);

        GridView gridView = (GridView) findViewById(R.id.gridView);

        textView = (TextView) findViewById(R.id.textView);

        imageView = (ImageView) findViewById(R.id.imageView);

        estadosAhorcado = new int[]{
                R.drawable.ahorcado_0,
                R.drawable.ahorcado_1,
                R.drawable.ahorcado_2,
                R.drawable.ahorcado_3,
                R.drawable.ahorcado_4,
                R.drawable.ahorcado_5,

        };


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mLetras);

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                validarCaracter(mLetras.get(i));
            }
        });

        seleccionarYConvertirFrase();

    }

    private void seleccionarYConvertirFrase() {
        fraseOculta = "";
        fraseParaAhorcado = mFrases.get(0).toUpperCase();
        for(int i=0; i<fraseParaAhorcado.length(); i++){
            if (fraseParaAhorcado.charAt(i) == ' ')
                fraseOculta+=' ';
            fraseOculta+="_";
        }
    }

    private void validarCaracter(String caracter){
        if (fraseParaAhorcado.contains(caracter)){
            for (int i = 0; i<fraseParaAhorcado.length(); i++){
                if(String.valueOf(fraseParaAhorcado.charAt(i)).equals( caracter)){
                    StringBuilder builder = new StringBuilder(fraseOculta);
                    builder.replace(i, i, caracter);
                    fraseOculta = builder.toString();
                }
            }
            textView.setText(fraseOculta);
        }else {
            intentos++;
            notificarYCambiarAhorcado();
        }
    }

    private void notificarYCambiarAhorcado() {
        Toast.makeText(getApplicationContext(), "Upps! error", Toast.LENGTH_LONG).show();
    }
}
