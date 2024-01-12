package com.example.Navigation
import android.os.Bundle
import android.service.controls.ControlsProviderService
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
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class SigninFragment : Fragment() {

    lateinit var enter_name: EditText
    lateinit var remail: EditText
    lateinit var rpassword: EditText
    lateinit var conform_password: TextInputEditText
    lateinit var register: Button
    lateinit var progressbar: ProgressBar

    var firebaseAuth: FirebaseAuth? = null
    var fstore: FirebaseFirestore? = null
    var name: String? = null
    var email:String? = null
    var password:String? = null
    var cpassword:String? = null
    var userID: String? = null
    private val appPreferences = AppPreferencesDelegates.get()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_signin, container, false)

        enter_name =view.findViewById(R.id.enter_name)
        remail = view.findViewById(R.id.remail)
        rpassword = view. findViewById(R.id.rpassword)
        conform_password =view.findViewById(R.id.conform_password)
        register = view.findViewById(R.id.register)
        progressbar = view.findViewById(R.id.progressbar)




        firebaseAuth = FirebaseAuth.getInstance()
        fstore = FirebaseFirestore.getInstance()

        register.setOnClickListener(View.OnClickListener {

            name = enter_name.text.toString()
            email = remail.text.toString().trim()
            password = rpassword.text.toString().trim()
            cpassword = conform_password.text.toString().trim()
            if (TextUtils.isEmpty(name)) {
                enter_name.setError("Enter Name")
                return@OnClickListener
            }
            if (TextUtils.isEmpty(email)) {
                remail.setError("Email is required")
                return@OnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                rpassword.setError("Password is required")
                return@OnClickListener
            }
            if (password!!.length < 6) {
                rpassword.setError("Password Must be >= 6 Characters ")
                return@OnClickListener
            }
            if (password != cpassword) {
                Toast.makeText(requireContext(), "Password doesn't match ...", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            progressbar.visibility = View.VISIBLE
            firebaseAuth!!.createUserWithEmailAndPassword(email!!, password!!)
                .addOnSuccessListener {

                    Toast.makeText(requireContext(), "Registration successful..", Toast.LENGTH_SHORT).show()
                    userID = firebaseAuth!!.currentUser!!.uid
                    val documentReference = fstore!!.collection("users").document(userID!!)
                    val userX: MutableMap<String, Any> = HashMap()
                    userX["name"] = name!!
                    userX["email"] = email!!
                    documentReference.set(userX).addOnSuccessListener {
                        Log.d(ControlsProviderService.TAG, "onSuccess$userID")

                    }.addOnFailureListener { e -> Log.d(ControlsProviderService.TAG, "onFailure$e") }
                    findNavController().navigate(R.id.successFragment)
                    appPreferences.wasOnboardingScreen = true
                    progressbar.visibility = View.GONE
                }.addOnFailureListener {
                    progressbar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Please try again", Toast.LENGTH_SHORT).show()
                }
        })

        return view

    }

    override fun onStart() {
        super.onStart()
        val currentUser = firebaseAuth!!.currentUser
        if (currentUser != null) {
            sendUserToMainActivity()
        }
    }

    private fun sendUserToMainActivity() {
        appPreferences.wasOnboardingScreen = true
        findNavController().navigate(R.id.successFragment)

    }
}