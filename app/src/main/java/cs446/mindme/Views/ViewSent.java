package cs446.mindme.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import cs446.mindme.Adapters.ReminderListAdapter;
import cs446.mindme.ConnectionData;
import cs446.mindme.R;
import cs446.mindme.DataHolders.ReminderDataHolder;
import cs446.mindme.SampleData;

public class ViewSent extends Fragment {

    public static ViewSent viewSent;
    public static ViewSent getViewSent() { return viewSent; }
    ReminderListAdapter listAdapter;
    ExpandableListView expListView;
    ArrayList<ReminderDataHolder> reminderList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("Creating Sent fragment.");
        viewSent = this;
        final View rootView = inflater.inflate(R.layout.view_sent, container, false);
        expListView = (ExpandableListView) rootView.findViewById(R.id.sent_list);

        reminderList = SampleData.getSentList();
        listAdapter = new ReminderListAdapter(rootView.getContext(), reminderList);
        expListView.setAdapter(listAdapter);

        // Must be false to enable child views
        // TODO: Figure out why???
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });

        // When reminder expanded, collapse the previous expanded reminder
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int prevPosition = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (prevPosition != groupPosition) {
                    // This may be executed even if position doesn't exit.
                    // Initially didn't work when removing reminders, had to compare with list count.
                    // TODO: Why?
                    expListView.collapseGroup(prevPosition);
                }
                prevPosition = groupPosition;
            }
        });

        // TODO: Remove if not needed
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override public void onGroupCollapse(int groupPosition) {}
        });

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                return true;
            }
        });

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (listAdapter != null && this.isVisible()) {
            listAdapter.notifyDataSetChanged();
        }
    }

    public void notifyDataSetChanged() {
        try {
            listAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            ConnectionData.showToast(e.getLocalizedMessage(), ConnectionData.callType.LOAD_REMINDERS);
        }
    }

}
