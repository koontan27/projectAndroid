package com.example.calcalculated

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView

class CustomeListView(var context: Context?, var listfood: ArrayList<listFoodDataClass>): BaseAdapter() {
    private class ViewHolder(row: View?){
        var txtName: TextView
        var txtCal: TextView

        init {
            this.txtName = row?.findViewById(R.id.txtFoodName) as TextView
            this.txtCal = row?.findViewById(R.id.txtCal) as TextView
        }
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View?
        var viewHolder: ViewHolder
        if(convertView == null){
            var layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.item_listview_main,parent,false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var car:listFoodDataClass = getItem(position) as listFoodDataClass
        viewHolder.txtName.text = car.foodName
        viewHolder.txtCal.text = car.kcal.toString()
        return view as View

    }

    override fun getItem(position: Int): Any {
        return listfood.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return listfood.count();
    }
    fun remove(position: Int) {
        listfood.removeAt(position)
    }

}