package com.example.OTPVerification

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.loginfirebasedemo.MainActivity
import com.example.loginfirebasedemo.R
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import java.util.concurrent.TimeUnit

class VerifyActivity : AppCompatActivity() {
    var inputcode1: EditText? = null
    var inputcode2:EditText? = null
    var inputcode3:EditText? = null
    var inputcode4:EditText? = null
    var inputcode5:EditText? = null
    var inputcode6:EditText? = null
    var textmobile: TextView? = null
    var resend_otp:TextView? = null
    var progress: ProgressBar? = null
    var Verify: Button? = null

    var verificationId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify)

        inputcode1 = findViewById(R.id.inputcode1)
        inputcode2 = findViewById(R.id.inputcode2)
        inputcode3 = findViewById(R.id.inputcode3)
        inputcode4 = findViewById(R.id.inputcode4)
        inputcode5 = findViewById(R.id.inputcode5)
        inputcode6 = findViewById(R.id.inputcode6)
        textmobile = findViewById(R.id.textmobile)
        progress = findViewById(R.id.verify_progressbar)
        Verify = findViewById(R.id.verify)
        resend_otp = findViewById(R.id.resend_otp)

        textmobile!!.text = String.format("91-%s", intent.getStringExtra("mobile"))
        verificationId = intent.getStringExtra("verificationId")
        setOtpInputs()

        Verify!!.setOnClickListener(View.OnClickListener {
            if (inputcode1!!.text.toString().trim { it <= ' ' }.isEmpty()
                || inputcode2!!.text.toString().trim { it <= ' ' }.isEmpty()
                || inputcode3!!.text.toString().trim { it <= ' ' }.isEmpty()
                || inputcode4!!.text.toString().trim { it <= ' ' }.isEmpty()
                || inputcode5!!.text.toString().trim { it <= ' ' }.isEmpty()
                || inputcode6!!.text.toString().trim { it <= ' ' }.isEmpty()
            ) {
                Toast.makeText(this@VerifyActivity, "Please enter valid code", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            val code = inputcode1!!.text.toString() +
                    inputcode2!!.text.toString() +
                    inputcode3!!.text.toString() +
                    inputcode4!!.text.toString() +
                    inputcode5!!.text.toString() +
                    inputcode6!!.text.toString()
            if (verificationId != null) {
                progress!!.visibility =View.VISIBLE
                Verify!!.visibility =View.GONE
                val phoneAuthCredential = PhoneAuthProvider.getCredential(
                    verificationId!!, code)
                FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                    .addOnCompleteListener { task ->
                        progress!!.visibility =View.GONE
                        Verify!!.visibility =View.VISIBLE
                        if (task.isSuccessful) {
                            val intent = Intent(applicationContext, MainActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@VerifyActivity,
                                "The verification code entered was invalid",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        })

        resend_otp!!.setOnClickListener(View.OnClickListener {
            PhoneAuthProvider.getInstance()
                .verifyPhoneNumber("+91" + intent.getStringExtra("mobile"), 60,
                    TimeUnit.SECONDS, this@VerifyActivity,
                    object : OnVerificationStateChangedCallbacks() {
                        override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {

                        }
                        override fun onVerificationFailed(e: FirebaseException) {
                            Toast.makeText(this@VerifyActivity, e.message, Toast.LENGTH_SHORT)
                                .show()
                        }

                        override fun onCodeSent(newverificationId: String, forceResendingToken: ForceResendingToken) {
                            verificationId = newverificationId
                            Toast.makeText(this@VerifyActivity, "OTP sent", Toast.LENGTH_SHORT)
                                .show()
                        }
                    })
        })
    }


    private fun setOtpInputs() {
        inputcode1!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (!charSequence.toString().trim { it <= ' ' }.isEmpty()) {
                    inputcode1!!.requestFocus()
                }
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })
        inputcode2!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (!charSequence.toString().trim { it <= ' ' }.isEmpty()) {
                    inputcode2!!.requestFocus()
                }
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })
        inputcode3!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (!charSequence.toString().trim { it <= ' ' }.isEmpty()) {
                    inputcode3!!.requestFocus()
                }
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })
        inputcode4!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (!charSequence.toString().trim { it <= ' ' }.isEmpty()) {
                    inputcode4!!.requestFocus()
                }
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })
        inputcode5!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (!charSequence.toString().trim { it <= ' ' }.isEmpty()) {
                    inputcode5!!.requestFocus()
                }
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })
        inputcode6!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (!charSequence.toString().trim { it <= ' ' }.isEmpty()) {
                    inputcode6!!.requestFocus()
                }
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })
    }
}