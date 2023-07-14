package com.github.lazyf1sh.telegram.api.domain;

public enum ChatMemberStatus
{
    CREATOR("creator"),
    ADMIN("administrator"),
    MEMBER("member"),
    RESTRICTED("restricted"),
    LEFT("left");

    private String value;

    ChatMemberStatus(final String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(final String value)
    {
        this.value = value;
    }
}
