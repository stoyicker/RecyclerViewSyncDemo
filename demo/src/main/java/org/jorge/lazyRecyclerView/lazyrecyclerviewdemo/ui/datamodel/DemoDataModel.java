package org.jorge.lazyRecyclerView.lazyrecyclerviewdemo.ui.datamodel;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * @author Jorge Antonio Diaz-Benito Soriano (github.com/Stoyicker).
 */
public final class DemoDataModel implements Parcelable {

    private final String mText;

    protected DemoDataModel(@NonNull final String mText) {
        this.mText = mText;
    }

    public DemoDataModel(@NonNull final Parcel in) {
        mText = in.readString();
    }

    public final String getText() {
        return mText;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull final Parcel dest, final int flags) {
        dest.writeString(mText);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public DemoDataModel createFromParcel(final Parcel in) {
            return new DemoDataModel(in);
        }

        public DemoDataModel[] newArray(final int size) {
            return new DemoDataModel[size];
        }
    };
}
