package com.example.calcalculated


import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.calcalculated.databinding.FragmentFoodInsertBinding
import com.example.calcalculated.db.listFood

/**
 * A simple [Fragment] subclass.
 */
class foodInsertFragment : Fragment() {
    private lateinit var listFoodModel: foodControllerModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentFoodInsertBinding>(
            inflater,
            R.layout.fragment_food_insert,
            container,
            false
        )
        listFoodModel = ViewModelProviders.of(this).get(foodControllerModel::class.java)

        onSave(binding)
        return binding.root
    }

    private fun onSave(binding: FragmentFoodInsertBinding) {
        binding.saveBtn.setOnClickListener {
            if (binding.editTxtFoodName.text.isNotEmpty() && binding.editTextKcal.text.isNotEmpty()) {
                listFoodModel.insert(
                    listFood(
                        binding.editTxtFoodName.text.toString(),
                        binding.editTextKcal.text.toString().toInt()
                    )
                )
                Toast.makeText(activity, "บันทึกข้อมูลเรียบร้อย", Toast.LENGTH_LONG).show()
                Handler().postDelayed({
                    view?.findNavController()
                        ?.navigate(R.id.action_foodInsertFragment_to_foodFragment)
                }, 200)
            } else {
                Toast.makeText(activity, "กรุณากรอกข้อมูลให้ครบทุกช่อง", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

}
