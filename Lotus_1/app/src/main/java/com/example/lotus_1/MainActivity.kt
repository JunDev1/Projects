package com.example.lotus_1
//для удаления не нужных импортов используй - CTRL + ALT + 0 (не цифра)
import Fragments.ChatFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.lotus_1.databinding.ActivityMainBinding
import com.example.lotus_1.models.user
import com.example.lotus_1.objects.AppDrawer
import com.example.lotus_1.utilits.*
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var mBinding: ActivityMainBinding //связка
    lateinit var mAppDrawer: AppDrawer
    private lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    override fun onStart() { // метод запускающий функции
        super.onStart()
        initFields() // ининциализация приватной функции выполняющая действия
        initFunc() // аналогично
        AppStates.updateState(AppStates.ONLINE)
    }

    override fun onStop() {
        super.onStop()
        AppStates.updateState(AppStates.OFFLINE)
    }

    private fun initFunc() {
        if (true) {
            setSupportActionBar(mToolbar)
            mAppDrawer.create()
            ReplaceActivity(ChatFragment())     //dataContainer находится в ActivityMain.
        } else {
            ReplaceActivity(RegistrationActivity())
        }
    }

    private fun initFields(){
        mToolbar = mBinding.toolbar
        mAppDrawer = AppDrawer(this, mToolbar)
        initFirebase()
        initUser()
    }

    private fun initUser() {
        REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).
        addListenerForSingleValueEvent(AppValueEventListener {
            USER = it.getValue(user::class.java)?:user()
        })
    }
}