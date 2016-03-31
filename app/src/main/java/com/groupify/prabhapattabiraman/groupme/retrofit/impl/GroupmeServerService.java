package com.groupify.prabhapattabiraman.groupme.retrofit.impl;

import com.groupify.prabhapattabiraman.groupme.retrofit.GroupmeServer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Path;

public class GroupmeServerService extends BaseRetrofitService {

    private static GroupmeServerService groupmeServerService;

    public static GroupmeServerService getServiceInstance() {
        if(groupmeServerService == null) {
            groupmeServerService = new GroupmeServerService();
        }
        return groupmeServerService;
    }

    protected GroupmeServerService() {
        super();

    }

    public GroupmeServer getService() {
        return getBaseClient().create(GroupmeServer.class);
    }
}
