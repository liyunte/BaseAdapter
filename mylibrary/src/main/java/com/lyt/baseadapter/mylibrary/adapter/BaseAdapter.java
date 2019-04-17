//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.lyt.baseadapter.mylibrary.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.GridLayoutManager.SpanSizeLookup;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.StaggeredGridLayoutManager.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import com.lyt.baseadapter.mylibrary.R;
import com.lyt.baseadapter.mylibrary.animation.AlphaInAnimation;
import com.lyt.baseadapter.mylibrary.animation.ScaleInAnimation;
import com.lyt.baseadapter.mylibrary.animation.SlideInBottomAnimation;
import com.lyt.baseadapter.mylibrary.animation.SlideInLeftAnimation;
import com.lyt.baseadapter.mylibrary.animation.SlideInRightAnimation;
import com.lyt.baseadapter.mylibrary.animation.BaseAnimation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T> extends Adapter<ViewHolder> {
    private boolean mNextLoadEnable;
    private boolean mLoadingMoreEnable;
    private boolean mFirstOnlyEnable;
    private boolean mOpenAnimationEnable;
    private boolean mEmptyEnable;
    private boolean mHeadAndEmptyEnable;
    private boolean mFootAndEmptyEnable;
    private Interpolator mInterpolator;
    private int mDuration;
    private int mLastPosition;
    private BaseAdapter.OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    private BaseAdapter.OnRecyclerViewItemLongClickListener onRecyclerViewItemLongClickListener;
    private BaseAdapter.RequestLoadMoreListener mRequestLoadMoreListener;
    private BaseAnimation mCustomAnimation;
    private BaseAnimation mSelectAnimation;
    private LinearLayout mHeaderLayout;
    private LinearLayout mFooterLayout;
    private LinearLayout mCopyHeaderLayout;
    private LinearLayout mCopyFooterLayout;
    private int pageSize;
    private View mContentView;
    private View mEmptyView;
    protected static final String TAG = BaseAdapter.class.getSimpleName();
    protected Context mContext;
    protected int mLayoutResId;
    protected LayoutInflater mLayoutInflater;
    protected List<T> mData;
    public static final int HEADER_VIEW = 273;
    public static final int LOADING_VIEW = 546;
    public static final int FOOTER_VIEW = 819;
    public static final int EMPTY_VIEW = 1365;
    private View mLoadingView;
    public static final int ALPHAIN = 1;
    public static final int SCALEIN = 2;
    public static final int SLIDEIN_BOTTOM = 3;
    public static final int SLIDEIN_LEFT = 4;
    public static final int SLIDEIN_RIGHT = 5;
    private BaseAdapter.OnRecyclerViewItemChildClickListener mChildClickListener;
    private BaseAdapter.OnRecyclerViewItemChildLongClickListener mChildLongClickListener;
    private DiffCallback diffCallback = new DiffCallback();

    /** @deprecated */
    @Deprecated
    public void setOnLoadMoreListener(int pageSize, BaseAdapter.RequestLoadMoreListener requestLoadMoreListener) {
        this.setOnLoadMoreListener(requestLoadMoreListener);
    }

    public void setOnLoadMoreListener(BaseAdapter.RequestLoadMoreListener requestLoadMoreListener) {
        this.mRequestLoadMoreListener = requestLoadMoreListener;
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    public void openLoadMore(int pageSize, boolean enable) {
        this.pageSize = pageSize;
        this.mNextLoadEnable = enable;
    }

    public void openLoadMore(boolean enable) {
        this.mNextLoadEnable = enable;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setOnRecyclerViewItemClickListener(BaseAdapter.OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    public void setOnRecyclerViewItemLongClickListener(BaseAdapter.OnRecyclerViewItemLongClickListener onRecyclerViewItemLongClickListener) {
        this.onRecyclerViewItemLongClickListener = onRecyclerViewItemLongClickListener;
    }

    public void setOnRecyclerViewItemChildClickListener(BaseAdapter.OnRecyclerViewItemChildClickListener childClickListener) {
        this.mChildClickListener = childClickListener;
    }

    public void setOnRecyclerViewItemChildLongClickListener(BaseAdapter.OnRecyclerViewItemChildLongClickListener childLongClickListener) {
        this.mChildLongClickListener = childLongClickListener;
    }

    public BaseAdapter(int layoutResId, List<T> data) {
        this.mNextLoadEnable = false;
        this.mLoadingMoreEnable = false;
        this.mFirstOnlyEnable = true;
        this.mOpenAnimationEnable = false;
        this.mInterpolator = new LinearInterpolator();
        this.mDuration = 300;
        this.mLastPosition = -1;
        this.mSelectAnimation = new AlphaInAnimation();
        this.mCopyHeaderLayout = null;
        this.mCopyFooterLayout = null;
        this.pageSize = -1;
        this.mData = (List)(data == null?new ArrayList():data);
        if(layoutResId != 0) {
            this.mLayoutResId = layoutResId;
        }

    }

    public BaseAdapter(List<T> data) {
        this(0, data);
    }

    public BaseAdapter(View contentView, List<T> data) {
        this(0, data);
        this.mContentView = contentView;
    }

    public void setNewData(List<T> data) {
        diffCallback.setOldList(mData);
        diffCallback.setNewList(data);
        if(this.mRequestLoadMoreListener != null) {
            this.mNextLoadEnable = true;
        }
        this.mLastPosition = -1;
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(diffCallback, true);
        this.mData = new ArrayList<>(data);
        result.dispatchUpdatesTo(this);
    }
    public void addData(List<T> data) {
        diffCallback.setOldList(mData);
        List<T> newdata = new ArrayList<>(mData);
        newdata.addAll(data);
        diffCallback.setNewList(newdata);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(diffCallback, true);
        this.mData = new ArrayList<>(newdata);
        result.dispatchUpdatesTo(this);
    }

    public void setLoadingView(View loadingView) {
        this.mLoadingView = loadingView;
    }

    public List<T> getData() {
        return this.mData;
    }

    public T getItem(int position) {
        return this.mData.get(position);
    }

    /** @deprecated */
    @Deprecated
    public int getHeaderViewsCount() {
        return this.mHeaderLayout == null?0:1;
    }

    /** @deprecated */
    @Deprecated
    public int getFooterViewsCount() {
        return this.mFooterLayout == null?0:1;
    }

    public int getHeaderLayoutCount() {
        return this.mHeaderLayout == null?0:1;
    }

    public int getFooterLayoutCount() {
        return this.mFooterLayout == null?0:1;
    }

    public int getmEmptyViewCount() {
        return this.mEmptyView == null?0:1;
    }

    public int getItemCount() {
        int i = this.isLoadMore()?1:0;
        int count = this.mData.size() + i + this.getHeaderLayoutCount() + this.getFooterLayoutCount();
        if(this.mData.size() == 0 && this.mEmptyView != null) {
            if(count == 0 && (!this.mHeadAndEmptyEnable || !this.mFootAndEmptyEnable)) {
                count += this.getmEmptyViewCount();
            } else if(this.mHeadAndEmptyEnable || this.mFootAndEmptyEnable) {
                count += this.getmEmptyViewCount();
            }

            if(this.mHeadAndEmptyEnable && this.getHeaderLayoutCount() == 1 && count == 1 || count == 0) {
                this.mEmptyEnable = true;
                count += this.getmEmptyViewCount();
            }
        }

        return count;
    }

    public int getItemViewType(int position) {
        if(this.mHeaderLayout != null && position == 0) {
            return 273;
        } else {
            if(this.mData.size() == 0 && this.mEmptyEnable && this.mEmptyView != null && position <= 2) {
                if((this.mHeadAndEmptyEnable || this.mFootAndEmptyEnable) && position == 1) {
                    if(this.mHeaderLayout == null && this.mEmptyView != null && this.mFooterLayout != null) {
                        return 819;
                    }

                    if(this.mHeaderLayout != null && this.mEmptyView != null) {
                        return 1365;
                    }
                } else if(position == 0) {
                    if(this.mHeaderLayout == null) {
                        return 1365;
                    }

                    if(this.mFooterLayout != null) {
                        return 1365;
                    }
                } else {
                    if(position == 2 && (this.mFootAndEmptyEnable || this.mHeadAndEmptyEnable) && this.mHeaderLayout != null && this.mEmptyView != null) {
                        return 819;
                    }

                    if((!this.mFootAndEmptyEnable || !this.mHeadAndEmptyEnable) && position == 1 && this.mFooterLayout != null) {
                        return 819;
                    }
                }
            } else {
                if(this.mData.size() == 0 && this.mEmptyView != null && this.getItemCount() == (this.mHeadAndEmptyEnable?2:1) && this.mEmptyEnable) {
                    return 1365;
                }

                if(position == this.mData.size() + this.getHeaderLayoutCount()) {
                    if(this.mNextLoadEnable) {
                        return 546;
                    }

                    return 819;
                }

                if(position > this.mData.size() + this.getHeaderLayoutCount()) {
                    return 819;
                }
            }

            return this.getDefItemViewType(position - this.getHeaderLayoutCount());
        }
    }

    protected int getDefItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder baseViewHolder = null;
        this.mContext = parent.getContext();
        this.mLayoutInflater = LayoutInflater.from(this.mContext);
        switch(viewType) {
            case 273:
                baseViewHolder = new BaseViewHolder(this.mHeaderLayout);
                break;
            case 546:
                baseViewHolder = this.getLoadingView(parent);
                break;
            case 819:
                baseViewHolder = new BaseViewHolder(this.mFooterLayout);
                break;
            case 1365:
                baseViewHolder = new BaseViewHolder(this.mEmptyView);
                break;
            default:
                baseViewHolder = this.onCreateDefViewHolder(parent, viewType);
                this.initItemClickListener(baseViewHolder);
        }

        return baseViewHolder;
    }

    private BaseViewHolder getLoadingView(ViewGroup parent) {
        return this.mLoadingView == null?this.createBaseViewHolder(parent, R.layout.def_loading):new BaseViewHolder(this.mLoadingView);
    }

    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int type = holder.getItemViewType();
        if(type != 1365 && type != 273 && type != 819 && type != 546) {
            this.addAnimation(holder);
        } else {
            this.setFullSpan(holder);
        }

    }

    protected void setFullSpan(ViewHolder holder) {
        if(holder.itemView.getLayoutParams() instanceof LayoutParams) {
            LayoutParams params = (LayoutParams)holder.itemView.getLayoutParams();
            params.setFullSpan(true);
        }

    }

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = (GridLayoutManager)manager;
            gridManager.setSpanSizeLookup(new SpanSizeLookup() {
                public int getSpanSize(int position) {
                    int type = BaseAdapter.this.getItemViewType(position);
                    return type != 1365 && type != 273 && type != 819 && type != 546?1:gridManager.getSpanCount();
                }
            });
        }

    }

    public void onBindViewHolder(ViewHolder holder, int positions) {
        int viewType = holder.getItemViewType();
        switch(viewType) {
            case 0:
                this.convert((BaseViewHolder)holder, this.mData.get(holder.getLayoutPosition() - this.getHeaderLayoutCount()));
            case 273:
            case 819:
            case 1365:
                break;
            case 546:
                this.addLoadMore(holder);
                break;
            default:
                this.convert((BaseViewHolder)holder, this.mData.get(holder.getLayoutPosition() - this.getHeaderLayoutCount()));
                this.onBindDefViewHolder((BaseViewHolder)holder, this.mData.get(holder.getLayoutPosition() - this.getHeaderLayoutCount()));
        }

    }


    public void onBindViewHolder(ViewHolder holder, int positions,List<Object> payloads) {
        if (payloads.isEmpty()){
            onBindViewHolder(holder,positions);
        }else {
            int viewType = holder.getItemViewType();
            switch(viewType) {
                case 0:
                    this.convert((BaseViewHolder)holder, this.mData.get(holder.getLayoutPosition() - this.getHeaderLayoutCount()),payloads);
                case 273:
                case 819:
                case 1365:
                    break;
                case 546:
                    this.addLoadMore(holder);
                    break;
                default:
                    this.convert((BaseViewHolder)holder, this.mData.get(holder.getLayoutPosition() - this.getHeaderLayoutCount()),payloads);
                    this.onBindDefViewHolder((BaseViewHolder)holder, this.mData.get(holder.getLayoutPosition() - this.getHeaderLayoutCount()));
            }
        }


    }


    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return this.createBaseViewHolder(parent, this.mLayoutResId);
    }

    protected BaseViewHolder createBaseViewHolder(ViewGroup parent, int layoutResId) {
        return this.mContentView == null?new BaseViewHolder(this.getItemView(layoutResId, parent)):new BaseViewHolder(this.mContentView);
    }

    public LinearLayout getHeaderLayout() {
        return this.mHeaderLayout;
    }

    public LinearLayout getFooterLayout() {
        return this.mFooterLayout;
    }

    public void addHeaderView(View header) {
        this.addHeaderView(header, -1);
    }

    public void addHeaderView(View header, int index) {
        if(this.mHeaderLayout == null) {
            if(this.mCopyHeaderLayout == null) {
                this.mHeaderLayout = new LinearLayout(header.getContext());
                this.mHeaderLayout.setOrientation(LinearLayout.VERTICAL);
                this.mHeaderLayout.setLayoutParams(new android.support.v7.widget.RecyclerView.LayoutParams(-1, -2));
                this.mCopyHeaderLayout = this.mHeaderLayout;
            } else {
                this.mHeaderLayout = this.mCopyHeaderLayout;
            }
        }

        index = index >= this.mHeaderLayout.getChildCount()?-1:index;
        this.mHeaderLayout.addView(header, index);
        this.notifyDataSetChanged();
    }

    public void addFooterView(View footer) {
        this.addFooterView(footer, -1);
    }

    public void addFooterView(View footer, int index) {
        this.mNextLoadEnable = false;
        if(this.mFooterLayout == null) {
            if(this.mCopyFooterLayout == null) {
                this.mFooterLayout = new LinearLayout(footer.getContext());
                this.mFooterLayout.setOrientation(LinearLayout.VERTICAL);
                this.mFooterLayout.setLayoutParams(new android.support.v7.widget.RecyclerView.LayoutParams(-1, -2));
                this.mCopyFooterLayout = this.mFooterLayout;
            } else {
                this.mFooterLayout = this.mCopyFooterLayout;
            }
        }

        index = index >= this.mFooterLayout.getChildCount()?-1:index;
        this.mFooterLayout.addView(footer, index);
        this.notifyDataSetChanged();
    }

    public void removeHeaderView(View header) {
        if(this.mHeaderLayout != null) {
            this.mHeaderLayout.removeView(header);
            if(this.mHeaderLayout.getChildCount() == 0) {
                this.mHeaderLayout = null;
            }

            this.notifyDataSetChanged();
        }
    }

    public void removeFooterView(View footer) {
        if(this.mFooterLayout != null) {
            this.mFooterLayout.removeView(footer);
            if(this.mFooterLayout.getChildCount() == 0) {
                this.mFooterLayout = null;
            }

            this.notifyDataSetChanged();
        }
    }

    public void removeAllHeaderView() {
        if(this.mHeaderLayout != null) {
            this.mHeaderLayout.removeAllViews();
            this.mHeaderLayout = null;
        }
    }

    public void removeAllFooterView() {
        if(this.mFooterLayout != null) {
            this.mFooterLayout.removeAllViews();
            this.mFooterLayout = null;
        }
    }

    public void setEmptyView(View emptyView) {
        this.setEmptyView(false, false, emptyView);
    }

    public void setEmptyView(boolean isHeadAndEmpty, View emptyView) {
        this.setEmptyView(isHeadAndEmpty, false, emptyView);
    }

    public void setEmptyView(boolean isHeadAndEmpty, boolean isFootAndEmpty, View emptyView) {
        this.mHeadAndEmptyEnable = isHeadAndEmpty;
        this.mFootAndEmptyEnable = isFootAndEmpty;
        this.mEmptyView = emptyView;
        this.mEmptyEnable = true;
    }

    public View getEmptyView() {
        return this.mEmptyView;
    }

    /** @deprecated */
    @Deprecated
    public void isNextLoad(boolean isNextLoad) {
        this.mNextLoadEnable = isNextLoad;
        this.mLoadingMoreEnable = false;
        this.notifyDataSetChanged();
    }

    public void notifyDataChangedAfterLoadMore(boolean isNextLoad) {
        this.mNextLoadEnable = isNextLoad;
        this.mLoadingMoreEnable = false;
        this.notifyDataSetChanged();
    }

    public void notifyDataChangedAfterLoadMore(List<T> data, boolean isNextLoad) {
        this.mData.addAll(data);
        this.notifyDataChangedAfterLoadMore(isNextLoad);
    }

    private void addLoadMore(ViewHolder holder) {
        if(this.isLoadMore() && !this.mLoadingMoreEnable) {
            this.mLoadingMoreEnable = true;
            this.mRequestLoadMoreListener.onLoadMoreRequested();
        }

    }

    private void initItemClickListener(final BaseViewHolder baseViewHolder) {
        if(this.onRecyclerViewItemClickListener != null) {
            baseViewHolder.itemView.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    BaseAdapter.this.onRecyclerViewItemClickListener.onItemClick(v, baseViewHolder.getLayoutPosition() - BaseAdapter.this.getHeaderLayoutCount());
                }
            });
        }

        if(this.onRecyclerViewItemLongClickListener != null) {
            baseViewHolder.itemView.setOnLongClickListener(new OnLongClickListener() {
                public boolean onLongClick(View v) {
                    return BaseAdapter.this.onRecyclerViewItemLongClickListener.onItemLongClick(v, baseViewHolder.getLayoutPosition() - BaseAdapter.this.getHeaderLayoutCount());
                }
            });
        }

    }

    private void addAnimation(ViewHolder holder) {
        if(this.mOpenAnimationEnable && (!this.mFirstOnlyEnable || holder.getLayoutPosition() > this.mLastPosition)) {
            BaseAnimation animation = null;
            if(this.mCustomAnimation != null) {
                animation = this.mCustomAnimation;
            } else {
                animation = this.mSelectAnimation;
            }

            Animator[] var3 = animation.getAnimators(holder.itemView);
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Animator anim = var3[var5];
                this.startAnim(anim, holder.getLayoutPosition());
            }

            this.mLastPosition = holder.getLayoutPosition();
        }

    }

    protected void startAnim(Animator anim, int index) {
        anim.setDuration((long)this.mDuration).start();
        anim.setInterpolator(this.mInterpolator);
    }

    private boolean isLoadMore() {
        return this.mNextLoadEnable && this.pageSize != -1 && this.mRequestLoadMoreListener != null && this.mData.size() >= this.pageSize;
    }

    protected View getItemView(int layoutResId, ViewGroup parent) {
        return this.mLayoutInflater.inflate(layoutResId, parent, false);
    }

    /** @deprecated */
    @Deprecated
    protected void onBindDefViewHolder(BaseViewHolder holder, T item) {
    }


    public void openLoadAnimation(int animationType) {
        this.mOpenAnimationEnable = true;
        this.mCustomAnimation = null;
        switch(animationType) {
            case 1:
                this.mSelectAnimation = new AlphaInAnimation();
                break;
            case 2:
                this.mSelectAnimation = new ScaleInAnimation();
                break;
            case 3:
                this.mSelectAnimation = new SlideInBottomAnimation();
                break;
            case 4:
                this.mSelectAnimation = new SlideInLeftAnimation();
                break;
            case 5:
                this.mSelectAnimation = new SlideInRightAnimation();
        }

    }

    public void openLoadAnimation(BaseAnimation animation) {
        this.mOpenAnimationEnable = true;
        this.mCustomAnimation = animation;
    }

    public void openLoadAnimation() {
        this.mOpenAnimationEnable = true;
    }

    public void isFirstOnly(boolean firstOnly) {
        this.mFirstOnlyEnable = firstOnly;
    }

    protected abstract void convert(BaseViewHolder var1, T var2);
    protected  void convert(BaseViewHolder var1, T var2,List<Object> payloads){

    };
    public long getItemId(int position) {
        return (long)position;
    }

    public interface RequestLoadMoreListener {
        void onLoadMoreRequested();
    }

    public class OnItemChildLongClickListener implements OnLongClickListener {
        public ViewHolder mViewHolder;

        public OnItemChildLongClickListener() {
        }

        public boolean onLongClick(View v) {
            return BaseAdapter.this.mChildLongClickListener != null?BaseAdapter.this.mChildLongClickListener.onItemChildLongClick(BaseAdapter.this, v, this.mViewHolder.getLayoutPosition() - BaseAdapter.this.getHeaderViewsCount()):false;
        }
    }

    public interface OnRecyclerViewItemChildLongClickListener {
        boolean onItemChildLongClick(BaseAdapter var1, View var2, int var3);
    }

    public class OnItemChildClickListener implements OnClickListener {
        public ViewHolder mViewHolder;

        public OnItemChildClickListener() {
        }

        public void onClick(View v) {
            if(BaseAdapter.this.mChildClickListener != null) {
                BaseAdapter.this.mChildClickListener.onItemChildClick(BaseAdapter.this, v, this.mViewHolder.getLayoutPosition() - BaseAdapter.this.getHeaderLayoutCount());
            }

        }
    }

    public interface OnRecyclerViewItemChildClickListener {
        void onItemChildClick(BaseAdapter var1, View var2, int var3);
    }

    public interface OnRecyclerViewItemLongClickListener {
        boolean onItemLongClick(View var1, int var2);
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View var1, int var2);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AnimationType {
    }

    protected abstract boolean areItemsTheSames(T oldBean, T newBean);
    protected abstract boolean areContentsTheSames(T oldBean, T newBean);
    protected abstract Object getChangePayloads(T oldBean, T newBean);

    private class DiffCallback extends DiffUtil.Callback{
        private List<T> oldList = new ArrayList<>();
        private List<T> newList = new ArrayList<>();
        public void setOldList( List<T> oldList){
            if (oldList!=null){
                this.oldList.clear();
                this.oldList.addAll(oldList);
            }
        }
        public void setNewList( List<T> newList){
            if (newList!=null){
                this.newList.clear();
                this.newList.addAll(newList);
            }
        }
        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int i, int i1) {
            return areItemsTheSames(oldList.get(i),newList.get(i1));
        }

        @Override
        public boolean areContentsTheSame(int i, int i1) {
            return areContentsTheSames(oldList.get(i),newList.get(i1));
        }
        @Nullable
        @Override
        public Object getChangePayload(int i, int i1) {

            return getChangePayloads(oldList.get(i),newList.get(i1));
        }
    }

    @Override
    public void registerAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        NotifyObserver observer1 = new NotifyObserver(observer);
        super.registerAdapterDataObserver(observer1);
    }

    @Override
    public void unregisterAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        NotifyObserver observer1 = new NotifyObserver(observer);
        super.unregisterAdapterDataObserver(observer1);
    }

    /**
     * 处理diffutil刷新带有headerview的adatper导致布局错乱
     */
    private class NotifyObserver extends RecyclerView.AdapterDataObserver {

        RecyclerView.AdapterDataObserver mDataObserver;

        public NotifyObserver(RecyclerView.AdapterDataObserver dataObserver) {
            mDataObserver = dataObserver;
        }
        @Override
        public void onChanged() {
            mDataObserver.onChanged();
        }
        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            mDataObserver.onItemRangeChanged(positionStart + getHeaderViewsCount() , itemCount);
        }
        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            mDataObserver.onItemRangeChanged(positionStart + getHeaderViewsCount() , itemCount, payload);
        }
        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            mDataObserver.onItemRangeInserted(positionStart + getHeaderViewsCount() , itemCount);
        }
        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            mDataObserver.onItemRangeMoved(fromPosition, toPosition, itemCount);
        }
        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            mDataObserver.onItemRangeRemoved(positionStart + getHeaderViewsCount() , itemCount);
        }
    }



}
