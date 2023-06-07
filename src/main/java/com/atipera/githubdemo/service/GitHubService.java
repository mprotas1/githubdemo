package com.atipera.githubdemo.service;

import com.atipera.githubdemo.model.Branch;
import com.atipera.githubdemo.model.Commit;
import com.atipera.githubdemo.model.GitHubRepository;
import com.atipera.githubdemo.model.User;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GitHubService {
    private final String GITHUB_API_BASE_URL = "https://api.github.com";
    private final WebClient webClient;
    public User getUser(String username) {
        User user = webClient.get()
                .uri(GITHUB_API_BASE_URL + "/users/" + username)
                .retrieve()
                .bodyToMono(User.class)
                .block();
        return user;
    }

    public List<GitHubRepository> getUserRepositories(String username) {
        String uri = GITHUB_API_BASE_URL + String.format("/users/%s/repos", username);

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(GitHubRepository.class)
                .filter(repo -> !repo.isFork())
                .flatMap(repo -> getBranches(username, repo.getName())
                        .map(branches -> {
                            repo.setBranches(branches);
                            return repo;
                        }))
                .collectList()
                .block();
    }

    private Flux<List<Branch>> getBranches(String ownerLogin, String repoName) {
        String uri = GITHUB_API_BASE_URL + String.format("/repos/%s/%s/branches",
                ownerLogin,
                repoName);

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(Branch.class)
                .collectList()
                .flux();
    }

}
