/*
 *  Copyright 2016 ADVA Optical Networking SE. All rights reserved.
 *
 *  Owner: ext_psofiadis
 *
 *  $Id: CreateAndActivateFlowDomainFragmentWorkerCM.java 96000 2016-02-08 10:56:27Z ext_psofiadis $
 */

package org.demo.monitor;


public interface MonitoringThreadMBean {

  int getQueueSize();

  int getNumberOfThreads();

  String getThreadState();

  boolean restartThread();

  boolean restartThreadClean();

}
