package com.atipera.githubdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubRepository {

    private String name;
    private Owner owner;
    private boolean fork;
    private List<Branch> branches;

}
