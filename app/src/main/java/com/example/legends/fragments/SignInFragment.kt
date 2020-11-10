package com.example.legends.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.legends.R
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_sign_in.*


class SignInFragment : Fragment(), View.OnClickListener {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ButtonSignIn.setOnClickListener(this)
        textViewSignUp.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            ButtonSignIn.id -> findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
            textViewSignUp.id -> findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }


}