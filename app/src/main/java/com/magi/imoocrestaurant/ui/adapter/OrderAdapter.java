package com.magi.imoocrestaurant.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.magi.imoocrestaurant.R;
import com.magi.imoocrestaurant.bean.Order;
import com.magi.imoocrestaurant.config.Config;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderItemViewHolder> {

    private List<Order> mList;
    private Context mContext;

    public OrderAdapter(Context mContext, List<Order> mList) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_order_list,parent,false);
        return new OrderItemViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, int position) {
        Order order = mList.get(position);
        Glide.with(mContext)
                .load(Config.baseUrl+order.getRestaurant().getIcon())
                .placeholder(R.drawable.pictures_no)
                .into(holder.item_order_imageView);
        holder.item_order_name_textView.setText(order.getRestaurant().getName());
        holder.item_order_price_textView.setText("共消费"+order.getPrice()+"元");
        if (order.getPs().size()>0) {
            holder.item_order_description_textView
                    .setText(order.getPs().get(0).product.getName()+"等"+order.getCount()+"件商品");
        } else {
            holder.item_order_description_textView.setText("无消费");
        }
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    public class OrderItemViewHolder extends RecyclerView.ViewHolder{

        private ImageView item_order_imageView;
        private TextView item_order_name_textView,item_order_description_textView,item_order_price_textView;


        public OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: 2019/10/5
                }
            });

            item_order_imageView = itemView.findViewById(R.id.item_order_imageView);
            item_order_name_textView = itemView.findViewById(R.id.item_order_name_textView);
            item_order_description_textView = itemView.findViewById(R.id.item_order_description_textView);
            item_order_price_textView = itemView.findViewById(R.id.item_order_price_textView);
        }
    }
}
