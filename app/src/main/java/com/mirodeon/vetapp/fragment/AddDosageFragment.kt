package com.mirodeon.vetapp.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mirodeon.vetapp.R
import com.mirodeon.vetapp.activity.main.MainActivity
import com.mirodeon.vetapp.adapter.MethodAdapter
import com.mirodeon.vetapp.databinding.FragmentAddDosageBinding
import com.mirodeon.vetapp.room.entity.DosageWithMethod
import com.mirodeon.vetapp.viewmodel.DosageViewModel
import com.mirodeon.vetapp.viewmodel.DosageViewModelFactory
import kotlinx.coroutines.launch
import com.mirodeon.vetapp.room.entity.Method
import com.mirodeon.vetapp.utils.validator.DosageValidator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddDosageFragment : Fragment() {
    private var binding: FragmentAddDosageBinding? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: MethodAdapter? = null
    private val viewModel: DosageViewModel by activityViewModels {
        DosageViewModelFactory()
    }
    private var methods: List<Method> = listOf()
    private var selectedMethods: List<Method> = listOf()
    private var addBtn: ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddDosageBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupForm()
        setupRecyclerView()
        setChoiceMethod()
    }

    override fun onResume() {
        super.onResume()
        updateMethodsData()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun setupRecyclerView() {
        recyclerView = binding?.containerMethod
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        adapter = MethodAdapter()
        recyclerView?.adapter = adapter
    }

    private fun updateMethodsData() {
        CoroutineScope(Dispatchers.IO).launch {
            methods = viewModel.allMethod()
        }
    }

    private fun setChoiceMethod() {
        binding?.methodBtn?.setOnClickListener {
            val array = methods.map { it.name }.toTypedArray()
            val selectedArray = methods.map { selectedMethods.contains(it) }.toBooleanArray()
            val builder = AlertDialog.Builder(activity)
            val tmpGenres: ArrayList<Method> = arrayListOf()
            tmpGenres.addAll(selectedMethods)
            builder.setTitle("Choose the corresponding routes of administration.")
            builder.setMultiChoiceItems(
                array,
                selectedArray
            ) { _, which, isChecked ->
                if (isChecked) {
                    tmpGenres.add(methods[which])
                } else {
                    tmpGenres.remove(methods[which])
                }
            }
            builder.setPositiveButton("Ok") { _, _ ->
                selectedMethods = tmpGenres
                updateRecyclerData()
            }
            val dialog = builder.create()
            dialog.show()
        }
    }

    private fun updateRecyclerData() {
        adapter?.submitList(selectedMethods)
    }

    private fun setupForm() {
        addBtn = (activity as MainActivity).binding?.toolbarMenu?.btnSave
        addBtn?.isEnabled = false
        DosageValidator(
            mapOf(
                DosageValidator.DosageProperty.NAME to Pair(
                    binding?.nameInputLayout,
                    binding?.nameInput
                ),
                DosageValidator.DosageProperty.CONCENTRATION to Pair(
                    binding?.concentrationInputLayout,
                    binding?.concentrationInput
                ),
                DosageValidator.DosageProperty.INGREDIENT to Pair(
                    binding?.ingredientInputLayout,
                    binding?.ingredientInput
                ),
                DosageValidator.DosageProperty.NUMBER to Pair(
                    binding?.numberInputLayout,
                    binding?.numberInput
                ),
                DosageValidator.DosageProperty.INTERVAL to Pair(
                    binding?.intervalInputLayout,
                    binding?.intervalInput
                ),
                DosageValidator.DosageProperty.WITHDRAWAL to Pair(
                    binding?.withdrawalInputLayout,
                    binding?.withdrawalInput
                )
            ),
            addBtn,
            ResourcesCompat.getDrawable(resources, R.drawable.baseline_check_24, activity?.theme),
            ResourcesCompat.getColor(resources, R.color.green, activity?.theme),
            ResourcesCompat.getColor(resources, R.color.red, activity?.theme),
        ) {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.insertDosage(DosageWithMethod(it, selectedMethods))
                withContext(Dispatchers.Main) {
                    (activity as MainActivity).onSupportNavigateUp()
                }
            }
        }
    }
}