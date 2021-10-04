package uz.mirsaidoff.scaliotest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uz.mirsaidoff.scaliotest.R
import uz.mirsaidoff.scaliotest.ui.results_screen.ResultsFragment
import uz.mirsaidoff.scaliotest.ui.search_screen.SearchFragment

class MainActivity : AppCompatActivity(), Navigation {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null)
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, SearchFragment.newInstance())
                .addToBackStack(SearchFragment::class.java.name)
                .commit()
    }

    override fun openUsersList(login: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ResultsFragment.newInstance(login))
            .addToBackStack(ResultsFragment::class.java.name)
            .commit()
    }
}