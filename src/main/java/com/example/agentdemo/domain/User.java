package com.example.agentdemo.domain;

/** Domain model. No framework, no annotations. */
public record User(Long id, String name, String email) {
}
