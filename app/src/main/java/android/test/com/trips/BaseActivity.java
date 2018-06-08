package android.test.com.trips;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


public abstract class BaseActivity extends AppCompatActivity implements BaseFragment.Callback {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected abstract void setUp();


    public void attachFragment(BaseFragment fragmentInstance, String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, fragmentInstance, tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }

    public void addFragment(BaseFragment fragmentInstance, String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content, fragmentInstance, tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }


    private BaseFragment getFragmentByTag(String tag) {
        Fragment fragmentItem = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragmentItem != null) return (BaseFragment) fragmentItem;
        return null;
    }

    private BaseFragment getCurrentFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() >= 1) {
            FragmentManager.BackStackEntry fragmentEntry = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1);
            if (fragmentEntry != null) {
                String fragmentTag = fragmentEntry.getName();
                return (BaseFragment) fragmentManager.findFragmentByTag(fragmentTag);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        BaseFragment currentFragment = getCurrentFragment();
        if (currentFragment != null && currentFragment.isVisible()) {
            currentFragment.onBackClick();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

}