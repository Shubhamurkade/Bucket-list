package com.example.mshack;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//import com.example.mshack.LORReminderListFragment.OnListFragmentInteractionListener;
//import com.example.mshack.dummy.DummyContent;
import com.example.mshack.dummy.ReminderItemContent.ReminderItem;
import com.example.mshack.dummy.ReminderItemContent;

import java.util.List;

public class LORReminderListRecyclerViewAdapter extends RecyclerView.Adapter<LORReminderListRecyclerViewAdapter.ViewHolder>
{

    private final List<ReminderItem> mValues;
    //private final OnListFragmentInteractionListener mListener;
    String TAG = "ReminderListRecyclerViewAdapter";

    public LORReminderListRecyclerViewAdapter(List<ReminderItem> items)//, OnListFragmentInteractionListener listener)
    {
        mValues = items;
        //mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_lorreminderlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mReminderTitleView.setText(mValues.get(position).reminderTitle);
        holder.mReminderPlaceView.setText(mValues.get(position).place);
        holder.mTimeView.setText(mValues.get(position).timeStamp);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Log.d(TAG, "onClick: " + holder.mItem.id);

                /*if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public final View mView;
        public final TextView mIdView;
        public final TextView mReminderTitleView;
        public final TextView mReminderTextView;
        public final TextView mReminderPlaceView;
        public final TextView mTimeView;


        public ReminderItem mItem;


        public ViewHolder(View view)
        {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mReminderTitleView = (TextView) view.findViewById(R.id.ReminderTitle);
            mReminderTextView = (TextView) view.findViewById(R.id.ReminderText);
            mReminderPlaceView = (TextView) view.findViewById(R.id.ReminderPlace);
            mTimeView = (TextView) view.findViewById(R.id.time);
        }

        /*@Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }*/
    }
}
