package com.example.dataholics.ui.login

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.util.Patterns.EMAIL_ADDRESS
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.util.PatternsCompat.EMAIL_ADDRESS
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import com.example.dataholics.MainActivity
import com.example.dataholics.R
import com.example.dataholics.ui.create_account.CreateAccountFragment
import com.example.dataholics.ui.input.InputFragment
import com.example.dataholics.ui.input.InputViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_create_account.*
import kotlinx.android.synthetic.main.fragment_login.*
import java.util.regex.Pattern
import kotlin.math.log

class LoginFragment : Fragment(), View.OnClickListener {
    private val mainActivity : MainActivity = MainActivity()
    private val auth:FirebaseAuth = mainActivity.getAuth()
    private final val TAG: String = "Login_fragment"
    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel
    var root: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_login, container, false)

        // Call login-db method when button is clicked
        val loginButton: Button = root!!.findViewById(R.id.login_butt)
        loginButton.setOnClickListener{
            login()
        }

        // Change fragment to create account
        val createButton : Button = root!!.findViewById(R.id.create_button)
        createButton.setOnClickListener {
            val transaction = fragmentManager!!.beginTransaction()
            // transaction.replace(COLLECTION OF FRAGMENTS, FRAGMENT TO CHANGE TO)
            transaction.replace(R.id.nav_host_fragment, CreateAccountFragment())
            transaction.commit()
            }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

    // Authentication for login
   private fun login() {
       val emailView : TextView = root!!.findViewById(R.id.email_view)
        val email = emailView.text.toString()
       val passwordView: TextView = root!!.findViewById(R.id.pass_view)
        val password : String = passwordView.text.toString()
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
       if (password.isEmpty()){
           pass_view.error = "Please enter a password"
           pass_view.requestFocus()
           return
       }

       auth.signInWithEmailAndPassword(email, password)
           .addOnCompleteListener(this.mainActivity) { task ->
               if (task.isSuccessful) {
                   // Sign in success, update UI with the signed-in user's information
                   Log.d(TAG, "signInWithEmail:success")
                   val user = auth.currentUser
                   updateUI(user)
                   // Update fragment
                   val transaction = fragmentManager!!.beginTransaction()
                   transaction.replace(R.id.nav_host_fragment, InputFragment())
                   transaction.commit()
               } else {
                   try {
                       throw task.exception!!
                   }
                   catch(exists: FirebaseAuthInvalidCredentialsException) {
                       email_view.error = "The password is incorrect."
                   }
                   catch (malformedEmail : FirebaseAuthInvalidCredentialsException){
                       email_view.error = "Email is not entered correctly."
                   }
                   catch (malformedEmail : FirebaseNetworkException){
                       Toast.makeText(this.activity, "Network connection is currently not working.",
                           Toast.LENGTH_SHORT).show()
                   }

                   // If sign in fails, display a message to the user.
                   Log.w(TAG, "signInWithEmail:failure", task.exception)
                   Toast.makeText(this.activity, "Authentication failed.",
                       Toast.LENGTH_SHORT).show()
                   updateUI(null)
                   // ...
               }

               // ...
           }

    }
    fun createAccount(view: View) {

    }

    public fun updateUI(user: FirebaseUser?){

    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }


}
