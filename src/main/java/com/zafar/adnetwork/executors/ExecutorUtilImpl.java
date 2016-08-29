package com.zafar.adnetwork.executors;

import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import rx.Scheduler;
import rx.schedulers.Schedulers;

import com.zafar.adnetwork.util.AdNetworkThreadFactory;

@Service
public class ExecutorUtilImpl implements ExecutorUtil{
	
	private Scheduler readScheduler;

	@Value("${read.thread.pool.size:50}")
	private int readThreads;

	private Scheduler writeScheduler;
	
	@Value("${write.thread.pool.size:50}")
	private int writeThreads; 
	
	private Scheduler cleanupScheduler;
	
	@Value("${cleanup.thread.pool.size:50}")
	private int cleanupThreads; 
	
	@PostConstruct
	public void init(){
		readScheduler=Schedulers.from(Executors.newFixedThreadPool(readThreads,new AdNetworkThreadFactory("Read-pool")));
		writeScheduler=Schedulers.from(Executors.newFixedThreadPool(writeThreads,new AdNetworkThreadFactory("write-pool")));
		cleanupScheduler=Schedulers.from(Executors.newFixedThreadPool(cleanupThreads,new AdNetworkThreadFactory("Cleanup-pool")));
	}
	public Scheduler getReadScheduler() {
		return readScheduler;
	}
	@Override
	public Scheduler getReadExecutors() {
		return readScheduler;
	}
	@Override
	public Scheduler getWriteExecutors() {
		return writeScheduler;
	}
	@Override
	public Scheduler getCleanupExecutors() {
		return cleanupScheduler;
	}

}
