package com.example.snowtrails.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.snowtrails.R

class NoAuthFragment : Fragment() {

    companion object {
        fun newInstance() = NoAuthFragment()
    }

    private lateinit var viewModel: NoAuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.no_auth_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NoAuthViewModel::class.java)
        // TODO: Use the ViewModel
    }

}