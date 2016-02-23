/**
 * Created by Panagiotis on 2/20/2016.
 */

package org.demo.monitor;

import com.codahale.metrics.MetricRegistry;

public class GlobalMetricRegistry {

  private static final MetricRegistry globalMetricRegistry = new MetricRegistry();


  public static MetricRegistry getGlobalMetricRegistry() {
    return globalMetricRegistry;
  }
}
