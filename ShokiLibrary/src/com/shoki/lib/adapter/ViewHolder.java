package com.shoki.lib.adapter;

import android.util.SparseArray;
import android.view.View;

/**
 * Component childview = ViewHolder.get(convertView, R.id.childviewId);
 * @author 이민석
 *
 */
public class ViewHolder {

	@SuppressWarnings("unchecked")
	public static <T extends View> T get(View view, int id) {
		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
		
		if(viewHolder == null) {
			viewHolder = new SparseArray<View>();
			view.setTag(viewHolder);
		}
		View childView = viewHolder.get(id);
		
		if(childView == null) {
			childView = view.findViewById(id);
			viewHolder.put(id, childView);
		}
		return (T) childView ;
	}
}
