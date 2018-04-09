package com.esprit.secondchanceserver.service;

import com.esprit.secondchanceserver.Util.DebugUtil;
import com.esprit.secondchanceserver.enumeration.GenderType;
import com.esprit.secondchanceserver.enumeration.RelationshipType;
import com.esprit.secondchanceserver.enumeration.StatusType;
import com.esprit.secondchanceserver.model.AppUser;
import com.esprit.secondchanceserver.model.Filter;
import com.esprit.secondchanceserver.model.Notation;
import com.esprit.secondchanceserver.repository.FilterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("filterService")
public class FilterServiceImpl implements FilterService {

    @Autowired
    private FilterRepository filterRepository;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private NotationService notationService;

    @Override
    public Filter findFilterByAppUser(AppUser appUser) {
        return filterRepository.findByAppUser(appUser);
    }

    @Override
    public void saveFilter(AppUser appUser) {
        Filter filter = new Filter();
        filter.setAppUser(appUser);

        filter.setCountry(appUser.getCountry());

        if (appUser.getGender() == null || appUser.getGender() == GenderType.Man)
            filter.setGender(GenderType.Woman);
        else
            filter.setGender(GenderType.Man);

        List<StatusType> statusList = new ArrayList<>(Arrays.asList(StatusType.values()));
        filter.setStatusList(statusList);

        List<RelationshipType> relationshipTypeList = new ArrayList<>(Arrays.asList(RelationshipType.values()));
        filter.setRelationshipTypeList(relationshipTypeList);

        filterRepository.save(filter);
    }

    @Override
    public void updateFilter(AppUser appUser, Filter filter) {
        Filter filterToUpdate = findFilterByAppUser(appUser);

        filterToUpdate.setGender(filter.getGender());
        filterToUpdate.setMinAge(filter.getMinAge());
        filterToUpdate.setMaxAge(filter.getMaxAge());
        filterToUpdate.setCountry(filter.getCountry());
        filterToUpdate.setTown(filter.getTown());
        filterToUpdate.setHasChildren(filter.isHasChildren());
        filterToUpdate.setStatusList(filter.getStatusList());
        filterToUpdate.setRelationshipTypeList(filter.getRelationshipTypeList());


        filterRepository.save(filterToUpdate);
    }

    @Override
    public List<AppUser> getFilteredUsers(AppUser appUser) {
        Filter filter = findFilterByAppUser(appUser);
        int childrenNumberMin = 0;
        int childrenNumberMax = 0;
        if (filter.isHasChildren())
            childrenNumberMax = 99;

        List<AppUser> filterResult = appUserService.findUsersFor(
                1,
                filter.getGender(),
                filter.getMinAge(), filter.getMaxAge(),
                childrenNumberMin, childrenNumberMax,
                filter.getStatusList(),
                filter.getCountry(),
                appUser.getId());
        //Remove AppUser From the list if they don't look for same kind of relationship
        filterResult.removeIf(currentAppUser -> Collections.disjoint(
                filter.getRelationshipTypeList(),
                findFilterByAppUser(currentAppUser).getRelationshipTypeList()) ||
                !(!filter.isLivesAlone() || currentAppUser.isLivesAlone()));

        //Remove AppUser From the list if it has already been liked
        filterResult.removeIf(currentAppUser ->
                notationService.getNotationListBySourceUser(appUser)
                        .stream()
                        .map(Notation::getTargetUser)
                        .collect(Collectors.toCollection(ArrayList::new))
                        .contains(currentAppUser));

        return filterResult;
    }
}
