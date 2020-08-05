// IMyAidlInterface.aidl
package com.layon.android.aidlservercalculator;

// Declare any non-default types here with import statements

interface IMyAidlInterface {
    /**
     * Demonstrates a basic method that you can use as parameters
     * and return values in AIDL.
     */
    double sum(double a, double b);
}
