package unicauca.movil.gegan.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;

import unicauca.movil.gegan.R;
import unicauca.movil.gegan.databinding.TemplateAnimalBinding;
import unicauca.movil.gegan.util.L;

/**
 * Created by jlbel on 15/12/2016.
 */

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> {



    public interface OnAnimalListener{
        void onAnimal(Long id);
        void onDelete(Long id) throws ParseException;
        void onEdit(Long id);
    }

    @Override
    public AnimalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.template_animal, parent, false);
        AnimalViewHolder holder = new AnimalViewHolder(v);
        return holder;
    }
    @Override
    public void onBindViewHolder(AnimalViewHolder holder, int position) {
        holder.binding.setAnimal(L.dataa.get(position));
        holder.binding.setHandler(this);

    }

    @Override
    public int getItemCount() {
        return L.dataa.size();
    }



    LayoutInflater inflater;
    OnAnimalListener onAnimalListener;

    public AnimalAdapter(LayoutInflater inflater, OnAnimalListener onAnimalListener){
        this.onAnimalListener = onAnimalListener;
        this.inflater = inflater;
    }

    public static class AnimalViewHolder extends RecyclerView.ViewHolder {
        TemplateAnimalBinding binding;

        public AnimalViewHolder(View itemView){
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }

     public void onClickAnimal(Long id){
        onAnimalListener.onAnimal(id);
    }

    public void onClickDelete(Long id) {
        try {
            onAnimalListener.onDelete(id);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void onClickEdit(Long id){
        onAnimalListener.onEdit(id);
    }


}
