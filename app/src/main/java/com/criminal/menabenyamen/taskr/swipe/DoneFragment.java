package com.criminal.menabenyamen.taskr.swipe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.criminal.menabenyamen.taskr.HttpService.ItemHttpRepository;
import com.criminal.menabenyamen.taskr.R;
import com.criminal.menabenyamen.taskr.adapter.WorkItemListAdapter;
import com.criminal.menabenyamen.taskr.internetservice.ConnectedToInternet;
import com.criminal.menabenyamen.taskr.model.WorkItem;
import com.criminal.menabenyamen.taskr.repository.ItemsReository;
import com.criminal.menabenyamen.taskr.sqlservice.SqlWorkItemRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DoneFragment extends Fragment {

    private RecyclerView recyclerView;
    private WorkItemListAdapter adapter;
    private ItemsReository workItemRepository;
    private ItemsReository sqlRepository;
    private CallBacks callBacks;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        workItemRepository = new ItemHttpRepository();
        sqlRepository = new SqlWorkItemRepository(getContext());
        return inflater.inflate(R.layout.fragment_done, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view_done);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);


        List<WorkItem> list = new ArrayList<>();


        if(ConnectedToInternet.isOnline(getContext())){

            HashSet<WorkItem> hashSet = new HashSet<>(workItemRepository.getWorkItemsWhihDone());

            for (WorkItem workItem : hashSet){
                if (workItem.getStatus().equals("DONE") &&
                        !sqlRepository.getWorkItemsWhihUnstarted().contains(workItem.getTitle())){
                    list.add(workItem);
                }

            }
            adapter = new WorkItemListAdapter(list, getContext(), new WorkItemListAdapter.onCLickResultListener() {
                @Override
                public void onClickResult(WorkItem workitem) {
                    callBacks.onListItemClicked(workitem);
                }
            }, new WorkItemListAdapter.onLongClickListener() {
                @Override
                public void onLongClickResult(WorkItem workItem) {
                    callBacks.onListItemLongClicked(workItem);
                }
            });
            adapter.notifyDataSetChanged();
            recyclerView.invalidate();
            recyclerView.setAdapter(adapter);


        } else {

            adapter = new WorkItemListAdapter(sqlRepository.getWorkItemsWhihDone(), getContext(), new WorkItemListAdapter.onCLickResultListener() {
                @Override
                public void onClickResult(WorkItem workitem) {
                    callBacks.onListItemClicked(workitem);
                }
            }, new WorkItemListAdapter.onLongClickListener() {
                @Override
                public void onLongClickResult(WorkItem workItem) {
                    callBacks.onListItemLongClicked(workItem);
                }
            });
            adapter.notifyDataSetChanged();
            recyclerView.invalidate();
            recyclerView.setAdapter(adapter);
        }




    }

    public interface CallBacks {
        void onListItemClicked(WorkItem workItem);
        void onListItemLongClicked(WorkItem workItem);
    }

}
