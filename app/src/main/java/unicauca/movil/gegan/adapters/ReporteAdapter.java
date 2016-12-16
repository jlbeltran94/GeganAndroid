package unicauca.movil.gegan.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;

import unicauca.movil.gegan.R;
import unicauca.movil.gegan.databinding.TemplateReportesBinding;
import unicauca.movil.gegan.util.L;

/**
 * Created by jlbel on 16/12/2016.
 */

public class ReporteAdapter extends RecyclerView.Adapter<ReporteAdapter.ReporteViewHolder> {

    public interface OnReporteListener{
        void onReporte(Long id);
        void onDelete(Long id) throws ParseException;
        void onEdit(Long id);
    }


    LayoutInflater inflater;
    OnReporteListener onReporteListener;

    @Override
    public ReporteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.template_reportes, parent, false);
        ReporteViewHolder holder = new ReporteViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ReporteViewHolder holder, int position) {
        holder.binding.setReporte(L.datar.get(position));
        holder.binding.setHandler(this);

    }

    @Override
    public int getItemCount() {
        return L.datar.size();
    }

    public ReporteAdapter(LayoutInflater inflater, OnReporteListener onReporteListener){
        this.onReporteListener = onReporteListener;
        this.inflater = inflater;
    }

    public static class ReporteViewHolder extends RecyclerView.ViewHolder{
        TemplateReportesBinding binding;

        public ReporteViewHolder(View itemView){
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

    }

    public void onClickReporte(Long id){
        onReporteListener.onReporte(id);
    }

    public void onClickDelete(Long id){
        onReporteListener.onDelete(id);
    }

    public void onClickEdit(Long id){
        onReporteListener.onEdit(id);
    }


}
