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
import iago.slopes.appeducation.models.Terreno
class AdapterTerrenos(var context: Context, var listaTerrenos: List<Terreno>): RecyclerView.Adapter<AdapterTerrenos.viewholder>(),
    Filterable {

    private var listaTerrenosFiltradas: List<Terreno> = listaTerrenos
    class viewholder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        var inflater:LayoutInflater = LayoutInflater.from(context)
        var view = inflater.inflate(R.layout.terreno_item, parent, false)

        return viewholder(view)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        val terreno:Terreno = listaTerrenos.elementAt(position)
        val txtNome:TextView = holder.itemView.findViewById(R.id.txtNomeTerreno)
        val imgTerreno: ImageView = holder.itemView.findViewById(R.id.imgTerreno)
        val txtNomeCientifico:TextView = holder.itemView.findViewById(R.id.txtNomeCientifico)
        txtNome.text = terreno.nome
        txtNomeCientifico.text = terreno.nome_cientifico
    }

    override fun getItemCount(): Int {
        return listaTerrenosFiltradas.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filtro = constraint.toString().toLowerCase().trim()

                val resultados = if (filtro.isEmpty()) {
                    listaTerrenos // Se o filtro estiver vazio, retorne a lista original
                } else {
                    listaTerrenos.filter { terreno ->
                        terreno.nome.toLowerCase().contains(filtro)
                    }
                }

                val filterResults = FilterResults()
                filterResults.values = resultados
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                listaTerrenosFiltradas = results?.values as? List<Terreno> ?: emptyList()
                notifyDataSetChanged()
            }
        }
    }
}