package br.ufg.inf.homeshop.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import br.ufg.inf.homeshop.fragment.FragmentTesteBlue;
import br.ufg.inf.homeshop.fragment.FragmentTesteRed;

public class FragmentAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public FragmentAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                FragmentTesteBlue tab1 = new FragmentTesteBlue();
                return tab1;
            case 1:
                FragmentTesteRed tab2 = new FragmentTesteRed();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
