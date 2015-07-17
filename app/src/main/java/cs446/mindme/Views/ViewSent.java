package cs446.mindme.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import cs446.mindme.Adapters.ReminderListAdapter;
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
        System.out.println("creating sent");
        viewSent = this;
        final View rootView = inflater.inflate(R.layout.view_sent, container, false);
        expListView = (ExpandableListView) rootView.findViewById(R.id.sent_list);

        // prepareSampleData();
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
        // TODO: Remove toast later
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int prevPosition = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                /*Toast.makeText(rootView.getContext().getApplicationContext(),
                        reminderList.get(groupPosition).getMessage()
                                + " Expanded:" + groupPosition
                                + " Prev:" + prevPosition,
                        Toast.LENGTH_SHORT).show();*/
                if (prevPosition != groupPosition) {
                   /* Toast.makeText(rootView.getContext().getApplicationContext(),
                            reminderList.get(groupPosition).getMessage()
                                    *//*+ " prevCount:" + prevCount*//*
                                    + " currCount:" + listAdapter.getGroupCount(),
                            Toast.LENGTH_SHORT).show();*/
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
            @Override
            public void onGroupCollapse(int groupPosition) {
               /* Toast.makeText(rootView.getContext().getApplicationContext(),
                        reminderList.get(groupPosition).getMessage() + " Collapsed",
                        Toast.LENGTH_SHORT).show();*/
            }
        });

        // Note: Reminder actions are implemented in the adapter.
        // TODO: Remove if unnecessary.
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

        }
    }

   /* private void prepareSampleData() {
        reminderList = new ArrayList<ReminderDataHolder>();
        ReminderDataHolder r1 = new ReminderDataHolder(ReminderDataHolder.reminderType.SENT,
                "Message1", "Randy Cheung", "12:00", ReminderDataHolder.reminderStatus.ACTIVE);
        ReminderDataHolder r2 = new ReminderDataHolder(ReminderDataHolder.reminderType.SENT,
                "Message2", "Emily Na", "12:00", ReminderDataHolder.reminderStatus.COMPLETED);
        ReminderDataHolder r3 = new ReminderDataHolder(ReminderDataHolder.reminderType.SENT,
                "Message3", "Arthur Jen", "12:00", ReminderDataHolder.reminderStatus.DECLINED);
        ReminderDataHolder r4 = new ReminderDataHolder(ReminderDataHolder.reminderType.SENT,
                "Message4", "Richard Fa", "12:00", ReminderDataHolder.reminderStatus.ACTIVE);
        reminderList.add(r1);
        reminderList.add(r2);
        reminderList.add(r3);
        reminderList.add(r4);
    }*/
}
