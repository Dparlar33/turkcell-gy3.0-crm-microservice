package com.turkcell.crm.accountService.core.business.abstracts;

public interface MessageService {
    String getMessage(String key);
    String getMessageWithArgs(String key, Object... args);
}
