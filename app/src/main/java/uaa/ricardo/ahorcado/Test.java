package uaa.ricardo.ahorcado;

import android.widget.TextView;

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
public class Test
{

    public static class Ahorcado{

        private List<String> mLetras;

        private List<String> mFrases;

        private String fraseParaAhorcado;
        private String fraseOculta;
        private int intentos;


        public Ahorcado()
        {
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

            seleccionarYConvertirFrase();

            intentos = 0;
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

        public void validarCaracter(String caracter){
            if (fraseParaAhorcado.contains(caracter)){
                for (int i = 0; i<fraseParaAhorcado.length(); i++){
                    if(String.valueOf(fraseParaAhorcado.charAt(i)).equals( caracter)){
                        StringBuilder builder = new StringBuilder(fraseOculta);
                        builder.replace(i, i, caracter);
                        fraseOculta = builder.toString();
                    }
                }
                System.out.println(fraseOculta);
            }else {
                intentos++;
            }
        }

        public void imprimirIntentos(){
            System.out.println(intentos);
        }
    }


    public static void main(String[]arg){
        Ahorcado test = new Ahorcado();

        test.validarCaracter("A");
        test.imprimirIntentos();

        test.validarCaracter("C");
        test.imprimirIntentos();

        test.validarCaracter("E");
        test.imprimirIntentos();

    }
}
