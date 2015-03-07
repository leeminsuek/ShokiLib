package com.shoki.lib.util;


import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.shoki.lib.material.MaterialDialog;

public class ShokiLibUtilManager {
	
	private static ShokiLibUtilManager INSTANCE;
	
	/**
	 * 싱글톤 클래스
	 * @return
	 */
	public static ShokiLibUtilManager getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new ShokiLibUtilManager();
		}
		return INSTANCE;
	}
	

	/**
	 * 다이알로그 callback 
	 * @author 이민석
	 *
	 */
	public static interface onDialogClick {
		/**
		 * 확인버튼 callback
		 */
		public void setOnDialogoskclick();
		/**
		 * 취소버튼 callback
		 */
		public void setOnDialogoskCancleclick();
	}
	
	/**
	 * 다이알로그(list 형식) callback(single)
	 * @author 이민석
	 *
	 */
	public static interface onDialogChoice {
		/**
		 * 아이템선택 callback(which = index)
		 * @param which
		 */
		public void setOnDialogSingleChoice(int which);
	}

	public onDialogClick mOnDialogClick;
	public onDialogChoice mOnDialogChoice;

	MaterialDialog mMaterialDialog;
	
	
	
	/**
	 * 키보드 숨기기
	 * @param context
	 * @param p1
	 */
	public void InputKeybordHidden(Context context , EditText edit){
		final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
	}
	
	
	
	/**
	 * 리스트 다이알로그
	 * @param context
	 * @param arr
	 * @param title
	 */
	public void showDialog(Context context, ArrayList<String> arr, String title, onDialogChoice listener) { 
		mOnDialogChoice = listener;
		final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
				context,
				android.R.layout.simple_list_item_1
				);

		for(int i = 0 ; i < arr.size() ; i ++) {
			arrayAdapter.add(arr.get(i)); 
		}
		ListView listView = new ListView(context);
		listView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		float scale = context.getResources().getDisplayMetrics().density;
		int dpAsPixels = (int) (8 * scale + 0.5f);
		listView.setPadding(0, dpAsPixels, 0, dpAsPixels);
		listView.setDividerHeight(0);
		listView.setAdapter(arrayAdapter);
		listView.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				mOnDialogChoice.setOnDialogSingleChoice(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		final MaterialDialog alert = new MaterialDialog(context)
		.setTitle(title)
		.setContentView(listView);

		alert.setPositiveButton(
				"OK", new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						alert.dismiss();
					}
				}
				);

		
		alert.show();
	}
	

	/**
	 * 제목 메세지 확인,취소 버튼
	 * @param context
	 * @param title
	 * @param message
	 * @param listener
	 */
	public void showDialog(final Context context, String title, String message, onDialogClick listener) {
		mOnDialogClick =  listener;
		
		mMaterialDialog = new MaterialDialog(context);
		mMaterialDialog.setTitle(title)
        .setMessage(message)
        .setPositiveButton(
            "OK", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMaterialDialog.dismiss();
                    mOnDialogClick.setOnDialogoskclick();

                }
            }
        )
        .setNegativeButton(
            "CANCLE", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMaterialDialog.dismiss();
                    mOnDialogClick.setOnDialogoskCancleclick();
                }
            }
        )
        .setCanceledOnTouchOutside(false)
//        .setOnDismissListener(listener)
        .show();
	}
	
	/**
	 * 확인버튼 다이알로그(타이틀x)
	 * @param context
	 * @param message
	 */
	public void showDialog(Context context, String message, onDialogClick listener) {
		mOnDialogClick = listener;
		
		final MaterialDialog materialDialog = new MaterialDialog(context);
		materialDialog.setMessage(message)
		.setPositiveButton(android.R.string.yes, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				materialDialog.dismiss();
				if(mOnDialogClick != null)
					mOnDialogClick.setOnDialogoskclick();
			}
		});
		materialDialog.show();
	}
}
