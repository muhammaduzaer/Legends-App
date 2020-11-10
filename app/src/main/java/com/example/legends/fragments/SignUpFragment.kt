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
import com.example.legends.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.editTextEmailField


class SignUpFragment : Fragment(), View.OnClickListener {

    private lateinit var auth: FirebaseAuth
    private val database = Firebase.database
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth


        ButtonRegister.setOnClickListener(this)
        textViewSignIn.setOnClickListener(this)
    }

    private fun signUpUser(name: String, email: String, pass: String) {
        auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val user = auth.currentUser
                        if (user != null) {
                            val myRef = database.getReference(USER).child(user.uid)
                            myRef.setValue(
                                    UserModel(name, email, pass)
                            ).addOnSuccessListener {
                                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()

                            }.addOnFailureListener {
                                Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_SHORT).show()
                            }
                            Toast.makeText(requireContext(), user.email, Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(), "user is null", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(requireContext(), "Authentication Failed.", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener {
                    Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_SHORT).show()
                }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            ButtonRegister.id -> {

                signUpUser(
                        editTextUserName.text.toString(),
                        editTextEmail.text.toString(),
                        editTextPassword.text.toString()
                )
            }
            textViewSignIn.id -> findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }
    }

    companion object {
        const val USER = "users"
    }
}