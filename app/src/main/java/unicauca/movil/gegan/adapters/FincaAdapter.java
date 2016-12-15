package unicauca.movil.gegan.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import unicauca.movil.gegan.R;
import unicauca.movil.gegan.databinding.TemplateFincaBinding;
import unicauca.movil.gegan.util.L;


/**
 * Created by jlbel on 12/12/2016.
 */

public class FincaAdapter extends RecyclerView.Adapter<FincaAdapter.FincaViewHolder> {

    public interface OnFincaListener{
        void onFinca(View v);
    }

    LayoutInflater inflater;
    OnFincaListener onFincaListener;

    public FincaAdapter(LayoutInflater inflater, OnFincaListener onFincaListener){
        this.onFincaListener = onFincaListener;
        this.inflater = inflater;
    }

    @Override
    public FincaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.template_finca, parent, false);
        FincaViewHolder holder = new FincaViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(FincaViewHolder holder, int position) {
        holder.binding.setFin(L.data.get(position));
        holder.binding.setHandler(this);

    }

    @Override
    public int getItemCount() {
        return L.data.size();
    }

    public static class FincaViewHolder extends RecyclerView.ViewHolder {
        TemplateFincaBinding binding;

        public FincaViewHolder(View itemView){
            super(itemView);

            binding = DataBindingUtil.bind(itemView);
        }
    }
}
