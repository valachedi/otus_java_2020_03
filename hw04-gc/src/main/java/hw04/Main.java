package hw04;

import com.sun.management.GarbageCollectionNotificationInfo;
import java.lang.OutOfMemoryError;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import javax.management.MBeanServer;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import java.util.Date;
import java.util.List;
import hw04.components.BenchmarkOverfillMemory;

public class Main {
  private static final String BEAN_REG_NAME = "hw04-gc:type=BenchmarkOverfillMemory";
  private static final String GC_PARALLEL_BEAN_NAME_MINOR = "PS Scavenge";
  private static final String GC_PARALLEL_BEAN_NAME_MAJOR = "PS MarkSweep";
  private static final String GC_G1_BEAN_NAME_MINOR = "G1 Young Generation";
  private static final String GC_G1_BEAN_NAME_MAJOR = "G1 Old Generation";
  private static final int METRIC_PERIOD_SNAP_SECONDS = 60;

  public static void main(String ...args) throws Exception {
    int msOfStart = (int)System.currentTimeMillis();
    switchOnMonitoring();
    long beginTime = System.currentTimeMillis();

    MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
    ObjectName name = new ObjectName(BEAN_REG_NAME);

    BenchmarkOverfillMemory mbean = new BenchmarkOverfillMemory();
    mbs.registerMBean(mbean, name);

    try {
      mbean.run();
    }
    catch(OutOfMemoryError e) {
      System.out.println("total execution time: " + (((int)System.currentTimeMillis() - msOfStart) / 1000) + " seconds");
      throw e;
    }

    System.out.println("time:" + (System.currentTimeMillis() - beginTime) / 1000);
  }

  private static void switchOnMonitoring() {
    List<GarbageCollectorMXBean> gcbeans = ManagementFactory.getGarbageCollectorMXBeans();

    for (GarbageCollectorMXBean gcbean : gcbeans) {
      String beanName = gcbean.getName();
      System.out.println("Monitoring bean " + beanName);
      NotificationEmitter emitter = (NotificationEmitter) gcbean;

      var periodMetrics = new Object() {
        int countGc = 0;
        long sumDuration = 0;
        long timePrevPeriod = 0;
      };

      NotificationListener listener = (notification, handback) -> {
        if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
          GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
          long duration = info.getGcInfo().getDuration();
          long startTime = info.getGcInfo().getStartTime();
          String gcAction = info.getGcAction();

          periodMetrics.countGc++;
          periodMetrics.sumDuration += duration;

          if(startTime - periodMetrics.timePrevPeriod > METRIC_PERIOD_SNAP_SECONDS * 1000) {
            String periodSeconds = String.valueOf(METRIC_PERIOD_SNAP_SECONDS);

            String gcActionTypeName = beanName.equals(GC_G1_BEAN_NAME_MINOR) || beanName.equals(GC_PARALLEL_BEAN_NAME_MINOR)
                                        ? "minor"
                                        : "major";

            System.out.println("* [" + gcActionTypeName + "] Metrics summary for last " + periodSeconds + " seconds *");
            System.out.println(periodMetrics.countGc + " times, " + periodMetrics.sumDuration + " ms");
            System.out.println("");

            periodMetrics.countGc = 0;
            periodMetrics.sumDuration = 0;
            periodMetrics.timePrevPeriod = startTime;
          }
        }
      };

      emitter.addNotificationListener(listener, null, null);
    }
  }
}
