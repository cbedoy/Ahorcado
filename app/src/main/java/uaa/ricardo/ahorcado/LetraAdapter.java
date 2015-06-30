package uaa.ricardo.ahorcado;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class LetraAdapter extends BaseAdapter {

    private List<LetraEstado> listaLetrasEstados;
    private LayoutInflater layoutInflater;

    public LetraAdapter(List<LetraEstado> listaLetrasEstados, LayoutInflater layoutInflater){
        this.listaLetrasEstados = listaLetrasEstados;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public int getCount() {
        return listaLetrasEstados.size();
    }

    @Override
    public Object getItem(int position) {
        return listaLetrasEstados.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        TextView textView = null;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.celda_letra, null);
            textView = (TextView) convertView.findViewById(R.id.letraView);
        }
        LetraEstado letraEstado = listaLetrasEstados.get(position);
        if (textView != null) {
            textView.setText(letraEstado.caracter);
            textView.setTextColor(!letraEstado.estado ? Color.parseColor("#333333") : Color.parseColor("#F50057"));
        }
        return convertView;
    }
}
