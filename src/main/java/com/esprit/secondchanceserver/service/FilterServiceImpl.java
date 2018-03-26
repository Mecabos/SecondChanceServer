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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service("filterService")
public class FilterServiceImpl implements FilterService {

    @Autowired
    private FilterRepository filterRepository;

    @Override
    public Filter findFilterByAppUser(AppUser appUser) {
        return filterRepository.findByAppUser(appUser);
    }

    @Override
    public void saveFilter(AppUser appUser) {
        Filter filter = new Filter();
        filter.setAppUser(appUser);

        if(appUser.getGender() == null || appUser.getGender() == GenderType.Man)
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


}
