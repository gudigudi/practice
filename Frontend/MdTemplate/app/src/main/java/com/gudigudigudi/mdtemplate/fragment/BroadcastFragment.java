package com.gudigudigudi.mdtemplate.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gudigudigudi.commonlib.base.BaseFragment;
import com.gudigudigudi.mdtemplate.R;
import com.gudigudigudi.mdtemplate.databinding.FragmentBroadcastBinding;

public class BroadcastFragment extends BaseFragment {

    private static final String TAG = "BroadcastFragment";

    private FragmentBroadcastBinding binding;

    private IntentFilter mIntentFilter;
    private NetworkChangeReceiver mNetworkChangeReceiver;

    private IntentFilter mIntentFilterLocal;
    private LocalReceiver mLocalReceiver;
    private LocalBroadcastManager mLocalBroadcastManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBroadcastBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        mNetworkChangeReceiver = new NetworkChangeReceiver();
        getActivity().registerReceiver(mNetworkChangeReceiver, mIntentFilter);

        binding.btnReplace.setOnClickListener(view1 -> {
            String intentStr = getResources().getString(R.string.CUSTOM_BROADCAST);
            Log.d(TAG, "send broadcast with intent " + intentStr);
            Intent intent = new Intent(intentStr);
            getActivity().sendBroadcast(intent);
        });

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        mIntentFilterLocal = new IntentFilter();
        mIntentFilterLocal.addAction(getString(R.string.LOCAL_BROADCAST));
        mLocalReceiver = new LocalReceiver();
        mLocalBroadcastManager.registerReceiver(mLocalReceiver, mIntentFilterLocal);

        binding.button2.setOnClickListener(view12 -> {
            Intent intent = new Intent(getString(R.string.LOCAL_BROADCAST));
            // send local broadcast.
            mLocalBroadcastManager.sendBroadcast(intent);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        getActivity().unregisterReceiver(mNetworkChangeReceiver);
        mLocalBroadcastManager.unregisterReceiver(mLocalReceiver);
    }

    /**
     * 网络检测广播接收器
     */
    class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (NetworkUtils.isConnected()) {
                ToastUtils.showShort("network is available");
            } else {
                ToastUtils.showShort("network is unavailable");
            }
        }
    }

    /**
     * 本地广播接收器
     */
    class LocalReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ToastUtils.showShort("received local broadcast");
        }
    }
}
