package com.esprit.secondchanceserver.service;

import java.util.Arrays;
import java.util.HashSet;

import com.esprit.secondchanceserver.model.AppUser;
import com.esprit.secondchanceserver.model.Role;
import com.esprit.secondchanceserver.repository.AppUserRepository;
import com.esprit.secondchanceserver.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



@Service("appUserService")
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public AppUser findUserByEmail(String email) {
        return appUserRepository.findByEmail(email);
    }

    @Override
    public void saveUser(AppUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        appUserRepository.save(user);
    }

}