package com.github.ivan.kopylove.telegram.api.domain;

import java.util.List;

public class GetUpdate extends BaseApiType
{
    private boolean      ok;
    private List<Update> result;

    public boolean isOk()
    {
        return ok;
    }

    public void setOk(final boolean ok)
    {
        this.ok = ok;
    }

    public List<Update> getResult()
    {
        return result;
    }

    public void setResult(final List<Update> result)
    {
        this.result = result;
    }
}

