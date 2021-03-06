@file:Suppress("DEPRECATION")

package Fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import com.example.lotus_1.MainActivity
import com.example.lotus_1.R
import com.example.lotus_1.utilits.*
import kotlinx.android.synthetic.main.fragment_change_name.*

class ChangeNameFragment : BaseFragment(R.layout.fragment_change_name) {

    companion object {
        fun newInstance() = ChangeNameFragment()
    }

    private lateinit var viewModel: ChangeNameViewModel

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        val fullnameList = USER.fullname.split("")
        if (fullnameList.size > 1) {
        settings_input_name.setText(fullnameList[0])
        settings_input_surname.setText(fullnameList[1])
    } else settings_input_name.setText(fullnameList[0])
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        (activity as MainActivity).menuInflater.inflate(R.menu.settings_menu_confirm,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.settings_confirm_change -> changeName()
        }
        return true
    }

    private fun changeName() {
        val name = settings_input_name.text.toString()
        val surname = settings_input_surname.text.toString()
        if (name.isEmpty()) {
            showToast(getString(R.string.settings_toast_name_is_empty))
        } else {
            val fullname ="$name $surname"
            REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_FULLNAME).
                    setValue(fullname).addOnCompleteListener {
                        if (it.isSuccessful) {
                            showToast(getString(R.string.toast_data_update))
                            USER.fullname = fullname
                            fragmentManager?.popBackStack()
                        }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChangeNameViewModel::class.java)

    }

}