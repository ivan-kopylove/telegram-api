package com.github.ivan_kopylov.telegram.api.domain;

public class ResponseBase
{
    public boolean ok;

    public boolean isOk()
    {
        return ok;
    }

    public void setOk(final boolean ok)
    {
        this.ok = ok;
    }
}
