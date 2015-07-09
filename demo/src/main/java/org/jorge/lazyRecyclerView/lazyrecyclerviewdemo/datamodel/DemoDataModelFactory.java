package org.jorge.lazyRecyclerView.lazyrecyclerviewdemo.datamodel;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jorge Antonio Diaz-Benito Soriano (github.com/Stoyicker).
 */
public abstract class DemoDataModelFactory {

    private static AtomicInteger DATA_MODEL_COUNTER = new AtomicInteger(-1);

    public static DemoDataModel createDemoDataModel() {
        return new DemoDataModel("Index " + DATA_MODEL_COUNTER.addAndGet(1));
    }
}
