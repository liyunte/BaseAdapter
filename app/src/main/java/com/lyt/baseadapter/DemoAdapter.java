package com.lyt.baseadapter;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lyt.baseadapter.mylibrary.adapter.BaseQuickAdapter;
import com.lyt.baseadapter.mylibrary.adapter.BaseViewHolder;

import java.util.List;

public class DemoAdapter extends BaseQuickAdapter<DemoBean> {
    public DemoAdapter() {
        super(R.layout.app_demo_adapter_item, null);
    }

    @Override
    protected void convert(final BaseViewHolder holder, DemoBean bean) {
        Log.e("liyunte","convert 2parms="+holder.getPosition());
        holder.setText(R.id.tv_demo_adapter_name,bean.getName());
        holder.setText(R.id.tv_demo_adapter_age,String.valueOf(bean.getAge()));
        holder.convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("liyunte","点击了 position="+holder.getPosition());
            }
        });
    }

    @Override
    protected void convert(BaseViewHolder holder, DemoBean bean, List<Object> payloads) {
        if (payloads.get(0)!=null){
            Log.e("liyunte","数据不为空");
            DemoBean demoBean = (DemoBean) payloads.get(0);
            if (demoBean!=null){
                Log.e("liyunte","数据不为空"+demoBean.getName());

                if (!TextUtils.isEmpty(demoBean.getName())){
                    holder.setText(R.id.tv_demo_adapter_name,demoBean.getName());
                }
                if (demoBean.getAge()!=-1){
                    holder.setText(R.id.tv_demo_adapter_age,String.valueOf(demoBean.getAge()));
                }


            }
        }
    }

    @Override
    protected boolean areItemsTheSames(DemoBean oldBean, DemoBean newBean) {
       boolean areItemsTheSames = oldBean.getId()==newBean.getId();
        Log.e("liyunte","old.id="+oldBean.getId()+"new.id="+newBean.getId()+"areItemsTheSames = "+areItemsTheSames);
        return areItemsTheSames;
    }

    @Override
    protected boolean areContentsTheSames(DemoBean oldBean, DemoBean newBean) {
        boolean areContentsTheSames = oldBean.getId()==newBean.getId()&&oldBean.getAge()==newBean.getAge()&&oldBean.getName().equals(newBean.getName());
        Log.e("liyunte","old.id="+oldBean.getId()+"new.id="+newBean.getId()+"areContentsTheSames = "+areContentsTheSames);
        return areContentsTheSames;
    }

    @Override
    protected Object getChangePayloads(DemoBean oldBean, DemoBean newBean) {

        DemoBean demoBean = new DemoBean();
      if (oldBean.getName().equals(newBean.getName())){
          demoBean.setName("");
      }else {
          demoBean.setName(newBean.getName());
      }
      if (oldBean.getAge()==newBean.getAge()){
          demoBean.setAge(-1);
      }else {
          demoBean.setAge(newBean.getAge());
      }
      if (oldBean.getId()==newBean.getId()){
          demoBean.setId(-1);
      }else {
          demoBean.setId(newBean.getId());
      }
        return demoBean;
    }
}
