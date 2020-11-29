package com.x.x17fun.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.x.x17fun.R;
import com.x.x17fun.custom.addressrlv.AZBaseAdapter;
import com.x.x17fun.custom.addressrlv.AZItemEntity;
import com.x.x17fun.utils.ResUtils;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter {

	public List<String> mList = new ArrayList<>();

	public ItemAdapter() {
		super();
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		return new ItemHolder(LayoutInflater.from(viewGroup.getContext()).
				inflate(R.layout.item_adapter, viewGroup,false));
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
		ItemHolder holder = (ItemHolder) viewHolder;
		holder.mTextName.setText(mList.get(position));
		String string = ResUtils.getString(R.string.digis);
		if (string.contains(mList.get(position))){

		}else{
			holder.itemView.setOnClickListener(v -> {
				if (onn!=null){
					onn.onClick(position);
				}
			});
		}

	}

	@Override
	public int getItemCount() {
		return mList.size() > 0 ? mList.size() : 0;
	}


	public void addNewData(List<String> date) {
		mList.addAll(date);
		notifyDataSetChanged();
	}

	static class ItemHolder extends RecyclerView.ViewHolder {

		TextView mTextName;

		ItemHolder(View itemView) {
			super(itemView);
			mTextName = itemView.findViewById(R.id.text_item_name);
		}
	}

	     private onItemClickListener onn;

	     public interface onItemClickListener{
	         void onClick(int position);
	     }
	     public void onClick(onItemClickListener listener){
	         onn = listener;
	     }
}