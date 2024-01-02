package com.mirodeon.vetapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mirodeon.vetapp.R
import com.mirodeon.vetapp.databinding.FragmentDosageBinding
import com.mirodeon.vetapp.viewmodel.DosageViewModel
import com.mirodeon.vetapp.viewmodel.DosageViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DosageFragment : Fragment() {
    private var binding: FragmentDosageBinding? = null
    private var recyclerView: RecyclerView? = null
    /*private var adapter: DosageAdapter? = null*/
    private val viewModel: DosageViewModel by activityViewModels {
        DosageViewModelFactory()
    }
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
        /*setupRecyclerView()*/
    }

    override fun onResume() {
        super.onResume()
        /*viewModel.onRefresh()*/
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    /*private fun toggleFav(item: DosageWithMethod) {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.setDosageFav(id = item.dosage.dosageId, isFav = !item.dosage.isFav)
        }
    }*/

    /*private fun setupRecyclerView() {
        recyclerView = binding?.containerRecycler
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        val itemAdapter = DosageAdapter { toggleFav(it) }
        recyclerView?.adapter = itemAdapter
        jobWaifu = lifecycle.coroutineScope.launch {
            viewModel.fullDosage().collect { dosages ->
                itemAdapter.submitList(dosages)
            }
        }
    }*/
}