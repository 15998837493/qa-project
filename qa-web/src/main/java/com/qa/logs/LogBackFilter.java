
package com.qa.logs;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class LogBackFilter extends Filter<ILoggingEvent> {

    @Override
    public FilterReply decide(ILoggingEvent event) {

        // 做日志过滤，暂时不用

        // AccountService accountService = (AccountService)
        // CacheProjectInfo.getInstance().getApplicationContext().getBean("AccountService");
        // accountService.getAccount("root");

        // [EVENT-LOG]
        if (event.getMessage() != null && event.getMessage().startsWith("[EVENT-LOG]")) {
            String message = event.getMessage();
            message = message.substring("[EVENT-LOG]".length());
            // String[] logArray = message.split("\\|");
        }
        // return FilterReply.NEUTRAL;
        return FilterReply.ACCEPT;
    }
}
