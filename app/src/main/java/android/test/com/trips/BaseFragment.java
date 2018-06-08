package android.test.com.trips;


import android.content.Context;

import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.View;


public abstract class BaseFragment extends Fragment {

    private BaseActivity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.mActivity = activity;
            activity.onFragmentAttached();
        }
    }

    public void willBeVisible() {
    }


    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }


    protected void onAttachToContext(Context context) {
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.mActivity = activity;
            activity.onFragmentAttached();
        }
    }


    public BaseActivity getBaseActivity() {
        return mActivity;
    }

    protected abstract void setUp(View view);

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void updateFragment(BaseFragment fragmentInstance, String tag) {
        ((BaseActivity) getActivity()).attachFragment(fragmentInstance, tag);
    }

    /**
     * Listener to go back: to be handled in each fragment accordingly
     */
    public void onBackClick() {

    }


    public interface Callback {

        void onFragmentAttached();

        void onFragmentDetached(String tag);
    }


    @Override
    public void onResume() {
        super.onResume();
    }


}

