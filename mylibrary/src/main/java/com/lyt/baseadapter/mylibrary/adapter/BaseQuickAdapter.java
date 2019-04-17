package com.lyt.baseadapter.mylibrary.adapter;

import android.view.View;

import java.util.List;

public abstract class BaseQuickAdapter<T> extends BaseAdapter<T> {

    public BaseQuickAdapter(int layoutResId, List<T> data) {
        super(layoutResId, data);
    }

    public BaseQuickAdapter(View contentView, List<T> data) {
        super(contentView, data);
    }

    public BaseQuickAdapter(List<T> data) {
        super(data);
    }


    /**
     * 正常数据
     * @param holder
     * @param bean
     */
    @Override
    protected void convert(BaseViewHolder holder, T bean) {

    }

    /**
     * 局部更新 不用做点击事件
     * @param holder
     * @param bean
     * @param payloads
     */
    @Override
    protected void convert(BaseViewHolder holder, T bean, List<Object> payloads) {

    }

    /**
     * 比较新旧item是否相同 建议比较两个Bean的id是否相同
     * @param oldBean
     * @param newBean
     * @return
     */
    @Override
    protected boolean areItemsTheSames(T oldBean, T newBean) {
        return false;
    }

    /**
     * 比较新旧item的内容是否相同 建议比较展示出来bean的内容
     * @param oldBean
     * @param newBean
     * @return
     */
    @Override
    protected boolean areContentsTheSames(T oldBean, T newBean) {
        return false;
    }

    /**
     * item局部刷新控件
     * 当 areItemsTheSame==true  areContentsTheSame==false 才会走此方法
     * @param oldBean
     * @param newBean
     * @return
     */
    @Override
    protected Object getChangePayloads(T oldBean, T newBean) {
        return null;
    }
}
