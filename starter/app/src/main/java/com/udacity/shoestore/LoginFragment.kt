package com.udacity.shoestore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.databinding.FragmentLoginBinding

/**
 * Fragment to handle authentication
 */
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        // Set title
        activity?.title = resources.getString(R.string.login)

        // bind click listeners
        binding.loginButton.setOnClickListener(this::navigateToWelcomeScreen)
        binding.createButton.setOnClickListener(this::navigateToWelcomeScreen)

        return binding.root
    }

    private fun navigateToWelcomeScreen(view: View) {
        findNavController().navigate(R.id.action_loginFragment_to_welcomeFragment)
    }

    companion object {
        /**
         * Create a new instance of this fragment.
         *
         * @return A new instance of LoginFragment.
         */
        @JvmStatic
        fun newInstance() = LoginFragment()
    }
}