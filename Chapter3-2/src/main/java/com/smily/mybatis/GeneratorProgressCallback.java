package com.smily.mybatis;

import org.mybatis.generator.api.ProgressCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneratorProgressCallback implements ProgressCallback {
	private final Logger logger;

	public GeneratorProgressCallback() {
		logger = LoggerFactory.getLogger(super.getClass());
	}

	@Override
	public void introspectionStarted(int totalTasks) {
	}

	@Override
	public void generationStarted(int totalTasks) {
	}

	@Override
	public void saveStarted(int totalTasks) {
	}

	@Override
	public void startTask(String taskName) {
		logger.info(taskName);
	}

	@Override
	public void done() {
	}

	@Override
	public void checkCancel() throws InterruptedException {
	}
}