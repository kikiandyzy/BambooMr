package com.example.bamboomr.Daily;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;

public class YolandaItemTouchHelper extends ItemTouchHelper {
    /**
     * Creates an ItemTouchHelper that will work with the given Callback.
     * <p>
     * You can attach ItemTouchHelper to a RecyclerView via
     * {@link #attachToRecyclerView(RecyclerView)}. Upon attaching, it will add an item decoration,
     * an onItemTouchListener and a Child attach / detach listener to the RecyclerView.
     *
     * @param callback The Callback which controls the behavior of this touch helper.
     */
    private Callback mCallback;

    public YolandaItemTouchHelper(@NonNull Callback callback) {
        super(callback);
        mCallback = callback;
    }

    public Callback getCallback(){
        return mCallback;
    }
}
