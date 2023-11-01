// NotifResponse.java

// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

package com.c2.arenafinder.data.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotifResponse {
    @Expose
    @SerializedName("canonical_ids")
    private Long canonicalids;
    @Expose
    @SerializedName("success")
    private Long success;
    @Expose
    @SerializedName("failure")
    private Long failure;
    @Expose
    @SerializedName("results")
    private List<Result> results;
    @Expose
    @SerializedName("multicast_id")
    private Double multicastid;

    public Long getCanonicalids() { return canonicalids; }
    public void setCanonicalids(long value) { this.canonicalids = value; }

    public Long getSuccess() { return success; }
    public void setSuccess(long value) { this.success = value; }

    public Long getFailure() { return failure; }
    public void setFailure(long value) { this.failure = value; }

    public List<Result> getResults() { return results; }
    public void setResults(List<Result> value) { this.results = value; }

    public Double getMulticastid() { return multicastid; }
    public void setMulticastid(double value) { this.multicastid = value; }
}

// Result.java

// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

class Result {
    @Expose
    @SerializedName("error")
    private String error;

    public String getError() { return error; }
    public void setError(String value) { this.error = value; }
}
