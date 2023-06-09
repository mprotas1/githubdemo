package com.atipera.githubdemo.utils;

import com.atipera.githubdemo.model.GitHubRepository;
import com.atipera.githubdemo.response.BranchResponse;
import com.atipera.githubdemo.response.GitHubRepoResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ResponseParser {

    public List<GitHubRepoResponse> toResponse(List<GitHubRepository> repos) {
        List<GitHubRepoResponse> response = new ArrayList<>();

        for(GitHubRepository repo : repos) {
            GitHubRepoResponse entry = GitHubRepoResponse.builder()
                            .repositoryName(repo.getName())
                            .ownerLogin(repo.getOwner().getLogin())
                            .branches(
                                    repo.getBranches().stream()
                                            .map(c -> new BranchResponse(
                                                    c.getName(),
                                                    c.getCommit().getSha())
                                            )
                                    .toList()
                            ).build();

            response.add(entry);
        }

        return response.stream()
                .sorted(Comparator.comparing(GitHubRepoResponse::getRepositoryName))
                .collect(Collectors.toList());
    }
}
