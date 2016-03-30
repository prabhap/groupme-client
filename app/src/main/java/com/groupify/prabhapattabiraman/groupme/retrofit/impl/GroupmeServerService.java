package com.groupify.prabhapattabiraman.groupme.retrofit.impl;

import com.groupify.prabhapattabiraman.groupme.retrofit.GroupmeServer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Path;

public class GroupmeServerService extends BaseRetrofitService {
    public GroupmeServerService() {
        super();
    }

    public GroupmeServer getService() {
        return getBaseClient().create(GroupmeServer.class);
    }
}
