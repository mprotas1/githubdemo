package com.atipera.githubdemo.response;

import lombok.Data;

@Data
public class BranchResponse {
    private String name;
    private String sha;

    public BranchResponse(String name, String sha) {
        this.name = name;
        this.sha = sha;
    }
}
