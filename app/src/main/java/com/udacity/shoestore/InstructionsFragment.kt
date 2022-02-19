package com.udacity.shoestore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.databinding.FragmentInstructionsBinding

class InstructionsFragment: Fragment() {

    private lateinit var binding: FragmentInstructionsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_instructions, container, false)

        activity?.setTitle(R.string.instructions_title)

        binding.advanceToInventoryBtn.setOnClickListener(this::navigateToInventory)

        return binding.root
    }

    private fun navigateToInventory(view: View) {
        findNavController().navigate(R.id.action_instructionsFragment_to_inventoryFragment)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment InstructionsFragment.
         */
        @JvmStatic
        fun newInstance() = InstructionsFragment()
    }
}