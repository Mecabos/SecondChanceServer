package com.esprit.secondchanceserver.service;

import com.esprit.secondchanceserver.Util.DebugUtil;
import com.esprit.secondchanceserver.enumeration.GenderType;
import com.esprit.secondchanceserver.enumeration.RelationshipType;
import com.esprit.secondchanceserver.enumeration.StatusType;
import com.esprit.secondchanceserver.model.AppUser;
import com.esprit.secondchanceserver.model.Filter;
import com.esprit.secondchanceserver.repository.FilterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("filterService")
public class FilterServiceImpl implements FilterService {

    @Autowired
    private FilterRepository filterRepository;

    @Autowired
    private AppUserService appUserService;

    @Override
    public Filter findFilterByAppUser(AppUser appUser) {
        return filterRepository.findByAppUser(appUser);
    }

    @Override
    public void saveFilter(AppUser appUser) {
        Filter filter = new Filter();
        filter.setAppUser(appUser);

        if (appUser.getGender() == null || appUser.getGender() == GenderType.Man)
            filter.setGender(GenderType.Woman);
        else
            filter.setGender(GenderType.Man);
        filter.setMinAge(18);
        filter.setMaxAge(100);
        filter.setHasChildren(true);

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
                appUser.getId());
        //Remove element if they don't look for same kind of relationship
        filterResult.removeIf(currentAppUser -> Collections.disjoint(
                filter.getRelationshipTypeList(),
                findFilterByAppUser(currentAppUser).getRelationshipTypeList()));

        return filterResult;
    }
}
