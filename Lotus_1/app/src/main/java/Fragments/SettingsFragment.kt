package Fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.lotus_1.MainActivity
import com.example.lotus_1.R
import com.example.lotus_1.RegistrationActivity
import com.example.lotus_1.utilits.ReplaceActivity
import com.example.lotus_1.utilits.ReplaceFragment
import com.example.lotus_1.utilits.USER
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private lateinit var viewModel: SettingsViewModel

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        initFields()
    }

    private fun initFields() {
        settings_login.text = USER.username
        settings_email.text = USER.email
        settings_status.text = USER.state
        settings_username.text = USER.fullname
               settings_btn_change_login.setOnClickListener { ReplaceFragment(ChangeUsernameFragment()) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

        imageButton_signOut.setOnClickListener {
            signOut()
        }
    }
    private fun signOut() {
        val mAuth = Firebase.auth
        mAuth.signOut()
        (activity as MainActivity).ReplaceActivity(RegistrationActivity())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.settings_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_menu_change_name -> ReplaceFragment(ChangeNameFragment())
        }
        return true
    }
}