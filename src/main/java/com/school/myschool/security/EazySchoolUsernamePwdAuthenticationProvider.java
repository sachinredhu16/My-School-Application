package com.school.myschool.security;

import com.school.myschool.model.Person;
import com.school.myschool.model.Roles;
import com.school.myschool.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EazySchoolUsernamePwdAuthenticationProvider
        implements AuthenticationProvider
{
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        Person person = personRepository.readByEmail(email);
        if(null != person && person.getPersonId()>0 &&
                passwordEncoder.matches(password,person.getPwd())){
            return new UsernamePasswordAuthenticationToken(
                    email, null , getGrantedAuthorities(person.getRoles()));
        }else{
            throw new BadCredentialsException("Invalid credentials!");
        }
    }

    private List<GrantedAuthority> getGrantedAuthorities(Roles roles) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+roles.getRoleName()));
        return grantedAuthorities;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
