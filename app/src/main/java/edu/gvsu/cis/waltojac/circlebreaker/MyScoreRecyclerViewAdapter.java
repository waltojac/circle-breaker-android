package edu.gvsu.cis.waltojac.circlebreaker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.gvsu.cis.waltojac.circlebreaker.ScoreFragment.OnListFragmentInteractionListener;
import edu.gvsu.cis.waltojac.circlebreaker.dummy.ScoreContent.ScoreItem;

import java.util.List;

public class MyScoreRecyclerViewAdapter extends RecyclerView.Adapter<MyScoreRecyclerViewAdapter.ViewHolder> {

    private final List<ScoreItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyScoreRecyclerViewAdapter(List<ScoreItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_score, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mPlace.setText(mValues.get(position).id);
        holder.mUsername.setText(mValues.get(position).username);
        holder.mLevel.setText(mValues.get(position).level);


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mPlace;
        public final TextView mUsername;
        public final TextView mLevel;
        public ScoreItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mPlace = (TextView) view.findViewById(R.id.place);
            mUsername = (TextView) view.findViewById(R.id.username);
            mLevel = (TextView) view.findViewById(R.id.level);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mUsername.getText() + "'";
        }
    }
}
