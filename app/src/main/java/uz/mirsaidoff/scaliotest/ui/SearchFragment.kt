package uz.mirsaidoff.scaliotest.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import uz.mirsaidoff.scaliotest.Service
import uz.mirsaidoff.scaliotest.databinding.FragmentSearchBinding
import java.lang.ref.SoftReference
import java.lang.ref.WeakReference

class SearchFragment : Fragment() {

    companion object {
        fun newInstance(): SearchFragment {
            val args = Bundle()
            val fragment = SearchFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val vmFactory by lazy {
        SearchViewModel.Factory()
    }

    private var navigation: SoftReference<Navigation?> = SoftReference(null)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Navigation) navigation = SoftReference(context)
    }

    private val vm by viewModels<SearchViewModel> { vmFactory }
    private lateinit var searchBinding: FragmentSearchBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        searchBinding = FragmentSearchBinding.inflate(inflater, container, false)
        return searchBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchBinding.btnSubmit.setOnClickListener {
            val login = searchBinding.teSearch.text.toString()
            if (login.isNotEmpty()) {
                navigation.get()?.openUsersList(login)
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        navigation.clear()
    }
}