package com.example.calcalculated


import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.view.View.*
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.calcalculated.databinding.FragmentMainBinding
import kotlinx.android.synthetic.main.fragment_food_insert.view.*
import kotlin.math.absoluteValue


/**
 * A simple [Fragment] subclass.
 */
class mainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var foodModel: foodControllerModel
    private lateinit var checkProfile: profileControllerModel
    private lateinit var foodSelected: listFoodSelectedControllerModel
    private var listViewAdapter: CustomeListView? = null
    private var yourCal = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main, container, false
        )
        checkProfile = ViewModelProviders.of(this).get(profileControllerModel::class.java)
        //checkProfile.clear()
        checkEmptyProfile()
        showProfile()
        foodModel = ViewModelProviders.of(this).get(foodControllerModel::class.java)
        foodSelected = ViewModelProviders.of(this).get(listFoodSelectedControllerModel::class.java)
        setListView()
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun checkEmptyProfile() {
        Handler().postDelayed({
            checkProfile.profileAlls.observe(this, Observer { item ->
                Log.i("empty", item.isEmpty().toString())
                if (item.isEmpty()) {
                    view?.findNavController()
                        ?.navigate(R.id.action_mainFragment_to_inputFragment)
                }
            })
        }, 200)
    }

    private fun setCal() {
        var allCal = 0
        Handler().postDelayed({
            foodSelected.listFoodAll.observe(this, Observer { item ->
                item.forEach {
                    allCal += it.kcalOfFood
                }
            })
        }, 200)
        Handler().postDelayed({
            Log.i("cal", allCal.toString())
            binding.txtCalOne.text = allCal.toString()
            if (yourCal - allCal < 0) {
                binding.imageView.setImageResource(R.drawable.emo2)
                binding.txtEat3.text = "คุณกินเกินไป"
            } else {
                binding.imageView.setImageResource(R.drawable.emo1)
                binding.txtEat3.text = "คุณยังกินได้อีก"
            }
            binding.txtCalThree.text = (yourCal - allCal).absoluteValue.toString()
        }, 200)
    }

    private fun showProfile() {
        checkProfile.profileAlls.observe(this, Observer { item ->
            item.map {
                binding.txtCalTwo.text = it.calTotal.toString()
                yourCal = it.calTotal
            }
        })
    }

    private fun setListView() {
        var array: ArrayList<listFoodDataClass> = ArrayList()
        var count = 0
        Handler().postDelayed({
            foodSelected.listFoodAll.observe(this, Observer { item ->
                item.map {
                    array.add(listFoodDataClass(it.foodName, it.kcalOfFood))
                    count++
                }
                if (count == item.size) {
                    setCal()
                    binding.listView.adapter =
                        CustomeListView(getActivity()?.applicationContext, array)
                    binding.listView.setOnItemClickListener { _, _, position, _ ->
                        listViewAdapter?.remove(position)
                        foodSelected.clear(array[position].foodName)
                        setListView()
                    }
                }
            })
        }, 200)
    }

    private fun onAboutMePage() {
        view?.findNavController()
            ?.navigate(R.id.action_mainFragment_to_aboutFragment)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (menu != null) {
            if (inflater != null) {
                super.onCreateOptionsMenu(menu, inflater)
            }
        }
        inflater?.inflate(R.menu.option_menu, menu)
        menu.getItem(0).setVisible(false);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.infoMenuButton -> onAboutMePage()
        }
        return super.onOptionsItemSelected(item)
    }
}
