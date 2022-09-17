package com.example.jetpack

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView

class ViewModelFragment : Fragment() {

    companion object {
        fun newInstance() = ViewModelFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_model, container, false)
        val listView = view.findViewById<ListView>(R.id.list_view)
        viewModel = activity?.let { ViewModelProvider(it).get(MainViewModel::class.java) }!!
        viewModel.liveUsers.observe(requireActivity()){
            listView.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item,it)
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}