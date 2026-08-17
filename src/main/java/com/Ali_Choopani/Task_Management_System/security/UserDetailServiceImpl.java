package com.Ali_Choopani.Task_Management_System.security;

import com.Ali_Choopani.Task_Management_System.entities.User;
import com.Ali_Choopani.Task_Management_System.exceptions.IdNotFoundException;
import com.Ali_Choopani.Task_Management_System.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import static java.lang.String.format;
import static java.util.Collections.singleton;

@Component
@RequiredArgsConstructor
public class UserDetailServiceImpl implements CustomUserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        final User entity = repository.findByEmail(identifier)
                .orElseGet(() -> repository.findByPhoneNumber(identifier)
                        .orElseThrow(() -> new UsernameNotFoundException(format("Not found any user with this identifier [%s] !", identifier))));

        return new UserDetailImpl(entity.getId(), identifier, entity.getPassword(), singleton(new SimpleGrantedAuthority(entity.getRole().name())));
    }


    @Override
    public UserDetails loadUserById(Long id) {
        final User entity = repository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(id));

        final String username = entity.getEmail() != null ? entity.getEmail() : entity.getPhoneNumber();
        return new UserDetailImpl(entity.getId(), username, entity.getPassword() ,singleton(new SimpleGrantedAuthority(entity.getRole().name()) ));
    }
}
