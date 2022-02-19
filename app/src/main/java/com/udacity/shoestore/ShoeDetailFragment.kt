package com.udacity.shoestore

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
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
    private val binding by lazy { FragmentShoeDetailBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        configureDataBinding()
        setFragmentTitle()
        configureMultiLineEditText()
        observeViewModel()
        return binding.root
    }

    private fun configureDataBinding() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.mainViewModel = mainViewModel
    }

    private fun setFragmentTitle() {
        activity?.title = resources.getString(R.string.add_shoe_title)
    }

    private fun configureMultiLineEditText() {
        binding.shoeDescriptionInput.apply {
            // In order to get a multiline EditText that respects IME_ACTION_DONE, we need to programmatically
            // set the RawInputType. No way to configure RawInputType in XML.
            imeOptions = EditorInfo.IME_ACTION_DONE
            setRawInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES or InputType.TYPE_TEXT_FLAG_MULTI_LINE)
        }
    }

    private fun observeViewModel() {
        mainViewModel.uiEvent.observe(viewLifecycleOwner, this::onUiEvent)
    }

    private fun onUiEvent(uiEvent: UiEvent) {
        if (uiEvent is UiEvent.Await) return

        when (uiEvent) {
            UiEvent.Save -> {
                onUiEventSave()
            }
            UiEvent.Cancel -> {
                onUiEventCancel()
            }
            else -> throw UnhandledUiEventException()
        }

        mainViewModel.awaitNextUiEvent()
        Timber.d("Finished processing $uiEvent, awaiting next ui event")
    }

    private fun onUiEventSave() {
        val shoe = readShoeFromInput()
        if (shoe != null) {
            mainViewModel.saveValidShoe(shoe)
            findNavController().navigate(R.id.action_shoeDetailFragment_to_inventoryFragment)
        }
    }

    private fun onUiEventCancel() {
        findNavController().navigate(R.id.action_shoeDetailFragment_to_inventoryFragment)
    }

    private fun readShoeFromInput(): Shoe? {
        val shoe = Shoe()
        with(binding) {
            // company
            if (shoeCompanyInput.text.isNullOrBlank()) {
                shoeCompanyInput.error = getString(R.string.error_empty_company)
                return null
            }
            shoe.company = shoeCompanyInput.text.toString()

            // name
            if (shoeNameInput.text.isNullOrBlank()) {
                shoeNameInput.error = getString(R.string.error_empty_name)
                return null
            }
            shoe.name = shoeNameInput.text.toString()

            // size
            if (shoeSizeInput.text.isNullOrBlank()) {
                shoeSizeInput.error = getString(R.string.error_empty_size)
                return null
            }
            if (shoeSizeInput.text.toString().toDoubleOrNull() == null) {
                shoeSizeInput.error = getString(R.string.error_size_not_number)
                return null
            }
            shoe.size = shoeSizeInput.text.toString().toDouble()

            // description
            shoe.description = shoeDescriptionInput.text.toString()
        }

        return shoe
    }

    companion object {
        fun newInstance() = ShoeDetailFragment()
    }
}