package com.neelhpatel.bakingapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.neelhpatel.bakingapp.R;
import com.neelhpatel.bakingapp.model.StepInfo;

import java.util.List;

public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.ViewHolder>{

    private List<StepInfo> mStepInfos;
    private final StepsClickHandler mStepsClickHandler;

    public StepListAdapter(List<StepInfo> stepInfos, StepsClickHandler clickHandler) {
        mStepInfos = stepInfos;
        mStepsClickHandler = clickHandler;
    }

    public interface StepsClickHandler {
        void onClick(StepInfo stepInfo, int adapterPosition);
    }

    @NonNull
    @Override
    public StepListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.step_list_item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StepListAdapter.ViewHolder viewHolder, int i) {
        StepInfo stepInfo = mStepInfos.get(i);
        viewHolder.stepDescTv.setText(stepInfo.getShortDescription());
    }

    @Override
    public int getItemCount() {
        return mStepInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView stepDescTv;

        public ViewHolder(View itemView) {
            super(itemView);
            stepDescTv = itemView.findViewById(R.id.step_item_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mStepsClickHandler.onClick(mStepInfos.get(adapterPosition), adapterPosition);
        }
    }
}
