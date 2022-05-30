package Fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import com.example.lotus_1.MainActivity
import com.example.lotus_1.R
import com.example.lotus_1.utilits.*
import kotlinx.android.synthetic.main.fragment_change_username.*
import java.util.*

class ChangeUsernameFragment : BaseFragment(R.layout.fragment_change_username) {

    companion object {
        fun newInstance() = ChangeUsernameFragment()
    }

    private lateinit var viewModel: ChangeUsernameViewModel
    private lateinit var mUsername: String

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        settings_input_username.setText(USER.username)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        (activity as MainActivity).menuInflater.inflate(R.menu.settings_menu_confirm,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.settings_confirm_change -> change()
        }
        return true
    }

    private fun change() {
        mUsername = settings_input_username.text.toString().lowercase(Locale.getDefault())
        if (mUsername.isEmpty()) {
            showToast("InputBox is blank")
        } else {
            REF_DATABASE_ROOT.child(NODE_USERNAMES).
            addListenerForSingleValueEvent(AppValueEventListener{
                if(it.hasChild(mUsername)) {
                    showToast("The user with same name is already exist")
                } else {
                    changeUsername()
                }
            })
            changeUsername()
        }
    }

    private fun changeUsername() {
        REF_DATABASE_ROOT.child(NODE_USERNAMES).child(mUsername).setValue(CURRENT_UID).
                addOnCompleteListener {
                    if (it.isSuccessful) {
                        updateCurrentUsername()
                    }
                }
    }

    private fun updateCurrentUsername() {
        REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_USERNAME).
                setValue(mUsername).addOnCompleteListener {
                    if (it.isSuccessful){
                        showToast("Data update")
                        deleteOldUsername()
                    } else {
                        showToast(it.exception?.message.toString())
                    }
        }
    }

    @Suppress("DEPRECATION")
    private fun deleteOldUsername() {
        REF_DATABASE_ROOT.child(NODE_USERNAMES).child(USER.username).removeValue().
                addOnCompleteListener {
                    if (it.isSuccessful) {
                        showToast("Data update")
                        fragmentManager?.popBackStack()
                        USER.username = mUsername
                    } else {
                        showToast(it.exception?.message.toString())
                    }
                    }
                }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChangeUsernameViewModel::class.java)
        // TODO: Use the ViewModel
    }

}