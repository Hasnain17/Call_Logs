package com.circularapk.project.calllogs;

import android.content.Intent;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.circularapk.project.calllogs.databinding.CallLogFragmentBinding;


public class OutgoingCallsFragment extends Fragment{
    CallLogFragmentBinding binding;
    CallLogAdapter adapter;
    CallLogAdapter.OnCallLogItemClickListener onItemClickListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),R.layout.call_log_fragment,container,false);
        initComponents();
        return binding.getRoot();
    }

    public void initComponents(){
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(mLayoutManager);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new CallLogAdapter(getContext());
        binding.recyclerView.setAdapter(adapter);
        loadData();
    }

    public void loadData(){
        CallLogUtils callLogUtils = CallLogUtils.getInstance(getContext());
        adapter.addAllCallLog(callLogUtils.getOutgoingCalls());
        adapter.notifyDataSetChanged();
        onItemClickListener = new CallLogAdapter.OnCallLogItemClickListener() {
            @Override
            public void onItemClicked(CallLogInfo callLogInfo) {
                Intent intent = new Intent(getContext(),StatisticsActivity.class);
                intent.putExtra("number",callLogInfo.getNumber());
                intent.putExtra("name",callLogInfo.getName());
                startActivity(intent);
            }
        };
        adapter.setOnItemClickListener(onItemClickListener);
    }
}
