package com.example.OTPVerification

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.loginfirebasedemo.R
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import java.util.concurrent.TimeUnit

class SendOtpActivity : AppCompatActivity() {
    val x: Int by lazy { 10 }
    var send_otp: Button? = null
    var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_otp_activity)
        send_otp = findViewById(R.id.send_otp)
        progressBar = findViewById(R.id.otp_progressbar)
        val mobile_number :EditText = findViewById(R.id.mobile_number)

        send_otp!!.setOnClickListener(View.OnClickListener {
            if (mobile_number.text.toString().trim { it <= ' ' }.isEmpty()) {
                Toast.makeText(this@SendOtpActivity, "Enter Mobile Number", Toast.LENGTH_SHORT)
                    .show()
                return@OnClickListener
            }
            progressBar!!.visibility =View.VISIBLE
            send_otp!!.visibility =View.GONE
            PhoneAuthProvider.getInstance()
                .verifyPhoneNumber("+91" + mobile_number.text.toString(), 60,
                    TimeUnit.SECONDS, this@SendOtpActivity,
                    object : OnVerificationStateChangedCallbacks() {
                        override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                            progressBar!!.visibility = View.GONE
                            send_otp!!.visibility = View.VISIBLE
                        }

                        override fun onVerificationFailed(e: FirebaseException) {
                            progressBar!!.visibility =View.GONE
                            send_otp!!.visibility =View.VISIBLE
                            Toast.makeText(this@SendOtpActivity, e.message, Toast.LENGTH_SHORT).show()
                        }

                        override fun onCodeSent(verificationId: String, forceResendingToken: ForceResendingToken, ) {
                            progressBar!!.visibility =View.GONE
                            send_otp!!.visibility =View.VISIBLE
                            val intent = Intent(this@SendOtpActivity, VerifyActivity::class.java)
                            intent.putExtra("mobile", mobile_number.text.toString())
                            intent.putExtra("verificationId", verificationId)
                            startActivity(intent)
                        }
                    })

        })
    }
}