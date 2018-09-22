package com.hojong.meokgol.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.hojong.meokgol.APIClient;
import com.hojong.meokgol.R;
import com.hojong.meokgol.activity.ShopReviewWriteActivity;
import com.hojong.meokgol.adapter.ShopReviewListAdapter;
import com.hojong.meokgol.data_model.Location;
import com.hojong.meokgol.data_model.Notice;
import com.hojong.meokgol.data_model.ShopReview;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class ShopReviewListFragment extends MyFragment {
    ListView listView;
    ShopReviewListAdapter adapter;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_shop_review_list, null);

		listView = rootView.findViewById(R.id.shop_review_list);
		adapter = new ShopReviewListAdapter();
		listView.setAdapter(adapter);

		ImageButton reviewWriteBtn = rootView.findViewById(R.id.review_write_btn);
		reviewWriteBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getContext(), ShopReviewWriteActivity.class);
				startActivityForResult(intent, RESULT_OK);
			}
		});

		return rootView;
	}

	private Callback<List<ShopReview>> callbackReviewList()
    {
        return new Callback<List<ShopReview>>() {
            @Override
            public void onResponse(Call<List<ShopReview>> call, Response<List<ShopReview>> response) {
                Log.d(this.toString(), "response "+response.body());
                adapter.clear();
                for (ShopReview notice : response.body())
                    adapter.addItem(notice);
                adapter.notifyDataSetChanged();
                showProgress(false);
            }

            @Override
            public void onFailure(Call<List<ShopReview>> call, Throwable t) {
                Log.d(this.toString(), "후기 가져오기 실패");
                if (getActivity() != null)
                    Toast.makeText(getContext(), "후기 가져오기 실패", Toast.LENGTH_SHORT).show();
                showProgress(false);
            }
        };
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    protected void showProgress(final boolean show)
    {
        if (getActivity() == null)
            return;

        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        listView.setVisibility(show ? View.GONE : View.VISIBLE);
        listView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                listView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    public void attemptData()
    {
        super.attemptData();
        APIClient.getService().listReview().enqueue(callbackReviewList());
    }
}