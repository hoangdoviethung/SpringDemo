package com.hung.logging;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.UUID;


/**
 * Aspect for logging execution of service and repository Spring components.
 * <p>
 * By default, it only runs with the "dev" profile.
 */
@Aspect
public class LoggingAspect {

    private final Environment env;
    private final SeabankMessageCache seabankMessageCache;


    
    public LoggingAspect(Environment env,
                         SeabankMessageCache seabankMessageCache) {
        this.env = env;
        this.seabankMessageCache = seabankMessageCache;
    }

    /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
    @Pointcut(
            "within(@org.springframework.stereotype.Repository *)" +
                    " || within(@org.springframework.stereotype.Service *)" +
                    " || within(@org.springframework.web.bind.annotation.RestController *)"
    )
    public void springBeanPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Pointcut that matches all Spring beans in the application's main packages.
     */
    @Pointcut("within(com.seabank.fe.repository..*)" + " || within(com.seabank.fe.service..*)" + " || within(com.seabank.fe.rest..*)")
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    @Pointcut("within(com.seabank.fe.rest..*)")
    public void applicationRestPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Retrieves the {@link Logger} associated to the given {@link JoinPoint}.
     *
     * @param joinPoint join point we want the logger for.
     * @return {@link Logger} associated to the given {@link JoinPoint}.
     */
    private Logger logger(JoinPoint joinPoint) {
        return LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringTypeName());
    }

    /**
     * Advice that logs methods throwing exceptions.
     *
     * @param joinPoint join point for advice.
     * @param e         exception.
     */
    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        if (env.acceptsProfiles(Profiles.of("dev"))) {
            logger(joinPoint)
                    .error(
                            "Exception in {}() with cause = '{}' and exception = '{}'",
                            joinPoint.getSignature().getName(),
                            e.getCause() != null ? e.getCause() : "NULL",
                            e.getMessage(),
                            e
                    );
        } else {
            logger(joinPoint)
                    .error(
                            "Exception in {}() with cause = {}",
                            joinPoint.getSignature().getName(),
                            e.getCause() != null ? e.getCause() : "NULL"
                    );
        }
    }

    /**
     * Advice that logs when a method is entered and exited.
     *
     * @param joinPoint join point for advice.
     * @return result.
     * @throws Throwable throws {@link IllegalArgumentException}.
     */
    @Around("applicationRestPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger log = logger(joinPoint);
        UUID uuid = UUID.randomUUID();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
        	    .getRequest();
        if (log.isDebugEnabled()) {
            try {
            	log.debug("Start call api: [{}] \n [request-id:{}] \n {}",
                		request.getRequestURI(),
                        uuid,
                        JsonF.toString(joinPoint.getArgs()));
            } catch (Exception e) {
                log.error("Log enter param has error: {}", e.getMessage());
            }
        }
        try {
            Object result = joinPoint.proceed();
            if (log.isDebugEnabled()) {
                try {
                    if (/*result instanceof ResponseEntity
                            && ((ResponseEntity<?>) result).getBody() instanceof ApiResponse<?>*/
                    true
                    ) {
                    /*  ApiResponse<?> response = (ApiResponse<?>) ((ResponseEntity<?>) result).getBody();
                        this.overwriteError(response);*/
                    }
                    log.debug("Response from api: [{}] \n [request-id:{}] \n [{}]",
                    		request.getRequestURI(),
                            uuid,
                            JsonF.toString(result));
                } catch (Exception e) {
                    log.error("------- >Call api [{}] has error: {}",request.getRequestURI(), e.getMessage());
                }
            }
            return result;
        } catch (IllegalArgumentException e) {
            log.error("[{}][{}][{}] \n Illegal argument: {} in {}()",
                /*    SecurityContextService.getChannel(),
                    SecurityContextService.getSubChannel(),
                    SecurityContextService.getContext(),
                    Arrays.toString(joinPoint.getArgs()),*/
                    joinPoint.getSignature().getName());
            throw e;
        }
    }

    /**
     * Overwrite message_vn and message_en to result
     *
     * @param response
     * @return
     */
/*    private void overwriteError(ApiResponse<?> response) {
        if (this.seabankMessageCache == null ||
                this.seabankMessageCache.getSeaBankMessages().isEmpty() ||
                ObjectUtils.isEmpty(response.getError()) ||
                ObjectUtils.isEmpty(response.getError())) {
            return;
        }
        String code = response.getError().getCode();
        this.seabankMessageCache.getSeaBankMessages().stream()
                .filter(m -> m.getCode().equals(code))
                .findFirst()
                .ifPresent(m -> {
                    response.getError().setMessageVn(m.getMessageVn());
                    response.getError().setMessageEn(m.getMessageEn());
                });
    }*/

}
