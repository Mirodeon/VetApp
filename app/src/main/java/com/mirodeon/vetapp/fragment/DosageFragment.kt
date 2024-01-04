package com.mirodeon.vetapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mirodeon.vetapp.adapter.DosageAdapter
import com.mirodeon.vetapp.databinding.FragmentDosageBinding
import com.mirodeon.vetapp.room.entity.DosageWithMethod
import com.mirodeon.vetapp.viewmodel.DosageViewModel
import com.mirodeon.vetapp.viewmodel.DosageViewModelFactory
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.launch

class DosageFragment : Fragment() {
    private var binding: FragmentDosageBinding? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: DosageAdapter? = null
    private val viewModel: DosageViewModel by activityViewModels {
        DosageViewModelFactory()
    }
    private var jobSearch: Job? = null
    private var jobDosage: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDosageBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.emptySearch?.root?.visibility = View.GONE
        setupRecyclerView()
        setInputSearch()
        setAfterTextChanged()
    }

    override fun onResume() {
        super.onResume()
        jobSearch?.cancel()
        launchDosage()
    }

    override fun onPause() {
        super.onPause()
        jobSearch?.cancel()
        jobDosage?.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun launchDosage() {
        jobDosage?.cancel()
        binding?.loader?.root?.visibility = View.VISIBLE
        val input = binding?.searchEditText?.text?.toString()
        jobDosage = lifecycle.coroutineScope.launch {
            delay(700)
            if (input.isNullOrEmpty()) {
                viewModel.allDosage().cancellable().collect { updateContent(it) }
            } else {
                viewModel.searchDosage(input).cancellable().collect { updateContent(it) }
            }
        }
    }

    private fun updateContent(dosages: List<DosageWithMethod>) {
        if (dosages.isEmpty()) {
            binding?.emptySearch?.root?.visibility = View.VISIBLE
            binding?.containerRecycler?.visibility = View.GONE
        } else {
            binding?.emptySearch?.root?.visibility = View.GONE
            binding?.containerRecycler?.visibility = View.VISIBLE
        }
        adapter?.submitList(dosages)
        binding?.loader?.root?.visibility = View.GONE
    }

    private fun setInputSearch() {
        binding?.searchInputLayout?.setEndIconOnClickListener {
            jobSearch?.cancel()
            launchDosage()
        }
        binding?.searchEditText?.onSubmit {
            jobSearch?.cancel()
            launchDosage()
        }
    }

    private fun setAfterTextChanged() {
        binding?.searchEditText?.doAfterTextChanged {
            jobSearch?.cancel()
            jobSearch = lifecycle.coroutineScope.launch {
                delay(2000)
                launchDosage()
            }
        }
    }

    private fun EditText.onSubmit(handler: () -> Unit) {
        setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                handler()
            }
            true
        }
    }

    private fun setupRecyclerView() {
        recyclerView = binding?.containerRecycler
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        adapter = DosageAdapter()
        recyclerView?.adapter = adapter
    }
}