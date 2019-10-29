package com.example.calcalculated


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.calcalculated.databinding.FragmentOverviewBinding

/**
 * A simple [Fragment] subclass.
 */
class overViewFragment : Fragment() {
    private lateinit var checkProfile: profileControllerModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentOverviewBinding>(inflater,R.layout.fragment_overview,container,false)
        checkProfile = ViewModelProviders.of(this).get(profileControllerModel::class.java)
        showValue(binding)
        onClick(binding)
        return binding.root
    }

    private fun onClick(binding: FragmentOverviewBinding) {
        binding.goMainBtn.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_overViewFragment_to_mainFragment)
        }
    }
    private fun showValue(binding: FragmentOverviewBinding){
        checkProfile.profileAlls.observe(this, Observer { item ->
            item.map {
                binding.BMIEditText.text = it.calBmi.toString()
                binding.statusEditText.text = it.calWeight
                binding.energyEditText.text = it.calTotal.toString()
            }
        })
    }

}
