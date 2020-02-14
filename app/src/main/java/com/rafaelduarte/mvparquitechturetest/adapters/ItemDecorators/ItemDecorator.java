package com.rafaelduarte.mvparquitechturetest.adapters.ItemDecorators;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class ItemDecorator extends RecyclerView.ItemDecoration {

    private final int top;
    private final int bottom;
    private final int left;
    private final int right;

    public ItemDecorator(int top, int bottom, int left, int right) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        //your padding...

        final int itemPosition = parent.getChildAdapterPosition(view);
        if (itemPosition == RecyclerView.NO_POSITION) {
            return;
        }

        final int itemCount = state.getItemCount();

        /** first position */
        if (itemPosition == 0) {
            outRect.set(40, 0, 20, 40);
        }
        /** last position */
        else if (itemCount > 0 && itemPosition == itemCount - 1) {
            outRect.set(20, 0, 40, 40);
        }
        /** positions between first and last */
        else {
            outRect.set(20, 0, 20, 40);
        }
    }

}
