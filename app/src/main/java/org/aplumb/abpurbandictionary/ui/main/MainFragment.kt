package org.aplumb.abpurbandictionary.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ProgressBar
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.aplumb.abpurbandictionary.R
import org.aplumb.abpurbandictionary.UrbanDictionaryApp
import org.aplumb.abpurbandictionary.api.model.Definition
import org.aplumb.abpurbandictionary.di.ViewModelFactory
import javax.inject.Inject


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: MainViewModel

    var searchEdit: EditText? = null
    var checkbox: CheckBox? = null
    var progress: ProgressBar? = null
    var recycler: RecyclerView? = null

    lateinit var definitionsAdapter: DefinitionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)

        definitionsAdapter = DefinitionsAdapter()
        recycler = view.findViewById<RecyclerView>(R.id.recycler).apply {
            layoutManager = LinearLayoutManager(inflater.context)
            adapter = definitionsAdapter
        }

        searchEdit = view.findViewById<EditText>(R.id.searchTerm).apply {
            addTextChangedListener { updateSearch() }
        }

        progress = view.findViewById(R.id.progress)

        checkbox = view.findViewById<CheckBox>(R.id.sortThumbsUp).apply {
            setOnCheckedChangeListener { _, isChecked ->
                setText(if (isChecked) R.string.thumbs_up else R.string.thumbs_down)
                updateSearch()
            }
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: activity scoped injection
        UrbanDictionaryApp.component().inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)

        viewModel.definitions.observe(
            this, Observer<List<Definition>> { definitions ->
                definitionsAdapter.definitions = definitions
            })
        viewModel.isLoading.observe(this, Observer<Boolean> { isLoading ->
            progress?.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
        })


    }

    private fun updateSearch() {
        // TODO: debounce this
        val searchTerm = searchEdit?.text.toString()
        val searchThumbsUp = checkbox?.isChecked() ?: true
        viewModel.lookupDefinition(searchTerm, searchThumbsUp)
    }
}
