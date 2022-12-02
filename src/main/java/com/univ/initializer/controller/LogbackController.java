package com.univ.initializer.controller;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.univ.initializer.util.response.R;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用来动态修改logback的日志级别
 * @author univ 2022/12/2 15:38
 */
@RestController
@RequestMapping("/logback")
@Slf4j
public class LogbackController {

	/**
	 * 修改某个包的日志级别
	 * @param packageName 包，如com.univ.initializer
	 * @param newLevel	新的日志级别
	 * @return
	 */
	@GetMapping("/level/change")
	public R<Map<String, String>> changeLevel(@RequestParam("packageName") String packageName, @RequestParam("newLevel") String newLevel) {
		ch.qos.logback.classic.LoggerContext loggerContext =(ch.qos.logback.classic.LoggerContext) LoggerFactory
				.getILoggerFactory();
		Logger logger= null;
		if(packageName.equals("-1")) {
			// 默认值-1，更改全局日志级别；否则按传递的包名或类名修改日志级别。
			logger=  loggerContext.getLogger("root");
		} else {
			logger= loggerContext.getLogger(packageName);
		}
		Level oldLevel = logger.getLevel();
		logger.setLevel(ch.qos.logback.classic.Level.toLevel(newLevel));

		return R.data(new HashMap<String, String>() {
			{
				put("oldLevel", oldLevel.toString());
				put("newLevel", newLevel);
			}
		});
	}

	@GetMapping("/test")
	public R<String> test() {
		log.info("------LogbackController.test.info------");
		if (log.isDebugEnabled()) {
			log.debug("------LogbackController.test.debug------");
		}
		return R.data("ok");
	}

}
