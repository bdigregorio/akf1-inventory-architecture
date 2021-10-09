package com.udacity.shoestore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.databinding.FragmentInventoryBinding
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 * Use the [InventoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InventoryFragment : Fragment() {

    lateinit var binding: FragmentInventoryBinding
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_inventory, container, false)

        // Set title
        activity?.title = resources.getString(R.string.shoe_list_title)

        binding.fab.setOnClickListener(this::navigateToBlankShoeDetail)
        subscribeToViewModel()

        return binding.root
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
                Timber.d("Update to shoe list observed - updating UI; container initially has ${binding.shoeListContainer.childCount} views")
                binding.shoeListContainer.removeAllViews()
                shoes.forEach { shoe ->
                    val newShoeView = LayoutInflater.from(context).inflate(R.layout.item_shoe, null)
                    newShoeView.findViewById<TextView>(R.id.shoe_company).text = shoe.company
                    newShoeView.findViewById<TextView>(R.id.shoe_name).text = shoe.name
                    newShoeView.findViewById<TextView>(R.id.shoe_size).text = getString(R.string.size_format, shoe.size)
                    newShoeView.findViewById<TextView>(R.id.shoe_description).text = shoe.description
                    binding.shoeListContainer.addView(newShoeView)
                    Timber.d("Inflated view added; container now has ${binding.shoeListContainer.childCount} views")
                }
            }
        }
    }
}