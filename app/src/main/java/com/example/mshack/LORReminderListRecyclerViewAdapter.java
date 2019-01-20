package com.example.mshack;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mshack.Utillities.ReminderNetworkUtils;

import java.util.List;

public class LORReminderListRecyclerViewAdapter extends RecyclerView.Adapter<LORReminderListRecyclerViewAdapter.ViewHolder>
{

    private final List<ReminderNetworkUtils.ReminderListResp.Remainder> mValues;
    //private final OnListFragmentInteractionListener mListener;
    String TAG = "ReminderListRecyclerViewAdapter";
    private Context context;

    public LORReminderListRecyclerViewAdapter(List<ReminderNetworkUtils.ReminderListResp.Remainder> items, Context context)//, OnListFragmentInteractionListener listener)
    {
        this.context = context;
        mValues = items;
        //mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_lorreminderlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        holder.mItem = mValues.get(position);
//        holder.mIdView.setText(mValues.get(position).getId());
        holder.mReminderTextView.setText(mValues.get(position).getText());
        holder.mReminderTitleView.setText(mValues.get(position).getTitle());
        holder.mReminderPlaceView.setText(mValues.get(position).getPlace());
        holder.mTimeView.setText(mValues.get(position).getCreatedAt());

        holder.mImgView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // do stuff
                Log.i("Test", holder.mItem.getTitle());
                new ReminderDeleteAsyncTask().execute("Nishanth", holder.mItem.getId());
                ((listOfRemindersActivity)context).finish();
                context.startActivity(((listOfRemindersActivity) context).getIntent());
            }

        });

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Log.d(TAG, "onClick: " + holder.mItem.getId());

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
        //        public final TextView mIdView;
        public final TextView mReminderTitleView;
        public final TextView mReminderTextView;
        public final TextView mReminderPlaceView;
        public final TextView mTimeView;
        public final ImageView mImgView;


        public ReminderNetworkUtils.ReminderListResp.Remainder mItem;


        public ViewHolder(View view)
        {
            super(view);
            mView = view;
//            mIdView = (TextView) view.findViewById(R.id.item_number);
            mReminderTitleView = (TextView) view.findViewById(R.id.ReminderTitle);
            mReminderTextView = (TextView) view.findViewById(R.id.ReminderText);
            mReminderPlaceView = (TextView) view.findViewById(R.id.ReminderPlace);
            mTimeView = (TextView) view.findViewById(R.id.time);
            mImgView = (ImageView) view.findViewById(R.id.close);
        }

        /*@Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }*/
    }
}