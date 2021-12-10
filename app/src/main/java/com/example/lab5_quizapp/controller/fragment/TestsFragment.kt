package com.example.lab5_quizapp.controller.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.lab5_quizapp.R
import com.example.lab5_quizapp.controller.TestsActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TestsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TestsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var fragment_tests = inflater.inflate(R.layout.fragment_tests,container,false)
        val context = activity as Context
        val name: EditText = fragment_tests.findViewById(R.id.name)
        val button : Button = fragment_tests.findViewById(R.id.btn_start)
        button.setOnClickListener{
            if(name.text.toString().isEmpty()){
                Toast.makeText(context,"Введите никнейм",Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(context, TestsActivity::class.java)
                intent.putExtra("user_name",name.text.toString())
                this.startActivity(intent)
            }
        }
        return fragment_tests
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TestsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TestsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}