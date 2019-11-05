package com.example.calcalculated


import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.calcalculated.databinding.FragmentFoodBinding
import com.example.calcalculated.db.listFoodSelected

/**
 * A simple [Fragment] subclass.
 */
class foodFragment : Fragment() {
    private lateinit var foodModel: foodControllerModel
    private lateinit var foodSelected: listFoodSelectedControllerModel
    var countFood = 0;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentFoodBinding>(
            inflater,
            R.layout.fragment_food,
            container,
            false
        )
        foodModel = ViewModelProviders.of(this).get(foodControllerModel::class.java)
        foodSelected = ViewModelProviders.of(this).get(listFoodSelectedControllerModel::class.java)
        setListView(binding)
        Log.i("inside", "inside")

        setHasOptionsMenu(true)
        return binding.root
    }


    private fun setListView(binding: FragmentFoodBinding) {
        var array: ArrayList<listFoodDataClass> = ArrayList()
        var count = 0
        Handler().postDelayed({
            foodModel.allFoods.observe(this, Observer { item ->
                item.forEach {
                    array.add(listFoodDataClass(it.foodName, it.kcalOfFood))
                    count++
                }
                if (count == item.size) {
                    //Log.i("array", array.toString())
                    binding.listFoodData.adapter =
                        CustomFoodListView(activity?.applicationContext, array)
                }
            })
        }, 200)
        binding.listFoodData.setOnItemClickListener { _, _, position, _ ->
            setDialog(array, position)
        }
    }

    private fun setDialog(array: ArrayList<listFoodDataClass>, position: Int) {
        val settingsDialog = Dialog(this.requireContext())
        settingsDialog.setContentView(layoutInflater.inflate(R.layout.dialog_countfood, null))
        var btnDialogSave = settingsDialog.findViewById<Button>(R.id.btnSaveCount)
        var editDialog = settingsDialog.findViewById<EditText>(R.id.editTxtCountFood)

        btnDialogSave.setOnClickListener {
            if (editDialog.text.isNotEmpty()) {
                if (editDialog.text.toString().matches("-?\\d+(\\.\\d+)?".toRegex())) {
                    if (editDialog.text.toString().toInt() > 0) {
                        countFood = editDialog.text.toString().toInt()
                        settingsDialog.hide()
                        val countKcal =array[position].kcal * countFood
                        Handler().postDelayed({
                            foodSelected.insert(
                                listFoodSelected(
                                    array[position].foodName,
                                    countKcal,
                                    countFood
                                )
                            )
                            Toast.makeText(activity, "นำเข้ารายการเรียบร้อย", Toast.LENGTH_LONG)
                                .show()
                        }, 200)
                    } else {
                        Toast.makeText(activity, "กรุณาใส่จำนวนใหม่", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(activity, "กรุณาใส่ตัวเลข", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(activity, "กรุณากรอกจำนวน", Toast.LENGTH_LONG).show()
            }
        }
        settingsDialog.show()

    }

    private fun onAdd() {
        view?.findNavController()
            ?.navigate(R.id.action_foodFragment_to_foodInsertFragment)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (menu != null) {
            if (inflater != null) {
                super.onCreateOptionsMenu(menu, inflater)
            }
        }
        inflater?.inflate(R.menu.option_menu, menu)
        menu.getItem(1).setVisible(false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addMenuButton -> onAdd()
        }
        return super.onOptionsItemSelected(item)
    }
}
