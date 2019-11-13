package org.aplumb.abpurbandictionary.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.aplumb.abpurbandictionary.R
import org.aplumb.abpurbandictionary.api.model.Definition

class DefinitionsAdapter : RecyclerView.Adapter<DefinitionsAdapter.DefinitionViewHolder>() {
    var definitions: List<Definition> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefinitionViewHolder {
        // create a new view
        val container = LayoutInflater.from(parent.context)
            .inflate(R.layout.definition_card, parent, false)
        return DefinitionViewHolder(container)
    }

    override fun getItemCount(): Int {
        return definitions.size
    }

    override fun onBindViewHolder(holder: DefinitionViewHolder, position: Int) {
        holder.definition = definitions[position]
    }

    class DefinitionViewHolder(val container: View) :
        RecyclerView.ViewHolder(container) {

        var wordText: TextView = container.findViewById(R.id.word)
        var definitionText: TextView = container.findViewById(R.id.definition)
        var countText: TextView = container.findViewById(R.id.counts)

        var definition: Definition? = null
            set(value) {
                field = value
                wordText.text = value?.word
                definitionText.text = value?.definition
                countText.setText(
                    container.context.getString(
                        R.string.count_text,
                        value?.thumbsUp ?: 0,
                        value?.thumbsDown ?: 0
                    )
                )
            }
    }
}