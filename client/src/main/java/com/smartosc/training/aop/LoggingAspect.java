package com.smartosc.training.aop;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.smartosc.training.aop.entity.ApiLog;
import com.smartosc.training.aop.services.ApiLogService;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect
{
	@Autowired
	private ApiLogService apiLogService;

	@Pointcut("within(com.smartosc.training..*)")
	public void service() {
	}

	@Around("service()")
	public Object aroundServiceMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Object result = proceedingJoinPoint.proceed();
		return result;
	}

	@AfterThrowing (pointcut = "service()", throwing = "ex")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) throws IllegalAccessException {
		CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
		log.error("Exception in {}() with message = {}", codeSignature.getDeclaringTypeName(), ex.getMessage());
		ApiLog apiLog = new ApiLog();
		apiLog.setCalledTime(Calendar.getInstance().getTime());
		apiLog.setErrorMessage(ex.getMessage());
		apiLog.setRetryNum(1);
		List<String> args = new ArrayList<>();
		String[] argNames = codeSignature.getParameterNames();
		Object[] argValues = joinPoint.getArgs();
		for (int i = 0; i < argNames.length; i++) {
			args.add(argNames[i] + ":" + argValues[i].toString());
		}
		apiLog.setData(String.join(", ", args));
		apiLogService.saveApiLog(apiLog);
		log.error(String.join(", ", args));
	}

}