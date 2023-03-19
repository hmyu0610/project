package com.hmyu.place.config;

import com.hmyu.place.constant.CommonProperties;
import org.apache.ibatis.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.Properties;

@Component
public class CommonListener implements ApplicationListener<ApplicationStartedEvent> {
	private static final Logger logger = LoggerFactory.getLogger(CommonListener.class);

	@Override
	public void onApplicationEvent(ApplicationStartedEvent event){
		String profiles = System.getProperty("spring.profiles.active");
		logger.info("[CommonListener] init : spring.profiles.active : {}", profiles);

		Properties properties = new Properties();
        try {
			String resource = "globals_"+ profiles +".properties";
            Reader reader = Resources.getResourceAsReader(resource);
            properties.load(reader);
 
            Iterator<Object> iter = properties.keySet().iterator();
            while(iter.hasNext()) {
            	String key = (String)iter.next();
            	CommonProperties.PROPERTIES_MAP.put(key, properties.getProperty(key));	// 프로퍼티 정보 설
            }
        } catch (IOException e) {
			logger.error("[CommonListener] IOException : " + e, e);
        }
	}

	
}
