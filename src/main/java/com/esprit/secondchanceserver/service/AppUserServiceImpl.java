package com.esprit.secondchanceserver.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import com.esprit.secondchanceserver.Util.DebugUtil;
import com.esprit.secondchanceserver.enumeration.GenderType;
import com.esprit.secondchanceserver.enumeration.StatusType;
import com.esprit.secondchanceserver.model.AppUser;
import com.esprit.secondchanceserver.model.Picture;
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

    @Autowired
    private PictureService pictureService;

    @Override
    public AppUser login(AppUser appUser) {
        AppUser foundAppUser = appUserRepository.findByEmail(appUser.getEmail());
        if (foundAppUser != null) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (passwordEncoder.matches(appUser.getPassword(), foundAppUser.getPassword())){
                foundAppUser.setProfilePicture(pictureService.getPicture(new Picture(0,foundAppUser)));
                return foundAppUser;
            }

        }
        return null;
    }

    @Override
    public AppUser findUserByEmail(String email) {
        AppUser foundAppUser = appUserRepository.findByEmail(email);
        if (foundAppUser != null)
            foundAppUser.setProfilePicture(pictureService.getPicture(new Picture(0,foundAppUser)));
        return foundAppUser;
    }

    @Override
    public AppUser findUserById(int id) {
        AppUser foundAppUser = appUserRepository.findById(id);
        if (foundAppUser != null)
            foundAppUser.setProfilePicture(pictureService.getPicture(new Picture(0,foundAppUser)));
        return foundAppUser;
    }

    @Override
    public List<AppUser> findUsersFor(int active, GenderType gender, int ageMin, int ageMax, int childrenNumberMin, int childrenNumberMax, List<StatusType> statusList, String country, int id) {
        return appUserRepository.findAllByActiveAndGenderAndAgeBetweenAndNumberOfChildrenBetweenAndStatusInAndCountryAndIdNot(
                active,
                gender,
                ageMin, ageMax,
                childrenNumberMin, childrenNumberMax,
                statusList,
                country,
                id);
    }

    @Override
    public AppUser saveUser(AppUser appUser) {
        appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
        appUser.setActive(1);
        Role userRole = roleRepository.findByRole("USER");
        appUser.setRoles(new HashSet<Role>(Arrays.asList(userRole)));

        AppUser registeredUser = appUserRepository.save(appUser);
        filterService.saveFilter(appUser);

        return registeredUser;
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