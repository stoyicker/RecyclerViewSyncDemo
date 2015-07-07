package org.jorge.lazyrecyclerviewdemo.ui.datamodel;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jorge Antonio Diaz-Benito Soriano (github.com/Stoyicker).
 */
public abstract class DemoDataModelFactory {

    private static AtomicInteger DATA_MODEL_COUNTER = new AtomicInteger(0);

    public static DemoDataModel createDemoDataModel() {
        return new DemoDataModel(DATA_MODEL_COUNTER.addAndGet(1) + "");
    }
}
