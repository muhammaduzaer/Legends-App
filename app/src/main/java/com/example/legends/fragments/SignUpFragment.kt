package com.example.legends.fragments

import android.content.ContentValues.TAG
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.legends.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.editTextEmailField


class SignUpFragment : Fragment(), View.OnClickListener {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        ButtonRegister.setOnClickListener(this)
        textViewSignIn.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when(v?.id) {
            ButtonRegister.id -> {

                val email = editTextEmail.text.toString()
                val password = editTextPassword.text.toString()

                Log.d("SignInFragment", "Email is:"+ email)
                Log.d("SignInFragment", "Password is: $password")

                //firebase auth to create a user with email and password


                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(requireContext(), "Please enter email and password to log in.", Toast.LENGTH_SHORT).show()
                    return
                }
                findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)
                }
            textViewSignIn.id -> findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }
    }
}