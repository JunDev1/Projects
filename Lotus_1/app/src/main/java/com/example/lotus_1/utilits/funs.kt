package com.example.lotus_1.utilits

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.lotus_1.R

fun AppCompatActivity.ReplaceActivity (activity: AppCompatActivity) {
    val intent = Intent (this, activity::class.java)
    startActivity(intent)
    this.finish()
}
fun AppCompatActivity.ReplaceActivity (fragment: Fragment) {
    supportFragmentManager.beginTransaction().addToBackStack(null)
        .replace(R.id.dataContainer, fragment).commit()
}
fun Fragment.ReplaceFragment(fragment: Fragment) {
    this.activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(null)?.
        replace(R.id.dataContainer, fragment)?.commit()

    }
fun Fragment.showToast (message: String) {
    Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
}

fun Activity.showToast (message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}