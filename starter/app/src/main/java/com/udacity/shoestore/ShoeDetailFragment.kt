package com.udacity.shoestore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.databinding.FragmentShoeDetailBinding

/**
 * A simple [Fragment] subclass.
 * Use the [ShoeDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShoeDetailFragment : Fragment() {

    lateinit var binding: FragmentShoeDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shoe_detail, container, false)

        activity?.title = resources.getString(R.string.add_shoe_title)

        binding.saveButton.setOnClickListener { saveNewEntry() }
        binding.cancelButton.setOnClickListener { cancelNewEntry() }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ShoeDetailFragment.
         */
        @JvmStatic
        fun newInstance() = ShoeDetailFragment()
    }

    fun saveNewEntry() {
        if (entryIsValid()) {
            findNavController().navigate(R.id.action_shoeDetailFragment_to_inventoryFragment)
        }
    }

    fun cancelNewEntry() {
        findNavController().navigate(R.id.action_shoeDetailFragment_to_inventoryFragment)
    }

    fun entryIsValid(): Boolean {
        if (binding.shoeNameInput.text.isNullOrBlank()) {
            binding.shoeNameInput.error = getString(R.string.error_empty_name)
            return false
        }
        if (binding.shoeCompanyInput.text.isNullOrBlank()) {
            binding.shoeCompanyInput.error = getString(R.string.error_empty_company)
            return false
        }
        if (binding.shoeSizeInput.text.isNullOrBlank()) {
            binding.shoeSizeInput.error = getString(R.string.error_empty_size)
            return false
        }
        return true
    }
}