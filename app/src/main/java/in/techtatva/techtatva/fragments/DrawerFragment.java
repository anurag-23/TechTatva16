package in.techtatva.techtatva.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import in.techtatva.techtatva.R;
import in.techtatva.techtatva.adapters.DrawerAdapter;
import in.techtatva.techtatva.models.DrawerModel;

public class DrawerFragment extends Fragment {

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    public DrawerFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_drawer, container, false);

        RecyclerView drawerRecyclerView = (RecyclerView) rootView.findViewById(R.id.drawer_recycler_view);
        DrawerAdapter adapter = new DrawerAdapter(getActivity(), getDrawerList(), mDrawerLayout);
        drawerRecyclerView.setAdapter(adapter);
        drawerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;

    }

    public List<DrawerModel> getDrawerList() {

        int[] icons = {R.drawable.drawer_favourites, R.drawable.drawer_online, R.drawable.drawer_results, R.drawable.drawer_register,  R.drawable.drawer_instagram, R.drawable.drawer_developers, R.drawable.drawer_about};
        int[] text = {R.string.drawer_favourites, R.string.drawer_online, R.string.drawer_results, R.string.drawer_register,  R.string.drawer_insta, R.string.drawer_developers, R.string.drawer_about};

        List<DrawerModel> list = new ArrayList<>();


        for (int i = 0; i < icons.length; i++) {
            DrawerModel model = new DrawerModel();
            model.setIcon(icons[i]);
            model.setText(text[i]);

            list.add(model);
        }

        return list;

    }


    public void setUp(DrawerLayout mDrawerLayout, Toolbar toolbar) {

        this.mDrawerLayout = mDrawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }

        };

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

}
