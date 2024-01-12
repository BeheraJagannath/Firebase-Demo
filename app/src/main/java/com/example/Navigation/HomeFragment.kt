package com.example.Navigation

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.loginfirebasedemo.R


class HomeFragment : Fragment() {
    lateinit var signin: Button
    lateinit var login: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        signin = view.findViewById(R.id.login_heree)
        login = view.findViewById(R.id.l_loginn)

//        val toast = Toast.makeText(requireContext(), "Hello Javatpoint", Toast.LENGTH_SHORT)
//        toast.setGravity(Gravity.TOP or Gravity.CENTER, 50, 50)
//        toast.show()

        signin.setOnClickListener(View.OnClickListener {

            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_signinFragment)

        })
        login.setOnClickListener(View.OnClickListener {

            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_loginFragment3)

        })
        return view
    }
}

