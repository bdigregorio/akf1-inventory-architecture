package com.udacity.shoestore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.databinding.FragmentInventoryBinding
import com.udacity.shoestore.databinding.ItemShoeBinding
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 * Use the [InventoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InventoryFragment : Fragment() {

    private lateinit var inventoryBinding: FragmentInventoryBinding
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        inventoryBinding = FragmentInventoryBinding.inflate(inflater, container, false)

        // Set title
        activity?.title = resources.getString(R.string.shoe_list_title)

        inventoryBinding.fab.setOnClickListener(this::navigateToBlankShoeDetail)
        subscribeToViewModel()

        return inventoryBinding.root
    }

    private fun navigateToBlankShoeDetail(view: View) {
        findNavController().navigate(R.id.action_inventoryFragment_to_shoeDetailFragment)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ShoeListFragment.
         */
        @JvmStatic
        fun newInstance() = InventoryFragment()
    }

    private fun subscribeToViewModel() {
        observeShoeList()
    }

    private fun observeShoeList() {
        mainViewModel.shoes.observe(viewLifecycleOwner) { shoes ->
            if (shoes.isNotEmpty()) {
                Timber.d("Update to shoe list observed - updating UI; container initially has ${inventoryBinding.shoeListContainer.childCount} views")
                inventoryBinding.shoeListContainer.removeAllViews()
                shoes.forEach { shoe ->
                    val shoeBinding = ItemShoeBinding.inflate(
                        LayoutInflater.from(context),
                        inventoryBinding.shoeListContainer,
                        false
                    )
                    shoeBinding.company.text = shoe.company
                    shoeBinding.name.text = shoe.name
                    shoeBinding.size.text = getString(R.string.size_format, shoe.size)
                    shoeBinding.description.text = shoe.description
                    Timber.d("Inflated view added; container now has ${inventoryBinding.shoeListContainer.childCount} views")
                    inventoryBinding.shoeListContainer.addView(shoeBinding.root)
                }
            }
        }
    }
}