package com.udacity.shoestore

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.databinding.FragmentShoeDetailBinding
import com.udacity.shoestore.models.Shoe
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 * Use the [ShoeDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShoeDetailFragment : Fragment() {
    private val mainViewModel: MainViewModel by activityViewModels()
    lateinit var binding: FragmentShoeDetailBinding

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shoe_detail, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        activity?.title = resources.getString(R.string.add_shoe_title)

        setEditTextOptions()
        binding.saveButton.setOnClickListener(::onSaveClicked)
        binding.cancelButton.setOnClickListener(::onCancelClicked)

        return binding.root
    }

    private fun setEditTextOptions() {
        binding.shoeDescriptionInput.apply {
            // In order to get a multiline EditText that has keyboard IME_ACTION_DONE, this needs to be done programmatically
            // using RawInputType. No way to configure this in XML.
            imeOptions = EditorInfo.IME_ACTION_DONE
            setRawInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES or InputType.TYPE_TEXT_FLAG_MULTI_LINE)
        }
    }

    private fun onSaveClicked(v: View) {
        if (entryIsValid()) {
            val shoe = Shoe(
                name = binding.shoeNameInput.text.toString(),
                size = binding.shoeSizeInput.text.toString().toDouble(),
                company = binding.shoeCompanyInput.text.toString(),
                description = binding.shoeDescriptionInput.text.toString()
            )
            Timber.d("Save button clicked. Shoe is: $shoe")
            mainViewModel.saveNewShoeEntry(shoe)
            findNavController().navigate(R.id.action_shoeDetailFragment_to_inventoryFragment)
        }
    }

    private fun onCancelClicked(v: View) {
        findNavController().navigate(R.id.action_shoeDetailFragment_to_inventoryFragment)
    }

    private fun entryIsValid(): Boolean {
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