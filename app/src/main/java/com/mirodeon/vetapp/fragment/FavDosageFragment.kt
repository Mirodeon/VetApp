package com.mirodeon.vetapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mirodeon.vetapp.adapter.DosageAdapter
import com.mirodeon.vetapp.databinding.FragmentFavDosageBinding
import com.mirodeon.vetapp.room.entity.DosageWithMethod
import com.mirodeon.vetapp.viewmodel.DosageViewModel
import com.mirodeon.vetapp.viewmodel.DosageViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.launch

class FavDosageFragment : Fragment() {
    private var binding: FragmentFavDosageBinding? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: DosageAdapter? = null
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
        binding = FragmentFavDosageBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.emptyContent?.root?.visibility = View.GONE
        setupRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        launchDosage()
    }

    override fun onPause() {
        super.onPause()
        jobDosage?.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun launchDosage() {
        jobDosage?.cancel()
        binding?.loader?.root?.visibility = View.VISIBLE
        jobDosage = lifecycle.coroutineScope.launch {
            //delay(700)
            viewModel.allDosageByFav(true).cancellable().collect { updateContent(it) }
        }
    }

    private fun updateContent(dosages: List<DosageWithMethod>) {
        if (dosages.isEmpty()) {
            binding?.emptyContent?.root?.visibility = View.VISIBLE
            binding?.containerRecycler?.visibility = View.GONE
        } else {
            binding?.emptyContent?.root?.visibility = View.GONE
            binding?.containerRecycler?.visibility = View.VISIBLE
        }
        adapter?.submitList(dosages)
        binding?.loader?.root?.visibility = View.GONE
    }

    private fun setupRecyclerView() {
        recyclerView = binding?.containerRecycler
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        adapter = DosageAdapter(
            { toggleFav(it) },
            { goToDetails(it) }
        )
        recyclerView?.adapter = adapter
    }

    private fun toggleFav(item: DosageWithMethod) {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.setDosageFav(id = item.dosage.dosageId, isFav = !item.dosage.isFav)
        }
    }

    private fun goToDetails(item: DosageWithMethod) {
        val directions =
            FavDosageFragmentDirections.actionFavDosageFragmentToDetailsDosageFragment(item.dosage.dosageId)
        findNavController().navigate(directions)
    }
}