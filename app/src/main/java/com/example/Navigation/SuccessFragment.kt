package com.example.Navigation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.loginfirebasedemo.MainActivity
import com.example.loginfirebasedemo.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
class SuccessFragment : Fragment() {
    lateinit var signout: Button
    lateinit var mName: TextView
    lateinit var mEmail: TextView

    lateinit var firebaseAuth:FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_success, container, false)

        signout =view .findViewById(R.id.signout)
        mEmail = view .findViewById(R.id.mEmail)
        mName = view .findViewById(R.id.mName)

        firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser: FirebaseUser = firebaseAuth.currentUser!!
        val email = firebaseUser.email
        val PersonName = firebaseUser.displayName
        mEmail.text = email
        mName.text = PersonName

        signout.setOnClickListener(View.OnClickListener {
            firebaseAuth.signOut()
//            startActivity(Intent(requireContext(),MainActivity::class.java))
        })

        return view
    }

}