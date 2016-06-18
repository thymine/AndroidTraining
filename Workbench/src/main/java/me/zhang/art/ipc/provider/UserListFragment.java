package me.zhang.art.ipc.provider;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.zhang.workbench.R;

/**
 * Created by Li on 6/18/2016 6:49 PM.
 */
public class UserListFragment extends BaseFragment {

    public static UserListFragment newInstance() {
        return new UserListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_users, container, false);
    }

    @Override
    public void createNew() {
        createNewUser();
    }

    private void createNewUser() {
        // TODO: 6/18/2016 create new user here
    }
}
