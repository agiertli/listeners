package com.company.service;

import java.util.ArrayList;
import java.util.List;

import org.kie.api.task.UserGroupCallback;
import org.kie.internal.identity.IdentityProvider;
import org.springframework.beans.factory.annotation.Autowired;

public class SimpleUserGroupCallback implements UserGroupCallback {

    @Autowired
    IdentityProvider identityProvider;

    public SimpleUserGroupCallback() {
    }

    public boolean existsUser(String userId) {
        return true;
    }

    public List<String> getGroupsForUser(String userId) {
        List<String> groups = new ArrayList<>();
        identityProvider.getRoles().stream().forEach(r -> groups.add(r));
        return groups;
    }

    public boolean existsGroup(String groupId) {
        return true;
    }
}

