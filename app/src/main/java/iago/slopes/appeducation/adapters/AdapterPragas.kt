package iago.slopes.appeducation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import iago.slopes.appeducation.R
import iago.slopes.appeducation.models.Praga
class AdapterPragas(var context: Context, var listaPragas: List<Praga>): RecyclerView.Adapter<AdapterPragas.viewholder>(),
    Filterable {

    private var listaPragasFiltradas: List<Praga> = listaPragas
    class viewholder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        var inflater:LayoutInflater = LayoutInflater.from(context)
        var view = inflater.inflate(R.layout.planta_item, parent, false)

        return viewholder(view)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        val praga:Praga = listaPragas.elementAt(position)
        val txtNome:TextView = holder.itemView.findViewById(R.id.txtNomePlanta)
        val imgPlanta: ImageView = holder.itemView.findViewById(R.id.imgPlanta)
        val txtNomeCientifico:TextView = holder.itemView.findViewById(R.id.txtNomeCientifico)
        txtNome.text = praga.nome
        txtNomeCientifico.text = praga.nome_cientifico
    }

    override fun getItemCount(): Int {
        return listaPragasFiltradas.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filtro = constraint.toString().toLowerCase().trim()

                val resultados = if (filtro.isEmpty()) {
                    listaPragas // Se o filtro estiver vazio, retorne a lista original
                } else {
                    listaPragas.filter { pragas ->
                        pragas.nome.toLowerCase().contains(filtro)
                    }
                }

                val filterResults = FilterResults()
                filterResults.values = resultados
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                listaPragasFiltradas = results?.values as? List<Praga> ?: emptyList()
                notifyDataSetChanged()
            }
        }
    }
}