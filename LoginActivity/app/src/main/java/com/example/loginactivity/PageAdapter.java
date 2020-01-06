package com.example.loginactivity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class PageAdapter extends FragmentPagerAdapter {

    private int numoftabs;
    private boolean acting = false;

    public PageAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numoftabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new tab1();
            case 1:
                return new tab2();
            case 2:
                return new tab3();
            case 3:
                return new tab4();
            case 4:
                return new tab5();



        }
        return null;
    }

    @Override
    public int getCount(){
        return numoftabs;
    }

    public int getItemPosition(@NonNull Object object){
        return POSITION_NONE;
    }




}
