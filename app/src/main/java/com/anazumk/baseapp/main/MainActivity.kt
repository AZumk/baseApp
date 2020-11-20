package com.anazumk.baseapp.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.anazumk.baseapp.R
import com.anazumk.baseapp.detail.DetailFragment
import com.anazumk.baseapp.network.model.RegionalData

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            pushFragment(MainFragment.newInstance(), MainFragment.Tag)
        }
    }

    fun openDetail(regionalData: RegionalData){
       pushFragment(DetailFragment.newInstance(regionalData), DetailFragment.Tag)
    }

    fun pushFragment(fragment: Fragment, tag: String){
        println("Fragnment teg $tag")
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.anim_slide_in, R.anim.anim_slide_out, R.anim.anim_slide_in_from_left, R.anim.anim_slide_out_from_left)
            .replace(R.id.fragment_container, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount > 1){
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}
