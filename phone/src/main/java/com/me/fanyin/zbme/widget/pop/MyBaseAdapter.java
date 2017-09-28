package com.me.fanyin.zbme.widget.pop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
/**
 * Created by wyc
 */

public class MyBaseAdapter<T> extends RecyclerView.Adapter<MyBaseAdapter.BaseHolder> {

    protected Context context;
    protected List<T> data;
    protected HolderCallback callback;
    protected MyBaseAdapterOnItemClickLinstener linstener;

    public MyBaseAdapter(HolderCallback callback, List<T> data, Context context, MyBaseAdapterOnItemClickLinstener linstener){
        this.callback=callback;
        this.context=context;
        this.data=data;
        this.linstener=linstener;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return callback.getLayoutVeiw(context,parent,viewType,linstener);
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        callback.setData(holder,position,data.get(position));
    }

    @Override
    public int getItemCount() {
        return data!=null?data.size():0;
    }

    public static class BaseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private View itemView;
        private MyBaseAdapterOnItemClickLinstener linstener;
        public BaseHolder(View itemView) {
            super(itemView);
            this.itemView=itemView;
        }
        public BaseHolder(View itemView,MyBaseAdapterOnItemClickLinstener linstener){
            super(itemView);
            this.itemView=itemView;
            this.linstener=linstener;
        }

        public BaseHolder setText(int id,String text){
            View view = itemView.findViewById(id);
            if (view instanceof TextView){
                ((TextView)view).setText(text);
            }else if (view instanceof Button){
                ((Button)view).setText(text);
            }else{
                throw new RuntimeException("this view has no setText");
            }
            return this;
        }

        public BaseHolder setImageResouce(int id,int resouceId){
            ((ImageView)itemView.findViewById(id)).setImageResource(resouceId);
            return this;
        }
        
        public BaseHolder setViewEnable(int id,boolean enable,int colorId){
            ((TextView)itemView.findViewById(id)).setEnabled(enable);
            if (!enable){
                ((TextView)itemView.findViewById(id)).setTextColor(colorId);
            }
            return this;
        }

        public BaseHolder setTextColor(int id,int colorId){
            View view = itemView.findViewById(id);
            if (view instanceof TextView){
                ((TextView)view).setTextColor(colorId);
            }else if (view instanceof Button){
                ((Button)view).setTextColor(colorId);
            }else{
                throw new RuntimeException("this view has no setText");
            }
            return this;
        }

        public BaseHolder setBackgroundColor(int id, int colorId){
            itemView.findViewById(id).setBackgroundColor(colorId);
            return this;
        }
        public BaseHolder setBackground(int id, int colorId){
            itemView.findViewById(id).setBackgroundResource(colorId);
            return this;
        }

        public BaseHolder setVisible(int id,int visible){
            itemView.findViewById(id).setVisibility(visible);
            return this;
        }

        public BaseHolder setOnClickLinstener(int id,int position){

            if (linstener!=null){
                itemView.findViewById(id).setTag(position);
                itemView.findViewById(id).setOnClickListener(this);
            }

            return this;
        }

        @Override
        public void onClick(View v) {
            linstener.onItemClick(v,(int)v.getTag());
        }
    }

    public interface HolderCallback<T>{
        void setData(BaseHolder holder, int position, T data);
        BaseHolder getLayoutVeiw(Context context, ViewGroup parent, int viewType, MyBaseAdapterOnItemClickLinstener linstener);
    }

    public interface MyBaseAdapterOnItemClickLinstener{
        void onItemClick(View view, int position);
    }
}
