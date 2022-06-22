package com.example.exambooktest.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//这里不确定对不对
import com.example.exambooktest.QuestionFavoritesActivity;
import com.example.exambooktest.R;
import com.example.exambooktest.utils.Sort;

import java.util.List;

/**
 * 下拉抽屉的适配器
 */
public class BottomSheetAdapter extends RecyclerView.Adapter<BottomSheetAdapter.BottomViewHolder> {

    private static final String TAG = "BottomSheetAdapter";
    private List<Sort> sorts;
    private Context mContext;

    private int errorState;
    private int rightState;


    public BottomSheetAdapter(List<Sort> sorts, Context context) {


        this.sorts = sorts;
        this.mContext = context;

    }


    @NonNull
    @Override
    public BottomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.recyclerview_timu_item, null);

        return new BottomViewHolder(view);
    }

    //绑定数据 设置数值
    @Override
    public void onBindViewHolder(@NonNull BottomViewHolder holder, int position) {


        //题号数字
        holder.btn_sort.setText(sorts.get(position).getBtn());

    }

    @Override
    public int getItemCount() {
        return sorts == null ? 0 : sorts.size();
    }



    public class BottomViewHolder extends RecyclerView.ViewHolder  {
        private Button btn_sort;

        public BottomViewHolder(@NonNull View itemView)  {
            super(itemView);
            btn_sort = itemView.findViewById(R.id.btn_sort);

            //这里之前用的 itemView，set.... 有问题 过后仔细了解一下
            //recycler点击事件
            btn_sort.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {

                        mOnItemClickListener.onRecyclerItemClick(getBindingAdapterPosition());
                    }
                }
            });

        }


    }

    private OnRecyclerItemClickListener mOnItemClickListener;

    public void setRecyclerItemClick(OnRecyclerItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface OnRecyclerItemClickListener {
        void onRecyclerItemClick(int position);
    }






    private OnRecyclerBtnBackground mBackground;

    public interface OnRecyclerBtnBackground {
        void onRecyclerItemBackground(int drawable);
    }

    public void setRecyclerBtnBackground(OnRecyclerBtnBackground background) {
        mBackground = background;
    }
}


