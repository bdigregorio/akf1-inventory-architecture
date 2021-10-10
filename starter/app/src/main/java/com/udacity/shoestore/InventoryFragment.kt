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

    lateinit var binding: FragmentInventoryBinding
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentInventoryBinding.inflate(inflater, container, false)

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
                    val newShoeBinding = ItemShoeBinding.inflate(
                        LayoutInflater.from(context),
                        binding.shoeListContainer,
                        false
                    )
                    newShoeBinding.shoeCompany.text = shoe.company
                    newShoeBinding.shoeName.text = shoe.name
                    newShoeBinding.shoeSize.text = getString(R.string.size_format, shoe.size)
                    newShoeBinding.shoeDescription.text = shoe.description
                    Timber.d("Inflated view added; container now has ${binding.shoeListContainer.childCount} views")
                    binding.shoeListContainer.addView(newShoeBinding.root)
                }
            }
        }
    }
}