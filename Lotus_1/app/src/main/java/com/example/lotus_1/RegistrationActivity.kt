package com.example.lotus_1

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lotus_1.databinding.ActivityRegistationBinding
import com.example.lotus_1.utilits.*
import com.example.lotus_1.utilits.initFirebase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_registation.*

class RegistrationActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mBinding: ActivityRegistationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegistationBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initFirebase()

        et_password.transformationMethod = PasswordTransformationMethod.getInstance()
        btn_sign_up.setOnClickListener {
            signUpUser()
        }
        btn_sign_in.setOnClickListener {
            signInUser()
        }
    }


    private fun signInUser() {
        initFirebase()
        val email: String = et_email.text.toString()
        val password: String = et_password.text.toString()
        val auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Toast.makeText(this, "Successfully logged In", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Login in failed", Toast.LENGTH_SHORT).show()
            }
            val intent = Intent(this@RegistrationActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signUpUser() {
        val email: String = et_email.text.toString()
        val password: String = et_password.text.toString()
        //Проверка на пустые поля
        if (email.isBlank() || password.isBlank()) { // "||" - логический оператор ИЛИ

            Toast.makeText(
                this, "Email and Password can't be blank",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        val mAuth = FirebaseAuth.getInstance()
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) {
            val uid = AUTH.currentUser?.uid.toString()
            val dateMap = mutableMapOf<String, Any>()
            dateMap[CHILD_ID] = uid
            dateMap[CHILD_EMAIL] = email
            dateMap[CHILD_USERNAME] = uid

            REF_DATABASE_ROOT.child(NODE_USERS).child(uid).updateChildren(dateMap)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        showToast("You're welcome")
                        this.ReplaceActivity(MainActivity())
                    } else {
                        showToast(task.exception?.message.toString())
                    }
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Account successfully created ", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(this, "Sign up failed", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}