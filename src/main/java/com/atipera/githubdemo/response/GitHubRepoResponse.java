package com.atipera.githubdemo.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GitHubRepoResponse {

    private String repositoryName;
    private String ownerLogin;
    private List<BranchResponse> branches;

}
