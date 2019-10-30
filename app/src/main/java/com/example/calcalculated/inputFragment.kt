package com.example.calcalculated


import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.calcalculated.databinding.FragmentInputBinding
import com.example.calcalculated.db.profile
import kotlin.math.pow

/**
 * A simple [Fragment] subclass.
 */
class inputFragment : Fragment() {

    private lateinit var checkProfile: profileControllerModel
    var txtSpinerSexValue = ""
    var txtSpinerActivityValue = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentInputBinding>(
            inflater,
            R.layout.fragment_input,
            container,
            false
        )

        checkProfile = ViewModelProviders.of(this).get(profileControllerModel::class.java)
        setSpinnerSex(binding)
        setSpinnerActivity(binding)
        checkEmptyProfile(binding)
        return binding.root
    }

    private fun onClickInsert(binding: FragmentInputBinding) {
        binding.saveBtn.setOnClickListener { view: View ->
            if (txtSpinerSexValue !== "" && binding.oldEditText.text.isNotEmpty() && binding.weightEditText.text.isNotEmpty()
                && binding.heightEditText.text.isNotEmpty() && txtSpinerActivityValue !== ""
            ) {
                var calbmi =
                    binding.weightEditText.text.toString().toDouble() / (binding.heightEditText.text.toString().toDouble() / 100).pow(
                        2
                    )
                var calWeight = ""
                calWeight = calculateBMI(calbmi, calWeight)
                var total = 0
                total = calculateBMR(total, binding)
                Log.i("total1",total.toString())
                var all = 0;
                Handler().postDelayed({
                    all = calTotal(total, txtSpinerActivityValue)
                    Log.i("total2", all.toString())
                },200)
                Handler().postDelayed({
                    Log.i("total3",all.toString())
                    checkProfile.insert(
                        profile(
                            txtSpinerSexValue,
                            binding.oldEditText.text.toString().toInt(),
                            binding.weightEditText.text.toString().toDouble(),
                            binding.heightEditText.text.toString().toDouble(),
                            txtSpinerActivityValue,
                            calbmi, calWeight, all
                        )
                    )
                    Toast.makeText(getActivity(), "บันทึกข้อมูลเรียบร้อย", Toast.LENGTH_LONG).show()
                    view.findNavController().navigate(R.id.action_inputFragment_to_overViewFragment)
                }, 600)
            } else {
                Toast.makeText(getActivity(), "กรุณากรอกข้อมูลให้ครบทุกช่อง", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun calTotal(
        total: Int,
        txtspinner: String
    ): Int {
        var total1 = 0
        Log.i("total4", txtspinner)
        if (txtspinner == "ไม่ได้ออกกำลังกาย") {
            total1 = (total * 1.2).toInt()
        } else if (txtspinner == "ออกกำลังกาย 1-3 วัน/สัปดาห์") {
            total1 = (total * 1.375).toInt()
        } else if (txtspinner == "ออกกำลังกาย 3-5 วัน/สัปดาห์") {
            total1 = (total * 1.55).toInt()
        } else if (txtspinner == "ออกกำลังกาย 6-7 วัน/สัปดาห์") {
            total1 = (total * 1.725).toInt()
        } else {
            total1 = (total * 1.9).toInt()
        }
        return total1
    }

    private fun calculateBMR(
        total: Int,
        binding: FragmentInputBinding
    ): Int {
        var total1 = total
        if (txtSpinerSexValue == "ชาย") {
            total1 =
                (66 + (13.7 * binding.weightEditText.text.toString().toDouble()) + (5 * binding.heightEditText.text.toString().toDouble())
                        - (6.8 * binding.oldEditText.text.toString().toInt())).toInt()
        } else {
            (665 + (9.6 * binding.weightEditText.text.toString().toDouble()) + (1.8 * binding.heightEditText.text.toString().toDouble())
                    - (4.7 * binding.oldEditText.text.toString().toInt())).toInt()
        }
        return total1
    }

    private fun calculateBMI(calbmi: Double, calWeight: String): String {
        var calWeight1 = calWeight
        if (calbmi < 18.5) {
            calWeight1 = "น้ำหนักต่ำกว่าเกณฑ์"
        } else if (calbmi >= 18.5 && calbmi < 23.0) {
            calWeight1 = "สมส่วน"
        } else if (calbmi >= 23.0 && calbmi < 25.0) {
            calWeight1 = "น้ำหนักเกิน"
        } else if (calbmi >= 25.0 && calbmi < 29.9) {
            calWeight1 = "โรคอ้วน"
        } else {
            calWeight1 = "โรคอ้วนอันตราย"
        }
        return calWeight1
    }

    private fun onClickUpdate(binding: FragmentInputBinding) {
        binding.saveBtn.setOnClickListener { view: View ->
            if (txtSpinerSexValue !== "" && binding.oldEditText.text.isNotEmpty() && binding.weightEditText.text.isNotEmpty()
                && binding.heightEditText.text.isNotEmpty() && txtSpinerActivityValue !== ""
            ) {
                var calbmi =
                    binding.weightEditText.text.toString().toDouble() / (binding.heightEditText.text.toString().toDouble() / 100).pow(
                        2
                    )
                var calWeight = ""
                calWeight = calculateBMI(calbmi, calWeight)
                var total = 0
                total = calculateBMR(total, binding)

                Handler().postDelayed({
                    checkProfile.update(
                        profile(
                            txtSpinerSexValue,
                            binding.oldEditText.text.toString().toInt(),
                            binding.weightEditText.text.toString().toDouble(),
                            binding.heightEditText.text.toString().toDouble(),
                            txtSpinerActivityValue,
                            calbmi, calWeight, total
                        )
                    )
                    Toast.makeText(getActivity(), "แก้ไขข้อมูลเรียบร้อย", Toast.LENGTH_LONG).show()
                    view.findNavController().navigate(R.id.action_inputFragment_to_overViewFragment)
                }, 200)
            } else {
                Toast.makeText(getActivity(), "กรุณากรอกข้อมูลให้ครบทุกช่อง", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }


    private fun checkEmptyProfile(binding: FragmentInputBinding) {
        checkProfile.profileAlls.observe(this, Observer { item ->
            if (item.isEmpty()) {
                onClickInsert(binding)
            } else {
                onClickUpdate(binding)
                onClickRemote(binding)
            }
        })

    }

    private fun onClickRemote(binding: FragmentInputBinding) {
        binding.deleteBtn.setOnClickListener {
            checkProfile.clear()
            Toast.makeText(getActivity(), "ลบข้อมูลโปรไฟล์ทั้งหมดแล้ว", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun setSpinnerSex(binding: FragmentInputBinding) {
        val sex = arrayOf("ชาย", "หญิง")

        val adapter = getActivity()?.applicationContext?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item,
                sex
            )
        }
        adapter?.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        binding.sexList.adapter = adapter

        binding.sexList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                txtSpinerSexValue = parent?.getItemAtPosition(position).toString()
            }
        }
    }

    private fun setSpinnerActivity(binding: FragmentInputBinding) {
        val activity = arrayOf(
            "ไม่ได้ออกกำลังกาย",
            "ออกกำลังกาย 1-3 วัน/สัปดาห์",
            "ออกกำลังกาย 3-5 วัน/สัปดาห์",
            "ออกกำลังกาย 6-7 วัน/สัปดาห์",
            "ออกกำลังกายอย่างหนัก หรือเป็นนักกีฬา"
        )

        val adapter = getActivity()?.applicationContext?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item,
                activity
            )
        }
        adapter?.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        binding.activitiesList.adapter = adapter

        binding.activitiesList.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    txtSpinerActivityValue = parent?.getItemAtPosition(position).toString()
                }
            }
    }

}
