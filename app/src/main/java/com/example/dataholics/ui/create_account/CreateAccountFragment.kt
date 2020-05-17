package com.example.dataholics.ui.create_account

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.dataholics.MainActivity

import com.example.dataholics.R
import com.example.dataholics.ui.input.InputFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_create_account.*
import kotlinx.android.synthetic.main.fragment_login.*

class CreateAccountFragment : Fragment() {
    private final val TAG: String = "Create_fragment"
    val mainActivity : MainActivity = MainActivity()
    val auth: FirebaseAuth = mainActivity.getAuth()

    companion object {
        fun newInstance() =
            CreateAccountFragment()
    }
    var root: View? = null
    private lateinit var viewModel: CreateAccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_create_account, container, false)
        // Call login-db method when button is clicked
        val createButton: Button = root!!.findViewById(R.id.create_account_button)
        createButton.setOnClickListener{
            createAccount()
        }
        
        
        
        
        
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CreateAccountViewModel::class.java)
        // TODO: Use the ViewModel
    }
    fun createAccount(){
        val emailView : TextView = root!!.findViewById(R.id.email_viewC)
        val email = emailView.text.toString()
        val passwordView: TextView = root!!.findViewById(R.id.pass_viewC)
        val password : String = passwordView.text.toString()
        val passwordRView : TextView = root!!.findViewById(R.id.pass_viewCR)
        val passwordR : String = passwordRView.text.toString()

        if(email.isEmpty()){
            email_view.error = "Please enter email"
            email_view.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_view.error = "Please enter a valid email address"
            email_view.requestFocus()
            return
        }
        if (!password.equals(passwordR)){
            pass_viewC.error = "Passwords do not match"
            pass_viewC.requestFocus()
            return
        }

        if (password.isEmpty()){
            pass_viewC.error = "Please enter a password"
            pass_viewC.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this.mainActivity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                    val transaction = fragmentManager!!.beginTransaction()
                    transaction.replace(R.id.nav_host_fragment, InputFragment())
                    transaction.commit()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(this.mainActivity, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }

                // ...
            }

    }
    private fun updateUI(firebaseUser: FirebaseUser?){

    }


}
