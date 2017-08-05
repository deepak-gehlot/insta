package rudiment.jsoupexample.fragment;

import android.Manifest;
import android.content.Intent;
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
import android.widget.Toast;

import com.github.jksiezni.permissive.PermissionsGrantedListener;
import com.github.jksiezni.permissive.PermissionsRefusedListener;
import com.github.jksiezni.permissive.Permissive;
import com.google.android.gms.ads.AdRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import rudiment.jsoupexample.PhotoFileterActivity2;
import rudiment.jsoupexample.R;
import rudiment.jsoupexample.adapter.DownloadListAdapter;
import rudiment.jsoupexample.databinding.FragmentDownloadListBinding;
import rudiment.jsoupexample.util.Constant;

import static android.os.Environment.DIRECTORY_PICTURES;
import static org.apache.commons.io.comparator.LastModifiedFileComparator.LASTMODIFIED_REVERSE;

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

        binding.swipeRefreshLayout.setOnRefreshListener(this);
        binding.fabOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.faboptions_camera:
                        openCamera();
                        break;
                    case R.id.faboptions_gallery:
                        openGallery();
                        break;
                }
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);
    }

    @Override
    public void onRefresh() {
        getListOfFiles();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getView() != null) {
            getListOfFiles();
        }
    }

    private void getListOfFiles() {
        new Permissive.Request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .whenPermissionsGranted(new PermissionsGrantedListener() {
                    @Override
                    public void onPermissionsGranted(String[] permissions) throws SecurityException {
                        // given permissions are granted
                        ArrayList<File> result = new ArrayList<>(); //ArrayList cause you don't know how many files there is
                        try {
                            File folder = new File(Environment.getExternalStorageDirectory() + File.separator + DIRECTORY_PICTURES + File.separator + Constant.FOLDER_NAME); //This is just to cast to a File type since you pass it as a String
                            File[] filesInFolder = folder.listFiles(); // This returns all the folders and files in your path
                            Arrays.sort(filesInFolder, LASTMODIFIED_REVERSE);
                            for (File file : filesInFolder) { //For each of the entries do:
                                if (!file.isDirectory()) { //check that it's not a dir
                                    result.add(file); //push the filename as a string
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        result.add(null);
                        setList(result);
                    }
                }).whenPermissionsRefused(new PermissionsRefusedListener() {
            @Override
            public void onPermissionsRefused(String[] permissions) {
                // given permissions are refused
                Toast.makeText(getActivity(), "App Required permission.", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        }).execute(getActivity());
    }


    private void setList(ArrayList<File> files) {
        binding.swipeRefreshLayout.setRefreshing(false);
        binding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        DownloadListAdapter adapter = new DownloadListAdapter(getActivity(), files);
        binding.recyclerView.setAdapter(adapter);
        if (files.size() == 1) {
            binding.messageTxt.setVisibility(View.VISIBLE);
        }
    }


    private void openCamera() {
        new Permissive.Request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .whenPermissionsGranted(new PermissionsGrantedListener() {
                    @Override
                    public void onPermissionsGranted(String[] permissions) throws SecurityException {
                        // given permissions are granted
                        EasyImage.openCamera(DownloadListFragment.this, 1);
                    }
                }).whenPermissionsRefused(new PermissionsRefusedListener() {
            @Override
            public void onPermissionsRefused(String[] permissions) {
                // given permissions are refused
            }
        }).execute(getActivity());
    }

    private void openGallery() {
        new Permissive.Request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .whenPermissionsGranted(new PermissionsGrantedListener() {
                    @Override
                    public void onPermissionsGranted(String[] permissions) throws SecurityException {
                        // given permissions are granted
                        EasyImage.openGallery(DownloadListFragment.this, 1);
                    }
                }).whenPermissionsRefused(new PermissionsRefusedListener() {
            @Override
            public void onPermissionsRefused(String[] permissions) {
                // given permissions are refused
            }
        }).execute(getActivity());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onImagePicked(final File imageFile, EasyImage.ImageSource source, int type) {
                startActivity(new Intent(getActivity(), PhotoFileterActivity2.class).putExtra("file", imageFile));
            }
        });
    }
}
