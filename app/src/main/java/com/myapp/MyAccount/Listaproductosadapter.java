package com.myapp.MyAccount;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//Clase para enlazar parte grafica y logica del recycler view
public class Listaproductosadapter extends
        RecyclerView.Adapter<Listaproductosadapter.productosView>
        implements View.OnClickListener{

    ArrayList<Producto> listaproducto;
    private View.OnClickListener listener;

    public Listaproductosadapter(ArrayList<Producto> listaproducto) {
        this.listaproducto = listaproducto;
    }


    @Override
    public productosView onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_productos, null, false);
        view.setOnClickListener(this);
        return new productosView(view);
    }

    @Override
    public void onBindViewHolder( productosView holder, int position) {
        holder.cantidad.setText(listaproducto.get(position).getCantidad().toString());
        holder.nombre.setText(listaproducto.get(position).getNombre());
        holder.preciounidad.setText(listaproducto.get(position).getPreciound().toString());
        holder.preciototal.setText(listaproducto.get(position).getPreciotot().toString());

    }

    @Override
    public int getItemCount() {
        return listaproducto.size();
    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }
    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }


    public class productosView extends RecyclerView.ViewHolder {
        private TextView cantidad, nombre, preciounidad, preciototal;


        public productosView( View itemView) {
            super(itemView);
            cantidad =(TextView)  itemView.findViewById(R.id.textcantidad);
            nombre = (TextView) itemView.findViewById(R.id.textnombre);
            preciounidad =(TextView)  itemView.findViewById(R.id.textpreciounidad);
            preciototal =(TextView)  itemView.findViewById(R.id.textpreciototal);

        }

    }
}
