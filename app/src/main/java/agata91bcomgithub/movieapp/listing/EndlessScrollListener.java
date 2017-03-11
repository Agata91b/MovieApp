package agata91bcomgithub.movieapp.listing;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.CompoundButton;

/**
 * Created by RENT on 2017-03-11.
 */

public class EndlessScrollListener extends RecyclerView.OnScrollListener {

    private static final double PAGE_SIZE = 10;
    private int totalsItemsNumber;
    private LinearLayoutManager layoutManager;
    private boolean isLoading;
    private OnLoadNextPageListener listener;
    private CurrentItemListener currentItemListener;
    private ShowOrHideCounter showOrHideCounter;
    private boolean isCounterShown;




    public EndlessScrollListener(LinearLayoutManager layoutManager, OnLoadNextPageListener listener) {
        this.layoutManager = layoutManager;
        this.listener = listener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int alreadyLoadedItems = layoutManager.getItemCount();
        int currentPage = (int) Math.ceil(alreadyLoadedItems / PAGE_SIZE);
        double numberAllOfPages = Math.ceil(totalsItemsNumber / PAGE_SIZE);
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition() + 1;

        if (currentPage < numberAllOfPages && lastVisibleItemPosition == alreadyLoadedItems && !isLoading) {
            loadNextPage(++currentPage);
            isLoading = true;
        }
        if(currentItemListener != null){
            currentItemListener.onNewCurrentItem(lastVisibleItemPosition,totalsItemsNumber);
        }

    }

    private void loadNextPage(int pageNumber) {
        listener.loadNextPage(pageNumber);
    }

    public void setTotalsItemsNumber(int totalsItemsNumber) {
        this.totalsItemsNumber = totalsItemsNumber;
        isLoading = false;
    }

    public void setCurrentItemListener(CurrentItemListener currentItemListener) {
        this.currentItemListener = currentItemListener;
    }

    public void setShowOrHideCounter(ShowOrHideCounter showOrHideCounter) {
        this.showOrHideCounter = showOrHideCounter;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        if(showOrHideCounter != null){
            if(isCounterShown && newState == RecyclerView.SCROLL_STATE_IDLE){
                recyclerView.postDelayed(() -> {
                    showOrHideCounter.hideCounter();
                    isCounterShown = false;
                }, 3000);
            }else if (!isCounterShown && newState == RecyclerView.SCROLL_STATE_DRAGGING){
                showOrHideCounter.showCounter();
                isCounterShown = true;
            }
        }
    }
}
