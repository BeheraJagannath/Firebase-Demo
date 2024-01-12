package com.example.Navigation

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.example.loginfirebasedemo.R
import com.example.prefrences.AppPreferencesDelegates
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {
    lateinit var lEmail: EditText
    lateinit var lPassword: EditText
    lateinit var ForgatePassword: TextView
    lateinit var login: Button
    lateinit var Logout: Button
    lateinit var lprogressbar: ProgressBar
    var firebaseAuth: FirebaseAuth? = null

    private val appPreferences = AppPreferencesDelegates.get()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?, ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_login, container, false)
        lEmail = view.findViewById(R.id.l_email)
        lPassword =  view.findViewById(R.id.l_password)
        login =  view.findViewById(R.id.login)
        Logout =  view.findViewById(R.id.Logout)
        ForgatePassword =  view.findViewById(R.id.forgate_password)
        lprogressbar = view. findViewById(R.id.l_progressbar)


        firebaseAuth = FirebaseAuth.getInstance()

        login.setOnClickListener(View.OnClickListener {
            val email: String = lEmail.text.toString().trim()
            val password: String = lPassword.text.toString().trim()
            if (TextUtils.isEmpty(email)) {
                lEmail.setError("Email is required")
                return@OnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                lPassword.setError("Password is required")
                return@OnClickListener
            }
            if (password.length < 6) {
                lPassword.setError("Password Must be >= 6 Characters ")
                return@OnClickListener
            }
            lprogressbar.visibility = View.VISIBLE
            firebaseAuth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(requireContext(), "Logged in successfully", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.successFragment)
                        appPreferences.wasOnboardingScreen =true

                        lprogressbar.visibility = View.GONE
                    } else {
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                        lprogressbar.visibility = View.GONE
                    }
                }
        })

        Logout.setOnClickListener{
            deleteuser(lEmail.text.toString(), lPassword.text.toString());

        }

        ForgatePassword.setOnClickListener {
             val forgate = lEmail.text.toString()
            firebaseAuth!!.sendPasswordResetEmail(forgate).addOnSuccessListener {
                Toast.makeText(requireContext(), "ForGate password", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            }

        }

        return view
    }

    private fun deleteuser(email: String, password: String) {
        val user = firebaseAuth!!.currentUser
        val credential = EmailAuthProvider.getCredential(email, password)
        user?.reauthenticate(credential)?.addOnCompleteListener {
            user.delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("TAG", "User account deleted.")
                        findNavController().navigate(R.id.homeFragment)
                        Toast.makeText(requireContext(), "Deleted User Successfully", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(requireContext(), "Server error", Toast.LENGTH_LONG).show()

                    }
                }
        }
    }


}