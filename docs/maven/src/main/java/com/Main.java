package com.example;

import jdd.*;
import jdd.bdd.*;

public class Main {
  public static void main(String []args) {
    System.out.printf("Using JDD version %s.\n", Version.VERSION);

    BDD jdd = new BDD(200);
    jdd.createVars(5);

    int t1 = BDDHelper.encode(jdd, null, "0---0");
    int t2 = BDDHelper.encode(jdd, null, "-111-");
    int t = jdd.ref( jdd.and(t1, t2));

    BDDPrinter.printSet(t1, 100, jdd, null);
    System.out.printf("%f variable assignments satisfy t1\n", jdd.satCount(t1));

    BDDPrinter.printSet(t2, 100, jdd, null);
    System.out.printf("%f variable assignments satisfy t2\n", jdd.satCount(t2));

    BDDPrinter.printSet(t, 100, jdd, null);
    System.out.printf("%f variable assignments satisfy t\n", jdd.satCount(t));
  }
}
