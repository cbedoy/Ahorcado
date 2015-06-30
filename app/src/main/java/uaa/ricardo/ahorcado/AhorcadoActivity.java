package uaa.ricardo.ahorcado;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AhorcadoActivity extends Activity
{
    private List<LetraEstado> mLetras;

    private List<String> mFrases;


    private String fraseParaAhorcado;
    private String fraseOculta;

    private int intentos;
    private int maxIntentos;

    private TextView textView;

    private ImageView imageView;

    private int[] estadosAhorcado;

    private boolean haFinalizadoElJuego;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ahorcado_activity);

        mFrases = new ArrayList<>();
        mFrases.add("pokemon");
        mFrases.add("el escorpion dorado");
        mFrases.add("hipster");
        mFrases.add("material design");
        mFrases.add("tarjeta de regalo");
        mFrases.add("dragon ball super");
        mFrases.add("mi ahorcado");
        mFrases.add("enciclopedia");
        mFrases.add("steren");
        mFrases.add("mustang");
        mFrases.add("gradlew");
        mFrases.add("universidad autonoma de aguascalientes");
        mFrases.add("teatro del pueblo");
        mFrases.add("cerveza");
        mFrases.add("xbox tres sesenta");
        mFrases.add("play station cuatro");
        mFrases.add("fender");
        mFrases.add("less paul");
        mFrases.add("cisco");
        mFrases.add("android developer");

        mLetras = new ArrayList<>();
        for(char i = 'A'; i<='Z'; i++){
            LetraEstado letraEstado = new LetraEstado();
            letraEstado.caracter = String.valueOf(i);
            letraEstado.estado = false;
            mLetras.add(letraEstado);
        }

        Collections.shuffle(mLetras);

        Collections.shuffle(mFrases);

        GridView gridView = (GridView) findViewById(R.id.gridView);

        //Text view que maneja la palabra a adivinar conforme se va respondiendo adecuandadamente
        textView = (TextView) findViewById(R.id.textView);

        //Image View para mostrar los estados del ahorcado
        imageView = (ImageView) findViewById(R.id.imageView);

        //Bandera opara finalizar el juego
        haFinalizadoElJuego = false;

        //Vector para manejar las imagenes del ahorcado
        estadosAhorcado = new int[]{
                R.drawable.ahorcado_0,
                R.drawable.ahorcado_1,
                R.drawable.ahorcado_2,
                R.drawable.ahorcado_3,
                R.drawable.ahorcado_4,
                R.drawable.ahorcado_5,
        };

        //Se define el numero de intentos

        maxIntentos = estadosAhorcado.length;

        intentos = 0;


        //Se crea el adaptador para manejar mas facil y de manera personalizada la celda
        final LetraAdapter letraAdapter = new LetraAdapter(mLetras, getLayoutInflater());


        //Se setea el adaptador
        gridView.setAdapter(letraAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                //Sio no esta el estado como previamente seleccionada progreigue
                if(!mLetras.get(i).estado) {
                    //Validar si no se ha seleccionado esa letra
                    LetraEstado letraEstado = mLetras.get(i);

                    letraEstado.estado = true;
                    String caracterAEvaluar = letraEstado.caracter;

                    validarCaracter(caracterAEvaluar);

                    mLetras.set(i, letraEstado);

                    letraAdapter.notifyDataSetChanged();
                }
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
            //Llenamos la string con _ en donde tenga las letras, solo se hace una vez
        }
        Log.e("error", fraseOculta);
    }

    private void validarCaracter(String caracter)
    {
        //Verificamos si la palabra seleccionada contiene la letra que usuario selecciondo
        if (fraseParaAhorcado.contains(caracter))
        {
            //Si la contiene entonces iteramos la cadena
            for (int i = 0; i<fraseParaAhorcado.length(); i++)
            {
                //Si el caracter en la posicion i de la palabra es igual al caracter de usuario ingresado
                if(String.valueOf(fraseParaAhorcado.charAt(i)).equals( caracter))
                {
                    //Usamos una clase auxiliar para poder sustituir datos de un string
                    StringBuilder builder = new StringBuilder(fraseOculta);
                    //Sustituimos el caracter
                    builder.setCharAt(i, caracter.charAt(0));
                    //Igualamos
                    fraseOculta = builder.toString();
                }
            }

            if(verificarSiYaTerminoElJuego()) {
                mostrarElFinDeJuego();
            }
        }
        else
        {
            notificarYCambiarAhorcado();
        }
        textView.setText(fraseOculta);
        //mostrarElFinDeJuego();
    }

    private void mostrarElFinDeJuego() {

        //Si se va a validar pero ya se te acabaron los intentos esto pasa
            if (intentos == maxIntentos) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("Has perdido")
                        .setMessage("Lo sentimos pero has perdido prueba tu suerte la proxima vez, la palabra" +
                                "que estabas tratando de desifrar fue: \n\n" + fraseParaAhorcado)
                        .setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                //Matamos esta activitydad y mostramos la anteiror
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                builder.create();
                builder.show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("Genial!!")
                        .setMessage("Eres un genio, adivinaste la palabra, te mereces una cerveza")
                        .setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                //Matamos esta activitydad y mostramos la anteiror
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                builder.create();
                builder.show();
            }

    }

    private void notificarYCambiarAhorcado() {
        Toast.makeText(getApplicationContext(), "Upps! te has equivocado", Toast.LENGTH_SHORT).show();
        intentos++;


//Verificamos si ya se termino el juego si lo es mostraremos el dialogo, si no actualizamos la imagen del ahorcado
        if(verificarSiYaTerminoElJuego()) {
            mostrarElFinDeJuego();
        }
        else {
            //Cambiamos la imagen del ahorcado porque se equivoco
            imageView.setImageResource(estadosAhorcado[intentos]);

        }


    }

    private boolean verificarSiYaTerminoElJuego(){
        if(intentos == maxIntentos) {
            return true;
            //Se acabaron los itentos
        }
        if(!fraseOculta.contains("_"))
            return true;
        //Aun hay letra por descubriur
        return false;
    }
}
