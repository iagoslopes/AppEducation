package iago.slopes.appeducation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import iago.slopes.appeducation.R
import iago.slopes.appeducation.models.Planta
class AdapterPlantas(var context: Context, var listaPlantas: List<Planta>): RecyclerView.Adapter<AdapterPlantas.viewholder>(),
    Filterable {

    private var listaPlantasFiltradas: List<Planta> = listaPlantas
    class viewholder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val txtNome: TextView = itemView.findViewById(R.id.txtNomePlanta)
        val txtNomeCientifico: TextView = itemView.findViewById(R.id.txtNomeCientifico)
        val imgPlanta: ImageView = itemView.findViewById(R.id.imgPlanta)
    }
    interface OnItemClickListener {
        fun onItemClick(planta: Planta)
    }

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        var inflater:LayoutInflater = LayoutInflater.from(context)
        var view = inflater.inflate(R.layout.planta_item, parent, false)

        return viewholder(view)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        val planta: Planta = listaPlantasFiltradas[position]
        holder.txtNome.text = planta.nome
        holder.txtNomeCientifico.text = planta.nome_cientifico

        val url = planta.foto // A URL da imagem da planta
        Glide.with(context)
            .load(url)
            .into(holder.imgPlanta)

        holder.itemView.setOnClickListener {
            itemClickListener?.onItemClick(planta)
        }
    }

    override fun getItemCount(): Int {
        return listaPlantasFiltradas.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filtro = constraint.toString().toLowerCase().trim()

                val resultados = if (filtro.isEmpty()) {
                    listaPlantas // Se o filtro estiver vazio, retorne a lista original
                } else {
                    listaPlantas.filter { planta ->
                        planta.nome.toLowerCase().contains(filtro)
                    }
                }

                val filterResults = FilterResults()
                filterResults.values = resultados
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                listaPlantasFiltradas = results?.values as? List<Planta> ?: emptyList()
                notifyDataSetChanged()
            }
        }
    }
}