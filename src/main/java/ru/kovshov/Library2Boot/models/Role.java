package ru.kovshov.Library2Boot.models;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    USER(Set.of(Permition.READ)),
    ADMIN(Set.of(Permition.READ, Permition.WRITE));

    private final Set<Permition> permition;

    Role(Set<Permition> permition) {
        this.permition = permition;
    }

    public Set<Permition> getPermition() {
        return permition;
    }

    public Set<SimpleGrantedAuthority> getAuthority() {
        return getPermition().stream()
                .map(permition -> new SimpleGrantedAuthority(permition.getPermition()))
                .collect(Collectors.toSet());
    }
}
