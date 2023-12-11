package com.ant.minis.context;

import com.ant.minis.beans.BeansException;

public interface ApplicationContextAware {
	void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
