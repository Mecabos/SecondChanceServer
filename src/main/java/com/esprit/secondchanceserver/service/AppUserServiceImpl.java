package com.esprit.secondchanceserver.service;

import java.util.Arrays;
import java.util.HashSet;

import com.esprit.secondchanceserver.enumeration.GenderType;
import com.esprit.secondchanceserver.enumeration.StatusType;
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

    @Autowired
    private FilterService filterService;

    @Override
    public AppUser findUserByEmail(String email) {
        return appUserRepository.findByEmail(email);
    }

    @Override
    public AppUser findUserById(int id) {
        return appUserRepository.findById(id);
    }

    @Override
    public void saveUser(AppUser appUser) {
        appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
        appUser.setActive(1);
        Role userRole = roleRepository.findByRole("USER");
        appUser.setRoles(new HashSet<Role>(Arrays.asList(userRole)));

        appUserRepository.save(appUser);
        filterService.saveFilter(appUser);
    }

    @Override
    public void updateInfo(AppUser appUser) {
        AppUser appUserToUpdate = findUserById(appUser.getId());

        String name = appUser.getName();
        if (name != null)
            appUserToUpdate.setName(name);

        String lastName = appUser.getLastName();
        if (lastName != null)
            appUserToUpdate.setLastName(lastName);

        String description = appUser.getDescription();
        if (description != null)
            appUserToUpdate.setDescription(description);

        String university = appUser.getUniversity();
        if (university != null)
            appUserToUpdate.setUniversity(university);

        String profession = appUser.getProfession();
        if (profession != null)
            appUserToUpdate.setProfession(profession);

        int numberOfChildren = appUser.getNumberOfChildren();
        appUserToUpdate.setNumberOfChildren(numberOfChildren);

        int age = appUser.getAge();
        appUserToUpdate.setAge(age);

        GenderType gender = appUser.getGender();
        if (gender != null)
            appUserToUpdate.setGender(gender);

        StatusType status = appUser.getStatus();
        if (gender != null)
            appUserToUpdate.setStatus(status);

        String country = appUser.getCountry();
        if (country != null)
            appUserToUpdate.setCountry(country);

        String town = appUser.getTown();
        if (town != null)
            appUserToUpdate.setTown(town);

        boolean livesAlone = appUser.isLivesAlone();
        appUserToUpdate.setLivesAlone(livesAlone);

        appUserRepository.save(appUserToUpdate);
    }

}