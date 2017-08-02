package rudiment.jsoupexample.fragment;

import android.Manifest;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jksiezni.permissive.PermissionsGrantedListener;
import com.github.jksiezni.permissive.PermissionsRefusedListener;
import com.github.jksiezni.permissive.Permissive;

import java.io.File;
import java.util.ArrayList;

import rudiment.jsoupexample.R;
import rudiment.jsoupexample.adapter.DownloadListAdapter;
import rudiment.jsoupexample.databinding.FragmentDownloadListBinding;

public class DownloadListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public DownloadListFragment() {
        // Required empty public constructor
    }

    private FragmentDownloadListBinding binding;

    public static DownloadListFragment newInstance() {
        return new DownloadListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_download_list, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getListOfFiles();

        binding.swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        getListOfFiles();
    }

    private void getListOfFiles() {
        new Permissive.Request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .whenPermissionsGranted(new PermissionsGrantedListener() {
                    @Override
                    public void onPermissionsGranted(String[] permissions) throws SecurityException {
                        // given permissions are granted
                        ArrayList<File> result = new ArrayList<>(); //ArrayList cause you don't know how many files there is
                        try {
                            File folder = new File(Environment.getExternalStorageDirectory() + "/Pictures/dJsonoup"); //This is just to cast to a File type since you pass it as a String
                            File[] filesInFolder = folder.listFiles(); // This returns all the folders and files in your path
                            for (File file : filesInFolder) { //For each of the entries do:
                                if (!file.isDirectory()) { //check that it's not a dir
                                    result.add(file); //push the filename as a string
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        setList(result);
                    }
                }).whenPermissionsRefused(new PermissionsRefusedListener() {
            @Override
            public void onPermissionsRefused(String[] permissions) {
                // given permissions are refused
            }
        }).execute(getActivity());
    }


    private void setList(ArrayList<File> files) {
        binding.swipeRefreshLayout.setRefreshing(false);
        binding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        DownloadListAdapter adapter = new DownloadListAdapter(getActivity(), files);
        binding.recyclerView.setAdapter(adapter);
    }
}
